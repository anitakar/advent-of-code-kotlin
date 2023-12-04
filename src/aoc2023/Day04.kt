package aoc2023

import readInput

fun main() {
    val regex = "Card[^:]+:([^|]+)\\|([^|]+)".toRegex()

    fun part1(lines: List<String>): Int {
        var sum = 0
        for (line in lines) {
            val matches = regex.findAll(line)
            val winning = matches.first().groupValues[1].trim().split("\\s+".toRegex()).map { it.toInt() }.toSet()
            val mine = matches.first().groupValues[2].trim().split("\\s+".toRegex()).map { it.toInt() }.toSet()
            val wins = winning.intersect(mine).size
            if (wins > 0) {
                sum += (1 shl (wins - 1))
            }
        }
        return sum
    }

    fun part2(lines: List<String>): Int {
        val copies = Array(lines.size) { 1 }
        for ((index, line) in lines.withIndex()) {
            val matches = regex.findAll(line)
            val winning = matches.first().groupValues[1].trim().split("\\s+".toRegex()).map { it.toInt() }.toSet()
            val mine = matches.first().groupValues[2].trim().split("\\s+".toRegex()).map { it.toInt() }.toSet()
            val wins = winning.intersect(mine).size
            for (i in (index + 1)..(index + wins)) {
                if (i < copies.size) {
                    copies[i] += copies[index]
                }
            }
        }
        return copies.sum()
    }

    println(part1(readInput("aoc2023/Day04_test")))
    println(part1(readInput("aoc2023/Day04")))
    println(part2(readInput("aoc2023/Day04_test")))
    println(part2(readInput("aoc2023/Day04")))
}