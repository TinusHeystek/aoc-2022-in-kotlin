package extensions.grid
import kotlin.math.abs
import kotlin.math.sign

typealias Point = Pair<Int, Int>
val Point.x: Int get() = first
val Point.y: Int get() = second
infix operator fun Point.plus(other: Point) = x + other.x to y + other.y
infix operator fun Point.minus(other: Point) = x - other.x to y - other.y
val Point.sign: Point get() = x.sign to y.sign

open class Node(val point: Point) {
    override fun toString(): String = "($point.x, $point.y)"
}

open class Grid<T>(val map: List<List<T>>) {
    val width = map[0].size
    val height = map.size

    private val signGrid = arrayOf(-1, 0, 1).flatMap { x -> arrayOf(-1, 0, 1).map { y -> x to y } }
    val directional8: List<Point> = signGrid.filter { it != 0 to 0 }
    val directional4: List<Point> = directional8.filter { abs(it.x) != abs(it.y) }

    fun getNode(point : Point) : T {
        return map[point.y][point.x]
    }

    private fun isValidGridPosition(point: Point) : Boolean
    {
        return point.x >= 0 && point.y >= 0 && point.x < width && point.y < height
    }

    fun getNeighbours(currentPoint: Point, directionalVectors: List<Point> = directional4): List<T> {
        val neighbours = mutableListOf<T>()
        for (neighbourVector in directionalVectors) {
            val neighbourPoint = currentPoint + neighbourVector
            if (isValidGridPosition(neighbourPoint))
                neighbours.add(getNode(neighbourPoint))
        }
        return neighbours
    }

    fun print() {
        map.forEach{ row -> println(row.joinToString(""))}
        println("")
    }
}