class Day04 : Day(4) {

    // --- Part 1 ---

    private fun getPair(pairString: String): Pair<Set<Int>, Set<Int>> {
        val split = pairString.trim().split(',')
        val left = split.first().split("-").let { (a, b) -> a.toInt()..b.toInt() }.toSet()
        val right = split.last().split("-").let { (a, b) -> a.toInt()..b.toInt() }.toSet()
        return Pair(left, right)
    }

    private fun pairIsFullyContained(pairString: String): Boolean {
        val pair = getPair(pairString)
        return pair.first.containsAll(pair.second) || pair.second.containsAll(pair.first)
    }

    override fun part1ToInt(input: String): Int {
        var total = 0
        val pairs = input.lines()
        for (pair in pairs) {
            if (pairIsFullyContained(pair))
                total++
        }
        return total
    }

    // --- Part 2 ---

    private fun pairOverlap(pairString: String): Boolean {
        val pair = getPair(pairString)
        return pair.first.intersect(pair.second).any()
    }

    override fun part2ToInt(input: String): Int {
        var total = 0
        val pairs = input.lines()
        for (pair in pairs) {
            if (pairOverlap(pair))
                total++
        }
        return total
    }
}

fun main() {
    val day = Day04()
    day.printToIntResults(2, 4)
}