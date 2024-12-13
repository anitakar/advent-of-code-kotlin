package aoc2024

data class Position(val x: Int, val y:Int)

class Grid(val map: List<String>) {
    fun isValid(position: Position): Boolean {
        if (position.x < 0) return false
        if (position.x >= map.size) return false
        if (position.y < 0) return false
        if (position.y >= map[position.x].length) return false
        return true
    }

    fun get(position: Position): Char {
        return map[position.x][position.y]
    }

    fun getNeighbours(position: Position): List<Position> {
        val neighbours = mutableListOf<Position>()

        up(position)?.let { neighbours.add(it) }
        down(position)?.let { neighbours.add(it) }
        left(position)?.let { neighbours.add(it) }
        right(position)?.let { neighbours.add(it) }

        return neighbours
    }

    fun up(position: Position): Position? {
        val up = Position(position.x - 1, position.y)
        if (isValid(up)) {
            return up
        }
        return null
    }

    fun down(position: Position): Position? {
        val down = Position(position.x + 1, position.y)
        if (isValid(down)) {
            return down
        }
        return null
    }

    fun left(position: Position): Position? {
        val left = Position(position.x, position.y - 1)
        if (isValid(left)) {
            return left
        }
        return null
    }

    fun right(position: Position): Position? {
        val right = Position(position.x, position.y + 1)
        if (isValid(right)) {
            return right
        }
        return null
    }
}