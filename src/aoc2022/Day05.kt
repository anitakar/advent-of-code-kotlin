package aoc2022

import println
import readInput
import java.util.*

fun main() {

    fun part1(input: List<String>): String {
        var firstLine = true
        var stacks: MutableList<LinkedList<Char>> = mutableListOf()
        var stacksNum = 0
        var lineIt = input.iterator()
        var line = lineIt.next()
        while (line.isNotBlank()) {
            if ("""( (\d+)( )+)+""".toRegex().matches(line)) {
                line = lineIt.next();
                continue
            }

            // nx3 + n-1 = x -> nx4 = x + 1 -> n = (x+1)/4
            if (firstLine) {
                stacksNum = (line.length + 1) / 4
                for (i in 0 until stacksNum) stacks.add(LinkedList<Char>())
                firstLine = false
            }

            val elems = line.replace("    ", "[!]")
                .replace(" ", "")
                .replace("[", "")
                .replace("]", "")
            for (i in 0 until stacksNum) {
                if (elems[i] != '!') {
                    stacks[i].addFirst(elems[i])
                }
            }
            line = lineIt.next()
        }

        val regex = """move (\d+) from (\d+) to (\d+)""".toRegex()
        while (lineIt.hasNext()) {
            line = lineIt.next()
            val matchResult = regex.find(line)
            val (num, from, to) = matchResult!!.destructured
            for (i in 0 until num.toInt()) {
                stacks[to.toInt() - 1].add(stacks[from.toInt() - 1].pollLast())
            }
        }
        val firstLetters = stacks.map { "" + it.pollLast() }
        return firstLetters.reduce { acc, s -> acc + s }
    }

    fun part2(input: List<String>): String {
        var firstLine = true
        var stacks: MutableList<LinkedList<Char>> = mutableListOf()
        var stacksNum = 0
        var lineIt = input.iterator()
        var line = lineIt.next()
        while (line.isNotBlank()) {
            if ("""( (\d+)( )+)+""".toRegex().matches(line)) {
                line = lineIt.next();
                continue
            }

            // nx3 + n-1 = x -> nx4 = x + 1 -> n = (x+1)/4
            if (firstLine) {
                stacksNum = (line.length + 1) / 4
                for (i in 0 until stacksNum) stacks.add(LinkedList<Char>())
                firstLine = false
            }

            val elems = line.replace("    ", "[!]")
                .replace(" ", "")
                .replace("[", "")
                .replace("]", "")
            for (i in 0 until stacksNum) {
                if (elems[i] != '!') {
                    stacks[i].addFirst(elems[i])
                }
            }
            line = lineIt.next()
        }

        val regex = """move (\d+) from (\d+) to (\d+)""".toRegex()
        while (lineIt.hasNext()) {
            line = lineIt.next()
            val matchResult = regex.find(line)
            val (num, from, to) = matchResult!!.destructured

            stacks[to.toInt() - 1].addAll(
                stacks[from.toInt() - 1].subList(
                    stacks[from.toInt() - 1].size - num.toInt(),
                    stacks[from.toInt() - 1].size
                )
            )

            for (i in 0 until num.toInt())
                stacks[from.toInt() - 1].pollLast()

        }
        val firstLetters = stacks.map { "" + it.pollLast() }
        return firstLetters.reduce { acc, s -> acc + s }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
