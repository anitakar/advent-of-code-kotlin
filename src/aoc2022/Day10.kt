package aoc2022

import println
import readInput

fun main() {

    fun part1(input: List<String>): Int {
        var s20: Int? = null
        var s60: Int? = null
        var s100: Int? = null
        var s140: Int? = null
        var s180: Int? = null
        var s220: Int? = null

        var curCycle = 1
        var register = 1
        for (line in input) {
            var toAdd = 0
            if (line == "noop") {
                curCycle += 1
            } else {
                toAdd = line.substring(5).toInt()
                curCycle += 2
            }

            if (curCycle == 20) {
                register += toAdd
                s20 = 20 * register
            } else if (curCycle == 21) {
                s20 = 20 * register
                register += toAdd
            } else if (curCycle == 60) {
                register += toAdd
                s60 = 60 * register
            } else if (curCycle == 61) {
                s60 = 60 * register
                register += toAdd
            } else if (curCycle == 100) {
                register += toAdd
                s100 = 100 * register
            } else if (curCycle == 101) {
                s100 = 100 * register
                register += toAdd
            } else if (curCycle == 140) {
                register += toAdd
                s140 = 140 * register
            } else if (curCycle == 141) {
                s140 = 140 * register
                register += toAdd
            } else if (curCycle == 180) {
                register += toAdd
                s180 = 180 * register
            } else if (curCycle == 181) {
                s180 = 180 * register
                register += toAdd
            } else if (curCycle == 220) {
                register += toAdd
                s220 = 220 * register
            } else if (curCycle == 221) {
                s220 = 220 * register
                register += toAdd
            } else {
                register += toAdd
            }
        }

        return s20!! + s60!! + s100!! + s140!! + s180!! + s220!!
    }

    fun part2(input: List<String>): Array<CharArray> {
        val matrix = Array(6) { CharArray(40) }

        val sprite = mutableSetOf(0, 1, 2)
        var curCycle = 0
        var register = 1
        for (line in input) {
            var toAdd = 0
            if (line == "noop") {
                if (sprite.contains(curCycle % 40)) {
                    matrix[curCycle / 40][curCycle % 40] = '#'
                } else {
                    matrix[curCycle / 40][curCycle % 40] = '.'
                }
                curCycle += 1
            } else {
                toAdd = line.substring(5).toInt()
                if (sprite.contains(curCycle % 40)) {
                    matrix[curCycle / 40][curCycle % 40] = '#'
                } else {
                    matrix[curCycle / 40][curCycle % 40] = '.'
                }
                curCycle += 1
                if (sprite.contains(curCycle % 40)) {
                    matrix[curCycle / 40][curCycle % 40] = '#'
                } else {
                    matrix[curCycle / 40][curCycle % 40] = '.'
                }
                curCycle += 1
                register += toAdd
                sprite.clear()
                sprite.add(register - 1);
                sprite.add(register);
                sprite.add(register + 1);
            }
        }
        return matrix
    }

    fun printMatrix(matrix: Array<CharArray>) {
        for (line in matrix) {
            println(String(line))
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    printMatrix(part2(testInput))

    val input = readInput("Day10")
    part1(input).println()
    printMatrix(part2(input))
}
