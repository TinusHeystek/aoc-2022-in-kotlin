package day00

import Day

class Day00 : Day(0) {

    // --- Part 1 ---

    override fun part1ToInt(input: String): Int {
        return input.lines().size
    }

    // --- Part 2 ---

    override fun part2ToInt(input: String): Int {
        return input.lines().size
    }
}

fun main() {
    val day = Day00()
    day.printToIntResults(1, 1)
}
