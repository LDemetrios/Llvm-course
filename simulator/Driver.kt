import java.io.File

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.option

data class InterpreterArgs(
    val classpath: String?,
    val mainFunc: String?,
    val files: List<String>,
    val freeArgs: List<String>
)

class InterpreterArgsParser : CliktCommand(help = "A Kotlin CLI tool") {
    private val classpath by option(
        "--classpath",
        help = "Specify the classpath (optional)"
    )
    private val mainClass by option(
        "--main",
        help = "Specify the main class name (optional)"
    )
    private val files by argument(
        name = "FILES",
        help = "List of input files"
    ).multiple(true)

    lateinit var parsedArgs: InterpreterArgs

    override fun run() {
        // Capture arguments after "--" via context.extra
        parsedArgs = InterpreterArgs(
            classpath = classpath,
            mainFunc = mainClass,
            files = files,
            freeArgs = listOf() // currentContext.extra
        )
    }
}


@Suppress("KotlinUnreachableCode")
fun main(vararg args: String) {
    // Usage: [--claspath .jar] [--main name] files... -- args...
    val cli = InterpreterArgsParser()
    cli.main(args)

    val (program, memory) = init(cli.parsedArgs)


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
