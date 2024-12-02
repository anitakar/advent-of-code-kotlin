package aoc2024

import readInput
import kotlin.math.abs


fun main() {
    fun isSafe(array: List<Int>): Boolean {
        if (array.size <= 1)
            return true
        val increasing = array[1] > array[0]

        for (i in 1 until array.size) {
            if (abs(array[i] - array[i-1]) < 1 || abs(array[i] - array[i-1]) > 3)
                return false

            if (array[i] > array[i-1] && !increasing)
                return false

            if (array[i] < array[i - 1] && increasing)
                return false
        }

        return true
    }

    fun isSafeWithDampener(array: List<Int>): Boolean {
        var isSafe = false
        for (i in 0 until array.size) {
            isSafe = isSafe || isSafe(array.subList(0,i) + array.subList(i+1,array.size))
        }
        return isSafe
    }

    fun part1(input: List<String>): Int {
        var safe = 0
        for (line in input) {
            if (isSafe(line.split("\\s+".toRegex()).map { it.toInt() }))
                safe++
        }
        return safe
    }

    fun part2(input: List<String>): Int {
        var safe = 0
        for (line in input) {
            if (isSafeWithDampener(line.split("\\s+".toRegex()).map { it.toInt() }))
                safe++
        }
        return safe
    }

    println(part1(readInput("aoc2024/Day02_test")))
    println(part1(readInput("aoc2024/Day02")))
    println(part2(readInput("aoc2024/Day02_test")))
    println(part2(readInput("aoc2024/Day02")))
}