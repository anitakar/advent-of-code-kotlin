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

    fun lineEnabled(input: String, enabled: Boolean): Pair<Long, Boolean> {
        val regex = """(mul\((\d+),(\d+)\)|don't\(\)|do\(\))""".toRegex()
        val muls = regex.findAll(input).iterator()

        var result = 0L
        var enabled = enabled
        while (muls.hasNext()) {
            val mul = muls.next()
            if (mul.value == "don't()") {
                enabled = false
                continue
            }
            if (mul.value == "do()") {
                enabled = true
                continue
            }

            if (enabled) {
                result += mul.groupValues[2].toInt() * mul.groupValues[3].toInt()
            }
        }
        return Pair(result, enabled)
    }

    fun part1(input: List<String>): Long {
        var result = 0L
        for (line in input) {
            result += line(line)
        }
        return result
    }

    fun part2(input: List<String>): Long {
        var enabled = true
        var result = 0L
        for (line in input) {
            val (lineResult, lineEnabled) = lineEnabled(line, enabled)
            enabled = lineEnabled
            result += lineResult
        }
        return result
    }

    println(part1(readInput("aoc2024/Day03_test")))
    println(part1(readInput("aoc2024/Day03")))
    println(part2(readInput("aoc2024/Day03_test2")))
    println(part2(readInput("aoc2024/Day03")))
}