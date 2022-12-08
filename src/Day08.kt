class Day08 : Day(8) {

    private fun parseInput(input: String) : Array<IntArray> {
        val lines = input.lines()
        val rows = lines.count()
        val col = lines[0].length
        val array: Array<IntArray> = Array(rows) { IntArray(col) }
        lines.forEachIndexed { index, line ->
            array[index] = line.map { it.digitToInt() }.toIntArray()
        }
        return array
    }

    private fun getSurroundingTrees(r : Int, c: Int, array: Array<IntArray>) : List<List<Int>> {
        val surroundingTrees = mutableListOf<List<Int>>()

        surroundingTrees.add(array[r].take(c).reversed())
        surroundingTrees.add(array[r].drop(c + 1))
        surroundingTrees.add(array.filterIndexed{ index, _ -> index < r}.map{ it[c] }.reversed())
        surroundingTrees.add(array.filterIndexed{ index, _ -> index > r}.map{ it[c] })

        return surroundingTrees
    }

    // --- Part 1 ---

    override fun part1ToInt(input: String): Int {
        val array = parseInput(input)
        var hiddenCount = 0

        for (r in 1 until array.size - 1) {
            for (c in 1 until array[0].size - 1) {
                val tree = array[r][c]
                val surroundingTrees = getSurroundingTrees(r, c, array)

                if (surroundingTrees.all {it.max() >= tree})
                    hiddenCount++
            }
        }

        return (array.size * array[0].size) - hiddenCount
    }

    // --- Part 2 ---

    override fun part2ToInt(input: String): Int {
        val array = parseInput(input)
        var largestScenicScore = 0

        for (r in 1 until array.size - 1) {
            for (c in 1 until array[0].size - 1) {
                val tree = array[r][c]
                val surroundingTrees = getSurroundingTrees(r, c, array)
                val distances = surroundingTrees.map { trees ->
                    if (trees.indexOfFirst { it >= tree} < 0)
                        trees.count()
                    else
                        trees.indexOfFirst { it >= tree} + 1
                }

                val scenicScore = distances.multiplyingOf { it }
                if (scenicScore > largestScenicScore)
                    largestScenicScore = scenicScore
            }
        }

        return largestScenicScore
    }
}

fun main() {
    val day = Day08()
    day.printToIntResults(21, 8)
}

inline fun <T> Iterable<T>.multiplyingOf(selector: (T) -> Int): Int {
    var total = 1
    for (element in this) {
        total *= selector(element)
    }
    return total
}
