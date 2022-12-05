import java.io.File

open class Day(private val dayNumber: Int) {

    open fun part1ToInt(input: String): Int {
        return -1
    }

    open fun part2ToInt(input: String): Int {
        return -1
    }

    open fun part1ToString(input: String): String {
        return "not implemented"
    }

    open fun part2ToString(input: String): String {
        return "not implemented"
    }

    private fun readTestInputForDay(dayNumber: Int): String {
        val dayString = dayNumber.toString().padStart(2, '0')
        return File("src/inputs" , "Day${dayString}_test.txt").readText()
    }

    private fun readInputForDay(dayNumber: Int): String {
        val dayString = dayNumber.toString().padStart(2, '0')
        return File("src/inputs" , "Day${dayString}.txt").readText()
    }

    fun printToIntResults(part1TestResults: Int, past2TestResults: Int = -1)
    {
        val testInput = readTestInputForDay(dayNumber)
        val input = readInputForDay(dayNumber)

        println("--- Part 1 ---")
        part1ToInt(testInput).also {
            println("  Test Result: [$it]")
            check(it == part1TestResults)
        }
        println("  Live Result: [${part1ToInt(input)}]")

        if (past2TestResults == -1)
            return

        println("--- Part 2 ---")
        part2ToInt(testInput).also {
            println("  Test Result: [$it]")
            check(it == past2TestResults)
        }
        println("  Live Result: [${part2ToInt(input)}]")
    }

    fun printToStringResults(part1TestResults: String, past2TestResults: String = "")
    {
        val testInput = readTestInputForDay(dayNumber)
        val input = readInputForDay(dayNumber)

        println("--- Part 1 ---")
        part1ToString(testInput).also {
            println("  Test Result: [$it]")
            check(it == part1TestResults)
        }
        println("  Live Result: [${part1ToString(input)}]")

        if (past2TestResults.isEmpty())
            return

        println("--- Part 2 ---")
        part2ToString(testInput).also {
            println("  Test Result: [$it]")
            check(it == past2TestResults)
        }
        println("  Live Result: [${part2ToString(input)}]")
    }
}