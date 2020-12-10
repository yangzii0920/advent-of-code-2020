import java.io.File

class Day8 {

    private val instructions = processInput()

    private fun processInput(): List<Pair<String, String>> {
        val instructions = mutableListOf<Pair<String, String>>()
        File("src/main/resources/Day8.input").forEachLine {
            instructions.add(it.substringBefore(" ") to it.substringAfter(" "))
        }
        return instructions
    }

    fun partOne(): Int {
        val (acc, _) = scanning()
        return acc
    }

    @ExperimentalStdlibApi
    fun partTwo(): Int {
        var (acc, executed) = scanning()
        var i = executed.removeLast()
        while (i >= 0) {
            if (instructions[i].first == "acc") {
                acc -= instructions[i].second.toInt()
                i = executed.removeLast()
            }else {
                val (newIndex, newAcc) = continueScanning(instructions, executed, i, acc)
                if (newIndex < instructions.size)
                    i = executed.removeLast()
                else {
                    acc = newAcc
                    break
                }
            }
        }

        return acc
    }

    private fun scanning(): Pair<Int, MutableList<Int>> {
        var i = 0
        var acc = 0
        val executed = mutableListOf<Int>()
        while (i < instructions.size && !executed.contains(i)) {
            executed.add(i)
            when (instructions[i].first) {
                "acc" -> {
                    acc += instructions[i].second.toInt()
                    i ++
                }
                "jmp" -> i += instructions[i].second.toInt()
                else -> i ++
            }
        }
        return acc to executed
    }

    private fun continueScanning(
        rules: List<Pair<String, String>>,
        checked: List<Int>,
        index: Int,
        sum: Int): Pair<Int, Int> {

        val instructions = rules.toMutableList()
        val executed = checked.toMutableList()
        var i = index
        var acc = sum

        instructions[i] = if (instructions[i].first == "jmp") "nop" to instructions[i].second else "jmp" to instructions[index].second
        while (i < instructions.size && !executed.contains(i)) {
            executed.add(i)
            when (instructions[i].first) {
                "acc" -> {
                    acc += instructions[i].second.toInt()
                    i ++
                }
                "jmp" -> i += instructions[i].second.toInt()
                else -> i ++
            }
        }
        return i to acc
    }
}