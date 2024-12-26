package aoc2024

import readInput
import kotlin.math.abs

fun main() {

    data class GridWithCheat(val original: Grid, val cheat1: Position, val cheat2: Position) : IGrid {
        override fun get(position: Position): Char {
            return if (position == cheat1 || position == cheat2) {
                '.'
            } else {
                original.get(position)
            }
        }

        override fun get(x: Int, y: Int): Char {
            return get(Position(x, y))
        }

        override fun isValid(position: Position): Boolean {
            return original.isValid(position)
        }
    }

    data class GridWithCheat2(val original: Grid, val stepWithCheat: Int) : IGrid {
        private var step = 0

        override fun get(position: Position): Char {
            return original.get(position)
        }

        override fun get(x: Int, y: Int): Char {
            return original.get(x, y)
        }

        override fun isValid(position: Position): Boolean {
            return original.isValid(position)
        }

        override fun getNeighbours(position: Position): List<Position> {
            step++
            if (step != stepWithCheat) {
                return original.getNeighbours(position)
            } else {
                val neighs = mutableSetOf<Position>()
                for (x in -20 .. 20) {
                    for (y in -20 .. 20) {
                        if (abs(x) + abs(y) <= 20
                            ) {
                            val newPosition = Position(position.x + x, position.y + y)
                            if (newPosition != position
                                && original.isValid(newPosition)
                                && original.get(newPosition) != '#') {
                                neighs.add(newPosition)
                            }
                        }
                    }
                }
                return neighs.toList()
            }
        }
    }

    fun part1(input: List<String>, minNumSaved: Int): Long? {
        val map = Grid(input)
        val start = map.find('S')!!
        val end = map.find('E')!!
        val dijkstra = Dijkstra(map)
        val smallest = dijkstra.shortestPath(start, end)!!
        var numCheatsAtLeast = 0L
        for (i in 1 .. (map.map.size - 2)) {
            for (j in 1 .. map.map[0].length - 3) {
                if (map.get(i, j) == '#' && map.get(i, j + 1) != '#') {
                    val innerDijkstra = Dijkstra(GridWithCheat(map, Position(i, j), Position(i, j + 1)))
                    val smaller = innerDijkstra.shortestPath(start, end)!!
                    if (smallest - smaller >= minNumSaved) numCheatsAtLeast++
                }
            }
        }
        for (j in 1 .. (map.map[0].length - 2)) {
            for (i in 1 .. map.map.size - 3) {
                if (map.get(i, j) == '#' && map.get(i + 1, j) != '#') {
                    val innerDijkstra = Dijkstra(GridWithCheat(map, Position(i, j), Position(i + 1, j)))
                    val smaller = innerDijkstra.shortestPath(start, end)!!
                    if (smallest - smaller >= minNumSaved) numCheatsAtLeast++
                }
            }
        }
        return numCheatsAtLeast
    }

    fun part2(input: List<String>, minNumSaved: Int): Long {
        val map = Grid(input)
        val start = map.find('S')!!
        val end = map.find('E')!!
        val dijkstra = Dijkstra(map)
        val smallest = dijkstra.shortestPath(start, end)!!
        var numCheatsAtLeast = 0L
        for (i in 1 .. smallest - minNumSaved) {
            val innerDijkstra = Dijkstra(GridWithCheat2(map, i.toInt()))
            val smaller = innerDijkstra.shortestPath(start, end)!!
            if (smallest - smaller >= minNumSaved) {
                numCheatsAtLeast += innerDijkstra.minPaths(mutableListOf(mutableListOf(end))).size
                println("Iteration $i: $numCheatsAtLeast")
            }
        }
        return numCheatsAtLeast
    }

//    println(part1(readInput("aoc2024/Day20_test"), 20))
//    println(part1(readInput("aoc2024/Day20"), 100))
    println(part2(readInput("aoc2024/Day20_test"), 76))
    println(part2(readInput("aoc2024/Day20_test"), 74))
    println(part2(readInput("aoc2024/Day20_test"), 72))
    println(part2(readInput("aoc2024/Day20_test"), 70))
    println(part2(readInput("aoc2024/Day20"), 100))
}
