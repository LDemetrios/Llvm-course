import RegisterON
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

val EMPTY_SCREEN = List(SCREEN_WIDTH * SCREEN_HEIGHT) { 0 }.toPersistentList()

fun Memory(
    heap: PersistentHeap,
    main: RuneFunction,
    argc: Int, argv: Long, // Pointer
) = Memory(
    heap,
    persistentListOf(main to Frame().settingI(0, argc).settingL(0, argv)),
    EMPTY_SCREEN, EMPTY_SCREEN
)

sealed class OperandName
data class RegisterON(val type: Char, val idx: Int) : OperandName() {
    override fun toString(): String = "${type}x$idx"
}

data class ImmON(val value: String) : OperandName() {
    override fun toString(): String = value
}

data class FunctionPtrON(val name: String) : OperandName() {
    override fun toString(): String = "&$name"
}

data class LabelON(val name: String) : OperandName() {
    override fun toString(): String = name
}


sealed class PseudoInstruction

data class LabelPI(val label: String) : PseudoInstruction() {
    override fun toString(): String = "\t$label:"
}

data class MovePI(val dst: RegisterON, val src: OperandName) : PseudoInstruction() {
    override fun toString(): String = "\t\t$dst = $src"
}

data class RegularPI(val dst: RegisterON?, val mnemonic: String, val args: List<OperandName>) : PseudoInstruction() {
    override fun toString(): String = "\t\t" + (dst?.let { "$it = " } ?: "") + mnemonic + " " + args.joinToString(" ")
}

data class CallPI(val dst: RegisterON?, val name: String, val signature: Signature, val args: List<RegisterON>) :
    PseudoInstruction() {
    override fun toString(): String =
        "\t\t" + (dst?.let { "$it = " } ?: "") + "call $name$signature " + args.joinToString(" ")
}

data class DyncallPI(val dst: RegisterON?, val ptr: RegisterON, val signature: Signature, val args: List<RegisterON>) :
    PseudoInstruction() {
    override fun toString(): String =
        "\t\t" + (dst?.let { "$it = " } ?: "") + "call $ptr $signature " + args.joinToString(" ")
}

data class ParsedFunction(
    val name: String,
    val public: Boolean,
    val signature: Signature,
    val instr: List<PseudoInstruction>
) {
    override fun toString(): String = buildString {
        append("func ")
        if (!public) append("#")
        append(name)
        append(signature)
        append("\n")
        instr.forEach { append(it); append("\n") }
    }
}

data class ParsedFile(val funcs: List<ParsedFunction>, val imports: Map<String, Signature>)

val NAME_REGEX = Regex("[a-zA-Z_][a-zA-Z0-9_]*")
val REG_REGEX = Regex("[ILFD]x[0-9]+")
val HEADER_REGEX = Regex("#?[a-zA-Z_][a-zA-Z0-9_]*\\([ILFD,]*\\)(:[ILFD])?")
val SIGNATURE_REGEX = Regex("\\([ILFD,]*\\)(:[ILFD])?")

