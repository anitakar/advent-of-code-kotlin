package aoc2024

import readInput

fun main() {

    fun getNeighbours(pairs: List<String>): Map<String, List<String>> {
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
        return neighbours
    }

    fun getTripletsOneStartingWithT(pairs: List<String>, neighbours: Map<String, List<String>>): Set<String> {
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
        return triplets
    }

    fun getTriplets(pairs: List<String>, neighbours: Map<String, List<String>>): Set<String> {
        val triplets = mutableSetOf<String>()
        for (neigh in neighbours) {
            if (neigh.value.size <= 1)
                continue

            for (i in 0 .. (neigh.value.size - 2)) {
                for (j in i .. (neigh.value.size - 1)) {
                    if (pairs.contains(neigh.value[i] + "-" + neigh.value[j]) ||
                        pairs.contains(neigh.value[j] + "-" + neigh.value[i])) {
                        val sorted = listOf(neigh.key, neigh.value[i], neigh.value[j]).sorted()
                        triplets.add(sorted.joinToString(","))
                    }
                }
            }
        }
        return triplets
    }

    fun getGroupsOneLarger(pairs: List<String>, prev: Set<String>, neighbours: Map<String, List<String>>): Set<String> {
        val result = mutableSetOf<String>()
        for (candidateGroup in prev) {
            val elems = candidateGroup.split(",")
            for (candidate in neighbours[elems[0]!!]!!.toSet()) {
                if (elems.contains(candidate))
                    continue

                val allContain = elems.all {
                    pairs.contains(candidate + "-" + it) || pairs.contains(it + "-" + candidate)
                }

                if (allContain) {
                    val toSort = mutableListOf<String>()
                    toSort.addAll(elems)
                    toSort.add(candidate)
                    val sorted = toSort.sorted()
                    result.add(sorted.joinToString(","))
                }
            }
        }
        return result
    }

    fun part1(pairs: List<String>): Int {
        val neighbours = getNeighbours(pairs)
        val triplets = getTripletsOneStartingWithT(pairs, neighbours)
        return triplets.size
    }

    fun part2(pairs: List<String>): List<String> {
        val neighbours = getNeighbours(pairs)
        val triplets = getTriplets(pairs, neighbours)
        var prevGroups: Set<String> = triplets
        var nextGroups: Set<String>? = null
        do {
            nextGroups = getGroupsOneLarger(pairs, prevGroups, neighbours)
            if (nextGroups.isNotEmpty()) {
                prevGroups = nextGroups
            }
        } while (nextGroups != null && nextGroups.isNotEmpty())
        return prevGroups.sorted()
    }

    println(part1(readInput("aoc2024/Day23_test")))
    println(part1(readInput("aoc2024/Day23")))
    println(part2(readInput("aoc2024/Day23_test")))
    println(part2(readInput("aoc2024/Day23")))
}