package aoc2024

import readInput

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

    fun part2(input: List<String>): Int {
        return 0
    }

    println(part1(readInput("aoc2024/Day20_test"), 20))
    println(part1(readInput("aoc2024/Day20"), 100))
//    println(part2(readInput("aoc2024/Day20_test")))
//    println(part2(readInput("aoc2024/Day20")))
}
