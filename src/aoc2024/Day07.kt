package aoc2024

import readInput
import kotlin.math.max


fun main() {

    fun canBeSolved1(numbers: List<Long>, resultSoFar: Long, expected: Long): Boolean {
        if (numbers.isEmpty()) {
            return resultSoFar == expected
        }

        return canBeSolved1(numbers.subList(1, numbers.size), resultSoFar * numbers[0], expected)
                || canBeSolved1(numbers.subList(1, numbers.size), resultSoFar + numbers[0], expected)
    }

    fun canBeSolved2(numbers: List<Long>, resultSoFar: Long, expected: Long): Boolean {
        if (numbers.isEmpty()) {
            return resultSoFar == expected
        }

        return canBeSolved2(numbers.subList(1, numbers.size), resultSoFar * numbers[0], expected)
                || canBeSolved2(numbers.subList(1, numbers.size), resultSoFar + numbers[0], expected)
                || canBeSolved2(numbers.subList(1, numbers.size), (resultSoFar.toString() + numbers[0].toString()).toLong(), expected)
    }


    fun part1(input: List<String>): Long {
        var total = 0L
        for (line in input) {
            val resultAndNumbers = line.split(":\\s?".toRegex())
            val result = resultAndNumbers[0].toLong()
            val numbers = resultAndNumbers[1].split("\\s+".toRegex()).map { it.toLong() }
            if (canBeSolved1(numbers.subList(1, numbers.size), numbers[0], result)) {
                total += result
            }
        }
        return total
    }

    fun part2(input: List<String>): Long {
        var total = 0L
        for (line in input) {
            val resultAndNumbers = line.split(":\\s?".toRegex())
            val result = resultAndNumbers[0].toLong()
            val numbers = resultAndNumbers[1].split("\\s+".toRegex()).map { it.toLong() }
            if (canBeSolved2(numbers.subList(1, numbers.size), numbers[0], result)) {
                total += result
            }
        }
        return total
    }

    println(part1(readInput("aoc2024/Day07_test")))
    println(part1(readInput("aoc2024/Day07")))
    println(part2(readInput("aoc2024/Day07_test")))
    println(part2(readInput("aoc2024/Day07")))
}