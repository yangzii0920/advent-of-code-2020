import java.io.File

class Day4 {

    val passports = processInput()

    private fun processInput(): List<String> {
        var oneLine = ""
        File("src/main/resources/Day4.input").forEachLine { line ->
            oneLine = if (line.isEmpty()) oneLine.plus(", ") else oneLine.plus(" $line")
        }
        return oneLine.split(",")
    }

    fun partOne(): Int {
        val fields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
        return passports.count {passport ->
            passport.split(" ").map { pair -> pair.substringBefore(":") }.containsAll(fields)
        }
    }

    fun partTwo(): Int {
        val rules = mapOf(
            "byr" to "^19[2-9]\\d|200[0-2]$",
            "iyr" to "^201\\d|2020$",
            "eyr" to "^202\\d|2030$",
            "hgt" to "^15\\dcm|1[6-8]\\dcm|19[0-3]cm|59in|6\\din|7[0-6]in$",
            "hcl" to "^#[\\da-f]{6}$",
            "ecl" to "^amb|blu|brn|gry|grn|hzl|oth$",
            "pid" to "^\\d{9}$",
            "cid" to "^/(?=a)b/$")
        return passports.count {passport ->
            passport.trim().split(" ").count { pair ->
                rules.getValue(pair.substringBefore(":")).toRegex().matches(pair.substringAfter(":"))
            } >= 7
        }
    }
}