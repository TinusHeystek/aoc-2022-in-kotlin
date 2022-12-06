class Day06 : Day(6) {

    private fun findMarker(input: String, markerLength: Int) : Int {
        val marker = input
            .toList()
            .windowed(markerLength)
            .first { it.distinct().count() == markerLength }
            .joinToString("")

        return input.indexOf(marker) + markerLength
    }

    // --- Part 1 ---

    override fun part1ToInt(input: String): Int {
        return findMarker(input, 4)
    }

    // --- Part 2 ---

    override fun part2ToInt(input: String): Int {
        return findMarker(input, 14)
    }
}

fun main() {
    val day = Day06()
    day.printToIntResults(7, 19)
}
