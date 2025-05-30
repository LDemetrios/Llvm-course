package org.ldemetrios.simulator

data class VFile(val path: String, val content: String)


val SPECIAL_CHARS = Regex("[\\[\\]{}()<>.,;:!@#$%^&*+=-]")

fun link(files: List<VFile>): List<ParsedFunction> {
    val parsed = files.flatMap { f ->
        val data = parse(f.content)

        val postfix = "@" + f.hashCode().toUInt().toString(16).padStart(8, ' ') +
                f.path.replace(SPECIAL_CHARS, "_")

        // Rename all private functions

        val privates = data.funcs
            .filterNot(ParsedFunction::public)
            .map(ParsedFunction::name)

        fun String.mapName() = if (this in privates) this + postfix else this

        fun OperandName.mapOperand() = if (this is FunctionPtrON) FunctionPtrON(this.name.mapName()) else this

        data.funcs.map {
            ParsedFunction(
                it.name.mapName(),
                it.public,
                it.signature,
                it.instr
                    .map {
                        when (it) {
                            is MovePI -> it.copy(src = it.src.mapOperand())
                            is RegularPI -> it.copy(args = it.args.map { it.mapOperand() })
                            is CallPI -> it.copy(name = it.name.mapName())
                            else -> it
                        }
                    }
            )
        }
    }

    // Ensure all functions are resolved

    val allNames = parsed.map(ParsedFunction::name).toSet()
    val allPtrs = parsed.flatMap { it.instr }
        .flatMap {
            when (it) {
                is CallPI -> listOf(it.name)
                is MovePI -> listOfNotNull((it.src as? FunctionPtrON)?.name)
                is RegularPI -> it.args.filterIsInstance<FunctionPtrON>().map { it.name }
                else -> listOf()
            }
        }
        .toSet()
    val unresolved = allPtrs - allNames
    require(unresolved.isEmpty()) {
        "Unresolved functions found: ${unresolved.joinToString(", ")}"
    }

    // Ensure signatures are met
    // 1. Calls happen according to the signature
    // 2. Argument registers are of according type
    // 3. Only allowed `return` instruction is used
    val signatures = parsed.associate { it.name to it.signature }
    parsed.forEach { function ->
        function.instr.forEach {
            when (it) {
                is CallPI -> {
                    val sig = signatures[it.name]!!
                    require(sig == it.signature) {
                        "Function ${it.name} called with signature ${it.signature}, expected $sig"
                    }
                    require(it.args.size == sig.args.length) {
                        "Function ${it.name} called with ${it.args.size} arguments, expected ${sig.args.length} ($it)"
                    }
                    it.args.forEachIndexed { idx, arg ->
                        val expected = sig.args[idx]
                        require(arg.type == expected) {
                            "Argument $idx of function ${it.name} should be of type $expected, but was ${arg.type} ($it)"
                        }
                    }
                }

                is MovePI -> {}

                is RegularPI -> if (it.mnemonic.lowercase() in listOf("iret", "lret", "fret", "dret", "ret")) {
                    val type = it.mnemonic[0].takeIf { it != 'r' }?.uppercase()[0]
                    require(type == function.signature.ret) {
                        "Function ${function.name} returns ${function.signature.ret}, but used ${it.mnemonic} ($it)"
                    }
                }

                is DyncallPI -> {
                    val sig = it.signature
                    require(it.args.size == sig.args.length) {
                        "${it.ptr} called with ${it.args.size} arguments, expected ${sig.args.length} ($it)"
                    }
                    it.args.forEachIndexed { idx, arg ->
                        val expected = sig.args[idx]
                        require(arg.type == expected) {
                            "Argument $idx of dynamically called ${it.ptr} should be of type $expected, but was ${arg.type} ($it)"
                        }
                    }
                }

                is LabelPI -> {}
            }
        }
    }

    return parsed
}