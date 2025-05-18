import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

private val EMPTY_SCREEN = List(SCREEN_WIDTH * SCREEN_HEIGHT) { 0 }.toPersistentList()

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
data class RegisterON(val type: Char, val idx: Int) {
    override fun toString(): String = "${type}x$idx"
}

data class ImmON(val value: String) {
    override fun toString(): String = value
}

data class FunctionPtrON(val name: String) {
    override fun toString(): String = "&$name"
}

data class LabelON(val name: String) {
    override fun toString(): String = name
}


sealed class PseudoInstruction

data class LabelPI(val label: String) : PseudoInstruction() {
    override fun toString(): String = "\t$label:"
}

data class MovePI(val dst: RegisterON, val src: OperandName) : PseudoInstruction() {
    override fun toString(): String = "\t\t$dst = $src"
}

data class RegularPI(val dst: RegisterON?, val mnemonic: String, val args: List<OperandName>) {
    override fun toString(): String = "\t\t" + (dst?.let { "$it = " } ?: "") + mnemonic + " " + args.joinToString(" ")
}

data class CallPI(val dst: RegisterON?, val name: String, val signature: Signature, val args: List<OperandName>) {
    override fun toString(): String =
        "\t\t" + (dst?.let { "$it = " } ?: "") + "call $name$signature " + args.joinToString(" ")
}

data class DyncallPI(val dst: RegisterON?, val ptr: RegisterON, val signature: Signature, val args: List<OperandName>) {
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
                    instr.map { it.parsePseudoInstruction() }
                )
            }
        }

    return ParsedFile(functions, imports)
}

private fun String.parseSignature(): Signature {
    require(matches(SIGNATURE_REGEX))


}


private fun String.parsePseudoInstruction(): PseudoInstruction {
    TODO()

}

