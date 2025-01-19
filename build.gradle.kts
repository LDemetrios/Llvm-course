fun stat(width: Int, logSize: Int): Map<List<String>, Int> {
    val statistics = mutableMapOf<List<String>, Int>()
    val window = mutableListOf<String>()

    fun update(line: String): Boolean {
        val full = window.size == width
        if (full) window.removeFirst()
        window.add(line)
        return full
    }

    var ind = 0
    var pc = 0
    File("$rootDir/log.txt").forEachLine {
        if (ind / (logSize / 100) > pc) {
            pc = ind / (logSize / 100)
            println("\u001B[A\u001B[2K\r$pc%")
        }
        if (update(it)) {
            statistics.compute(window.toList()) { _, i -> (i ?: 0) + 1 }
        }
        ind++
    }
    println("\u001B[A\u001B[2K\r100%")
    return statistics
}


tasks.register("collect-stat") {
    doLast {
        var size = 0
        File("$rootDir/log.txt").forEachLine { size++ }

        for (i in 1..5) {
            println("Computing with window size = $i" + " ".repeat(20))
            println("0%")

            val data = stat(i, size)
                .entries
                .sortedByDescending { it.value }
            val freqLen = data[0].value.toString().length
            val commandLens = (0 until i).map { c -> data.maxOf { it.key[c].length } }
            File("$rootDir/stat/Statistics-$i.txt").run {
                if (!exists()) createNewFile()
                writeText(
                    data.joinToString("") {
                        it.value.toString().padStart(freqLen, ' ') +
                                " : " +
                                it.key.mapIndexed { ind, s -> s.padEnd(commandLens[ind], ' ') }
                                    .joinToString(", ") +
                                "\n"
                    }
                )
            }
        }
    }
}
