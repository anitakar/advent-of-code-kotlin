package aoc2023

import readInput
import java.util.PriorityQueue

fun main() {

    fun somePathHeatLoss(map: Array<Array<Int>>): Int {
        return (1 until map.size).sumOf { map[it - 1][it] + map[it][it] }
    }

    data class Point(val x: Int, val y: Int)
    data class Move(val direction: Char, val point: Point, val steps: Int)
    data class CurrentState(val move: Move, val cost: Int)

    fun promising(state: CurrentState, currentMin: Int, gridSize: Int, currentMinAtPoint: Int): Boolean {
        val distanceToEnd = gridSize - state.move.point.x - 1 + gridSize - state.move.point.y - 1
        if (state.cost + distanceToEnd >= currentMin) {
            return false
        }
        if (state.cost > currentMinAtPoint + 4) {
            return false
        }
        return true
    }

    fun up(point: Point, map: Array<Array<Int>>): Point? {
        val newX = point.x - 1
        if (newX < 0) {
            return null
        }
        return Point(newX, point.y)
    }

    fun down(point: Point, map: Array<Array<Int>>): Point? {
        val newX = point.x + 1
        if (newX >= map.size) {
            return null
        }
        return Point(newX, point.y)
    }

    fun right(point: Point, map: Array<Array<Int>>): Point? {
        val newY = point.y + 1
        if (newY >= map.size) {
            return null
        }
        return Point(point.x, newY)
    }

    fun left(point: Point, map: Array<Array<Int>>): Point? {
        val newY = point.y - 1
        if (newY < 0) {
            return null
        }
        return Point(point.x, newY)
    }

    fun next(state: CurrentState, map: Array<Array<Int>>): List<CurrentState> {
        val result = mutableListOf<CurrentState>()
        if (state.move.direction == 'R') {
            val down = down(state.move.point, map)
            if (down != null) {
                result.add(CurrentState(Move('D', down, 0), state.cost + map[down.x][down.y]))
            }
            if (state.move.steps < 2) {
                val right = right(state.move.point, map)
                if (right != null) {
                    result.add(CurrentState(Move('R', right, state.move.steps + 1), state.cost + map[right.x][right.y]))
                }
            }
            val up = up(state.move.point, map)
            if (up != null) {
                result.add(CurrentState(Move('U', up, 0), state.cost + map[up.x][up.y]))
            }
        } else if (state.move.direction == 'D') {
            val right = right(state.move.point, map)
            if (right != null) {
                result.add(CurrentState(Move('R', right, 0), state.cost + map[right.x][right.y]))
            }
            if (state.move.steps < 2) {
                val down = down(state.move.point, map)
                if (down != null) {
                    result.add(CurrentState(Move('D', down, state.move.steps + 1), state.cost + map[down.x][down.y]))
                }
            }
            val left = left(state.move.point, map)
            if (left != null) {
                result.add(CurrentState(Move('L', left, 0), state.cost + map[left.x][left.y]))
            }
        } else if (state.move.direction == 'U') {
            val right = right(state.move.point, map)
            if (right != null) {
                result.add(CurrentState(Move('R', right, 0), state.cost + map[right.x][right.y]))
            }
            val left = left(state.move.point, map)
            if (left != null) {
                result.add(CurrentState(Move('L', left, 0), state.cost + map[left.x][left.y]))
            }
            if (state.move.steps < 2) {
                val up = up(state.move.point, map)
                if (up != null) {
                    result.add(CurrentState(Move('U', up, state.move.steps + 1), state.cost + map[up.x][up.y]))
                }
            }
        } else if (state.move.direction == 'L') {
            val down = down(state.move.point, map)
            if (down != null) {
                result.add(CurrentState(Move('D', down, 0), state.cost + map[down.x][down.y]))
            }
            if (state.move.steps < 2) {
                val left = left(state.move.point, map)
                if (left != null) {
                    result.add(CurrentState(Move('L', left, state.move.steps + 1), state.cost + map[left.x][left.y]))
                }
            }
            val up = up(state.move.point, map)
            if (up != null) {
                result.add(CurrentState(Move('U', up, state.move.steps + 1), state.cost + map[up.x][up.y]))
            }
        }
        return result
    }

    fun part1(lines: List<String>): Int {
        val map = Array(lines.size) { Array(lines[0].length) { 0 } }
        for (i in lines.indices) {
            map[i] = lines[i].toCharArray().map { it.digitToInt() }.toTypedArray()
        }

        var currentMin = somePathHeatLoss(map)

        val curMinAtPoint = mutableMapOf<Point, Int>()
        val visited = mutableListOf<CurrentState>()
        val toVisit = PriorityQueue((compareBy<CurrentState> { 2 * (map.size + map.size - it.move.point.x - it.move.point.y  - 2) + it.cost }))
        toVisit.add(CurrentState(Move('R', Point(0, 0), 0), 0))
        toVisit.add(CurrentState(Move('D', Point(0, 0), 0), 0))
        while (toVisit.isNotEmpty()) {
            val current = toVisit.remove()
            visited.add(current)
            val next = next(current, map)
            for (elem in next) {
                if (elem.move.point == Point(map.size - 1, map.size - 1)) {
                    if (elem.cost < currentMin) {
                        currentMin = elem.cost
                        println(currentMin)
                    }
                } else if (!visited.contains(elem)) {
                    if (promising(elem, currentMin, map.size, curMinAtPoint.getOrDefault(elem.move.point, currentMin))) {
                        toVisit.add(elem)
                        if (elem.cost < curMinAtPoint.getOrDefault(elem.move.point, currentMin)) {
                            curMinAtPoint.put(elem.move.point, elem.cost)
                        }
                    }
                }
            }
            toVisit.removeIf { !promising(it, currentMin, map.size, curMinAtPoint.getOrDefault(it.move.point, currentMin)) }
        }

        return currentMin
    }

    println(part1(readInput("aoc2023/Day17_test")))
    println(part1(readInput("aoc2023/Day17")))
}