import java.util.SequencedMap

data class Program(
    val functions: Map<String, RuneFunction>,
    val functionByPointer: List<RuneFunction>
)


data class Signature(val args: String, val ret: Char?) {
    override fun toString(): String = args.toList().joinToString(",", "(", ret?.let { "):$it" } ?: ")")
}


data class RuneFunction(
    val name: String,
    val signature: Signature,
    val instructions: List<Instruction>,
    val labels: Map<String, Int>,
)

sealed class Instruction(
    val programStep: context(Program) Memory.() -> Memory
) {
    var repr: String? = null
    override fun toString(): String = repr ?: buildString {
        append(this@Instruction.javaClass.simpleName)
        append("(")
        this@Instruction.javaClass
            .declaredFields
            .map { it.get(this@Instruction) }
            .joinToString(", ")
            .let(::append)
        append(")")
    }
}

fun Memory.withLastFrame(transform: context(RuneFunction) Frame.() -> Frame): Memory {
    val last = frameStack.last()
    val func = last.first
    return copy(
        frameStack = frameStack.set(
            frameStack.lastIndex,
            func to with(func) { last.second.transform() }
        )
    )
}

sealed class FLInst(
    val step: context(RuneFunction) Frame.() -> Frame
) : Instruction({
    withLastFrame { step() }
})
