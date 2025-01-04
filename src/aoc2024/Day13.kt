package aoc2024

import readInput
import kotlin.math.min


fun main() {

    fun cost(buttonA: Pair<Long, Long>, buttonB: Pair<Long, Long>, prize: Pair<Long, Long>): Long {
        var minCost = Long.MAX_VALUE
        var i = 0
        while (buttonA.first * i < prize.first &&
            buttonA.second * i < prize.second
        ) {
            if (((prize.first - buttonA.first * i) % buttonB.first) == 0L &&
                ((prize.second - buttonA.second * i) % buttonB.second) == 0L &&
                ((prize.first - buttonA.first * i) / buttonB.first) == ((prize.second - buttonA.second * i) / buttonB.second)
            ) {
                val cost = (3 * i) + ((prize.first - buttonA.first * i) / buttonB.first)
                minCost = min(cost, minCost)
            }
            i++
        }
        return if (minCost == Long.MAX_VALUE) 0 else minCost
    }

    fun parseButtonA(input: String): Pair<Long, Long> {
        val regex = "Button A: X\\+(\\d+), Y\\+(\\d+)".toRegex()
        val matchResult = regex.matchEntire(input)
        return Pair(matchResult!!.groupValues.get(1).toLong(), matchResult!!.groupValues.get(2).toLong())
    }

    fun parseButtonB(input: String): Pair<Long, Long> {
        val regex = "Button B: X\\+(\\d+), Y\\+(\\d+)".toRegex()
        val matchResult = regex.matchEntire(input)
        return Pair(matchResult!!.groupValues.get(1).toLong(), matchResult!!.groupValues.get(2).toLong())
    }

    fun parsePrize(input: String): Pair<Long, Long> {
        val regex = "Prize: X=(\\d+), Y=(\\d+)".toRegex()
        val matchResult = regex.matchEntire(input)
        return Pair(matchResult!!.groupValues.get(1).toLong(), matchResult!!.groupValues.get(2).toLong())
    }

    fun part1(input: List<String>): Long {
        var total = 0L
        for (i in 0..(input.size / 4)) {
            val a = parseButtonA(input.get(i * 4))
            val b = parseButtonB(input.get(i * 4 + 1))
            val prize = parsePrize(input.get(i * 4 + 2))
            total += cost(a, b, prize)
        }
        return total
    }

    fun part2(input: List<String>): Long {
        var total = 0L
        for (i in 0..(input.size / 4)) {
            val a = parseButtonA(input.get(i * 4))
            val b = parseButtonB(input.get(i * 4 + 1))
            val prize = parsePrize(input.get(i * 4 + 2))
            val actualPrize = Pair(prize.first + 10000000000000, prize.second + 10000000000000)
            //val actualPrize = prize

            var minCost = Long.MAX_VALUE
            for (j in 0 .. 1000000000) {
                val numX = actualPrize.first / b.first
                val numY = actualPrize.second / b.second
                val numBPresses = min(numX, numY) - j
                if (numBPresses < 0) break
                // numBPresses (b.first - b.second) + numAPresses (a.first - a.second) = (actualPrize.first - actualPrize.second)
                val restX = actualPrize.first - numBPresses * b.first
                val restY = actualPrize.second - numBPresses * b.second
                if ((restX % a.first) == 0L && (restY % a.second) == 0L &&
                    (restX / a.first) == (restY / a.second)
                ) {
                    val numAPresses = (actualPrize.first - numBPresses * b.first) / a.first
                    minCost = (3 * numAPresses) + numBPresses
                }
            }
            if (minCost != Long.MAX_VALUE) {
                total += minCost
            }
        }
        return total
    }

    println(part1(readInput("aoc2024/Day13_test")))
    println(part1(readInput("aoc2024/Day13")))
    println(part2(readInput("aoc2024/Day13_test")))
    println(part2(readInput("aoc2024/Day13")))
}