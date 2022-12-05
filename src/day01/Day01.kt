package day01

import Day

class Day01 : Day(1) {

    // --- Part 1 ---

    private fun getElvesCalories(input: String): List<Int> {
        val newLine = System.lineSeparator()
        return input.split("$newLine$newLine")
            .map { e -> e.split(newLine).sumOf { c -> c.toInt() } }
    }

    override fun part1ToInt(input: String): Int {
        val elves = getElvesCalories(input)
        return elves.max()
    }

    // --- Part 2 ---

    override fun part2ToInt(input: String): Int {
        val elves = getElvesCalories(input)
        return elves.sorted().takeLast(3).sum()
    }
}

fun main() {
    val day = Day01()
    day.printToIntResults(24000, 45000)
}
