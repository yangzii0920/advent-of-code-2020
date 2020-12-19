import java.io.File

class Day13 {

    fun partOne(): Int {
        val input = File("src/main/resources/Day13.input").readLines()
        val start = input[0].toInt()
        val times = "\\d+".toRegex().findAll(input[1]).map { it.value.toInt() }.toList()
        return times.map { id ->
            when (start.rem(id)) {
                0 -> id to start
                else -> id to (start / id + 1) * id
            }
        }.withIndex().minBy { (_, it) -> it.second }!!.value.toList().reduce {acc, i -> acc * (i - start) }
    }

    fun partTwo(): Long {
        val input = File("src/main/resources/Day13.input").readLines()
        val times = input[1].split(",").mapIndexed { index, id -> if (id == "x") -1 to index else id.toInt() to index }.filter { it.first != -1 }.sortedByDescending { it.first }
        var timestamp: Long = 0
        val step = times[0].first
        while (timestamp <= Long.MAX_VALUE) {
            if (times.all { (timestamp + (it.second - times[0].second)).rem(it.first).toInt() == 0 })
                return timestamp - times[0].second
            timestamp += step
        }
        return -1
    }
}