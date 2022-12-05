package day02

import Day

class Day02 : Day(2) {

    // --- Part 1 ---

    private fun getHandScore(hand: Char, handType: String) : Int {
        return handType.indexOf(hand)
    }

    private fun calculateGame(opponentHand: Char, yourHand: Char) : Int {
        val handType = " XYZ"
        if ((opponentHand == 'A' && yourHand == 'X') ||
            (opponentHand == 'B' && yourHand == 'Y') ||
            (opponentHand == 'C' && yourHand == 'Z'))
            return getHandScore(yourHand, handType) + 3

        if ((opponentHand == 'A' && yourHand == 'Y') ||
            (opponentHand == 'B' && yourHand == 'Z') ||
            (opponentHand == 'C' && yourHand == 'X'))
            return getHandScore(yourHand, handType) + 6

        return getHandScore(yourHand, handType)
    }

    override fun part1ToInt(input: String): Int {
        var total = 0
        val games = input.lines()
        for (game in games) {
            total += calculateGame(game[0], game[2])
        }
        return total
    }

    // --- Part 2 ---

    private fun calculateGame2(opponentHand: Char, result: Char) : Int {
        val lose = 'X'
        val draw = 'Y'
        val win = 'Z'

        val yourWinHand = when (opponentHand) {
            'A' -> 'B'
            'B' -> 'C'
            'C' -> 'A'
            else -> ' '
        }

        val yourLoseHand = when (opponentHand) {
            'A' -> 'C'
            'B' -> 'A'
            'C' -> 'B'
            else -> ' '
        }

        val handType = " ABC"
        val score = when (result) {
            draw -> getHandScore(opponentHand, handType) + 3
            lose -> getHandScore(yourLoseHand, handType)
            win -> getHandScore(yourWinHand, handType) + 6
            else -> 0
        }

        return score
    }

    override fun part2ToInt(input: String): Int {
        val games = input.lines()
        var total = 0
        for (game in games) {
            total += calculateGame2(game[0], game[2])
        }
        return total
    }
}

fun main() {
    val day = Day02()
    day.printToIntResults(15, 12)
}
