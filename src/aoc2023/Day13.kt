package aoc2023

import readInput
import kotlin.math.min

fun main() {
    fun check(terrain: List<String>, split: Int): Boolean {
        for (i in 1..min(split, terrain.size - split - 2)) {
            val before = terrain[split - i]
            val after = terrain[split + 1 + i]
            if (after != before) return false
        }
        return true
    }

    fun findHorizontalLine(terrain: List<String>): Int {
        for (i in 0 until terrain.size - 1) {
            if (terrain[i] == terrain[i + 1] && check(terrain, i)) return i
        }

        return -1
    }

    fun revert(terrain: List<String>): List<String> {
        val result = mutableListOf<String>()
        for (i in terrain[0].indices) {
            result.add("")
        }
        for (i in terrain.indices) {
            for (j in terrain[0].indices) {
                result[j] = result[j] + terrain[i][j]
            }
        }
        return result
    }

    fun summary(terrain: List<String>): Int {
        val horizontalIndex = findHorizontalLine(terrain)
        if (horizontalIndex >= 0) {
            return 100 * (horizontalIndex + 1)
        }
        val verticalIndex = findHorizontalLine(revert(terrain))
        if (verticalIndex >= 0) {
            return verticalIndex + 1
        }
        return 0
    }

    fun summaryWithSmudge(terrain: List<String>): Int {
        var returnWithSmudge = false
        val summaryWithoutSmudge = summary(terrain)
        val mapping = mapOf('.' to '#', '#' to '.')
        for (i in terrain.indices) {
            for (j in terrain[0].indices) {
                val withSmudge = mutableListOf<String>()
                withSmudge.addAll(terrain)
                withSmudge[i] = terrain[i].substring(0, j) + mapping[terrain[i][j]] + terrain[i].substring(j + 1)
                val summaryWithSmudge = summary(withSmudge)
                if (summaryWithSmudge > 0) {
                    if (summaryWithSmudge != summaryWithoutSmudge) return summaryWithSmudge
                    else returnWithSmudge = true
                }
            }
        }
        return if (returnWithSmudge) summaryWithoutSmudge else 0
    }

    fun part1(lines: List<String>): Int {
        var sum = 0
        var startIndex = 0
        var endIndex: Int
        while (startIndex < lines.size) {
            endIndex = lines.subList(startIndex, lines.size).indexOfFirst { it.trim().isEmpty() }
            if (endIndex == -1) endIndex = lines.size - startIndex
            val terrain = lines.subList(startIndex, startIndex + endIndex)
            sum += summary(terrain)
            startIndex += (endIndex + 1)
        }
        return sum
    }

    fun part2(lines: List<String>): Int {
        var sum = 0
        var startIndex = 0
        var endIndex: Int
        while (startIndex < lines.size) {
            endIndex = lines.subList(startIndex, lines.size).indexOfFirst { it.trim().isEmpty() }
            if (endIndex == -1) endIndex = lines.size - startIndex
            val terrain = lines.subList(startIndex, startIndex + endIndex)
            sum += summaryWithSmudge(terrain)
            startIndex += (endIndex + 1)
        }
        return sum
    }

    println(part1(readInput("aoc2023/Day13_test")))
    println(part1(readInput("aoc2023/Day13")))
    println(part2(readInput("aoc2023/Day13_test")))
    println(part2(readInput("aoc2023/Day13")))
}