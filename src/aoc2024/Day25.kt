package aoc2024

import readInput


fun main() {

    fun isLock(schema: List<String>): Boolean {
        return schema[0][0] == '#'
    }

    fun parseSingle(input: List<String>): List<Int> {
        val result = mutableListOf(0, 0, 0, 0, 0)
        for (row in input.indices) {
            for (column in input[row].indices) {
                if (input[row][column] == '#') {
                    result[column] = result[column] + 1
                }
            }
        }
        return result
    }

    fun parseInput(input: List<String>): Pair<List<List<Int>>, List<List<Int>>> {
        val keys = mutableListOf<List<Int>>()
        val locks = mutableListOf<List<Int>>()

        val numSchemas = (input.size + 1) / 8
        for (i in 0 until numSchemas) {
            val candidate = input.subList(i * 8, (i + 1) * 8 - 1)
            if (isLock(candidate)) {
                locks.add(parseSingle(candidate))
            } else {
                keys.add(parseSingle(candidate))
            }
        }

        return Pair(keys, locks)
    }

    fun matches(key: List<Int>, lock: List<Int>): Boolean {
        for (i in 0 .. 4) {
            if (key[i] + lock[i] > 7) {
                return false
            }
        }
        return true
    }

    fun part1(input: List<String>): Long {
        val (keys, locks) = parseInput(input)
        var numMatches = 0L
        for (key in keys) {
            for (lock in locks) {
                if (matches(key, lock)) {
                    numMatches++
                }
            }
        }
        return numMatches
    }

    fun part2(input: List<String>): Int {
        return 0
    }



    println(part1(readInput("aoc2024/Day25_test")))
    println(part1(readInput("aoc2024/Day25")))
//    println(part2(readInput("aoc2024/Day25_test")))
//    println(part2(readInput("aoc2024/Day25")))
}