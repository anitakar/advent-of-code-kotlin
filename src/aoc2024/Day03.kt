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

    fun lineEnabled(input: String): Long {
        val regex = """(mul\((\d+),(\d+)\)|don't\(\)|do\(\))""".toRegex()
        val muls = regex.findAll(input).iterator()
        var result = 0L

        var enabled = true
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
            result += lineEnabled(line)
        }
        return result
    }

    println(part1(readInput("aoc2024/Day03_test")))
    println(part1(readInput("aoc2024/Day03")))
    println(part2(readInput("aoc2024/Day03_test2")))
    println(part2(readInput("aoc2024/Day03")))
}