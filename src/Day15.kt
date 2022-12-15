import extensions.grid.*
import kotlin.math.abs

class Day15 : Day(15) {
    data class SensorInfo(val sensor: Point, val beacon: Point)

    private fun parseInput(input: String): List<SensorInfo> {
        return input.lines()
            .map { line -> line.filter { it.isDigit() || it == '-' || it == '='} }
            .map { line ->
                val split = line.substring(1).split("=").map { it.toInt() }
                SensorInfo(split[0] to split[1], split[2] to split[3])
            }
    }

    private fun manhattanDistance(point1: Point, point2: Point): Int {
        val distance = point2 - point1
        return abs(distance.x) + abs(distance.y)
    }

    // --- Part 1 ---

    override fun part1ToString(input: String): String {
        val sensorInfo = parseInput(input)
        val level =  (if (sensorInfo.count() < 15) 10 else 2000000)

        val covered = mutableSetOf<Pair<Int, Int>>()
        sensorInfo.forEach { info ->
            val distance = manhattanDistance(info.sensor, info.beacon)
            for (x in (info.sensor.x - distance)..(info.sensor.x + distance)) {
                val point = Pair(x, level)
                if (point != info.beacon && manhattanDistance(info.sensor, point) <= distance) {
                    covered.add(point)
                }
            }
        }
        return covered.size.toString()
    }

    // --- Part 2 ---

    override fun part2ToString(input: String): String {
        val sensorInfo = parseInput(input)
        val maxLevel = (if (sensorInfo.count() < 15) 20 else 4_000_000)

        val sensors = sensorInfo.map { info ->
            info.sensor to manhattanDistance(info.sensor, info.beacon)
        }.sortedBy { (sensor, _) -> sensor.x }
        for (y in 0..maxLevel) {
            var x = 0
            sensors.forEach { (sensor, distance) ->
                if (manhattanDistance(sensor, Pair(x, y)) <= distance) {
                    x = sensor.x + distance - abs(sensor.y - y) + 1
                }
            }
            if (x <= maxLevel) {
                return (x * 4_000_000L + y).toString()
            }
        }

        error("Point not found")
    }
}

fun main() {
    Day15().printToStringResults("26", "56000011")
}
