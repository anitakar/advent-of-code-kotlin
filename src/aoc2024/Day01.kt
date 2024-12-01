package aoc2024

import readInput
import kotlin.math.abs


fun main() {
    fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        for (line in input) {
            val splitted = line.split("\\s+".toRegex())
            list1.add(splitted[0].toInt())
            list2.add(splitted[1].toInt())
        }
        return Pair(list1, list2)
    }

    fun part1(input: List<String>): Int {
        var (list1, list2) = parseInput(input)
        var total = 0
        list1 = list1.sorted()
        list2 = list2.sorted()
        for (i in 0 until list1.size) {
            total += abs(list1[i] - list2[i])
        }
        return total
    }

    fun part2(input: List<String>): Int {
        val (list1, list2) = parseInput(input)
        var total = 0
        val list1Lookup = list1.groupBy { it }.mapValues { it.value.size }
        for (number in list2) {
            val occurrences = list1Lookup[number] ?: 0
            total += number * occurrences
        }
        return total
    }



    println(part1(readInput("aoc2024/Day01_test")))
    println(part1(readInput("aoc2024/Day01")))
    println(part2(readInput("aoc2024/Day01_test")))
    println(part2(readInput("aoc2024/Day01")))
}