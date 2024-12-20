package aoc2024

import java.util.*
import kotlin.math.min

class Dijkstra(val map: IGrid) {
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