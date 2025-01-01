package aoc2024

import java.util.*
import kotlin.math.min

abstract class DijkstraGeneric<Key> {
    val smallest = mutableMapOf<Key, Long>()
    val smallestPrev = mutableMapOf<Key, MutableList<Key>>()
    val visited = mutableSetOf<Key>()
    val toVisit = PriorityQueue<Pair<Key, Long>> { a, b -> (a.second - b.second).toInt() }

    abstract fun getNeighbours(key: Key): List<Key>
    abstract fun costUpdate(cur: Key, next: Key): Int

    fun shortestPath(start: Key, end: Key): Long? {
        toVisit.add(Pair(start, 0))
        smallest[start] = 0
        while (toVisit.isNotEmpty()) {
            val current = toVisit.poll()
            if (visited.contains(current.first))
                continue

            visited.add(current.first)
            val cost = current.second

            val neighbours = getNeighbours(current.first)
            for (neighbour in neighbours) {
                    updateCost(neighbour,
                    cost + costUpdate(current.first, neighbour),
                    current.first)
            }
        }

        return smallest.filter { it.key == end }.minByOrNull { it.value }?.value
    }

    fun updateCost(next: Key?, newCost: Long, prev: Key) {
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

    fun minPaths(end: Key): MutableList<MutableList<Key>> {
        return minPaths(mutableListOf(mutableListOf(end)))
    }

    fun minPaths(paths: MutableList<MutableList<Key>>): MutableList<MutableList<Key>> {
        var added = true
        while (added) {
            added = false
            val toAdd = mutableListOf<MutableList<Key>>()
            for (path in paths) {
                val prevs = smallestPrev.filter { it.key == path.last() }.values.flatten()
                if (prevs.size == 1) {
                    added = true
                    path.add(prevs[0])
                } else if (prevs.size > 1) {
                    added = true
                    for (i in 1 until prevs.size) {
                        val newList = mutableListOf<Key>()
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

    fun numMinPaths(end: Key): Int {
        val paths = mutableListOf(mutableListOf(end))
        var added = true
        while (added) {
            added = false
            val toAdd = mutableListOf<MutableList<Key>>()
            for (path in paths) {
                val prevs = smallestPrev.filter { it.key == path.last() }.values.flatten()
                if (prevs.size == 1) {
                    added = true
                    path.removeLast()
                    path.add(prevs[0])
                } else if (prevs.size > 1) {
                    added = true
                    path.removeLast()
                    path.add(prevs[0])
                    for (i in 1 until prevs.size) {
                        val newList = mutableListOf<Key>()
                        newList.add(prevs[i])
                        toAdd.add(newList)
                    }
                }
            }
            paths.addAll(toAdd)
        }
        return paths.size
    }
}