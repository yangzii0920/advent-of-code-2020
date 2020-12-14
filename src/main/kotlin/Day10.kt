import java.io.File

class Day10 {

    private val outlets = processInput()
    private val pcJoltage = outlets.last().plus(3)

    private fun processInput(): List<Int> {
        val outputJoltage = mutableListOf<Int>()
        File("src/main/resources/Day10.input").forEachLine {
            outputJoltage.add(it.toInt())
        }
        return outputJoltage.sorted()
    }

    fun partOne(): Int {
        val diff = IntArray(4)
        var prev = 0
        outlets.forEach {
            diff[it - prev] += 1
            prev = it
        }
        diff[pcJoltage - prev] += 1
        return diff[1] * diff[3]
    }

    fun partTwo(): Int {
        return -1

    }
}