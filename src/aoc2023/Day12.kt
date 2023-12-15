package aoc2023

import readInput

fun main() {
    fun arrangementForSequence(sequence: String): String {
        var result = ""
        var count = 0
        for (i in sequence.indices) {
            if (sequence[i] == '#') {
                count += 1
            } else {
                if (count > 0) {
                    result = "$result,$count"
                    count = 0
                }
            }
        }
        if (count > 0) {
            result = "$result,$count"
        }
        if (result.isNotEmpty()) {
            result = result.removeRange(0, 1)
        }
        return result
    }

    fun replaceFirstQuestionMark(sequence: String): MutableList<String> {
        val qustionMarkIndex = sequence.indexOfFirst { it == '?' }
        if (qustionMarkIndex == -1) {
            return mutableListOf(sequence)
        } else {
            return mutableListOf(
                sequence.substring(0, qustionMarkIndex) + "." + sequence.substring(qustionMarkIndex + 1),
                sequence.substring(0, qustionMarkIndex) + "#" + sequence.substring(qustionMarkIndex + 1)
            )
        }
    }

    fun replaceQuestionMarks(sequence: String): MutableList<String> {
        var current = mutableListOf<String>()
        current.add(sequence)
        var new = mutableListOf<String>()
        var newAdded = false
        do {
            newAdded = false
            for (seq in current) {
                val toAdd = replaceFirstQuestionMark(seq)
                if (toAdd.size > 1) {
                    newAdded = true
                }
                new.addAll(toAdd)
            }
            current = new
            new = mutableListOf()
        } while (newAdded)
        return current
    }

    fun part1(lines: List<String>): Int {
        var sum = 0
        for (line in lines) {
            val spiltted = line.split(" ")
            val sequence = spiltted[0]
            val arrangement = spiltted[1]
            val allCombinations = replaceQuestionMarks(sequence)
            for (replaced in allCombinations) {
                if (arrangementForSequence(replaced) == arrangement) {
                    sum += 1
                }
            }
        }
        return sum
    }

//    println(arrangementForSequence("#.#.###"))
//    println(arrangementForSequence(".#...#....###."))
//    println(arrangementForSequence(".#.###.#.######"))
//    println(arrangementForSequence("####.#...#..."))
//    println(arrangementForSequence("#....######..#####."))
//    println(arrangementForSequence(".###.##....#"))

//    println(part1(listOf("???.### 1,1,3")))

    println(part1(readInput("aoc2023/Day12_test")))
    println(part1(readInput("aoc2023/Day12")))
}