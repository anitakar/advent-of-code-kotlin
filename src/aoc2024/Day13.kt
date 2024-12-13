package aoc2024

import readInput
import kotlin.math.min


fun main() {

    fun cost(buttonA: Pair<Int, Int>, buttonB: Pair<Int, Int>, prize: Pair<Int, Int>): Int {
        var minCost = Int.MAX_VALUE
        var i = 0
        while (buttonA.first * i < prize.first &&
            buttonA.second * i < prize.second
        ) {
            if (((prize.first - buttonA.first * i) % buttonB.first) == 0 &&
                ((prize.second - buttonA.second * i) % buttonB.second) == 0 &&
                ((prize.first - buttonA.first * i) / buttonB.first) == ((prize.second - buttonA.second * i) / buttonB.second)
            ) {
                val cost = (3 * i) + ((prize.first - buttonA.first * i) / buttonB.first)
                minCost = min(cost, minCost)
            }
            i++
        }
        return if (minCost == Int.MAX_VALUE) 0 else minCost
    }

    fun parseButtonA(input: String): Pair<Int, Int> {
        val regex = "Button A: X\\+(\\d+), Y\\+(\\d+)".toRegex()
        val matchResult = regex.matchEntire(input)
        return Pair(matchResult!!.groupValues.get(1).toInt(), matchResult!!.groupValues.get(2).toInt())
    }

    fun parseButtonB(input: String): Pair<Int, Int> {
        val regex = "Button B: X\\+(\\d+), Y\\+(\\d+)".toRegex()
        val matchResult = regex.matchEntire(input)
        return Pair(matchResult!!.groupValues.get(1).toInt(), matchResult!!.groupValues.get(2).toInt())
    }

    fun parsePrize(input: String): Pair<Int, Int> {
        val regex = "Prize: X=(\\d+), Y=(\\d+)".toRegex()
        val matchResult = regex.matchEntire(input)
        return Pair(matchResult!!.groupValues.get(1).toInt(), matchResult!!.groupValues.get(2).toInt())
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
        return 0
    }

    println(parseButtonA("Button A: X+94, Y+34"))
    println(parseButtonB("Button B: X+22, Y+67"))
    println(parsePrize("Prize: X=8400, Y=5400"))

    println(part1(readInput("aoc2024/Day13_test")))
    println(part1(readInput("aoc2024/Day13")))
//    println(part2(readInput("aoc2024/Day13_test")))
//    println(part2(readInput("aoc2024/Day13")))
}