fun parse(file: String): ParsedFile {
    val lines = file.lines()
        .map { it.split("//")[0] }
        .filter { it.isNotBlank() }
    val imports = lines
        .takeWhile { !it.startsWith("func") }
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .onEach { require(it.startsWith("import ")) { "Unexpected line: `$it`, only imports or func declarations are allowed" } }
        .map { it.removePrefix("import").trim() }
        .associate {
            val name = it.substringBefore('(').trim()
            require(name.matches(NAME_REGEX)) { "Incorrect name `$name`, should match /[a-zA-Z_][a-zA-Z0-9_]*/ " }
            require(!name.matches(REG_REGEX)) { "Incorrect name `$name`, names of form /[ILFD]x[0-9]+/ are reserved for registers" }
            val sig = it.substring(name.length).trim().parseSignature()
            name to sig
        }

    val functions = lines.indices
        .filter { lines[it].startsWith("func") }
        .let { it + lines.size }
        .let { starts ->
            List(starts.size - 1) {
                val header = lines[starts[it]].removePrefix("func").filter { !it.isWhitespace() }
                val instr = lines.subList(starts[it] + 1, starts[it + 1])
                require(header.matches(HEADER_REGEX)) { "Invalid function header `$header`" }
                val name = header.substringBefore("(")
                ParsedFunction(
                    name.removePrefix("#"),
                    name[0] != '#',
                    header.substring(name.length).parseSignature(),
                    instr.map(String::parsePseudoInstruction)
                )
            }
        }

    // Check for duplicate function names
    val functionNames = (functions.map { it.name } + imports.keys)

    val duplicates = functionNames.groupingBy { it }.eachCount().filter { it.value > 1 }
    require(duplicates.isEmpty()) {
        duplicates.entries.joinToString("\n") { (name, count) ->
            "Duplicate name `$name` found $count times"
        }
    }

    // Check all labels are resolvable
    functions.forEach {
        val labels = it.instr.filterIsInstance<LabelPI>().map { it.label }
        val mentioned = it.instr
            .filterIsInstance<RegularPI>()
            .flatMap(RegularPI::args)
            .filterIsInstance<LabelON>()
            .map(LabelON::name)
        val unresolved = mentioned - labels
        require(unresolved.isEmpty()) {
            "Function `${it.name}` has unresolved labels: ${unresolved.joinToString(", ")}"
        }
    }

    // Check all functions are resolvable

    functions.forEach { func ->
        val calls = func.instr.filterIsInstance<CallPI>().map { it.name }
        val pointers = func.instr
            .flatMap {
                when (it) {
                    is MovePI -> listOf(it.dst)
                    is RegularPI -> it.args
                    else -> emptyList()
                }
            }
            .filterIsInstance<FunctionPtrON>()
            .map(FunctionPtrON::name)
        val unresolved = calls + pointers - functionNames
        require(unresolved.isEmpty()) {
            "Function `${func.name}` has unresolved function mentions: ${unresolved.joinToString(", ")}"
        }
    }

    return ParsedFile(functions, imports)
}

private fun String.parseSignature(): Signature {
    require(matches(SIGNATURE_REGEX)) { "`$this` is not signature" }

    val args = substringBefore(":").filter { it in "ILFD" }
    val ret = substringAfter(":").takeIf { it.length == 1 }
    return Signature(args, ret?.get(0))
}

private fun String.parsePseudoInstruction(): PseudoInstruction {
    val value = trim()
    if (value.endsWith(':')) {
        val ident = value.removeSuffix(":")
        require(ident.matches(NAME_REGEX)) { "$ident is not correct label identifier" }
        return LabelPI(ident)
    }
    val parts = split('=').map { it.trim() }

    val (dst, instr) = when (parts.size) {
        1 -> null to parts[0]
        2 -> parts[0] to parts[1]
        else -> throw AssertionError("More than one equal sign in `$this`")
    }
    val instrParts = instr.split(Regex("[ \t\r\n]+"))
    return if (instrParts.size == 1) {
        val it = instrParts[0]
        when {
            REG_REGEX.matches(it) -> MovePI(
                dst?.parseReg() ?: throw AssertionError("Single register is not a correct instruction"),
                it.parseReg()
            ) as PseudoInstruction

            NAME_REGEX.matches(it) -> RegularPI(dst?.parseReg(), it, listOf())

            it.startsWith("&") -> {
                MovePI(
                    dst?.parseReg() ?: throw AssertionError("Single literal is not a correct instruction"),
                    FunctionPtrON(it.removePrefix("&"))
                )
            }

            else -> MovePI(
                dst?.parseReg() ?: throw AssertionError("Single literal (or whatever `$it` is) is not a correct instruction"),
                ImmON(it)
            )
        }
    } else {
        when (val mnemonic = instrParts[0]) {
            "call" -> {
                CallPI(
                    dst?.parseReg(),
                    instrParts[1],
                    instrParts[2].parseSignature(),
                    instrParts.drop(3).map { it.parseReg() }
                )
            }

            "dyncall" -> {
                DyncallPI(
                    dst?.parseReg(),
                    instrParts[1].parseReg(),
                    instrParts[2].parseSignature(),
                    instrParts.drop(3).map { it.parseReg() }
                )
            }

            else -> {
                RegularPI(
                    dst?.parseReg(),
                    mnemonic,
                    instrParts.drop(1).map { it.parseON() }
                )
            }
        }
    }
}

fun String.parseReg(): RegisterON {
    require(matches(REG_REGEX)) {"`$this` is not a register"}
    val (ch, idx) = split("x")
    return RegisterON(ch[0], idx.toInt())
}

fun String.parseON() = when {
    REG_REGEX.matches(this) -> parseReg()
    NAME_REGEX.matches(this) -> LabelON(this)
    startsWith("&") -> FunctionPtrON(drop(1))
    else -> ImmON(this)
}

