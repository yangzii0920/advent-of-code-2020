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
        val times = input[1].split(",")
            .mapIndexed { index, id -> if (id == "x") -1L to index.toLong() else id.toLong() to index.toLong() }
            .filter { it.first != -1L }
        val prod = times.map { it.first }.reduce { acc, i -> acc * i }

        val found = chineseRemainderTheorem(prod, times.map { it.first }, times.map { if (it.second == 0L) 0 else it.first - (it.second.rem(it.first)) } )
        return found - found / prod * prod
    }

     private fun chineseRemainderTheorem(prod: Long, divisors: List<Long>, remainders: List<Long>): Long {
        return divisors.mapIndexed { i, divisor ->
            val n = prod / divisor
            var d = 1L
            while (d <= divisor) {
                if ((n * d).rem(divisor) == remainders[i]) {
                     break
                }
                d += 1
            }
            n * d
        }.sum()
    }

}