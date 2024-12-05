package aoc2024

import readInput


fun main() {

    val beforeRules = mutableMapOf<Int, MutableSet<Int>>()

    fun addRule(line: String) {
        val (before, after) = line.split("|")
        if (!beforeRules.contains(before.toInt())) {
            beforeRules[before.toInt()] = mutableSetOf()
        }

        beforeRules[before.toInt()]?.add(after.toInt())
    }

    fun translateInput(input: List<String>): List<String> {
        beforeRules.clear()

        var blankIndex = 0
        for (i in input.indices) {
            if (input[i].isBlank()) {
                blankIndex = i
                break
            }

            addRule(input[i])
        }

        return input.subList(blankIndex + 1, input.size)
    }

    fun validUpdateMiddleValue(line: String): Int {
        val update = line.split(",").map { it.toInt() }
        val added = mutableSetOf<Int>()

        for (elem in update) {
            added.add(elem)
            if (beforeRules.contains(elem) && beforeRules[elem]!!.intersect(added).isNotEmpty()) {
                return 0
            }
        }

        return update[update.size / 2]
    }

    fun part1(input: List<String>): Long {
        var result = 0L
        val updates = translateInput(input)
        for (update in updates) {
            result += validUpdateMiddleValue(update)
        }
        return result
    }

    fun part2(input: List<String>): Long {
        var result = 0L
        return result
    }

    println(part1(readInput("aoc2024/Day05_test")))
    println(part1(readInput("aoc2024/Day05")))
//    println(part2(readInput("aoc2024/Day05_test")))
//    println(part2(readInput("aoc2024/Day05")))
}