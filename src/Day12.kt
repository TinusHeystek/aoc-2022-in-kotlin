import extensions.grid.*

class HeightNode(point: Point, val character: Char) : PathNode(point) {
    var elevation: Int = 0
    init {
        elevation = when (character) {
            'S' -> 'a' - 'a'
            'E' -> 'z' - 'a'
            else -> character - 'a'
        }
    }

    override fun toString(): String = "$point - $character[$elevation]"
}

typealias Heightmap = Triple<List<List<HeightNode>>, HeightNode, HeightNode>
val Heightmap.grid: List<List<HeightNode>> get() = first
val Heightmap.startNode: HeightNode get() = second
val Heightmap.endNode: HeightNode get() = third

class Day12 : Day(12) {

    private fun buildHeightmap (input: String) : Heightmap {
        val map = input.lines()
            .mapIndexed { y, row ->
                row.mapIndexed { x, character -> HeightNode(x to y, character) }
            }

        return Heightmap(map,
            map.flatten().first{ it.character == 'S' },
            map.flatten().first{ it.character == 'E' })
    }

    // --- Part 1 ---

    override fun part1ToInt(input: String): Int {
        val map = buildHeightmap(input)
        val grid = Grid(map.grid)
        val aStar = PathfindingAStar(grid, grid.directional4)

        val pathNodes = aStar.findPath(map.startNode.point, map.endNode.point) {
                currentNode, neighbourNode -> neighbourNode.elevation <= currentNode.elevation + 1 }

        return pathNodes.count() - 1
    }

    // --- Part 2 ---

    override fun part2ToInt(input: String): Int {
        val map = buildHeightmap(input)
        val grid = Grid(map.grid)
        val aStar = PathfindingAStar(grid, grid.directional4)

        val startPoints = map.grid.flatten().filter { it.character == 'a' }
        val allPaths = startPoints.map {
            aStar.findPath(it.point, map.endNode.point) {
                    currentNode, neighbourNode -> neighbourNode.elevation <= currentNode.elevation + 1 }
        }

        val shortestPath = allPaths.filter{ it.isNotEmpty() }.minOf { it.count() }

        return shortestPath - 1
    }
}

fun main() {
    Day12().printToIntResults(31, 29)
}









