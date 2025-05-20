import org.jline.reader.LineReaderBuilder
import org.jline.reader.UserInterruptException
import org.jline.reader.EndOfFileException
import org.jline.terminal.TerminalBuilder
import kotlin.random.Random

fun main() {
    println("AAA")
    Thread.sleep(1000)
    print("\u001B[1A") // move cursor up
        print("\u001B[2K") // clear the line
    print("B")
//    val info = mutableListOf("Initial status")
//    val rnd = Random(System.nanoTime())
//    while (true) {
//        print("\u001B[H") // move cursor up
//        print("\u001B[2K") // clear the line
//        println(info.joinToString("\n"))
//        print("> ")
//        val line = readln().trim()
//        if (line.equals("exit", ignoreCase = true)) break
//
//        // Execute a "command"
////        if (rnd.nextInt(3) < 2) {
//            info += "Executed: $line at ${System.currentTimeMillis()}"
////        } else if (info.isNotEmpty()) {
////            info.removeFirst()
////        }
//    }
//    val terminal = TerminalBuilder.terminal()
//    val reader = LineReaderBuilder.builder()
//        .terminal(terminal)
//        .build()
//
//    val info = mutableListOf("Initial status")
//    val rnd = Random(System.nanoTime())
//
//    while (true) {
//        try {
//            // Clear previous info line
//            terminal.writer().print("\u001B[1F") // move cursor up
//            terminal.writer().print("\u001B[2K") // clear the line
//            terminal.writer().flush()
//
//            // Print updated info
//            terminal.writer().println(info.joinToString("\n"))
//            terminal.writer().flush()
//
//            // Read user input
//            val line = reader.readLine("> ").trim()
//            if (line.equals("exit", ignoreCase = true)) break
//
//            // Execute a "command"
//            if (rnd.nextBoolean()) {
//                info += "Executed: $line at ${System.currentTimeMillis()}"
//            } else if (info.isNotEmpty()) {
//                info.removeFirst()
//            }
//        } catch (e: UserInterruptException) {
//            // Ctrl+C
//            break
//        } catch (e: EndOfFileException) {
//            // Ctrl+D
//            break
//        }
//    }
//
//    terminal.writer().println("Bye!")
//    terminal.flush()
}
