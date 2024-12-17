package aoc2024

import readInput


fun main() {

    fun part1(input: List<String>): List<Int> {
        val computer = ComputerDay17(729, 0, 0)
        val output = computer.calculate(listOf(0,1,5,4,3,0))
        val computer2 = ComputerDay17(729, 0, 0)
        val result = computer2.calculate(output)
        return result
    }

    fun part2(input: List<String>): Int {
        return 0
    }



    println(part1(readInput("aoc2024/Day17_test")))
    println(part1(readInput("aoc2024/Day17")))
    println(part2(readInput("aoc2024/Day17_test")))
    println(part2(readInput("aoc2024/Day17")))
}