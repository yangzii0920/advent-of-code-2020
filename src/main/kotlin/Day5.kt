import java.io.File

class Day5 {

    val seats = processInput()

    private fun processInput(): List<String> {
        val seats = mutableListOf<String>()
        File("src/main/resources/Day5.input").forEachLine { seats.add(it) }
        return seats
    }

    fun partOne(): Int {
        return seatToId().max()!!
    }

    fun partTwo(): Int {
        val ids = seatToId()
        for (id in 0 * 8 + 7..127 * 8 + 0) {
            if (!ids.contains(id) && ids.contains(id + 1) && ids.contains(id - 1)) {
                return id
            }
        }
        return -1
    }

    private fun seatToId(): List<Int> {
        return seats.map { seat ->
            var f = 0
            var b = 127
            var l = 0
            var r = 7
            seat.forEach { character ->
                if (character == 'F') { b = (f + b + 1) / 2 - 1 }
                if (character == 'B') { f = (f + b + 1) / 2 }
                if (character == 'L') { r = (l + r + 1) / 2 - 1 }
                if (character == 'R') { l = (l + r + 1) / 2 }
            }
            f * 8 + r
        }.toList()
    }
}