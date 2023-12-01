package aoc2022

import println
import readInput

fun main() {

    data class Tree(val x: Int, val y: Int, val h: Int)

    fun part1(input: List<String>): Int {

        // i top to bottom, j left to right == LEFT
        val unique = mutableSetOf<Tree>()
        for (i in input.indices) {
            var tallest = 0
            for (j in input[i].indices) {
                if (j == 0) {
                    tallest = input[i][j].digitToInt()
                    unique.add(Tree(i, j, input[i][j].digitToInt()))
                }

                if (input[i][j].digitToInt() > tallest) {
                    tallest = input[i][j].digitToInt()
                    unique.add(Tree(i, j, input[i][j].digitToInt()))
                }
            }
        }
        // i top to bottom, j right to left == RIGHT
        for (i in input.indices) {
            var tallest = 0
            for (j in input[i].indices.reversed()) {
                if (j == input[i].length - 1) {
                    tallest = input[i][j].digitToInt()
                    unique.add(Tree(i, j, input[i][j].digitToInt()))
                }

                if (input[i][j].digitToInt() > tallest) {
                    tallest = input[i][j].digitToInt()
                    unique.add(Tree(i, j, input[i][j].digitToInt()))
                }
            }
        }
        // j left to right, i top to bottom == TOP
        for (j in input[0].indices) {
            var tallest = 0
            for (i in input.indices) {
                if (i == 0) {
                    tallest = input[i][j].digitToInt()
                    unique.add(Tree(i, j, input[i][j].digitToInt()))
                }

                if (input[i][j].digitToInt() > tallest) {
                    tallest = input[i][j].digitToInt()
                    unique.add(Tree(i, j, input[i][j].digitToInt()))
                }
            }
        }
        // j left to right, i bottom to top == BOTTOM
        for (j in input[0].indices) {
            var tallest = 0
            for (i in input.indices.reversed()) {
                if (i == input.size - 1) {
                    tallest = input[i][j].digitToInt()
                    unique.add(Tree(i, j, input[i][j].digitToInt()))
                }

                if (input[i][j].digitToInt() > tallest) {
                    tallest = input[i][j].digitToInt()
                    unique.add(Tree(i, j, input[i][j].digitToInt()))
                }
            }
        }
        return unique.size
    }

    fun part2(input: List<String>): Int {
        var maxScenicScore = 0
        for (i in 1 until input.size - 1) {
            for (j in 1 until input[i].length - 1) {
                var topView = 0
                for (k in 1 .. i ) {
                    topView += 1
                    if (input[i - k][j].digitToInt() >= input[i][j].digitToInt())
                        break
                }

                var downView = 0
                for (k in 1 .. input.size - 1 - i ) {
                    downView += 1
                    if (input[i + k][j].digitToInt() >= input[i][j].digitToInt())
                        break
                }

                var leftView = 0
                for (k in 1 .. j ) {
                    leftView += 1
                    if (input[i][j - k].digitToInt() >= input[i][j].digitToInt())
                        break
                }

                var rightView = 0
                for (k in 1 .. input[0].length - 1 - j ) {
                    rightView += 1
                    if (input[i][j + k].digitToInt() >= input[i][j].digitToInt())
                        break
                }

                val score = topView * downView * leftView * rightView
                if (score > maxScenicScore) maxScenicScore = score
            }
        }
        return maxScenicScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
