package aoc2023

import readInput

fun main() {
    fun part1(lines: List<String>): Int {
        val dish = Array(lines.size) { Array(lines[0].length) { '.' } }
        for (i in lines.indices) {
            dish[i] = lines[i].toCharArray().toTypedArray()
        }

        var moved: Boolean
        do {
            moved = false
            for (i in 1 until dish.size) {
                for (j in dish[i].indices) {
                    if (dish[i][j] == 'O' && dish[i - 1][j] == '.') {
                        moved = true
                        dish[i - 1][j] = 'O'
                        dish[i][j] = '.'
                    }
                }
            }
        } while (moved)

        var sum = 0
        for (i in dish.indices) {
            sum += dish[i].count { it == 'O' } * (dish.size - i)
        }
        return sum
    }

    println(part1(readInput("aoc2023/Day14_test")))
    println(part1(readInput("aoc2023/Day14")))
}