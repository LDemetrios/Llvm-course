fun reify(funcs: List<ParsedFunction>): Program {
    val names = funcs.map(ParsedFunction::name).withIndex()
        .associate { (idx, value) -> value to idx + 1 }

    val reified: List<RuneFunction> = funcs.map { f ->
        reifyFunction(f, names)
    }
    return Program(
        reified.associateBy { it.name },
        reified,
    )
}


private fun reifyFunction(
    f: ParsedFunction,
    functions: Map<String, Int>,
): RuneFunction {
    val labels = mutableMapOf<String, Int>()
    var idx = 0
    for (instr in f.instr) when (instr) {
        is LabelPI -> labels[instr.label] = idx
        else -> idx++
    }

    val instructions = f.instr.mapNotNull { instr ->
        Reifier(instr, functions, labels).reify()?.also { it.repr = instr.toString() }
    }

    return RuneFunction(
        f.name,
        f.signature,
        instructions,
        labels,
    )
}

sealed class OperandVariant<T>
data class RegVar<T>(val reg: Reg) : OperandVariant<T>()
data class ImmVar<T>(val imm: T) : OperandVariant<T>()

fun String.parseIntegralOrNull(): Long? = when {
    Regex("[+-]?0b[01']+").matches(this) -> {
        val sign = if (this[0] == '-') "-" else ""
        val value = this.substringAfterLast('b').filter { it != '\'' }
        (sign + value).toLong(2)
    }

    Regex("[+-]?[0-9']+").matches(this) -> {
        filter { it != '\'' }.toLong()
    }

    Regex("0x[0-9a-fA-F']+").matches(this) -> {
        substring(2).filter { it != '\'' }.toULong(16).toLong()
    }

    Regex("[+-]?[0-9A-Za-z']_[0-9]+").matches(this) -> {
        val value = substringBefore('_').filter { it != '\'' }
        val base = substringAfter('_').toInt()
        value.toLong(base)
    }

    else -> null
}

fun String.parseIntegral() =
    parseIntegralOrNull() ?: throw NumberFormatException("`$this` is not a correct integral literal")

fun String.parseI(): Int = parseIntegral().toInt()
fun String.parseL(): Long {
    require(endsWith("L")) { "$this is not a long (should be a number ended by L)" }
    return removeSuffix("L").parseIntegral()
}

fun String.parseD() = toDouble()
fun String.parseF() = toFloat()

fun <A, B, C> flip(func: (A, B) -> C): (B, A) -> C = { b, a -> func(a, b) }
fun <A, B, C, D> flipAB(func: (A, B, C) -> D): (B, A, C) -> D = { b, a, c -> func(a, b, c) }
fun <A, B, C, D> flipBC(func: (A, B, C) -> D): (A, C, B) -> D = { a, c, b -> func(a, b, c) }
fun <A, B, C, D> flipAC(func: (A, B, C) -> D): (C, B, A) -> D = { c, b, a -> func(a, b, c) }

