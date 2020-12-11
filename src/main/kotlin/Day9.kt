import java.io.File

class Day9 {

    private val preambleSize = 25
    private val numbers = processInput()

    private fun processInput(): List<Long> {
        val numbers = mutableListOf<Long>()
        File("src/main/resources/Day9.input").forEachLine {
            numbers.add(it.toLong())
        }
        return numbers
    }

    fun partOne(): Long {
        for (i in preambleSize until numbers.size) {
            if (!hasProperty(i))
                return numbers[i]
        }
        return -1
    }

    private fun hasProperty(index: Int): Boolean {
        val preamble = numbers.subList(index - preambleSize, index).sorted()
        for (i in preamble.indices) {
            val compliment = numbers[index] - preamble[i]
            if (preamble.contains(compliment) && preamble.lastIndexOf(compliment) != i) return true
        }
        return false
    }

    fun partTwo(): Long {
        val target = partOne()
        for (l in numbers.indices) {
            var rTo = l
            var sum: Long = 0

            while (rTo < numbers.size && sum < target) {
                sum += numbers[rTo]
                if (sum >= target) break
                rTo += 1
            }

            if (rTo == numbers.size || sum > target) continue
            else if ( sum == target && rTo == l) continue
            else return numbers.subList(l, rTo + 1).max()!!.plus(numbers.subList(l, rTo + 1).min()!!)
        }
        return -1
    }
}