package aoc2023

import readInput

fun main() {
    fun resolveSequence(start: List<Int>): Long {
        var totalLastValues = 0L
        var current = start
        var next = mutableListOf<Int>()
        while (current.any { it != 0 }) {
            for (i in 0 until current.size - 1) {
                next.add(current[i + 1] - current[i])
            }

            totalLastValues += current.last()

            current = next
            next = mutableListOf()
        }
        return totalLastValues
    }

    fun resolveSequenceBackwards(start: List<Int>): Int {
        var firstValues = mutableListOf<Int>()
        var current = start
        var next = mutableListOf<Int>()
        while (current.any { it != 0 }) {
            for (i in 0 until current.size - 1) {
                next.add(current[i + 1] - current[i])
            }

            firstValues.add(current.first())

            current = next
            next = mutableListOf()
        }
        firstValues.add(0)
        // new + cur = prev; new = prev - cur
        var cur = 0
        for (i in firstValues.size - 1 downTo 0) {
            cur = (firstValues[i] - cur)
        }
        return cur
    }

    fun part1(lines: List<String>): Long {
        var sum = 0L
        for (line in lines) {
            sum += resolveSequence(line.trim().split(" ").map { it.toInt() })
        }
        return sum
    }

    fun part2(lines: List<String>): Long {
        var sum = 0L
        for (line in lines) {
            sum += resolveSequenceBackwards(line.trim().split(" ").map { it.toInt() })
        }
        return sum
    }

    println(part1(readInput("aoc2023/Day09_test")))
    println(part1(readInput("aoc2023/Day09")))
    println(part2(readInput("aoc2023/Day09_test")))
    println(part2(readInput("aoc2023/Day09")))
}