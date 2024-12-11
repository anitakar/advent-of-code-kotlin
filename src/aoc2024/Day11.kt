package aoc2024

import readInput
import kotlin.math.abs
import kotlin.math.max


fun main() {

    fun blink(stones: MutableList<Long>): MutableList<Long> {
        val result = mutableListOf<Long>()
        for (stone in stones) {
            if (stone == 0L) {
                result.add(1)
            } else if (stone.toString().length % 2 == 0) {
                val asString = stone.toString()
                val first = asString.substring(asString.length / 2, asString.length)
                result.add(first.toLong())
                val second = asString.substring(0, asString.length / 2)
                result.add(second.toLong())
            } else {
                result.add(stone * 2024)
            }
        }
        return result
    }

    fun part1(input: List<String>): Int {
        val splitted = input[0].split(" ").map { it.toLong() }
        var stones = mutableListOf<Long>()
        stones.addAll(splitted)
        for (i in 0 until 25) {
            stones = blink(stones)
        }
        return stones.size
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    println(part1(readInput("aoc2024/Day11_test")))
    println(part1(readInput("aoc2024/Day11")))
//    println(part2(readInput("aoc2024/Day11_test")))
//    println(part2(readInput("aoc2024/Day11")))
}