package aoc2023

import readInput
import java.math.BigDecimal

fun main() {
    fun part1(input: List<String>): BigDecimal {
        var sum = BigDecimal.ZERO
        for (line in input) {
            sum = sum.add((10 * line.find { it.isDigit() }?.digitToInt()!!).toBigDecimal())
            sum = sum.add(line.findLast { it.isDigit() }?.digitToInt()?.toBigDecimal())
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val digits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val digitValues = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )
        var sum = 0L
        for (line in input) {
            val (indexChars, valueChars) = line.findAnyOf(digits) ?: Pair(input.size + 1, 0)
            val indexDigit = line.indexOfFirst { it.isDigit() }
            if (indexDigit < indexChars) {
                sum += 10 * (line[indexDigit].digitToInt())
            } else {
                sum += 10 * (digitValues[valueChars]!!)
            }

            val (indexCharsLast, valueCharsLast) = line.findLastAnyOf(digits) ?: Pair(-1, 0)
            val indexDigitLast = line.indexOfLast { it.isDigit() }
            if (indexDigitLast > indexCharsLast) {
                sum += line[indexDigitLast].digitToInt()
            } else {
                sum += digitValues[valueCharsLast]!!
            }
        }
        return sum
    }

    println(part1(readInput("aoc2023/Day01")))
    println(part2(readInput("aoc2023/Day01")))
}

