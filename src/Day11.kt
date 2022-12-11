class Day11 : Day(11) {

    class Monkey(input: String) {
        var items = mutableListOf<Long>()
        var divisibleBy = 0
        var divisibleTrueToMonkey = 0
        var divisibleFalseToMonkey = 0
        var numberOfItemsThrown : Int = 0

        init {
            val lines = input.lines()

            items = lines[1].substringAfter("Starting items: ").split(", ").map { it.toLong() }.toMutableList()
            divisibleBy = lines[3].substringAfter("Test: divisible by ").toInt()
            divisibleTrueToMonkey = lines[4].substringAfter("If true: throw to monkey ").toInt()
            divisibleFalseToMonkey = lines[5].substringAfter("If false: throw to monkey ").toInt()
        }
    }

//    private fun doOperationAction(index: Int, old: Long) : Long {
//        var result: Long = 0
//        when (index) {
//            0 -> result = old * 7
//            1 -> result = old + 7
//            2 -> result = old * 3
//            3 -> result = old + 3
//            4 -> result = old * old
//            5 -> result = old + 8
//            6 -> result = old + 2
//            7 -> result = old + 4
//        }
//        return result
//    }

    // Test
    private fun doOperationAction(index: Int, old: Long) : Long {
        var result: Long = 0
        when (index) {
            0 -> result = old * 19
            1 -> result = old + 6
            2 -> result = old * old
            3 -> result = old + 3
        }
        return result
    }

    private fun parseInput(input: String) : List<Monkey> {
        val newLine = System.lineSeparator()
        val monkeyInput = input.split("$newLine$newLine")
        val monkeys = mutableListOf<Monkey>()
        monkeyInput.forEach{ m -> monkeys.add(Monkey(m)) }
        return monkeys
    }

    private fun playMonkeyInTheMiddle(input: String, rounds: Int, reliefDivisibleBy: Int) : Long {
        val monkeys = parseInput(input)

        // Part 2
        val monkeyDivisor = monkeys.map{ monkey -> monkey.divisibleBy }.reduce { a, b -> a * b }

        repeat(rounds) {
            monkeys.forEachIndexed { index, monkey ->
                while (monkey.items.any()) {
                    var worryLevel = doOperationAction(index, monkey.items.first())
                    if (reliefDivisibleBy > 0)
                        worryLevel = worryLevel.floorDiv(reliefDivisibleBy)
                    else
                        worryLevel %= monkeyDivisor


                    if (worryLevel.mod(monkey.divisibleBy) == 0)
                        monkeys[monkey.divisibleTrueToMonkey].items.add(worryLevel)
                    else
                        monkeys[monkey.divisibleFalseToMonkey].items.add(worryLevel)

                    monkey.numberOfItemsThrown++
                    monkey.items.removeAt(0)
                }
            }

//            if ((it + 1).mod(1000) == 0 || it == 19 || it == 0) {
//                println("== After round ${(it + 1)} ==")
//                monkeys.forEachIndexed { index, monkey ->
//                    println("Monkey $index inspected items ${monkey.numberOfItemsThrown} times - ${monkey.items.joinToString(" - ")}" )
//
//                }
//                println("")
//            }
        }

        val max2 = monkeys.sortedByDescending { it.numberOfItemsThrown }.take(2)
        println(max2[0].numberOfItemsThrown.toString() + " * " + max2[1].numberOfItemsThrown.toString())

        return max2[0].numberOfItemsThrown.toLong() * max2[1].numberOfItemsThrown.toLong()
    }

    // --- Part 1 ---

    override fun part1ToString(input: String): String {
        return playMonkeyInTheMiddle(input, 20 , 3).toString()
    }

    // --- Part 2 ---

    override fun part2ToString(input: String): String {
        return playMonkeyInTheMiddle(input, 10000, 0).toString()
    }
}

fun main() {
    Day11().printToStringResults("10605", "2713310158")
}
