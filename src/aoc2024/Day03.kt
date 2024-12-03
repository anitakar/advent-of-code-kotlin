package aoc2024

import readInput


fun main() {

    fun line(input: String): Long {
        val regex = """mul\((\d+),(\d+)\)""".toRegex()
        val muls = regex.findAll(input).iterator()
        var result = 0L
        while (muls.hasNext()) {
            val mul = muls.next()
            result += mul.groupValues[1].toInt() * mul.groupValues[2].toInt()
        }
        return result
    }

    fun isEnabled(startLocation: Int): Boolean {
        return true
    }

    fun lineEnabled(input: String): Long {
        val regex = """mul\((\d+),(\d+)\)""".toRegex()
        val muls = regex.findAll(input).iterator()
        var result = 0L

        var disabledRegex = """don't\(\)""".toRegex()
        val disabled = disabledRegex.findAll(input).iterator()
        val disabledRanges = mutableListOf<Int>()
        while (disabled.hasNext()) {
            val startDisabled = disabled.next().range.last
            disabledRanges.add(startDisabled)
        }

        var enabledRegex = """do\(\)""".toRegex()
        val enabled = enabledRegex.findAll(input).iterator()
        val enabledRanges = mutableListOf<Int>()
        enabledRanges.add(0)
        while (enabled.hasNext()) {
            val startEnabled = enabled.next().range.last
            enabledRanges.add(startEnabled)
        }

        while (muls.hasNext()) {
            val mul = muls.next()
            if (isEnabled(mul.range.start)) {
                result += mul.groupValues[1].toInt() * mul.groupValues[2].toInt()
            }
        }
        return result
    }

    fun part1(input: List<String>): Long {
        var result = 0L
        for (line in input) {
            result += line(line)
        }
        return result
    }

    fun part2(input: List<String>): Long {
        var result = 0L
        for (line in input) {
            result += line(line)
        }
        return result
    }

    println(part1(readInput("aoc2024/Day03_test")))
    println(part1(readInput("aoc2024/Day03")))
//    println(part2(readInput("aoc2024/Day03_test")))
//    println(part2(readInput("aoc2024/Day03")))
}