package org.ldemetrios.simulator

import java.io.*

fun preprocess(
    input: String,
): String {
    // Build command: gcc -E -P -I<path>... -
    val cmd = listOf("gcc", "-E", "-P", "-")

    val process = ProcessBuilder(cmd).start()

    Thread {
        process.outputStream.bufferedWriter().use {
            it.write(input)
        }
    }.start()

    val output = process.inputStream.bufferedReader().readText()

    val error = process.errorStream.bufferedReader().readText()
    val exitCode = process.waitFor()

    if (exitCode != 0) {
        throw IOException("GCC preprocessing failed (exit $exitCode):\n$error")
    }

    return output.replace(";", "\n").replace(".", " ")
}
