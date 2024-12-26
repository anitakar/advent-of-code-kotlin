package aoc2024

import kotlin.math.abs

class Dijkstra(val map: IGrid) : DijkstraGeneric<Position>() {
    override fun getNeighbours(key: Position): List<Position> {
        return map.getNeighbours(key).filter { map.get(it) != '#' }
    }

    override fun costUpdate(cur: Position, next: Position): Int {
        return abs(next.x - cur.x) + abs(next.y - cur.y)
    }
}