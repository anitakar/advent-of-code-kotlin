package aoc2024

import readInput
import kotlin.math.min


fun main() {

    fun nextStraight(current: Pair<Position, Char>, map: Grid): Pair<Position, Char>? {
        return if (current.second == 'W') {
            val next = map.left(current.first)
            return if (next != null && map.get(next) != '#') {
                Pair(next, 'W')
            } else null
        } else if (current.second == 'E') {
            val next = map.right(current.first)
            return if (next != null && map.get(next) != '#') {
                Pair(next, 'E')
            } else null
        } else if (current.second == 'N') {
            val next = map.up(current.first)
            return if (next != null && map.get(next) != '#') {
                Pair(next, 'N')
            } else null
        } else if (current.second == 'S') {
            val next = map.down(current.first)
            return if (next != null && map.get(next) != '#') {
                Pair(next, 'S')
            } else null
        }  else {
            null
        }
    }

    val smallest = mutableMapOf<Pair<Position, Char>, Long>()
    val visited = mutableSetOf<Pair<Position, Char>>()
    val toVisit = mutableListOf<Pair<Position, Char>>()

    fun updateCost(next: Pair<Position, Char>?, newCost: Long) {
        if (next != null) {
            if (!visited.contains(next)) {
                toVisit.add(next)
            }

            if (smallest.contains(next)) {
                smallest[next] = min(smallest[next]!!, newCost)
            } else {
                smallest[next] = newCost
            }
        }
    }

    fun part1(input: List<String>): Long? {
        smallest.clear()
        visited.clear()
        toVisit.clear()

        val map = Grid(input)
        val start = map.find('S')!!
        val end = map.find('E')!!

        toVisit.add(Pair(start, 'E'))
        smallest[Pair(start, 'E')] = 0
        while(toVisit.isNotEmpty()) {
            val current = toVisit.removeAt(0)
            if (visited.contains(current))
                continue

            visited.add(current)
            val cost = smallest[current]!!

            val nextStraight = nextStraight(current, map)
            updateCost(nextStraight, cost + 1)

            val nextClockwise = Pair(current.first, Direction(current.second).nextClockwise()!!)
            updateCost(nextClockwise, cost + 1000)

            val nextCounterClockwise = Pair(current.first, Direction(current.second).nextCounterClockwise()!!)
            updateCost(nextCounterClockwise, cost + 1000)
        }

        return smallest.filter { it.key.first == end }.minBy { it.value }?.value
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    println(part1(readInput("aoc2024/Day16_test")))
    println(part1(readInput("aoc2024/Day16_test2")))
    println(part1(readInput("aoc2024/Day16")))
//    println(part2(readInput("aoc2024/Day16_test")))
//    println(part2(readInput("aoc2024/Day16")))
}