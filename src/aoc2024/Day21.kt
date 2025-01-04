package aoc2024

import readInput


fun main() {

    val numericKeyPad = DijkstraNumericKeyPad()
    val directionalKeyPad = DijkstraDirectionalKeyPad()

    fun keysToPressesNumericKeyPad(keys: List<Char>): List<Char> {
        val result = mutableListOf<Char>()
        for (i in (keys.size - 1) downTo 1) {
            result.add(numericKeyPad.getDirection(keys[i], keys[i-1]))
        }
        return result
    }

    fun keysToPressesDirectionalKeyPad(keys: List<Char>): List<Char> {
        val result = mutableListOf<Char>()
        for (i in (keys.size - 1) downTo 1) {
            result.add(directionalKeyPad.getDirection(keys[i], keys[i-1]))
        }
        return result
    }

    fun numChanges(code: String): Long {
        var numChanges = 0L
        for (i in 1 until code.length) {
            if (code[i-1] != code[i]) numChanges++
        }
        return numChanges
    }

    fun keyPadDirections(code: String): List<String> {
        var result = mutableListOf<String>()
        var prev = 'A'

        for (next in code) {
            val dijkstra = DijkstraNumericKeyPad()
            dijkstra.shortestPath(prev, next)
            val paths = dijkstra.minPaths(next).map { keysToPressesNumericKeyPad(it) + 'A' }.map { it.joinToString(separator = "") }

            if (result.isEmpty()) {
                result = paths.toMutableList()
                prev = next
                continue
            }

            if (paths.size == 1) {
                for (i in result.indices) {
                    result[i] = (result[i] + paths[0])
                }
            } else {
                val sizeBeforeAdding = result.size
                for (j in 1 until paths.size) {
                    for (i in 0 until sizeBeforeAdding) {
                        result.add(result[i] + paths[j])
                    }
                }
                for (i in 0 until sizeBeforeAdding) {
                    result[i] = (result[i] + paths[0])
                }
            }
            prev = next
        }

        return result
    }

    val memoizedKeyPadKeyPadDirections = mutableMapOf<Pair<Char, Char>, List<String>>()

    fun distance(code: String): Long {
        var distance = 0L
        for (i in 1 until code.length) {
            if (code[i-1] != code[i]) {
                distance += memoizedKeyPadKeyPadDirections[Pair(code[i-1], code[i])]!!.first().length
            }
        }
        return distance
    }

    fun trimResults(results: MutableList<String>) {
        results.sortBy { distance(it) }
        val minResults = distance(results.first())
        results.removeIf { distance(it) > minResults }
    }

    fun combineResults(result: MutableList<String>, paths: List<String>): MutableList<String> {
        if (result.isEmpty()) {
            result.addAll(paths)
        } else if (paths.size == 1) {
            for (i in result.indices) {
                result[i] = (result[i] + paths[0])
            }
        } else {
            val sizeBeforeAdding = result.size
            for (j in 1 until paths.size) {
                for (i in 0 until sizeBeforeAdding) {
                    result.add(result[i] + paths[j])
                }
            }
            for (i in 0 until sizeBeforeAdding) {
                result[i] = (result[i] + paths[0])
            }
        }
        trimResults(result)
        return result
    }

    fun keyPadKeyPadDirections(code: String): List<String> {
        var result = mutableListOf<String>()
        var prev = 'A'

        for (next in code) {
            val paths = memoizedKeyPadKeyPadDirections[Pair(prev, next)]!!
            result = combineResults(result, paths)
            prev = next
        }

        return result.toSet().toList()
    }

    val possibleCombinations = mutableMapOf<String, List<String>>()
    fun keyPadKeyPadDirectionsCombinations(code: String): List<String> {
        var result = mutableListOf<String>()
        val splitted = code.split('A')
        for (combination in splitted) {
            if (!possibleCombinations.containsKey(combination + 'A')) {
                val combinations = keyPadKeyPadDirections(combination + 'A')
                possibleCombinations[combination + 'A'] = combinations
            }
            val paths = possibleCombinations[combination + 'A']!!
            result = combineResults(result, paths)
        }
        return result.toSet().toList()
    }

    fun memoizeAllKeyPadDirections() {
        for (key1 in listOf('<', '>', 'v', '^', 'A')) {
            for (key2 in listOf('<', '>', 'v', '^', 'A')) {
                val dijkstra = DijkstraDirectionalKeyPad()
                dijkstra.shortestPath(key1, key2)
                val result = dijkstra.minPaths(key2)
                    .map { keysToPressesDirectionalKeyPad(it) + 'A' }
                    .map { it.joinToString(separator = "") }
                    .sortedBy { numChanges(it) }
                val minChanges = numChanges(result.first())
                memoizedKeyPadKeyPadDirections[Pair(key1, key2)] = result.filter { numChanges(it) == minChanges }
            }
        }
    }

    fun finalDirections(code: String): String {
        var possibleRobot1Directions = keyPadDirections(code).sortedBy { distance(it) }
        val minChangesRobot1 = distance(possibleRobot1Directions.first())
        possibleRobot1Directions = possibleRobot1Directions.filter { distance(it) == minChangesRobot1 }
        var possibleRobot2Directions = possibleRobot1Directions.map {
            keyPadKeyPadDirections(it)
        }.flatten().sortedBy { distance(it) }
        val minChangesRobot2 = distance(possibleRobot2Directions.first())
        possibleRobot2Directions = possibleRobot2Directions.filter { distance(it) == minChangesRobot2 }
        val possibleFinalDirections = possibleRobot2Directions.map {
            keyPadKeyPadDirections(it)
        }.flatten()
        return possibleFinalDirections.minBy { it.length }
    }

    fun complexity(directions: String, code: String): Long {
        return directions.length * (code.substring(0, 3).toLong())
    }

    fun secondHistorianDirections(code: String): String {
        val possibleRobot1Directions = keyPadDirections(code).sortedBy { distance(it) }
        val minChangesKeyPad = distance(possibleRobot1Directions.first())
        var possibleDirections = possibleRobot1Directions.filter { distance(it) == minChangesKeyPad }
        for (i in 0 .. 25) {
            possibleDirections = possibleDirections.map {
                keyPadKeyPadDirectionsCombinations(it)
            }.flatten().sortedBy { distance(it) }
            val minChanges = distance(possibleDirections.first())
            possibleDirections = possibleDirections.filter { distance(it) == minChanges }
            println(possibleDirections[0])
        }
        return possibleDirections.minBy { it.length }
    }

    fun part1(input: List<String>): Long {
        memoizeAllKeyPadDirections()
        var total = 0L
        for (code in input) {
            val directions = finalDirections(code)
            total += complexity(directions, code)
        }
        return total
    }

    fun part2(input: List<String>): Long {
        memoizeAllKeyPadDirections()
        var total = 0L
        for (code in input) {
            val directions = secondHistorianDirections(code)
            total += complexity(directions, code)
        }
        return total
    }


//    println(keyPadDirections("029A"))
    memoizeAllKeyPadDirections()
//    println(keyPadKeyPadDirections(keyPadDirections("029A")[0]))
//    println(keyPadKeyPadDirections(keyPadDirections("029A")[1]))
//    println(keyPadKeyPadDirections(keyPadDirections("029A")[2]))
//    println(complexity(finalDirections("029A"), "029A"))
    println(part1(readInput("aoc2024/Day21")))
    println(part2(readInput("aoc2024/Day21")))
}