private class Reifier(
    val instr: PseudoInstruction,
    val functions: Map<String, Int>,
    val labels: Map<String, Int>
) { // It's a class to make inline functions possible
    fun incorrect(message: String): Nothing = error("Incorrect instruction `$instr`: $message")

    fun <T> OperandName?.opT(t: Char, parse: String.() -> T): OperandVariant<T> = when (this) {
        null -> incorrect("Expected destination register") // Only called on nullable .dst
        is FunctionPtrON -> if (t == 'L') ImmVar(functions[this.name]!!) as ImmVar<T>
        else incorrect("Function pointer $this has type L, not $t")

        is ImmON -> ImmVar(value.parse())
        is LabelON -> incorrect("Expected $t, received label $this")
        is RegisterON -> if (type == t) RegVar(idx) else incorrect("Expected $t, received $this")
    }

    fun OperandName?.opI(): OperandVariant<I> = opT('I') { parseI() }
    fun OperandName?.opL(): OperandVariant<L> = opT('L') { parseL() }
    fun OperandName?.opF(): OperandVariant<F> = opT('F') { parseF() }
    fun OperandName?.opD(): OperandVariant<D> = opT('D') { parseD() }

    inline fun <reified T> OperandName?.opTReified(): OperandVariant<T> = when (T::class.java) {
        I::class.javaObjectType -> opI() as OperandVariant<T>
        L::class.javaObjectType -> opL() as OperandVariant<T>
        F::class.javaObjectType -> opF() as OperandVariant<T>
        D::class.javaObjectType -> opD() as OperandVariant<T>
        else -> throw AssertionError("Unreachable (${T::class.java} ${I::class.java})")
    }

    fun unsupported(): Nothing = incorrect("Unexpected argument combination (imm/reg)`")

    fun <A, B> reifyVar2(
        rrVariant: ((Reg, Reg) -> Instruction)?,
        riVariant: ((Reg, B) -> Instruction)?,
        irVariant: ((A, Reg) -> Instruction)?,
        a: OperandVariant<A>,
        b: OperandVariant<B>,
    ): Instruction = when {
        a is RegVar<A> && b is RegVar<B> -> rrVariant?.invoke(a.reg, b.reg) ?: unsupported()
        a is RegVar<A> && b is ImmVar<B> -> riVariant?.invoke(a.reg, b.imm) ?: unsupported()
        a is ImmVar<A> && b is RegVar<B> -> irVariant?.invoke(a.imm, b.reg) ?: unsupported()
        else -> unsupported()
    }

    inline fun <reified A, reified B> reifyVar2dst(
        noinline rVariant: ((Reg, Reg) -> Instruction)?,
        noinline iVariant: ((A, Reg) -> Instruction)?,
    ): Instruction {
        val instr = this.instr as RegularPI
        require(instr.args.size == 1) { "Unexpected number of arguments" }
        val a: OperandVariant<A> = instr.args[0].opTReified<A>()
        val b: OperandVariant<B> = instr.dst.opTReified<B>()
        return reifyVar2(rVariant, null, iVariant, a, b)
    }

    fun <A, B, C> reifyVar3(
        rrrVariant: ((Reg, Reg, Reg) -> Instruction)?,
        rriVariant: ((Reg, Reg, C) -> Instruction)?,
        rirVariant: ((Reg, B, Reg) -> Instruction)?,
        riiVariant: ((Reg, B, C) -> Instruction)?,
        irrVariant: ((A, Reg, Reg) -> Instruction)?,
        iriVariant: ((A, Reg, C) -> Instruction)?,
        iirVariant: ((A, B, Reg) -> Instruction)?,
        a: OperandVariant<A>,
        b: OperandVariant<B>,
        c: OperandVariant<C>,
    ): Instruction = when {
        a is RegVar<A> && b is RegVar<B> && c is RegVar<C> -> rrrVariant?.invoke(a.reg, b.reg, c.reg) ?: unsupported()
        a is RegVar<A> && b is RegVar<B> && c is ImmVar<C> -> rriVariant?.invoke(a.reg, b.reg, c.imm) ?: unsupported()
        a is RegVar<A> && b is ImmVar<B> && c is RegVar<C> -> rirVariant?.invoke(a.reg, b.imm, c.reg) ?: unsupported()
        a is RegVar<A> && b is ImmVar<B> && c is ImmVar<C> -> riiVariant?.invoke(a.reg, b.imm, c.imm) ?: unsupported()
        a is ImmVar<A> && b is RegVar<B> && c is RegVar<C> -> irrVariant?.invoke(a.imm, b.reg, c.reg) ?: unsupported()
        a is ImmVar<A> && b is RegVar<B> && c is ImmVar<C> -> iriVariant?.invoke(a.imm, b.reg, c.imm) ?: unsupported()
        a is ImmVar<A> && b is ImmVar<B> && c is RegVar<C> -> iirVariant?.invoke(a.imm, b.imm, c.reg) ?: unsupported()
        else -> unsupported()
    }

    inline fun <reified A, reified B, reified C> reifyVar3dst(
        noinline rrVariant: ((Reg, Reg, Reg) -> Instruction)?,
        noinline riVariant: ((Reg, B, Reg) -> Instruction)?,
        noinline irVariant: ((A, Reg, Reg) -> Instruction)?,
    ): Instruction {
        val instr = this.instr as RegularPI
        require(instr.args.size == 2) { "Unexpected number of arguments" }
        val a: OperandVariant<A> = instr.args[0].opTReified<A>()
        val b: OperandVariant<B> = instr.args[1].opTReified<B>()
        val c: OperandVariant<C> = instr.dst.opTReified<C>()
        return reifyVar3(rrVariant, null, riVariant, null, irVariant, null, null, a, b, c)
    }

    inline fun <reified A> checkedGoto1(func: (Reg, Int) -> Instruction): Instruction {
        val instr = this.instr as RegularPI
        require(instr.args.size == 2) { "Unexpected number of arguments" }
        val a: RegVar<A> = instr.args[0].opTReified<A>() as? RegVar<A> ?: incorrect("First operand should be register")
        val b = instr.args[1] as? LabelON ?: incorrect("Second operand should be label")
        return func(a.reg, labels[b.name]!!)
    }

    fun reify() = when (instr) {
        is LabelPI -> null
        is MovePI -> when (val type = instr.dst.type) {
            'I' -> reifyVar2(::IMove_rr, null, ::IMove_ir, instr.src.opI(), instr.dst.opI())
            'L' -> reifyVar2(::LMove_rr, null, ::LMove_ir, instr.src.opL(), instr.dst.opL())
            'F' -> reifyVar2(::FMove_rr, null, ::FMove_ir, instr.src.opF(), instr.dst.opF())
            'D' -> reifyVar2(::DMove_rr, null, ::DMove_ir, instr.src.opD(), instr.dst.opD())
            else -> throw AssertionError("Unreachable")
        }

        is RegularPI -> when (instr.mnemonic.lowercase()) {
            "i2l" -> reifyVar2dst<I, L>(::I2L, null)
            "l2i" -> reifyVar2dst<L, I>(::L2I, null)
            "i2f" -> reifyVar2dst<I, F>(::I2F, null)
            "f2i" -> reifyVar2dst<F, I>(::F2I, null)
            "i2d" -> reifyVar2dst<I, D>(::I2D, null)
            "d2i" -> reifyVar2dst<D, I>(::D2I, null)
            "l2f" -> reifyVar2dst<L, F>(::L2F, null)
            "f2l" -> reifyVar2dst<F, L>(::F2L, null)
            "l2d" -> reifyVar2dst<L, D>(::L2D, null)
            "d2l" -> reifyVar2dst<D, L>(::D2L, null)
            "f2d" -> reifyVar2dst<F, D>(::F2D, null)
            "d2f" -> reifyVar2dst<D, F>(::D2F, null)
            "iadd" -> reifyVar3dst<I, I, I>(::IAdd_rr, ::IAdd_ri, flipAB(::IAdd_ri))
            "ladd" -> reifyVar3dst<L, L, L>(::LAdd_rr, ::LAdd_ri, flipAB(::LAdd_ri))
            "fadd" -> reifyVar3dst<F, F, F>(::FAdd_rr, ::FAdd_ri, flipAB(::FAdd_ri))
            "dadd" -> reifyVar3dst<D, D, D>(::DAdd_rr, ::DAdd_ri, flipAB(::DAdd_ri))
            "isub" -> reifyVar3dst<I, I, I>(::ISub_rr, ::ISub_ri, ::ISub_ir)
            "lsub" -> reifyVar3dst<L, L, L>(::LSub_rr, ::LSub_ri, ::LSub_ir)
            "fsub" -> reifyVar3dst<F, F, F>(::FSub_rr, ::FSub_ri, ::FSub_ir)
            "dsub" -> reifyVar3dst<D, D, D>(::DSub_rr, ::DSub_ri, ::DSub_ir)
            "imul" -> reifyVar3dst<I, I, I>(::IMul_rr, ::IMul_ri, flipAB(::IMul_ri))
            "lmul" -> reifyVar3dst<L, L, L>(::LMul_rr, ::LMul_ri, flipAB(::LMul_ri))
            "fmul" -> reifyVar3dst<F, F, F>(::FMul_rr, ::FMul_ri, flipAB(::FMul_ri))
            "dmul" -> reifyVar3dst<D, D, D>(::DMul_rr, ::DMul_ri, flipAB(::DMul_ri))
            "idiv" -> reifyVar3dst<I, I, I>(::IDiv_rr, ::IDiv_ri, ::IDiv_ir)
            "ldiv" -> reifyVar3dst<L, L, L>(::LDiv_rr, ::LDiv_ri, ::LDiv_ir)
            "fdiv" -> reifyVar3dst<F, F, F>(::FDiv_rr, ::FDiv_ri, ::FDiv_ir)
            "ddiv" -> reifyVar3dst<D, D, D>(::DDiv_rr, ::DDiv_ri, ::DDiv_ir)
            "irem" -> reifyVar3dst<I, I, I>(::IRem_rr, ::IRem_ri, ::IRem_ir)
            "lrem" -> reifyVar3dst<L, L, L>(::LRem_rr, ::LRem_ri, ::LRem_ir)
            "frem" -> reifyVar3dst<F, F, F>(::FRem_rr, ::FRem_ri, ::FRem_ir)
            "drem" -> reifyVar3dst<D, D, D>(::DRem_rr, ::DRem_ri, ::DRem_ir)
            "ineg" -> reifyVar2dst<I, I>(::INeg_rr, null)
            "lneg" -> reifyVar2dst<L, L>(::LNeg_rr, null)
            "fneg" -> reifyVar2dst<F, F>(::FNeg_rr, null)
            "dneg" -> reifyVar2dst<D, D>(::DNeg_rr, null)
            "il" -> reifyVar3dst<I, I, I>(::IL_rr, ::IL_ri, flipAB(::IG_ri))
            "ile" -> reifyVar3dst<I, I, I>(::ILe_rr, ::ILe_ri, flipAB(::IGe_ri))
            "ig" -> reifyVar3dst<I, I, I>(::IG_rr, ::IG_ri, flipAB(::IL_ri))
            "ige" -> reifyVar3dst<I, I, I>(::IGe_rr, ::IGe_ri, flipAB(::ILe_ri))
            "ieq" -> reifyVar3dst<I, I, I>(::IEq_rr, ::IEq_ri, flipAB(::IEq_ri))
            "ineq" -> reifyVar3dst<I, I, I>(::INeq_rr, ::INeq_ri, flipAB(::INeq_ri))
            "ll" -> reifyVar3dst<L, L, I>(::LL_rr, ::LL_ri, flipAB(::LG_ri))
            "lle" -> reifyVar3dst<L, L, I>(::LLe_rr, ::LLe_ri, flipAB(::LGe_ri))
            "lg" -> reifyVar3dst<L, L, I>(::LG_rr, ::LG_ri, flipAB(::LL_ri))
            "lge" -> reifyVar3dst<L, L, I>(::LGe_rr, ::LGe_ri, flipAB(::LLe_ri))
            "leq" -> reifyVar3dst<L, L, I>(::LEq_rr, ::LEq_ri, flipAB(::LEq_ri))
            "lneq" -> reifyVar3dst<L, L, I>(::LNeq_rr, ::LNeq_ri, flipAB(::LNeq_ri))
            "fl" -> reifyVar3dst<F, F, I>(::FL_rr, ::FL_ri, flipAB(::FG_ri))
            "fle" -> reifyVar3dst<F, F, I>(::FLe_rr, ::FLe_ri, flipAB(::FGe_ri))
            "fg" -> reifyVar3dst<F, F, I>(::FG_rr, ::FG_ri, flipAB(::FL_ri))
            "fge" -> reifyVar3dst<F, F, I>(::FGe_rr, ::FGe_ri, flipAB(::FLe_ri))
            "feq" -> reifyVar3dst<F, F, I>(::FEq_rr, ::FEq_ri, flipAB(::FEq_ri))
            "fneq" -> reifyVar3dst<F, F, I>(::FNeq_rr, ::FNeq_ri, flipAB(::FNeq_ri))
            "dl" -> reifyVar3dst<D, D, I>(::DL_rr, ::DL_ri, flipAB(::DG_ri))
            "dle" -> reifyVar3dst<D, D, I>(::DLe_rr, ::DLe_ri, flipAB(::DGe_ri))
            "dg" -> reifyVar3dst<D, D, I>(::DG_rr, ::DG_ri, flipAB(::DL_ri))
            "dge" -> reifyVar3dst<D, D, I>(::DGe_rr, ::DGe_ri, flipAB(::DLe_ri))
            "deq" -> reifyVar3dst<D, D, I>(::DEq_rr, ::DEq_ri, flipAB(::DEq_ri))
            "dneq" -> reifyVar3dst<D, D, I>(::DNeq_rr, ::DNeq_ri, flipAB(::DNeq_ri))
            "iand" -> reifyVar3dst<I, I, I>(::IAnd_rr, ::IAnd_ri, flipAB(::IAnd_ri))
            "ior" -> reifyVar3dst<I, I, I>(::IOr_rr, ::IOr_ri, flipAB(::IOr_ri))
            "ixor" -> reifyVar3dst<I, I, I>(::IXor_rr, ::IXor_ri, flipAB(::IXor_ri))
            "land" -> reifyVar3dst<L, L, L>(::LAnd_rr, ::LAnd_ri, flipAB(::LAnd_ri))
            "lor" -> reifyVar3dst<L, L, L>(::LOr_rr, ::LOr_ri, flipAB(::LOr_ri))
            "lxor" -> reifyVar3dst<L, L, L>(::LXor_rr, ::LXor_ri, flipAB(::LXor_ri))
            "iinv" -> reifyVar2dst<I, I>(::IInv_rr, null)
            "linv" -> reifyVar2dst<L, L>(::LInv_rr, null)
            "ishl" -> reifyVar3dst<I, I, I>(::IShl_rr, ::IShl_ri, ::IShl_ir)
            "ishr" -> reifyVar3dst<I, I, I>(::IShr_rr, ::IShr_ri, ::IShr_ir)
            "iushr" -> reifyVar3dst<I, I, I>(::IUshr_rr, ::IUshr_ri, ::IUshr_ir)
            "lshl" -> reifyVar3dst<L, I, L>(::LShl_rr, ::LShl_ri, ::LShl_ir)
            "lshr" -> reifyVar3dst<L, I, L>(::LShr_rr, ::LShr_ri, ::LShr_ir)
            "lushr" -> reifyVar3dst<L, I, L>(::LUshr_rr, ::LUshr_ri, ::LUshr_ir)
            "alloc" -> reifyVar2dst<L, L>(::Alloc_r, ::Alloc_i)
            "free" -> Free(
                (instr.args[0].opL() as? RegVar<L> ?: incorrect("Freeing is possible only by register")).reg
            )

            "bastore" -> reifyVar3(
                ::BAStore_rrr, ::BAStore_rri, ::BAStore_rir, ::BAStore_rii,
                null, null, null,
                instr.args[0].opL(), instr.args[1].opI(), instr.args[2].opI(),
            )

            "sastore" -> reifyVar3(
                ::SAStore_rrr, ::SAStore_rri, ::SAStore_rir, ::SAStore_rii,
                null, null, null,
                instr.args[0].opL(), instr.args[1].opI(), instr.args[2].opI(),
            )

            "iastore" -> reifyVar3(
                ::IAStore_rrr, ::IAStore_rri, ::IAStore_rir, ::IAStore_rii,
                null, null, null,
                instr.args[0].opL(), instr.args[1].opI(), instr.args[2].opI(),
            )

            "lastore" -> reifyVar3(
                ::LAStore_rrr, ::LAStore_rri, ::LAStore_rir, ::LAStore_rii,
                null, null, null,
                instr.args[0].opL(), instr.args[1].opI(), instr.args[2].opL(),
            )

            "fastore" -> reifyVar3(
                ::FAStore_rrr, ::FAStore_rri, ::FAStore_rir, ::FAStore_rii,
                null, null, null,
                instr.args[0].opL(), instr.args[1].opI(), instr.args[2].opF(),
            )

            "dastore" -> reifyVar3(
                ::DAStore_rrr, ::DAStore_rri, ::DAStore_rir, ::DAStore_rii,
                null, null, null,
                instr.args[0].opL(), instr.args[1].opI(), instr.args[2].opD(),
            )

            "baload" -> reifyVar3dst<L, I, I>(::BALoad_rr, ::BALoad_ri, null)
            "saload" -> reifyVar3dst<L, I, I>(::SALoad_rr, ::SALoad_ri, null)
            "iaload" -> reifyVar3dst<L, I, I>(::IALoad_rr, ::IALoad_ri, null)
            "laload" -> reifyVar3dst<L, I, L>(::LALoad_rr, ::LALoad_ri, null)
            "faload" -> reifyVar3dst<L, I, F>(::FALoad_rr, ::FALoad_ri, null)
            "daload" -> reifyVar3dst<L, I, D>(::DALoad_rr, ::DALoad_ri, null)

            "goto" -> Goto(
                labels[
                    (instr.args[0] as? LabelON ?: incorrect("Freeing is possible only by register")).name
                ]!!
            )

            "ifiz" -> checkedGoto1<I>(::IfIZ)
            "iflz" -> checkedGoto1<L>(::IfLZ)
            "iffz" -> checkedGoto1<F>(::IfFZ)
            "ifdz" -> checkedGoto1<D>(::IfDZ)
            "ifinz" -> checkedGoto1<I>(::IfInZ)
            "iflnz" -> checkedGoto1<L>(::IfLnZ)
            "iffnz" -> checkedGoto1<F>(::IfFnZ)
            "ifdnz" -> checkedGoto1<D>(::IfDnZ)

            "iret" -> IRet(
                (instr.args[0].opI() as? RegVar<I> ?: incorrect("Returning is possible only by register")).reg
            )

            "lret" -> LRet(
                (instr.args[0].opL() as? RegVar<L> ?: incorrect("Returning is possible only by register")).reg
            )

            "fret" -> FRet(
                (instr.args[0].opF() as? RegVar<F> ?: incorrect("Returning is possible only by register")).reg
            )

            "dret" -> DRet(
                (instr.args[0].opD() as? RegVar<D> ?: incorrect("Returning is possible only by register")).reg
            )

            "ret" -> Ret

            "putpxl" -> PutPxl(
                (instr.args[0].opI() as? RegVar<I> ?: incorrect("Putting pixel is possible only by register")).reg,
                (instr.args[1].opI() as? RegVar<I> ?: incorrect("Putting pixel is possible only by register")).reg,
                (instr.args[2].opI() as? RegVar<I> ?: incorrect("Putting pixel is possible only by register")).reg,
            )

            "scrflush" -> ScrFlush

            "rnd" -> Rnd(instr.dst?.idx ?: incorrect("Expected destination"))

            "brpoint" -> BrPoint

            else -> incorrect("Unknown mnemonic")
        }

        is CallPI -> StaticCall(instr.dst?.idx ?: -1, instr.name, instr.args.map { it.idx })
        is DyncallPI -> DynamicCall(
            instr.dst?.idx ?: -1,
            instr.ptr.idx,
            instr.args.map { it.idx },
        )
    }
}

