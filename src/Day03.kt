class Day03 : Day(3) {

    private val priorities = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    // --- Part 1 ---

    private fun calculateRucksack(rucksack : String) : Int {
        val middle = rucksack.length / 2
        val compartment1 = rucksack.substring(0, middle).toCharArray()
        val compartment2 = rucksack.substring(middle).toCharArray()

        val intersecting = compartment1.intersect(compartment2.toSet())
        val duplicate = intersecting.first()

        return priorities.indexOf(duplicate)
    }

    override fun part1ToInt(input: String): Int {
        var total = 0
        val rucksacks = input.lines()
        for (rucksack in rucksacks) {
            total += calculateRucksack(rucksack)
        }
        return total
    }

    // --- Part 2 ---

    private fun calculateRucksackGroup(group: List<String>) : Int {
        var intersecting = group.first().toCharArray().toSet()
        for (rucksack in group.subList(1, group.count())) {
            intersecting = intersecting.intersect(rucksack.toCharArray().toSet())
        }

        val duplicate = intersecting.first()

        return priorities.indexOf(duplicate)
    }

    override fun part2ToInt(input: String): Int {
        var total = 0
        val rucksacks = input.lines()
        val groups = rucksacks.chunked(3)
        for (group in groups) {
            total += calculateRucksackGroup(group)
        }
        return total
    }
}

fun main() {
    val day = Day03()
    day.printToIntResults(157, 70)
}