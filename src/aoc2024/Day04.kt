package aoc2024

import readInput
import kotlin.math.min


fun main() {

    fun findAll(line: String): Long {
        var total = 0L
        val regex = """XMAS""".toRegex()
        val matchResult = regex.findAll(line).iterator()
        while (matchResult.hasNext()) {
            matchResult.next()
            total++
        }
        return total
    }

    fun part1(input: List<String>): Long {
        var result = 0L
        for (line in input) {
            result += findAll(line)
            result += findAll(line.reversed())
        }
        for (column in 0 until input[0].length) {
            var toCheck = ""
            for (line in input) {
                toCheck += line[column]
            }
            result += findAll(toCheck)
            result += findAll(toCheck.reversed())
        }
        var column = 0
        for (row in 0 until input.size) {
            var toCheck = ""
            for (i in 0 until min(input[0].length - column, input.size - row)) {
                toCheck += input[row + i][column + i]
            }
            result += findAll(toCheck)
            result += findAll(toCheck.reversed())
        }
        var row = 0
        for (column in 1 until input[0].length) {
            var toCheck = ""
            for (i in 0 until min(input[0].length - column, input.size - row)) {
                toCheck += input[row + i][column + i]
            }
            result += findAll(toCheck)
            result += findAll(toCheck.reversed())
        }
        column = input[0].length - 1
        for (row in input.size - 1 downTo 1) {
            var toCheck = ""
            for (i in 0..min(column, row)) {
                toCheck += input[row - i][column - i]
            }
            result += findAll(toCheck)
            result += findAll(toCheck.reversed())
        }
        row = input.size - 1
        for (column in input[0].length - 2 downTo 0) {
            var toCheck = ""
            for (i in 0..min(column, row)) {
                toCheck += input[row - i][column - i]
            }
            result += findAll(toCheck)
            result += findAll(toCheck.reversed())
        }
        return result
    }

    println(part1(readInput("aoc2024/Day04_test2")))
    println(part1(readInput("aoc2024/Day04_test")))
    println(part1(readInput("aoc2024/Day04")))
//    println(part2(readInput("aoc2024/Day03_test2")))
//    println(part2(readInput("aoc2024/Day03")))
}