import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.floor

class Day09 : Day(9) {

    data class Vector2(val x: Int, val y: Int)
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
        val knots = Array(ropeLength) { Vector2(0, 0) }
        val grid = mutableMapOf<Vector2, Boolean>()

        grid[knots.last()] = true
        for (command in commands) {
            for (step in 1..command.amount) {
                knots[0] = Vector2(knots[0].x + command.directions.x, knots[0].y + command.directions.y)

                for (index in 1 until knots.count()) {
                    val distance = Vector2(knots[index -1].x- knots[index].x, knots[index -1].y - knots[index].y)
                    if (distance.x.absoluteValue > 1 || distance.y.absoluteValue > 1) {
                        val x = if (distance.x >= 0) { ceil(distance.x / 2.0).toInt() } else { floor(distance.x / 2.0).toInt() }
                        val y = if (distance.y >= 0) { ceil(distance.y / 2.0).toInt() } else { floor(distance.y / 2.0).toInt() }
                        knots[index] = Vector2(knots[index].x + x, knots[index].y + y)
                    } else {
                        break
                    }
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
    val day = Day09()
    day.printToIntResults(13, 1)
}
