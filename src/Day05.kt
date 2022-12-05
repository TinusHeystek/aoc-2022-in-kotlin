class Day05 : Day(5) {

    class Instruction(val move: Int, val from: Int, val to: Int)

    // --- Part 1 ---

    private fun parseCrates(input: String, shouldReverseOrder: Boolean): String {
        val crateGroups = mutableMapOf<Int, MutableList<Char>>()
        val instructions = mutableListOf<Instruction>()

        for (line in input.lines()) {
            if (line.contains("[")) {
                // build crates
                for (index in 1 until line.toCharArray().size step 4) {
                    val crate = line[index]
                    if (crate == ' ')
                        continue

                    val crateIndex: Int = index.floorDiv(4)
                    if (crateGroups.containsKey(crateIndex)) {
                        crateGroups[crateIndex]?.add(crate)
                    } else {
                        crateGroups[crateIndex] = mutableListOf(crate)
                    }
                }
            } else if (line.startsWith("move")) {
                //move 1 from 2 to 1
                val move = line.substring(line.indexOf("move") + 5, line.indexOf("from") - 1)
                val from = line.substring(line.indexOf("from") + 5, line.indexOf("to") - 1)
                val to = line.substring(line.indexOf("to") + 3, line.length)

                instructions.add(Instruction(move.toInt(), from.toInt() -1, to.toInt() -1))
            }
        }

        for (instruction in instructions) {
            var moveCrates = crateGroups[instruction.from]?.take(instruction.move)
            if (moveCrates != null) {

                // Part 2
                if (shouldReverseOrder)
                    moveCrates = moveCrates.asReversed()

                for (moveCrate in moveCrates) {
                    crateGroups[instruction.to]?.add(0, moveCrate)
                    crateGroups[instruction.from]?.remove(moveCrate)
                }
            }
        }

        val outputChars = crateGroups.toSortedMap()
            .map { it.value.first() }

        return String(outputChars.toCharArray())
    }

    override fun part1ToString(input: String): String {
        return parseCrates(input, false)
    }

    // --- Part 2 ---

    override fun part2ToString(input: String): String {
        return parseCrates(input, true)
    }
}

fun main() {
    val day = Day05()
    day.printToStringResults("CMZ", "MCD")
}
