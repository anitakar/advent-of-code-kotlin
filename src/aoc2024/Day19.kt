package aoc2024

import readInput


fun main() {

    fun parseInput(input: List<String>): Pair<List<String>, List<String>> {
        val patterns = input[0].split(", ")
        val designs = mutableListOf<String>()
        for (i in 2 until input.size) {
            designs.add(input[i])
        }

        return Pair(patterns, designs)
    }

    val memoized = mutableMapOf<String, Boolean>()

    fun possible(patterns: List<String>, left: String): Boolean {
        if (left.isEmpty()) {
            return true
        }

        val toCheck = mutableListOf<String>()
        for (pattern in patterns) {
            if (left.startsWith(pattern)) {
                toCheck.add(left.substring(pattern.length, left.length))
            }
        }
        if (toCheck.isNotEmpty()) {
            val result = toCheck.any {
                if (memoized.contains(it)) {
                    memoized[it]!!
                } else {
                    val toMemoize = possible(patterns, it)
                    memoized[it] = toMemoize
                    toMemoize
                }
            }
            return result
        }
        return false
    }

    val numMemoized = mutableMapOf<String, Long>()

    fun numPossible(patterns: List<String>, left: String): Long {
        if (left.isEmpty()) {
            return 1
        }

        val toCheck = mutableListOf<String>()
        for (pattern in patterns) {
            if (left.startsWith(pattern)) {
                toCheck.add(left.substring(pattern.length, left.length))
            }
        }
        if (toCheck.isNotEmpty()) {
            val result = toCheck.sumOf {
                if (numMemoized.contains(it)) {
                    numMemoized[it]!!
                } else {
                    val toMemoize = numPossible(patterns, it)
                    numMemoized[it] = toMemoize
                    toMemoize
                }
            }
            return result
        }
        return 0
    }

    fun part1(input: List<String>): Int {
        memoized.clear()
        val (patterns, designs) = parseInput(input)
        var total = 0
        for (design in designs) {
            if (possible(patterns, design)) {
                total++
            }
        }
        return total
    }

    fun part2(input: List<String>): Long {
        numMemoized.clear()
        val (patterns, designs) = parseInput(input)
        var total = 0L
        for (design in designs) {
            total += numPossible(patterns, design)
        }
        return total
    }



    println(part1(readInput("aoc2024/Day19_test")))
    println(part1(readInput("aoc2024/Day19")))
    println(part2(readInput("aoc2024/Day19_test")))
    println(part2(readInput("aoc2024/Day19")))
}