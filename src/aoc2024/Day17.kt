package aoc2024

import readInput


fun main() {

    fun part1Test(): List<Int> {
        val computer = ComputerDay17(729, 0, 0, 0)
        val output = computer.calculate(listOf(0,1,5,4,3,0))
        println(output)
        return output
    }

    fun part1(): List<Int> {
        val computer = ComputerDay17(62769524, 0, 0, 0)
        val output = computer.calculate(listOf(2,4,1,7,7,5,0,3,4,0,1,7,5,5,3,0))
        println(output)
        val computer2 = ComputerDay17(62769524, 0, 0, 0)
        val result = computer2.calculate(output)
        println(result)
        return result
    }

    fun part2(input: List<String>): Int {
        return 0
    }



    println(part1Test())
    println(part1())
//    println(part2(readInput("aoc2024/Day17_test")))
//    println(part2(readInput("aoc2024/Day17")))
}