import java.io.File

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.option
import kotlin.system.exitProcess

data class InterpreterArgs(
    val classpath: String?,
    val mainFunc: String?,
    val files: List<String>,
    val disableDisplay: Boolean,
    val disableBacktracking: Boolean,
    val freeArgs: List<String>
)

enum class Action {
    StepOver, StepIn, StepOut, StepBackward, Resume;

    companion object {
        fun of(l: String) = when (l) {
            "s" -> StepOver
            "i" -> StepIn
            "o" -> StepOut
            "b" -> StepBackward
            "r" -> Resume
            else -> null
        }
    }
}

fun main(vararg args: String) {
    Class.forName("clojure.lang.RT") // Force static initializer
    // Usage: [--claspath .jar] [--main name] [--disable-display] [--no-backtracking] files... -- args...
    val arguments = parseArgs(args)

    val (program, memory) = init(arguments)

    var resumingUntilLevel: Int = 0

    var history: MutableList<Memory>? =
        if (arguments.disableBacktracking) null else mutableListOf<Memory>(memory) // Maybe replace with logarithmic dropping?
    var state: Memory = memory
    var defaultAction = Action.StepOver
    var steppingBack = false
    val canvas = if (arguments.disableDisplay) null else CanvasFrame()
    outer@ while (true) {
        if (resumingUntilLevel >= state.frameStack.size) {
            if (!steppingBack) history?.add(state)
            steppingBack = false
            dump(program, state)

            var action: Action
            while (true) {
                print("> ")
                val line = readLine()
                action = when {
                    line == null -> {
                        println("Input closed. Resuming automatically.")
                        resumingUntilLevel = 0
                        history = null
                        continue@outer
                    }

                    line == "exit" -> {
                        println("Exiting...")
                        exitProcess(0)
                    }

                    line.startsWith("def ") -> {
                        val toSet = Action.of(line.substring(4))
                        if (toSet == null) {
                            println("Unknown action: $line")
                            continue
                        }

                        defaultAction = toSet
                        continue
                    }

                    line == "" -> defaultAction
                    else -> {
                        val toSet = Action.of(line)
                        if (toSet == null) {
                            println("Unknown action: $line")
                            continue
                        }

                        toSet
                    }
                }
                if (action == Action.StepBackward && arguments.disableBacktracking) {
                    println("Can't step back: backtracking is disabled")
                }
                break
            }

            resumingUntilLevel = when (action) {
                Action.StepOver -> state.frameStack.size
                Action.StepIn -> Int.MAX_VALUE
                Action.StepOut -> state.frameStack.size - 1
                Action.StepBackward -> {
                    history!!.removeLast()
                    state = history.last()
                    Int.MAX_VALUE
                    steppingBack = true
                    canvas?.screen = state.display
                    continue@outer
                }

                Action.Resume -> 0
            }
        }

        val instruction = state.frameStack.last().let { (func, frame) ->
            func.instructions[frame.ip]
        }

        try {
            val func: context(Program) Memory.() -> Memory = instruction.programStep
            state = with(program) { state.func() }.withLastFrame { stepping() }
        } catch (e: AssertionError) {
            println(e)
            println("Not advancing last instruction")
            resumingUntilLevel = Int.MAX_VALUE
        } catch (e: TerminationException) {
            println("Simulated program finished with exit code ${e.code}")
            exitProcess(0)
        }

        when (instruction) {
            BrPoint -> {
                resumingUntilLevel = state.frameStack.size
            }

            ScrFlush -> {
                canvas?.screen = state.display
            }

            else -> {}
        }
    }
}

fun dump(program: Program, state: Memory) {
    println("====================================")
    println("Heap: ${state.heap.allocated} allocated in total")
    for ((idx, func) in program.functionByPointer.withIndex()) {
        println("${idx + 1} -> ${func.name} ${func.signature}")
    }
    for ((addr, bytes) in state.heap.heap) {
        val bytesStr = bytes.joinToString(" ") { it.toUByte().toString(16).padStart(2, '0') }
        println(
            "  $addr: $bytesStr"
        )
    }
    for ((func, frame) in state.frameStack) {
        println(func.name + ":")
        dumpRegisters<I>("I", frame.iReg) { it.toString() }
        dumpRegisters<L>("L", frame.lReg) { it.toString() }
        dumpRegisters<F>("F", frame.fReg) { "%.6g".format(it) }
        dumpRegisters<D>("D", frame.dReg) { "%.6g".format(it) }

        println("  Instructions:")
        val ip = frame.ip
        val min = maxOf(ip - 5, 0)
        val max = minOf(ip + 6, func.instructions.size)
        for (i in min until max) {
            func.labels.filter { it.value == i }.forEach { println("  $it:") }
            if (i == ip) print("  -> ") else print("     ")
            println(func.instructions[i])
        }
    }
}

