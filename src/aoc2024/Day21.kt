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

    fun keyPadKeyPadDirections(code: String): List<String> {
        var result = mutableListOf<String>()
        var prev = 'A'

        for (next in code) {
            val dijkstra = DijkstraDirectionalKeyPad()
            dijkstra.shortestPath(prev, next)
            val paths = dijkstra.minPaths(next).map { keysToPressesDirectionalKeyPad(it) + 'A' }.map { it.joinToString(separator = "") }

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

    fun finalDirections(code: String): String {
        val possibleRobot1Directions = keyPadDirections(code)
        val possibleRobot2Directions = possibleRobot1Directions.map {
            keyPadKeyPadDirections(it)
        }.flatten()
        val possibleFinalDirections = possibleRobot2Directions.map {
            keyPadKeyPadDirections(it)
        }.flatten()
        return possibleFinalDirections.minBy { it.length }
    }

    fun complexity(directions: String, code: String): Long {
        return directions.length * (code.substring(0, 3).toLong())
    }

    fun part1(input: List<String>): Long {
        var total = 0L
        for (code in input) {
            val directions = finalDirections(code)
            total += complexity(directions, code)
        }
        return total
    }

    fun part2(input: List<String>): Int {
        return 0
    }


    println(keyPadDirections("029A"))
    println(complexity(finalDirections("029A"), "029A"))
    println(part1(readInput("aoc2024/Day21")))
//    println(part2(readInput("aoc2024/Day21_test")))
//    println(part2(readInput("aoc2024/Day21")))
}