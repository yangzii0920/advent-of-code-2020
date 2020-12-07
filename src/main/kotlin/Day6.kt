import java.io.File

class Day6 {

    private val answers = processInput()

    private fun processInput(): List<String> {
        var group = ""
        val allAnswers = mutableListOf<String>()
        File("src/main/resources/Day6.input").forEachLine {
            group = if (it.isEmpty()) {
                allAnswers.add(group)
                ""
            } else {
                if (group.isEmpty()) it else group.plus(",$it")
            }
        }
        allAnswers.add(group)
        return allAnswers
    }

    fun partOne(): Int {
        return answers.sumBy { it.replace(",", "").toSet().size }
    }

    fun partTwo(): Int {
        return answers.sumBy { groupAnswer ->
            val groupSize = groupAnswer.trim().split(",").size
            groupAnswer.replace(",","").groupBy { it }.values.filter { aggregated -> aggregated.size == groupSize }.count()
        }
    }
}