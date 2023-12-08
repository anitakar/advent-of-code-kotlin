package aoc2023

import readInput

fun main() {
    fun part1(lines: List<String>): Int {
        val steps = lines[0].map { if (it == 'R') 1 else 0 }

        val regex = "(\\w{3}) = \\((\\w{3}), (\\w{3})\\)".toRegex()
        val instructions = lines.subList(2, lines.size).map {
            val groups = regex.find(it)!!.groupValues
            Pair(groups[1], listOf(groups[2], groups[3]))
        }.groupBy({ it.first }, { it.second })

        var instruction = "AAA"
        var stepIndex = 0
        var stepCount = 0
        while (instruction != "ZZZ") {
            instruction = instructions[instruction]!!.flatten()[steps[stepIndex]]
            stepCount += 1

            stepIndex += 1
            stepIndex %= steps.size
        }
        return stepCount
    }

    fun ghostCycle(ghostStart: String, steps: List<Int>, instructions: Map<String, List<List<String>>>): Long {
        var instruction = ghostStart
        var stepIndex = 0
        var stepCount = 0L
        while (!instruction.endsWith('Z')) {
            instruction = instructions[instruction]!!.flatten()[steps[stepIndex]]
            stepCount += 1

            stepIndex += 1
            stepIndex %= steps.size
        }
        return stepCount
    }

    fun findLeastCommonMultiple(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    fun findLeastCommonMultipleOfListOfNumbers(numbers: List<Long>): Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            result = findLeastCommonMultiple(result, numbers[i])
        }
        return result
    }

    fun part2(lines: List<String>): Long {
        val steps = lines[0].map { if (it == 'R') 1 else 0 }

        val regex = "(\\w{3}) = \\((\\w{3}), (\\w{3})\\)".toRegex()
        val instructions = lines.subList(2, lines.size).map {
            val groups = regex.find(it)!!.groupValues
            Pair(groups[1], listOf(groups[2], groups[3]))
        }.groupBy({ it.first }, { it.second })

        var startNodes = instructions.keys.filter { it.endsWith('A') }
        var ghostCycles = startNodes.map { ghostCycle(it, steps, instructions) }
        return findLeastCommonMultipleOfListOfNumbers(ghostCycles)
    }

    println(part1(readInput("aoc2023/Day08_test")))
    println(part1(readInput("aoc2023/Day08")))
    println(part2(readInput("aoc2023/Day08_test_2")))
    println(part2(readInput("aoc2023/Day08")))
}