import extensions.grid.*

class Day14 : Day(14) {

    private val directionalLower3: List<Point> = listOf(0 to 1, -1 to 1, 1 to 1)

    enum class CavePointType {
        ROCK, AIR, SAND, SPAWN_POINT
    }

    class CaveNode(point: Point) : Node(point) {
        var type = CavePointType.AIR

        override fun toString(): String = when(type) {
            CavePointType.ROCK -> "#"
            CavePointType.SAND -> "o"
            CavePointType.SPAWN_POINT -> "+"
            else -> "."
        }
    }

    private fun parseInput(input: String): List<List<Point>> {
        return input.lines()
            .map {coordinates -> coordinates.split(" -> ")
                .map {
                    val points = it.split(",")
                    Pair(points[0].toInt(), points[1].toInt() )
                }
            }
    }

    private fun buildGrid(points: List<List<Point>>, addFloor: Boolean = false): Pair<Grid<CaveNode>, Int> {
        var topLeft = 0 to 0
        var bottomRight = 0 to 0
        points.flatten()
            .also { point ->
                val extraSpace = if (addFloor) point.maxOf { it.y } else 1
                topLeft = point.minOf { it.x } - extraSpace to point.minOf { it.y }
                bottomRight = point.maxOf { it.x } + extraSpace to point.maxOf { it.y } + 1 + (if (addFloor) 1 else 0)
            }

        val size = bottomRight - (topLeft.x to 0)

        val map = MutableList(size.y + 1) { row -> MutableList(size.x + 1) { col -> CaveNode(col to row) } }
        val grid: Grid<CaveNode> = Grid(map)

        points.forEach { p -> p.windowed(2, 1)
                .forEach { pair ->
                    for (row in pair.minOf { it.y } until pair.maxOf { it.y } + 1) {
                        for (col in pair.minOf { it.x } until pair.maxOf { it.x } + 1) {
                            grid.getNode(col - topLeft.x to row).type = CavePointType.ROCK
                        }
                    }
                }
        }

        if (addFloor) {
            map.last().forEach{ it.type = CavePointType.ROCK}
        }

        return (grid to topLeft.x)
    }

    private fun checkNeighboursNodes(grid: Grid<CaveNode>, currentNode: CaveNode) : Boolean {
        val neighbours = grid.getNeighbours(currentNode.point, directionalLower3)
        if (neighbours.isEmpty())
            return false

        for (node in neighbours) {
            if (node.type == CavePointType.AIR) {
                val seekNodes = grid.getNeighbours(node.point, directionalLower3)
                if (seekNodes.isEmpty())
                    return false
                if (seekNodes.all { it.type != CavePointType.AIR }) {
                    node.type = CavePointType.SAND
                    return true
                }
                return checkNeighboursNodes(grid, node)
            }
        }
        return false
    }

    private fun countSandFalling(input: String, addFloor: Boolean): Int {
        val points = parseInput(input)
        val (grid, minX) = buildGrid(points, addFloor)

        val spawnNode = grid.getNode(500 - minX to 0)
        spawnNode.type = CavePointType.SPAWN_POINT

        while (checkNeighboursNodes(grid, spawnNode)) {
            // grid.print()
        }
        grid.print()
        return grid.map.flatten().count {it.type == CavePointType.SAND}
    }

    // --- Part 1 ---

    override fun part1ToInt(input: String): Int {
        return countSandFalling(input, false)
    }

    // --- Part 2 ---

    override fun part2ToInt(input: String): Int {
        return countSandFalling(input, true) + 1
    }
}

fun main() {
    Day14().printToIntResults(24, 93)
}