package aoc2022

import println
import readInput
import java.util.*

fun main() {

    data class Position(val x: Int, val y: Int) : Comparable<Position> {
        public var distance: Int = Int.MAX_VALUE

        override fun compareTo(other: Position): Int {
            return distance - other.distance
        }

        fun neighbours(): List<Position> {
            return listOf(Position(x + 1, y), Position(x - 1, y), Position(x, y + 1), Position(x, y - 1))
        }
    }

    class Terrain(val input: List<String>) {
        fun contains(position: Position): Boolean {
            return position.x >= 0 && position.x < input.size && position.y >= 0 && position.y < input[0].length
        }

        fun value(position: Position): Char {
            if (input[position.x][position.y] == 'S') return 'a'
            if (input[position.x][position.y] == 'E') return 'z'
            return input[position.x][position.y]
        }
    }

    fun minDistance(input: List<String>, start: Position): Int {
        val terrain = Terrain(input)
        val queue = PriorityQueue<Position>()
        val visited = mutableSetOf<Position>()
        start.distance = 0
        queue.add(start)
        while (queue.isNotEmpty()) {
            val elem = queue.poll()
            visited.add(elem)

            for (n in elem.neighbours()) {
                if (terrain.contains(n) && !visited.contains(n) && terrain.value(elem) - terrain.value(n) >= -1) {
                    if (input[n.x][n.y] == 'E')
                        return elem.distance + 1
                    if (queue.contains(n)) {
                        val inQueue = queue.find { it.equals(n) }
                        queue.remove(inQueue)
                        inQueue!!.distance = Math.min(inQueue!!.distance, elem.distance + 1)
                        queue.add(inQueue)
                    } else {
                        n.distance = elem.distance + 1
                        queue.add(n)
                    }
                }
            }
        }
        return Int.MAX_VALUE
    }

    fun part1(input: List<String>): Int {
        var start = Position(0, 0)
        for (i in input.indices) {
            for (j in input.indices) {
                if (input[i][j] == 'S') {
                    start = Position(i, j)
                }
            }
        }
        return minDistance(input, start)
    }

    fun part2(input: List<String>): Int {
        val starts = mutableListOf<Position>()
        for (i in input.indices) {
            for (j in input[0].indices) {
                if (input[i][j] == 'S' || input[i][j] == 'a') {
                    starts.add(Position(i, j))
                }
            }
        }
        var min = Int.MAX_VALUE
        for (start in starts) {
            val dist = minDistance(input, start)
            min = Math.min(min, dist)
        }
        return min
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
