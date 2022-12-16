package extensions.grid

import java.util.TreeSet

class Edge(val nodeFrom: String, val nodeTo: String, val distance: Int)

/** One vertex of the graph, complete with mappings to neighbouring vertices */
class Vertex(val nodeName: String) : Comparable<Vertex> {

    var distance = Int.MAX_VALUE  // MAX_VALUE assumed to be infinity
    var cameFromVertex: Vertex? = null
    val neighbours = HashMap<Vertex, Int>()

    override fun compareTo(other: Vertex): Int {
        if (distance == other.distance) return nodeName.compareTo(other.nodeName)
        return distance.compareTo(other.distance)
    }

    override fun toString() = "($nodeName, $distance)"
}

class Graph(
    val edges: List<Edge>,
    val directed: Boolean = true,
) {
    // mapping of vertex names to Vertex objects, built from a set of Edges
    private val graph = HashMap<String, Vertex>(edges.size)

    init {
        // one pass to find all vertices
        for (edge in edges) {
            if (!graph.containsKey(edge.nodeFrom)) graph[edge.nodeFrom] = Vertex(edge.nodeFrom)
            if (!graph.containsKey(edge.nodeTo)) graph[edge.nodeTo] = Vertex(edge.nodeTo)
        }

        // another pass to set neighbouring vertices
        for (edge in edges) {
            graph[edge.nodeFrom]!!.neighbours[graph[edge.nodeTo]!!] = edge.distance
            // also do this for an undirected graph if applicable
            if (!directed) graph[edge.nodeTo]!!.neighbours[graph[edge.nodeFrom]!!] = edge.distance
        }
    }

    /** Runs dijkstra using a specified source vertex */
    fun dijkstra(startNodeName: String) {
        if (!graph.containsKey(startNodeName)) {
            println("Graph doesn't contain start vertex '$startNodeName'")
            return
        }
        val source = graph[startNodeName]
        val queue = TreeSet<Vertex>()

        // set-up vertices
        for (vertex in graph.values) {
            vertex.cameFromVertex = if (vertex == source) source else null
            vertex.distance = if (vertex == source)  0 else Int.MAX_VALUE
            queue.add(vertex)
        }

        dijkstra(queue)
    }

    /** Implementation of dijkstra's algorithm using a binary heap */
    private fun dijkstra(queue: TreeSet<Vertex>) {
        while (!queue.isEmpty()) {
            // vertex with the shortest distance (first iteration will return source)
            val shortestDistanceVertex = queue.pollFirst()
            // if distance is infinite we can ignore 'u' (and any other remaining vertices)
            // since they are unreachable
            if (shortestDistanceVertex.distance == Int.MAX_VALUE) break

            //look at distances to each neighbour
            for (neighbour in shortestDistanceVertex.neighbours) {
                val vertex = neighbour.key // the neighbour in this iteration

                val alternateDist = shortestDistanceVertex.distance + neighbour.value
                if (alternateDist < vertex.distance) { // shorter path to neighbour found
                    queue.remove(vertex)
                    vertex.distance = alternateDist
                    vertex.cameFromVertex = shortestDistanceVertex
                    queue.add(vertex)
                }
            }
        }
    }

    fun findPath(startNodeName: String, endNodeName: String): List<String> {
        if (startNodeName == endNodeName)
            return listOf(startNodeName)

        dijkstra(startNodeName)
        if (!graph.containsKey(endNodeName)) {
            println("Graph doesn't contain end vertex '$endNodeName'")
            return listOf()
        }

        val pathEdges = mutableListOf<String>()
        pathEdges.add(graph[endNodeName]!!.nodeName)

        var cameFromVertex = graph[endNodeName]!!.cameFromVertex
        while (cameFromVertex != null) {
            pathEdges.add(cameFromVertex.nodeName)
            if (cameFromVertex.nodeName == startNodeName)
                break
            cameFromVertex = cameFromVertex.cameFromVertex
        }

        pathEdges.reverse()
        return pathEdges
    }
}