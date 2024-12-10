package aoc2024

import readInput
import kotlin.math.abs
import kotlin.math.max


fun main() {

    data class Position(val x: Int, val y: Int)
    data class Trail(val path: MutableList<Position>)

    fun findAllTrailHeads(map: List<String>): List<Position> {
        val positions = mutableListOf<Position>()
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == '0') {
                    positions.add(Position(i, j))
                }
            }
        }
        return positions
    }

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

    fun findAllTrails(position: Position, map: List<String>): MutableList<Trail> {
        val result = mutableListOf<Trail>()
        val trails = mutableListOf<Trail>()
        trails.add(Trail(mutableListOf(position)))
        while (trails.isNotEmpty()) {
            val trail = trails.removeAt(0)
            val lastElem = trail.path[trail.path.size - 1]
            val neighbours = getNeighbours(lastElem, map)
            var added = false
            for (neighbour in neighbours) {
                if (map[neighbour.x][neighbour.y].toString().toInt() - map[lastElem.x][lastElem.y].toString().toInt() == 1) {
                    val newPath = mutableListOf<Position>()
                    newPath.addAll(trail.path)
                    newPath.add(neighbour)
                    trails.add(Trail(newPath))
                    added = true
                }
            }
            if (!added) {
                result.add(trail)
            }
        }
        return result
    }

    fun part1(input: List<String>): Long {
        var total = 0L
        val positions = findAllTrailHeads(input)
        for (position in positions) {
            val trails = findAllTrails(position, input)
            val longTrails = trails.filter { it.path.size == 10 }
            val uniqueEndPointsCount = longTrails.map { it.path[it.path.size - 1] }.distinct().count()
            total += uniqueEndPointsCount
        }
        return total
    }

    fun part2(input: List<String>): Long {
        var total = 0L
        val positions = findAllTrailHeads(input)
        for (position in positions) {
            val trails = findAllTrails(position, input)
            val longTrails = trails.filter { it.path.size == 10 }
            val uniqueTrailsCount = longTrails.size
            total += uniqueTrailsCount
        }
        return total
    }

    println(part1(readInput("aoc2024/Day10_test")))
    println(part1(readInput("aoc2024/Day10")))
    println(part2(readInput("aoc2024/Day10_test")))
    println(part2(readInput("aoc2024/Day10")))
}