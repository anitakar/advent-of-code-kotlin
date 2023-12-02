package aoc2023

import readInput

fun main() {
    val MAX_RED = 12
    val MAX_GREEN = 13
    val MAX_BLUE = 14
    val RED_REGEX = " (\\d+) red".toRegex()
    val GREEN_REGEX = " (\\d+) green".toRegex()
    val BLUE_REGEX = " (\\d+) blue".toRegex()

    fun part1(input: List<String>): Long {
        var sumOfIds = 0L
        for ((index, line) in input.withIndex()) {
            var valid = true

            val reds = RED_REGEX.findAll(line)
            for (red in reds) {
                if (red.groups[1]!!.value.toInt() > MAX_RED) {
                    valid = false; break;
                }
            }

            val greens = GREEN_REGEX.findAll(line)
            for (green in greens) {
                if (green.groups[1]!!.value.toInt() > MAX_GREEN) {
                    valid = false; break;
                }
            }

            val blues = BLUE_REGEX.findAll(line)
            for (blue in blues) {
                if (blue.groups[1]!!.value.toInt() > MAX_BLUE) {
                    valid = false; break;
                }
            }
            if (valid) sumOfIds += (index + 1)
        }
        return sumOfIds
    }

    fun part2(input: List<String>): Long {
        var sumOfPowers = 0L
        for ((index, line) in input.withIndex()) {
            var minReds = 0
            val reds = RED_REGEX.findAll(line)
            for (red in reds) {
                val minRed = red.groups[1]!!.value.toInt()
                if (minRed > minReds) {
                    minReds = minRed
                }
            }

            var minGreens = 0
            val greens = GREEN_REGEX.findAll(line)
            for (green in greens) {
                val minGreen = green.groups[1]!!.value.toInt()
                if (minGreen > minGreens) {
                    minGreens = minGreen
                }
            }

            var minBlues = 0
            val blues = BLUE_REGEX.findAll(line)
            for (blue in blues) {
                val minBlue = blue.groups[1]!!.value.toInt()
                if (minBlue > minBlues) {
                    minBlues = minBlue
                }
            }
            sumOfPowers += (minReds * minGreens * minBlues)
        }
        return sumOfPowers
    }

    println(part1(readInput("aoc2023/Day02")))
    println(part2(readInput("aoc2023/Day02")))
}