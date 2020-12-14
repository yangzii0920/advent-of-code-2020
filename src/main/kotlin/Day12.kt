import java.io.File
import kotlin.math.absoluteValue

class Day12 {

    private var currentDirection = 'E'

    fun partOne(): Int {
        var (x, y) = 0 to 0

        File("src/main/resources/Day12.input").forEachLine {
            val (direction, distance) = it.first() to it.substring(1).toInt()
            when (direction) {
                'L' -> { currentDirection = currentDirection.turn(direction, distance) }
                'R' -> { currentDirection = currentDirection.turn(direction, distance) }
                else -> {
                    val updated = go(direction, distance, x, y)
                    x = updated.first
                    y = updated.second
                }
            }
        }

        return x.absoluteValue.plus(y.absoluteValue)
    }

    private fun go(direction: Char, distance: Int, x: Int, y: Int): Pair<Int, Int> {
        return when (direction) {
            'N' -> { x to y + distance }
            'S' -> { x to y - distance }
            'E' -> { x + distance to y}
            'W' -> { x - distance to y}
            else -> {
                go(currentDirection, distance, x, y)
            }
        }
    }

    fun partTwo(): Int {
        var (relativeX, relativeY) = 10 to 1
        var (x, y) = 0 to 0

        File("src/main/resources/Day12.input").forEachLine {
            val (direction, distance) = it.first() to it.substring(1).toInt()
            when (direction) {
                'F' -> {
                    x += distance * relativeX
                    y += distance * relativeY
                }
                'N' -> relativeY += distance
                'S' -> relativeY -= distance
                'E' -> relativeX += distance
                'W' -> relativeX -= distance
                else -> {
                    val (newX, newY) = (relativeX to relativeY).rotate(direction, distance)
                    relativeX = newX
                    relativeY = newY
                }
            }
        }

        return x.absoluteValue.plus(y.absoluteValue)
    }
}

fun Char.turn(turn: Char, degree: Int): Char {
    val directions = arrayOf('N', 'E', 'S', 'W')
    var indexOfDirection = directions.indexOf(this)
    indexOfDirection = if (turn == 'R') indexOfDirection + degree.rem(360)/ 90 else indexOfDirection - degree.rem(360) / 90
    if (indexOfDirection >= directions.size) {
        indexOfDirection = indexOfDirection.rem(directions.size)
    }
    while (indexOfDirection < 0) {
        indexOfDirection += 4
    }
    return directions[indexOfDirection]
}

fun Pair<Int, Int>.rotate(turn:Char, degree: Int): Pair<Int, Int> {
    val currentX = this.first
    val currentY = this.second
    val directions = arrayOf(
        currentX to currentY,
        currentY to -currentX,
        -currentX to -currentY,
        -currentY to currentX
    )
    var indexOfPosition = if (turn == 'R') degree.rem(360) / 90 else -degree.rem(360) / 90
    if (indexOfPosition >= directions.size) {
        indexOfPosition = indexOfPosition.rem(directions.size)
    }
    while (indexOfPosition < 0) {
        indexOfPosition += 4
    }
    return directions[indexOfPosition]
}