import java.io.File
import kotlin.math.max

class Day16 {

    private val rawRules = mutableListOf<Pair<Int, Int>>()
    private val ranges = mutableListOf<Pair<Int, Int>>()
    private val rangesByField = mutableMapOf<String, List<Pair<Int, Int>>>()

    private lateinit var myTicket: String
    private val otherTickets = mutableListOf<String>()
    private val numbersByCol = mutableMapOf<Int, List<Int>>()

    @ExperimentalStdlibApi
    constructor() {
        File("src/main/resources/Day16.input").forEachLine {
            when {
                it.contains("-") -> {
                    val name = it.substringBefore(":").trim()
                    val ranges = it.substringAfter(":").trim().split("or").map { range ->
                        val (min, max) = range.trim().split("-").map { num -> num.toInt() }
                        min to max
                    }.toList()
                    rawRules.addAll(ranges)
                    rangesByField[name] = ranges
                }
                it == "your ticket:" -> {
                    myTicket = ""
                }
                it.contains(",") && myTicket.isBlank() -> {
                    myTicket = it
                }
                it.contains(",") && myTicket.isNotBlank() -> {
                    otherTickets.add(it)
                    it.split(",").forEachIndexed { index, num ->
                        numbersByCol[index] = if (numbersByCol[index] == null) listOf(num.toInt()) else numbersByCol[index]!!.plus(num.toInt())
                    }
                }
            }
        }
        rawRules.sortBy { it.first }
        ranges.add(rawRules[0])
        rawRules.forEachIndexed { index, pair ->
            if (index > 0) {
                val (lastMin, lastMax) = ranges.last()
                val (min, max) = pair
                if (min in lastMin..lastMax + 1 && max >= lastMax) {
                    ranges.removeLast()
                    ranges.add(lastMin to max)
                }
                if (min > lastMax + 1) {
                    ranges.add(min to max)
                }
            }
        }
    }

    fun partOne(): Int {
        return otherTickets.sumBy { ticket ->
            val ticketSum = ticket.split(",").map { it.toInt() }.sumBy { num ->
                val fallInRange = ranges.find { range -> num >= range.first && num <= range.second }
                if (fallInRange == null) num
                else 0
            }
            ticketSum
        }
    }

    fun partTwo(): Int {
        val validTickets = otherTickets.filter { ticket ->
            ticket.split(",").map { it.toInt() }.count { num ->
                ranges.find { range -> num >= range.first && num <= range.second } == null
            } == 0
        }.toList()


        return -1
    }

    fun recursion(n: Int, mem: MutableMap<Int, Int>): Int {
        if (mem.containsKey(n)) {
            return mem[n]!!
        } else {
            mem[n] = mem[n - 1] ?: recursion(n - 1, mem) + mem[n - 2] ?: recursion(n - 2, mem)
            return mem[n]!!
        }
    }
}