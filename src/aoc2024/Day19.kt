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

    fun part2(input: List<String>): Int {
        return 0
    }



    println(part1(readInput("aoc2024/Day19_test")))
    println(part1(readInput("aoc2024/Day19")))
//    println(part2(readInput("aoc2024/Day19_test")))
//    println(part2(readInput("aoc2024/Day19")))
}