fun <T> dumpRegisters(prefix: String, list: List<T?>, formatter: (T) -> String) {
    val rows = list.indices.filter { list[it] != null }.map { it / 4 }.toSortedSet()
    if (rows.isEmpty()) return
    println("  $prefix registers")
    for (row in rows) {
        fun value(idx: Int) = prefix + "x" + (row * 4 + idx).toString().padEnd(4, ' ') +
                " = " + (list[row * 4 + idx]?.let(formatter) ?: "<uninit>").padEnd(20, ' ')
        println("    ${value(0)}; ${value(1)}; ${value(2)}; ${value(3)}")
    }
}

const val usage = """
Usage: <launcher> [--main <main function name>] [--classpath <jar file>] FILES... [-- PROGRAM ARGS...] 
     | <launcher> --help
     
<launcher>                The executable, e.g. java -jar simulator.jar
--main | -m               Name of the entry point function. Defaults to `main`. 
                          The function should have signature (I,L):I or (I,L).
--classpath | -cp         Jar file with native JVM functions to be linked with the program. (Not supported yet)
--disable-display | -d    Do not show display
--no-backtracking | -nb   Do not store previous memory state (makes stepping back impossible)
FILES                     Assembly files.
PROGRAM ARGS              Arguments to pass to main. The semantics are the same as in C: 
                          I argc, L argv (array of null-terminated strings).
--help | -h               Print help.
                    
"""

const val help = """
You may use the following commands when the program is paused on breakpoint:
s -- step forward ("over")
i -- step forward ("into" function call)
o -- step forward ("out" of the function)
b -- step backwards (to the frame previously stopped on)
r -- resume computation until breakpoint
<nothing> -- perform default action
def <letter> -- set default action (is set to `s` by default)
"""

inline fun check(cond: Boolean, message: () -> String) {
    if (!cond) {
        println(message())
        println(
            usage
        )
        exitProcess(1)
    }
}

fun parseArgs(args: Array<out String>): InterpreterArgs {
    if (args.size == 1 && (args[0] == "--help" || args[0] == "-h")) {
        println(usage)
        println(help)
        exitProcess(0)
    }
    val freeArgsStart = args.indices
        .firstOrNull { args[it] == "--" }
    val freeArgs = freeArgsStart
        ?.let { args.asList().subList(it + 1, args.size) }
        ?: listOf()
    val regularArgs = freeArgsStart?.let { args.take(freeArgsStart) } ?: args.asList()
    val files = mutableListOf<String>()
    var main: String? = null
    var classpath: String? = null
    var disableDisplay = false
    var disableBacktracking = false
    var i = 0
    while (i < args.size) {
        when (val opt = args[i]) {
            "--main", "-m" -> {
                check(main != null) { "Duplicate --main option" }
                check(i + 1 < args.size) { "Required value for --main option" }
                main = args[i + 1]
                i++
            }

            "--classpath", "-cp" -> {
                check(classpath != null) { "Duplicate --classpath option" }
                check(i + 1 < args.size) { "Required value for --main option" }
                classpath = args[i + 1]
                i++
            }

            "--disable-display", "-d" -> {
                disableDisplay = true
            }

            "--no-backtracking", "-nb" -> {
                disableBacktracking = true
            }

            "--help", "-h" -> {
                println("Warning: `--help` is interpreted as a filename. Use it solely to get help.")
                files.add(opt)
            }

            else -> {
                if (opt[0] == '-') {
                    println("Unknown option `$opt`, interpreting as a filename")
                }
                files.add(opt)
            }
        }
        i++
    }

    return InterpreterArgs(
        classpath = classpath,
        mainFunc = main,
        files = files,
        disableDisplay = disableDisplay,
        disableBacktracking = disableBacktracking,
        freeArgs = freeArgs,

        )
}

fun init(args: InterpreterArgs): Pair<Program, Memory> {
    val classpath: File? = args.classpath?.let(::File)
    require(classpath == null) { "Linking JVM functions is not supported yet" }
    val files: List<File> = args.files.map(::File)
    val main: String = args.mainFunc ?: "main"
    val programArguments: List<String> = args.freeArgs
    require(files.isNotEmpty()) { "Files should be present" }

    val paths = files.map {
        it.absolutePath.split(File.separatorChar)
    }
    val minSize = paths
        .minOf { it.size }
    val rootPrefix = (0 until minSize)
        .firstOrNull { idx -> paths.map { it[idx] }.toSet().size != 1 }
        ?: minSize

    val vfiles = List(files.size) {
        VFile(paths[it].drop(rootPrefix).joinToString("/"), files[it].readText())
    }
    val program: Program = reify(link(vfiles))

    val padForFunction = program.functions.size
    val argBytes = programArguments
        .map { it.toByteArray() + 0.toByte() }

    var (argv, heap) = PersistentHeap(padForFunction.toLong())
        .alloc(programArguments.size * 8L)

    for ((i, arg) in argBytes.withIndex()) {
        val (ptr, nh) = heap.alloc(arg)
        heap = nh.store(argv + i * 8L, ptr.toBytes())
    }

    val mainF = program.functions[main] ?: throw AssertionError("Not found function $main")
    require(mainF.signature.args == "IL") { "Main function should accept exactly two arguments: argc (I) and argv (L)" }
    val memory = Memory(heap, mainF, programArguments.size, argv)
    return program to memory
}
