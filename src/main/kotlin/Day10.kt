import java.io.File

class Day10 {

    private val outlets = processInput()
    private val pcJoltage = outlets.last().plus(3)

    private fun processInput(): List<Int> {
        val outputJoltage = mutableListOf<Int>()
        outputJoltage.add(0)
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

    fun partTwo(): Long {

        return countPathsToNum(outlets.last())
    }

    private val counts = Array(outlets.size) { Long.MIN_VALUE }

    private fun countPathsToNum(num: Int): Long {
        val index = outlets.indexOf(num)
        if (index == -1) return 0
        if (index == 0) return 1

        if (counts[index] != Long.MIN_VALUE) return counts[index]
        val count = countPathsToNum(num - 1) + countPathsToNum(num - 2) + countPathsToNum(num - 3)
        counts[index] = count
        return count
    }

}
