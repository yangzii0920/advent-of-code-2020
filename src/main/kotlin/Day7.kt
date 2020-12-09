import java.io.File

class Day7 {

    @ExperimentalStdlibApi
    fun partOne(): Int {
        val bags = mutableMapOf<String, Set<String>>()
        val regex = "^([a-z\\s]+) bags contain (no other bags|\\d+ ([a-z\\s]+) bag|\\d+ ([a-z\\s]+) bags)(, (\\d ([a-z\\s]+) bag|\\d ([a-z\\s]+) bags))?(, (\\d ([a-z\\s]+) bag|\\d ([a-z\\s]+) bags))?(, (\\d ([a-z\\s]+) bag|\\d ([a-z\\s]+) bags))?(, (\\d ([a-z\\s]+) bag|\\d ([a-z\\s]+) bags))?(, (\\d ([a-z\\s]+) bag|\\d ([a-z\\s]+) bags))?.\$".toRegex()
        File("src/main/resources/Day7.input").forEachLine { rule ->
            val colors = regex.find(rule)!!.groupValues.drop(1).filter { it.isNotEmpty() && !it.contains("\\d+".toRegex()) }.toList()
            colors.forEachIndexed { index, color ->
                if (index > 0) {
                    bags[color] = bags[color].orEmpty().plus(colors[0])
                }
            }
        }
        bags.remove("no other bags")

        val parents = bags.getOrElse("shiny gold") { setOf() }.toMutableList()
        val checked = mutableSetOf<String>()

        while (parents.isNotEmpty()) {
            val newColor = parents.removeLast()
            checked.add(newColor)
            bags[newColor]?.forEach {
                if (!checked.contains(it)) {
                    checked.add(it)
                    parents.add(it)
                }
            }
        }
        return checked.size
    }

    fun partTwo(): Int {
        val bags = mutableMapOf<String, Set<String>>()
        val regex = "^([a-z\\s]+) bags contain (no other bags|\\d+ [a-z\\s]+ bags*)(, (\\d+ [a-z\\s]+ bags*))?(, (\\d+ [a-z\\s]+ bags*))?(, (\\d [a-z\\s]+ bags*))?(, (\\d [a-z\\s]+ bags*))?.\$".toRegex()
        File("src/main/resources/Day7.input").forEachLine { rule ->
            val colors = regex.find(rule)!!.groupValues.drop(1).filter { it.isNotEmpty() && !it.contains(",") }.toList()
            bags[colors[0]] = if (colors[1] == "no other bags") setOf() else colors.drop(1).map { it.substringBefore(" bag") }.toSet()
        }
        return sumBag(bags, "shiny gold") - 1
    }

    private fun sumBag(bags: Map<String, Set<String>>, color: String): Int {
        return 1 + (bags[color]?.sumBy {
            val (quantity, child) = Pair(it.substringBefore(" "), it.substringAfter(" "))
            quantity.toInt() * sumBag(bags, child)
        } ?: 0)
    }
}
