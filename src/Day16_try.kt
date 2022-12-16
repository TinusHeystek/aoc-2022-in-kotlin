import extensions.grid.*
import kotlin.math.max

class Day16Try : Day(16) {

    class Valve(val input: String) {
        val name: String
        val flowRate: Int
        val connectedVales: List<String>
        var isOpen: Boolean = false
        var openedOnXMinutesLeft : Int = 0

        init {
            name = input.substring(6, 8)
            val split = input.substringAfter("flow rate=").split(";")
            flowRate = split[0].toInt()
            connectedVales = split[1].split(" ").drop(5).map { it.replace(",", "") }
        }

        fun eventualPressure(withMinutes: Int): Int {
            return flowRate * withMinutes
        }

        fun openValve(timer: Int): Int {
            isOpen = true
            openedOnXMinutesLeft = timer
            return 1
        }

        override fun toString(): String {
            return "$name - Flow: $flowRate - IsOpen: $isOpen - OpenedOnXMinutesLeft: $openedOnXMinutesLeft"
        }
    }

    private fun parseInput(input: String) : List<Valve> {
        return input.lines()
            .map { Valve(it) }
    }

    private fun getBestFlowRateValve(valves: List<Valve>, graph: Graph, currentValve : Valve, timer: Int, ): Valve? {
        val top2 = valves
            .filter { !it.isOpen && it.flowRate > 0 }
            .map {
                val timeTo = graph.findPath(currentValve.name, it.name).count()
                val count = it.eventualPressure(max(0, timer - timeTo)) / timeTo
                Triple(it, count, timeTo)
            }
            .sortedByDescending {it.second }
            .take(2)

        if (top2.count() < 2)
            return top2.firstOrNull()?.first

        val t1 = graph.findPath(top2[0].first.name, top2[1].first.name).count()
        val t1Total = top2[1].first.eventualPressure(max(0, timer - top2[0].third - t1)) / t1

        val t2 = graph.findPath(top2[1].first.name, top2[0].first.name).count()
        val t2Total = top2[0].first.eventualPressure(max(0, timer - top2[1].third - t2)) / t2

        if (top2[0].second + t1Total > top2[1].second + t2Total)
            return top2[0].first
        else
            return top2[1].first

        // return top2.first()
        //val v1 = top2[0].eventualPressure()
    }

    // --- Part 1 ---

    override fun part1ToInt(input: String): Int {
        val valves = parseInput(input)
        val edges = valves.map { p -> p.connectedVales.map { Edge(p.name, it, 1) } }
            .flatten();

        val graph = Graph(edges);

        var timer = 30
        var currentValve = valves.first {it.name == "AA"}
        var bestValve : Valve? = getBestFlowRateValve(valves, graph, currentValve, timer)
        while (bestValve != null && timer > 0) {
            val aa = graph.findPath(currentValve.name, bestValve.name)
            timer -= aa.count()
            currentValve = bestValve
            //timer -=
                bestValve.openValve(timer)
            bestValve = getBestFlowRateValve(valves, graph, currentValve, timer)
        }

        val totalEventualPressure = valves
            .sumOf { it.eventualPressure(it.openedOnXMinutesLeft) }
        return totalEventualPressure
    }

    // --- Part 2 ---

    override fun part2ToInt(input: String): Int {
        return input.lines().size
    }
}

fun main() {
    Day16Try().printToIntResults(1651)
}
