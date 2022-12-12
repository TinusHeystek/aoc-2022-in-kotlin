package extensions.grid

import kotlin.math.*

open class PathfindingAStar<T: PathNode>(val grid: Grid<T>, val directionalVectors: List<Point>) {
    companion object {
        const val MOVE_STRAIGHT_COST = 10
        const val MOVE_DIAGONAL_COST = 14
    }

    fun findPath(startPoint: Point, endPoint: Point,
        additionalCondition: (T, T) -> Boolean = { _, _ -> true }
    ): List<T> {
        val openList = mutableListOf<T>()
        val closedList = mutableListOf<T>()

        resetPathNodes()

        val startNode = grid.getNode(startPoint)
        val endNode = grid.getNode(endPoint)
        openList.add(startNode)

        startNode.setGCost(0)
        startNode.setHCost(calculateDistance(startPoint, endPoint))

        while (openList.isNotEmpty()) {
            val currentNode = getLowestFCostPathNode(openList)

            if (currentNode == endNode) { // Reached final Node
                return calculatePath(endNode)
            }

            openList.remove(currentNode)
            closedList.add(currentNode)

            val neighbourNodes = grid.getNeighbours(currentNode.point)
            for (neighbourNode in neighbourNodes) {
                if (closedList.contains(neighbourNode)) {
                    continue
                }

                if (!additionalCondition(currentNode, neighbourNode))
                {
                    // closedList.add(neighbourNode)
                    continue
                }

                val tentativeGCost = currentNode.getGCost() +
                        calculateDistance(currentNode.point, neighbourNode.point)

                if (tentativeGCost < neighbourNode.getGCost()) {
                    neighbourNode.setCameFromPathNode(currentNode)
                    neighbourNode.setGCost(tentativeGCost)
                    neighbourNode.setHCost(calculateDistance(neighbourNode.point, endPoint))

                    if (!openList.contains(neighbourNode)) {
                        openList.add(neighbourNode)
                    }
                }
            }
        }

        return listOf()  // No Path found
    }

    private fun resetPathNodes() {
        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
            grid.getNode( x to y).initialize()
            }
        }
    }

    private fun calculateDistance(gridPositionA: Point, gridPositionB: Point): Int {
        val gridPositionDistance = gridPositionA - gridPositionB
        val xDistance = abs(gridPositionDistance.x)
        val yDistance = abs(gridPositionDistance.y)
        val remainingDistance = abs(xDistance - yDistance)
        return MOVE_DIAGONAL_COST * min(xDistance, yDistance) + MOVE_STRAIGHT_COST * remainingDistance
    }

    private fun getLowestFCostPathNode(pathNodes: List<T>): T {
        return pathNodes.minBy { node -> node.getFCost() }
    }

    private fun calculatePath(endNode: T): List<T> {
        val pathNodes = mutableListOf<T>()
        pathNodes.add(endNode)

        var cameFromPathNode = endNode.getCameFromPathNode()
        while (cameFromPathNode != null) {
            pathNodes.add(cameFromPathNode as T)
            cameFromPathNode = cameFromPathNode.getCameFromPathNode()
        }

        pathNodes.reverse()
        return pathNodes
    }
}

/*
    G = Walking Cost from the Start Node
    H = Heuristic Cost to reach End Node
    F = G + H

    Open List = Nodes queued up for searching
    Closed List = Nodes that have already been searched

    Keep going until
        Current Node == End Node
    Or
        Open List is Empty
*/