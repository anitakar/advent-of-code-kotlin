package aoc2024

import readInput


fun main() {

    val numericKeyPadPresses = mapOf(
        Pair('0', 'A') to listOf(">"),
        Pair('0', '1') to listOf("^<"),
        Pair('0', '2') to listOf("^"),
        Pair('0', '3') to listOf(">^", "^>"),
        Pair('0', '4') to listOf("^^<", "^<^"),
        Pair('0', '5') to listOf("^^"),
        Pair('0', '6') to listOf("^^>", "^>^"),
        Pair('0', '7') to listOf("^^^<", "^<^^", "^^<^"),
        Pair('0', '8') to listOf("^^^"),
        Pair('0', '9') to listOf("^^^", "^>^^", "^^>^"),
        Pair('A', '0') to listOf("<"),
        Pair('A', '1') to listOf("<^<", "^<<"),
        Pair('A', '2') to listOf("<^", "^<"),
        Pair('A', '3') to listOf("^"),
        Pair('A', '4') to listOf("^^<<", "^<<^", "<^^<", "<^<^"),
        Pair('A', '5') to listOf("^^<", "^<^", "<^^"),
        Pair('A', '6') to listOf("^^"),
        Pair('A', '7') to listOf("^^^<<", "^^<^<", "^^<<^"),
    )

    val directionalKeyPadPresses = mapOf(
        Pair('<', 'v') to listOf(">"),
        Pair('<', '^') to listOf(">^"),
        Pair('<', 'A') to listOf(">>^", ">^>"),
        Pair('v', '>') to listOf(">"),
        Pair('v', '<') to listOf("<"),
        Pair('v', 'A') to listOf(">^", "^>"),
        Pair('^', '<') to listOf("v<"),
        Pair('^', '>') to listOf("v>", ">v"),
        Pair('^', 'A') to listOf(">"),
        Pair('>', 'v') to listOf("<"),
        Pair('>', '^') to listOf("<^", "6<"),
        Pair('>', 'A') to listOf("^"),
        Pair('A', '<') to listOf("<v<", "v<<"),
        Pair('A', '^') to listOf("<"),
        Pair('A', 'v') to listOf("<v", "v<"),
        Pair('A', '>') to listOf("v"),
    )

    fun keyPadDirections(code: String): String {
        var result = ""
        var first = 'A'
        var second = code[0]
        var third = code[1]
        var fourth = code[2]
        var fifth = code[3]

        result = result + numericKeyPadPresses[Pair(first, second)]!![0]
        result = result + numericKeyPadPresses[Pair(second, third)]!![0]
        result = result + numericKeyPadPresses[Pair(third, fourth)]!![0]
        result = result + numericKeyPadPresses[Pair(fourth, fifth)]!![0]

        return result
    }

    fun keyPadKeyPadDirections(code: String): String {
        var result: String = ""

        var prev: Char = 'A'
        for (i in 0 until code.length - 1) {
            val next: Char = code[i]
            result = result + directionalKeyPadPresses[Pair(prev, next)]!![0]
            prev = next
        }

        return result
    }

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }



    println(part1(readInput("aoc2024/Day21_test")))
    println(part1(readInput("aoc2024/Day21")))
    println(part2(readInput("aoc2024/Day21_test")))
    println(part2(readInput("aoc2024/Day21")))
}