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

    fun possible(patterns: List<String>, left: String): Boolean {
        if (left.isEmpty()) {
            return true
        }

        for (pattern in patterns) {
            val toCheck = mutableListOf<String>()
            if (left.startsWith(pattern)) {
                toCheck.add(left.substring(pattern.length, left.length))
            }
            if (toCheck.isNotEmpty()) {
                return toCheck.any { possible(patterns, it) }
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
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