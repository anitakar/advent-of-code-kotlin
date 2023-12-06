package aoc2023

import readInput
import kotlin.text.Typography.times

fun main() {
    fun part1(lines: List<String>): Long {
        val times =
            "(Time:\\s+)(.*)".toRegex().findAll(lines[0]).iterator().next().groupValues[2].split("\\s+".toRegex())
                .map { it.toInt() }
        val distances =
            "(Distance:\\s+)(.*)".toRegex().findAll(lines[1]).iterator().next().groupValues[2].split("\\s+".toRegex())
                .map { it.toInt() }

        var result = 1L
        for (i in times.indices) {
            var ways = 0
            for (j in 1 until times[i]) {
                val distance = (j * (times[i] - j))
                if (distance > distances[i]) {
                    ways += 1
                }
            }
            result *= ways
        }

        return result
    }

    fun part2(lines: List<String>): Long {
        val time =
            "(Time:\\s+)(.*)".toRegex().findAll(lines[0]).iterator().next().groupValues[2].replace("\\s+".toRegex(), "")
                .toLong()
        val distance =
            "(Distance:\\s+)(.*)".toRegex().findAll(lines[1]).iterator().next().groupValues[2].replace(
                "\\s+".toRegex(),
                ""
            ).toLong()

        // binary search index of the first element less than distance
        var start = 0L
        var end = time / 2L
        var speed = (start + end) / 2L

        while (end - start > 1) {
            val curDistance = speed * (time - speed)
            if (curDistance > distance) {
                end = speed
            } else {
                start = speed
            }
            speed = (start + end) / 2L
        }

        return time - 2 * speed - 1
    }

    println(part1(readInput("aoc2023/Day06_test")))
    println(part1(readInput("aoc2023/Day06")))

    println(part2(readInput("aoc2023/Day06_test")))
    println(part2(readInput("aoc2023/Day06")))
}