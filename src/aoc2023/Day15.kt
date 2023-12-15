package aoc2023

import readInput

fun main() {
    fun hash(step: String): Int {
        var hash = 0
        for (c in step) {
            hash += c.code
            hash *= 17
            hash %= 256
        }
        return hash
    }

    fun part1(lines: List<String>): Int {
        val steps = lines[0].split(',')
        var sum = 0
        for (step in steps) {
            sum += hash(step)
        }
        return sum
    }

    fun part2(input: String): Int {
        val steps = input.split(',')
        val boxes = Array(256) { mutableListOf<Pair<String, Int>>() }

        for (step in steps) {
            if (step.contains('=')) {
                val splitted = step.split('=')
                val label = splitted[0]
                val box = hash(label)
                val focalLength = splitted[1].toInt()
                val index = boxes[box].indexOfFirst { it.first == label }
                if (index == -1) {
                    boxes[box].add(Pair(label, focalLength))
                } else {
                    boxes[box][index] = Pair(label, focalLength)
                }
            } else {
                val label = step.substring(0, step.length - 1)
                val box = hash(label)
                val index = boxes[box].indexOfFirst { it.first == label }
                if (index != -1) {
                    boxes[box].removeAt(index)
                }
            }
        }

        var sum = 0
        for (i in boxes.indices) {
            for (slotIndex in boxes[i].indices)  {
                sum += (i + 1) * (slotIndex + 1) * boxes[i][slotIndex].second
            }
        }
        return sum
    }

    println(part1(readInput("aoc2023/Day15_test")))
    println(part1(readInput("aoc2023/Day15")))
    println(part2(readInput("aoc2023/Day15_test")[0]))
    println(part2(readInput("aoc2023/Day15")[0]))
}