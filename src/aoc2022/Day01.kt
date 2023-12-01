package aoc2022

import println
import readInput
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0;
        var maxSum = 0;
        for (cur in input) {
            if (cur.isEmpty()) {
                if (sum > maxSum) {
                    maxSum = sum
                }
                sum = 0
            } else {
                sum += cur.toInt()
            }
        }
        return maxSum
    }

    fun part2(input: List<String>): Int {
        val queue = PriorityQueue<Int>()
        var sum = 0
        for (elem in input) {
            if (elem.isBlank()) {
                if (queue.size >= 3) {
                    if (queue.peek() < sum) {
                        queue.poll()
                        queue.add(sum)
                    }
                } else {
                    queue.add(sum)
                }
                sum = 0;
            } else {
                sum += elem.toInt()
            }
        }
        return queue.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
