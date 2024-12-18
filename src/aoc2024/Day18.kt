package aoc2024

import readInput
import java.util.*
import kotlin.math.min

fun main() {

    fun readObstacles(input: List<String>, numLines: Int): MutableSet<Position> {
        val obstacles = mutableSetOf<Position>()
        for (i in 0 until numLines) {
            val line = input[i].split(",")
            obstacles.add(Position(line[1].toInt(), line[0].toInt()))
        }
        return obstacles
    }

    class Dijkstra(val map: RectangularGrid) {

        val smallest = mutableMapOf<Position, Long>()
        val smallestPrev = mutableMapOf<Position, MutableList<Position>>()
        val visited = mutableSetOf<Position>()
        val toVisit = PriorityQueue<Pair<Position, Long>> { a, b -> (a.second - b.second).toInt() }

        fun shortestPath(start: Position, end: Position): Long? {
            toVisit.add(Pair(start, 0))
            smallest[start] = 0
            while (toVisit.isNotEmpty()) {
                val current = toVisit.poll()
                if (visited.contains(current.first))
                    continue

                visited.add(current.first)
                val cost = current.second

                val neighbours = map.getNeighbours(current.first)
                for (neighbour in neighbours) {
                    if (map.get(neighbour) != '#') {
                        updateCost(neighbour, cost + 1, current.first)
                    }
                }
            }

            return smallest.filter { it.key == end }.minByOrNull { it.value }?.value
        }

        fun updateCost(next: Position?, newCost: Long, prev: Position) {
            if (next != null) {
                if (!smallestPrev.contains(next)) {
                    smallestPrev[next] = mutableListOf()
                }

                if (smallest.contains(next)) {
                    val prevCost = smallest[next]!!
                    smallest[next] = min(smallest[next]!!, newCost)
                    if (newCost == smallest[next]) {
                        if (newCost != prevCost) {
                            smallestPrev[next]!!.clear()
                        }
                        smallestPrev[next]!!.add(prev)
                    }
                } else {
                    smallest[next] = newCost
                    smallestPrev[next]!!.add(prev)
                }

                if (!visited.contains(next)) {
                    toVisit.add(Pair(next, smallest[next]!!))
                }
            }
        }
    }

    fun part1(input: List<String>, numLines: Int, gridSize: Int): Long? {
        val obstacles = readObstacles(input, numLines)
        val map = RectangularGrid(gridSize, gridSize, obstacles)
        val dijkstra = Dijkstra(map)
        return dijkstra.shortestPath(Position(0, 0), Position(gridSize - 1, gridSize - 1))
    }

    fun part2(input: List<String>, minNumLines: Int, gridSize: Int): String {
        var minIndex = minNumLines
        var maxIndex = input.size - 1
        var curIndex = (minIndex + maxIndex) / 2

        var indexResult = part1(input, curIndex, gridSize)
        var indexPlusResult = part1(input, curIndex + 1, gridSize)
        while (!(indexResult != null && indexPlusResult == null)) {
            if (indexPlusResult != null) {
                minIndex = curIndex + 2
            } else {
                maxIndex = curIndex - 1
            }
            curIndex = (minIndex + maxIndex) / 2
            indexResult = part1(input, curIndex, gridSize)
            indexPlusResult = part1(input, curIndex + 1, gridSize)
        }
        return input[curIndex]
    }

    println(part1(readInput("aoc2024/Day18_test"), 12, 7))
    println(part1(readInput("aoc2024/Day18"), 1024, 71))
    println(part2(readInput("aoc2024/Day18_test"), 12, 7))
    println(part2(readInput("aoc2024/Day18"), 1024, 71))

}
