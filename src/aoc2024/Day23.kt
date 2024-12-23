package aoc2024

import readInput

fun main() {

    fun part1(pairs: List<String>): Int {
        val neighbours = mutableMapOf<String, MutableList<String>>()
        for (pair in pairs) {
            val (first, second) = pair.split("-")
            if (!neighbours.contains(first)) {
                neighbours[first] = mutableListOf()
            }
            if (!neighbours.contains(second)) {
                neighbours[second] = mutableListOf()
            }
            neighbours[first]?.add(second)
            neighbours[second]?.add(first)
        }
        val triplets = mutableSetOf<String>()
        for (neigh in neighbours) {
            if (neigh.value.size <= 1)
                continue

            for (i in 0 .. (neigh.value.size - 2)) {
                for (j in i .. (neigh.value.size - 1)) {
                    if (!neigh.key.startsWith('t') &&
                        !neigh.value[i].startsWith('t') && !
                        neigh.value[j].startsWith('t')) {
                        continue
                    }

                    if (pairs.contains(neigh.value[i] + "-" + neigh.value[j]) ||
                        pairs.contains(neigh.value[j] + "-" + neigh.value[i])) {
                        val sorted = listOf(neigh.key, neigh.value[i], neigh.value[j]).sorted()
                        triplets.add(sorted.joinToString(","))
                    }
                }
            }
        }
        return triplets.size
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    println(part1(readInput("aoc2024/Day23_test")))
    println(part1(readInput("aoc2024/Day23")))
//    println(part2(readInput("aoc2024/Day23_test")))
//    println(part2(readInput("aoc2024/Day23")))
}