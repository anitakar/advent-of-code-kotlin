package aoc2022

import readInput

fun main() {

    fun points(me: Char): Int {
        return me.minus('X') + 1
    }

    fun draw(op: Char, me: Char): Boolean {
        return (op == 'A' && me == 'X') || (op == 'B' && me == 'Y') || (op == 'C' && me == 'Z')
    }

    fun won(op: Char, me: Char): Boolean {
        return (op == 'A' && me == 'Y') || (op == 'B' && me == 'Z') || (op == 'C' && me == 'X')
    }

    fun move(op: Char, result: Char): Char {
        if (result == 'Y') {
            return when (op) {
                'A' -> 'X'
                'B' -> 'Y'
                'C' -> 'Z'
                else -> 'X'
            }
        } else if (result == 'X') {
            return when (op) {
                'A' -> 'Z'
                'B' -> 'X'
                'C' -> 'Y'
                else -> 'X'
            }
        } else {
            return when (op) {
                'A' -> 'Y'
                'B' -> 'Z'
                'C' -> 'X'
                else -> 'X'
            }
        }
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (play in input) {
            val opAndMe = play.split(" ")
            val op = opAndMe[0][0]
            val me = opAndMe[1][0]
            sum += points(me)

            if (draw(op, me)) {
                sum += 3
            } else if (won(op, me)) {
                sum += 6
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (play in input) {
            val opAndResult = play.split(" ")
            val op = opAndResult[0][0]
            val result = opAndResult[1][0]
            val me = move(op, result)
            sum += points(me)

            if (draw(op, me)) {
                sum += 3
            } else if (won(op, me)) {
                sum += 6
            }
        }
        return sum
    }

    val testInput = readInput("Day02_test")
    println(part1(testInput))
    check(part1(testInput) == 15)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}