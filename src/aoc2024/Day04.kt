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
        // all rows == 5
        for (line in input) {
            result += findAll(line)
            result += findAll(line.reversed())
        }
        // all columns == 3 (8)
        for (column in 0 until input[0].length) {
            var toCheck = ""
            for (line in input) {
                toCheck += line[column]
            }
            result += findAll(toCheck)
            result += findAll(toCheck.reversed())
        }
        // diagonal down left for first column == 3 (11)
        var column = 0
        for (row in 0 until input.size) {
            var toCheck = ""
            for (i in 0 until min(input.size - row, input[0].length)) {
                toCheck += input[row + i][column + i]
            }
            result += findAll(toCheck)
            result += findAll(toCheck.reversed())
        }
        // diagonal down left for first row == 2 (13)
        var row = 0
        for (column in 1 until input[0].length) {
            var toCheck = ""
            for (i in 0 until min(input.size, input[0].length - column)) {
                toCheck += input[row + i][column + i]
            }
            result += findAll(toCheck)
            result += findAll(toCheck.reversed())
        }
        // diagonal down right for last column == 4 (17)
        column = input[0].length - 1
        for (row in 0 until input.size) {
            var toCheck = ""
            for (i in 0 until min(input.size - row, input[0].length)) {
                toCheck += input[row + i][column - i]
            }
            result += findAll(toCheck)
            result += findAll(toCheck.reversed())
        }
        // diagonal down right for first row == 1 (18)
        row = 0
        for (column in 0 until input[0].length - 1) {
            var toCheck = ""
            for (i in 0 until min(input.size, column + 1)) {
                toCheck += input[row + i][column - i]
            }
            result += findAll(toCheck)
            result += findAll(toCheck.reversed())
        }
        return result
    }

    fun isXMas(input: List<String>, row: Int, column: Int): Boolean {
        if (input[row][column] != 'A') return false
        if (! ((input[row-1][column-1] == 'M' && input[row+1][column+1] == 'S') || (input[row-1][column-1] == 'S' && input[row+1][column+1] == 'M')) )
            return false
        if (! ((input[row-1][column+1] == 'M' && input[row+1][column-1] == 'S') || (input[row-1][column+1] == 'S' && input[row+1][column-1] == 'M')) )
            return false

        return true
    }

    fun part2(input: List<String>): Long {
        var result = 0L
        for (row in 1 until  input.size - 1) {
            for (column in 1 until input[0].length - 1) {
                if (isXMas(input, row, column)) {
                    result++
                }
            }
        }
        return result
    }

    println(part1(readInput("aoc2024/Day04_test2")))
    println(part1(readInput("aoc2024/Day04_test")))
    println(part1(readInput("aoc2024/Day04")))
    println(part2(readInput("aoc2024/Day04_test")))
    println(part2(readInput("aoc2024/Day04")))
}