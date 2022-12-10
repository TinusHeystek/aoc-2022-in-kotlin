class Day10 : Day(10) {

    private fun getCycles(input: String) : List<Int> {
        val lines = input.lines()
        val cycles = mutableListOf<Int>()
        cycles.add(1)
        lines.forEach{
            when (it.substringBefore(" ")) {
                "noop" -> cycles.add(cycles.last())
                "addx" -> {
                    cycles.add(cycles.last())
                    cycles.add(cycles.last() + it.substringAfter(" ").toInt())
                }
            }
        }
        return cycles
    }

    // --- Part 1 ---

    override fun part1ToInt(input: String): Int {
        val cycles = getCycles(input)
        return cycles.drop(19)
            .windowed(1, 40)
            .withIndex()
            .sumOf { it.value.first() * ((it.index * 40) + 20) }
    }

    // --- Part 2 ---

    override fun part2ToString(input: String): String {
        val cycles = getCycles(input)
        val pixels = cycles
            .windowed(40, 40)
            .map {it.mapIndexed { index, cycle ->
                if (cycle >= index -1 && cycle <= index +1) '#' else '.'                }
            }

        return pixels.joinToString("\n") { it.joinToString("") }
    }
}

fun main() {
    Day10().printToIntResults(13140)
    Day10().printToStringResults("not implemented", """
##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######.....""".trimIndent())
}
