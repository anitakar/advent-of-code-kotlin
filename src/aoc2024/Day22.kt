package aoc2024

import readInput


fun main() {

    fun nextSecret(secret: Long): Long {
        var secret = (secret xor (secret * 64)) % 16777216
        secret = (secret xor (secret / 32)) % 16777216
        secret = (secret xor (secret * 2048)) % 16777216
        return secret
    }

    fun part1(input: List<String>): Long {
        var result = 0L
        for (line in input) {
            var secret = line.toLong()
            for (i in 0 until 2000) {
                secret = nextSecret(secret)
            }
            result += secret
        }
        return result
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    println(nextSecret(123))
    println(nextSecret(nextSecret(123)))

    println(part1(readInput("aoc2024/Day22_test")))
    println(part1(readInput("aoc2024/Day22")))
//    println(part2(readInput("aoc2024/Day22_test")))
//    println(part2(readInput("aoc2024/Day22")))
}