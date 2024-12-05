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

    fun isValidUpdate(update: List<Int>): Boolean {
        val added = mutableSetOf<Int>()

        for (elem in update) {
            added.add(elem)
            if (beforeRules.contains(elem) && beforeRules[elem]!!.intersect(added).isNotEmpty()) {
                return false
            }
        }

        return true
    }

    fun reorder(update: List<Int>): List<Int> {
        val updated = mutableListOf<Int>()
        updated.addAll(update)
        val added = mutableSetOf<Int>()

        for (elem in update) {
            added.add(elem)
            if (beforeRules.contains(elem) && beforeRules[elem]!!.intersect(added).isNotEmpty()) {
                updated.remove(elem)
                for (i in updated.indices) {
                    if (beforeRules[elem]!!.contains(updated[i])) {
                        updated.add(i, elem)
                        break
                    }
                }
            }
        }

        return updated
    }

    fun parseUpdate(line: String): List<Int> {
        return line.split(",").map { it.toInt() }
    }

    fun validUpdateMiddleValue(line: String): Int {
        val update = parseUpdate(line)
        return if (isValidUpdate(update)) {
            update[update.size / 2]
        } else {
            0
        }
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
        val updates = translateInput(input)
        for (update in updates) {
            val updateParsed = parseUpdate(update)
            if (!isValidUpdate(updateParsed)) {
                val reordered = reorder(updateParsed)
                result += reordered[reordered.size / 2]
            }
        }
        return result
    }

    println(part1(readInput("aoc2024/Day05_test")))
    println(part1(readInput("aoc2024/Day05")))
    println(part2(readInput("aoc2024/Day05_test")))
    println(part2(readInput("aoc2024/Day05")))
}