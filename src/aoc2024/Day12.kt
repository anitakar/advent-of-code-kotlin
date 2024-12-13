package aoc2024

import readInput
import kotlin.math.abs
import kotlin.math.max


fun main() {
    data class Position(val x: Int, val y:Int)

    fun isValid(position: Position, map: List<String>): Boolean {
        if (position.x < 0) return false
        if (position.x >= map.size) return false
        if (position.y < 0) return false
        if (position.y >= map[position.x].length) return false
        return true
    }

    fun getNeighbours(position: Position, map: List<String>): List<Position> {
        val neighbours = mutableListOf<Position>()

        val up = Position(position.x - 1, position.y)
        if (isValid(up, map)) {
            neighbours.add(up)
        }

        val down = Position(position.x + 1, position.y)
        if (isValid(down, map)) {
            neighbours.add(down)
        }

        val left = Position(position.x, position.y - 1)
        if (isValid(left, map)) {
            neighbours.add(left)
        }

        val right = Position(position.x, position.y + 1)
        if (isValid(right, map)) {
            neighbours.add(right)
        }

        return neighbours
    }

    fun get(position: Position, map: List<String>): Char {
        return map[position.x][position.y]
    }

    data class Area(val positions: MutableSet<Position>, var perimeter: Int)

    fun part1(input: List<String>): Int {

        val areas = mutableListOf<Area>()
        val visited = mutableSetOf<Position>()

        val seeds = mutableSetOf<Position>()
        seeds.add(Position(0,0))
        while(seeds.isNotEmpty()) {
            val seed = seeds.iterator().next()
            seeds.remove(seed)

            if (visited.contains(seed)) {
                continue
            }

            val currentArea = Area(mutableSetOf(), 0)
            areas.add(currentArea)

            val next = mutableSetOf<Position>()
            next.add(seed)

            while (next.isNotEmpty()) {
                val cur = next.iterator().next()
                next.remove(cur)
                visited.add(cur)
                currentArea.positions.add(cur)
                val neighs = getNeighbours(cur, input)
                currentArea.perimeter += (4 - neighs.size)
                for (neigh in neighs) {
                    if (get(cur, input) == get(neigh, input)) {
                        if (!visited.contains(neigh)) {
                            next.add(neigh)
                        }
                    } else {
                        currentArea.perimeter += 1
                        if (!visited.contains(neigh)) {
                            seeds.add(neigh)
                        }
                    }
                }
            }
        }

        var cost = 0
        for (area in areas) {
            cost += (area.perimeter * area.positions.size)
        }
        return cost
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    println(part1(readInput("aoc2024/Day12_test")))
    println(part1(readInput("aoc2024/Day12")))
//    println(part2(readInput("aoc2024/Day12_test")))
//    println(part2(readInput("aoc2024/Day12")))
}