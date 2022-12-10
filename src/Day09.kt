import extensions.*
import kotlin.math.absoluteValue

class Day09 : Day(9) {

    class Command(line: String) {
        var directions = Vector2(0, 0)
        var amount = 0
        init {
            val split = line.split(" ")
            when (split[0]) {
                "R" -> directions = Vector2(1, 0)
                "U" -> directions = Vector2(0, 1)
                "L" -> directions = Vector2(-1, 0)
                "D" -> directions = Vector2(0, -1)
            }
            amount = split[1].toInt()
        }
    }

    private fun followTheRope(input: String, ropeLength: Int) : Int {
        val commands = input.lines().map {Command(it)}
        val knots = MutableList(ropeLength) { Vector2(0, 0) }
        val grid = mutableMapOf<Vector2, Boolean>()

        grid[knots.last()] = true
        for (command in commands) {
            repeat(command.amount) {
                knots[0] =  knots[0] + command.directions

                for (index in 1 until knots.count()) {
                    val distance = knots[index -1] - knots[index]
                    if (distance.x.absoluteValue > 1 || distance.y.absoluteValue > 1) {
                        knots[index] = knots[index] + distance.sign
                    } else break
                }

                grid[knots.last()] = true
            }
        }

        return grid.count { it.value }
    }

    // --- Part 1 ---

    override fun part1ToInt(input: String): Int {
        return followTheRope(input, 2)
    }

    // --- Part 2 ---

    override fun part2ToInt(input: String): Int {
        return followTheRope(input, 10)
    }
}

fun main() {
    Day09().printToIntResults(13, 1)
}
