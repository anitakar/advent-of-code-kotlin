package aoc2024

import readInput
import kotlin.math.max


fun main() {

    data class Position(val x: Int, val y: Int)
    data class PositionAndDirection(val x: Int, val y: Int, val dir: Char)

    class Guard(var x: Int, var y: Int, private val map: List<String>) {
        private var dir: Char = 'U'

        fun position(): Position {
            return Position(x, y)
        }

        fun positionAndDirection(): PositionAndDirection {
            return PositionAndDirection(x, y, dir)
        }

        fun move(): Boolean {
            if (dir == 'U') {
                var nextX = x - 1
                var nextY = y
                if (outsideMap(nextX, nextY)) {
                    return false
                }
                if (!isObstacle(nextX, nextY)) {
                    x = nextX
                    y = nextY
                    return true
                }
                dir = 'R'
                return move()
            } else if (dir == 'R') {
                var nextX = x
                var nextY = y + 1
                if (outsideMap(nextX, nextY)) {
                    return false
                }
                if (!isObstacle(nextX, nextY)) {
                    x = nextX
                    y = nextY
                    return true
                }
                dir = 'D'
                return move()
            } else if (dir == 'D') {
                var nextX = x + 1
                var nextY = y
                if (outsideMap(nextX, nextY)) {
                    return false
                }
                if (!isObstacle(nextX, nextY)) {
                    x = nextX
                    y = nextY
                    return true
                }
                dir = 'L'
                return move()
            } else if (dir == 'L') {
                var nextX = x
                var nextY = y - 1
                if (outsideMap(nextX, nextY)) {
                    return false
                }
                if (!isObstacle(nextX, nextY)) {
                    x = nextX
                    y = nextY
                    return true
                }
                dir = 'U'
                return move()
            }
            return false
        }

        private fun isObstacle(x: Int, y: Int): Boolean {
            return map[x][y] == '#'
        }

        private fun outsideMap(x: Int, y: Int): Boolean {
            if (x < 0) return true
            if (x >= map.size) return true
            if (y < 0) return true
            if (y >= map[0].length) return true
            return false
        }
    }

    fun findGuard(map: List<String>): Guard? {
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == '^') {
                    return Guard(i, j, map)
                }
            }
        }
        return null
    }

    fun part1(input: List<String>): Int {
        val visited = mutableSetOf<Position>()
        val guard = findGuard(input)!!
        visited.add(guard.position())
        while (guard.move()) {
            visited.add(guard.position())
        }
        return visited.size
    }

    fun checkIfLoop(guard: Guard, map: List<String>): Boolean {
        val visited = mutableSetOf<PositionAndDirection>()
        visited.add(guard.positionAndDirection())
        while (guard.move()) {
            if (visited.contains(guard.positionAndDirection())) {
                return true
            }
            visited.add(guard.positionAndDirection())
        }
        return false
    }

    fun part2(input: List<String>): Int {
        var totalWithLoop = 0
        val guard = findGuard(input)!!
        val initialGuardPosition = guard.position()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (i == initialGuardPosition.x && j == initialGuardPosition.y) {
                    continue
                }
                if (input[i][j] == '#') {
                    continue
                }

                val modifiedMap = mutableListOf<String>()
                modifiedMap.addAll(input)
                modifiedMap[i] = input[i].substring(0, max(0,j)) + "#" + input[i].substring(j + 1, input[i].length)
                if (checkIfLoop(Guard(initialGuardPosition.x, initialGuardPosition.y, modifiedMap), modifiedMap)) {
                    totalWithLoop++
                }
            }
        }
        return totalWithLoop
    }

    println(part1(readInput("aoc2024/Day06_test")))
    println(part1(readInput("aoc2024/Day06")))
    println(part2(readInput("aoc2024/Day06_test")))
    println(part2(readInput("aoc2024/Day06")))
}