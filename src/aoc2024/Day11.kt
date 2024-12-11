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

    fun next(stone: Long): List<Long> {
        val result = mutableListOf<Long>()
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
        return result
    }

    val memoized = mutableMapOf<Pair<Long, Int>, Long>()

    fun problem(stone: Long, iterations: Int): Long {
        if (iterations == 0) return 1

        val next = next(stone)

        return if (next.size == 2) {
            val val1 = if (memoized.contains(Pair(next[0], iterations - 1))) {
                memoized.get(Pair(next[0], iterations - 1))!!
            } else {
                val mem = problem(next[0], iterations - 1)
                memoized.put(Pair(next[0], iterations - 1), mem)
                mem
            }
            val val2 = if (memoized.contains(Pair(next[1], iterations - 1))) {
                memoized.get(Pair(next[1], iterations - 1))!!
            } else {
                val mem = problem(next[1], iterations - 1)
                memoized.put(Pair(next[1], iterations - 1), mem)
                mem
            }
            val1 + val2
        } else {
            val val1 = if (memoized.contains(Pair(next[0], iterations - 1))) {
                memoized.get(Pair(next[0], iterations - 1))!!
            } else {
                val mem = problem(next[0], iterations - 1)
                memoized.put(Pair(next[0], iterations - 1), mem)
                mem
            }
            val1
        }
    }

    fun part2(input: List<String>): Long {
        val splitted = input[0].split(" ").map { it.toLong() }
        var total = 0L
        for (stone in splitted) {
            total += problem(stone, 75)
        }
        return total
    }

    println(part1(readInput("aoc2024/Day11_test")))
    println(part1(readInput("aoc2024/Day11")))
    println(part2(readInput("aoc2024/Day11_test")))
    memoized.clear()
    println(part2(readInput("aoc2024/Day11")))
}