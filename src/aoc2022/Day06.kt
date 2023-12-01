package aoc2022

import println
import readInput

fun main() {

    fun part1(input: List<String>): Int {
        val seq = input[0]
        val occur = mutableMapOf<Char, Int>()
        for (i in 0 until seq.length) {
            if (i > 3) {
                val char = seq.get(i - 4)
                val count = occur.getValue(char)
                if (count == 1) occur.remove(char)
                else occur.put(char, count - 1)
            }
            occur.compute(seq.get(i)) { k, v -> (v?:0) + 1 };
            if (occur.size == 4)
                return i + 1
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val seq = input[0]
        val occur = mutableMapOf<Char, Int>()
        for (i in 0 until seq.length) {
            if (i > 13) {
                val char = seq.get(i - 14)
                val count = occur.getValue(char)
                if (count == 1) occur.remove(char)
                else occur.put(char, count - 1)
            }
            occur.compute(seq.get(i)) { k, v -> (v?:0) + 1 };
            if (occur.size == 14)
                return i + 1
        }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 10)
    check(part2(testInput) == 29)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
