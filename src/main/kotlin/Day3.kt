import java.io.File

class Day3 {

    private val map = processInput()

    private fun processInput(): List<String> {
        val map = mutableListOf<String>()
        File("src/main/resources/Day3.input").forEachLine { line -> map.add(line) }
        return map
    }

    fun partOne(): Int {
        return map.filterIndexed { lineNum, line ->
            line[(3 * lineNum) % line.length] == '#'
        }.count()
    }

    fun partTwo(): Long {
        val slops = listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))
        return slops.map { (right, down) ->
            map.filterIndexed { lineNum, line ->
                line[(right * (lineNum / down)) % line.length] == '#' && lineNum % down == 0
            }.count().toLong()
        }.reduce { i, acc ->
            i * acc
        }
    }
}