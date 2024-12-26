package aoc2024

import java.util.*
import kotlin.math.abs
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
                    updateCost(neighbour,
                        cost + abs(neighbour.x - current.first.x) + abs(neighbour.y - current.first.y),
                        current.first)
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

    fun minPaths(paths: MutableList<MutableList<Position>>): MutableList<MutableList<Position>> {
        var added = true
        while (added) {
            added = false
            val toAdd = mutableListOf<MutableList<Position>>()
            for (path in paths) {
                val prevs = smallestPrev.filter { it.key == path.last() }.values.flatten()
                if (prevs.size == 1) {
                    added = true
                    path.add(prevs[0])
                } else if (prevs.size > 1) {
                    added = true
                    for (i in 1 until prevs.size) {
                        val newList = mutableListOf<Position>()
                        newList.addAll(path)
                        newList.add(prevs[i])
                        toAdd.add(newList)
                    }
                    path.add(prevs[0])
                }
            }
            paths.addAll(toAdd)
        }
        return paths
    }
}