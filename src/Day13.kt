import kotlinx.serialization.*
import kotlinx.serialization.json.*

class Day13 : Day(13) {

    private fun parseInput(input: String) : List<List<JsonElement>> {
        return input.lines()
            .filter { it.isNotEmpty() }
            .map { Json.decodeFromString<JsonElement>(it) }
            .chunked(2)
    }

    private fun compareValues(a: JsonElement, b: JsonElement): Int = when (a) {
        is JsonArray -> when (b) {
            is JsonArray -> a.zip(b)
                .map { (x, y) -> compareValues(x, y) }
                .find { it != 0 } ?: a.size.compareTo(b.size)
            is JsonPrimitive -> compareValues(a, buildJsonArray { add(b) })
            else -> error("unknown type")
        }
        is JsonPrimitive -> when (b) {
            is JsonArray -> compareValues(buildJsonArray { add(a) }, b)
            is JsonPrimitive -> a.int.compareTo(b.int)
            else -> error("unknown type")
        }
        else -> error("unknown type")
    }

    // --- Part 1 ---

    override fun part1ToInt(input: String): Int {
        val pairs = parseInput(input)

        return pairs.mapIndexed { ind, (a, b) -> ind + 1 to compareValues(a, b) }
            .filter { it.second == -1 }
            .sumOf { it.first }
    }

    // --- Part 2 ---

    override fun part2ToInt(input: String): Int {
        val pairs = parseInput(input)

        val divider1 = buildJsonArray { addJsonArray { add(2) } }
        val divider2 = buildJsonArray { addJsonArray { add(6) } }
        val pairsOfInterest = mutableListOf<JsonElement>(divider1, divider2)

        pairsOfInterest.addAll(pairs.flatten())
        pairsOfInterest.sortWith(::compareValues)

        return (pairsOfInterest.indexOf(divider1) + 1) * (pairsOfInterest.indexOf(divider2) + 1)
    }
}

fun main() {
    Day13().printToIntResults(13, 140)
}