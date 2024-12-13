package aoc2024

import readInput


fun main() {

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

    fun findAllTrails(position: Position, input: List<String>): MutableList<Trail> {
        val map = Grid(input)
        val result = mutableListOf<Trail>()
        val trails = mutableListOf<Trail>()
        trails.add(Trail(mutableListOf(position)))
        while (trails.isNotEmpty()) {
            val trail = trails.removeAt(0)
            val lastElem = trail.path[trail.path.size - 1]
            val neighbours = map.getNeighbours(lastElem)
            var added = false
            for (neighbour in neighbours) {
                if (map.get(Position(neighbour.x, neighbour.y)).toString().toInt() - map.get(Position(lastElem.x, lastElem.y)).toString().toInt() == 1) {
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