package aoc2024

data class Position(val x: Int, val y: Int)

class MutableGrid() {
    var map: MutableList<MutableList<Char>> = mutableListOf()

    constructor(inputMap: List<String>) : this() {
        for (i in inputMap.indices) {
            map.add(mutableListOf())
            for (j in inputMap[i].indices) {
                map[i].add(inputMap[i][j])
            }
        }
    }

    fun get(position: Position): Char {
        return map[position.x][position.y]
    }

    fun get(x: Int, y: Int): Char {
        return map[x][y]
    }

    fun set(position: Position, value: Char): Char {
        map[position.x][position.y] = value
        return value
    }

    fun set(x: Int, y: Int, value: Char): Char {
        map[x][y] = value
        return value
    }

    fun isValid(position: Position): Boolean {
        if (position.x < 0) return false
        if (position.x >= map.size) return false
        if (position.y < 0) return false
        if (position.y >= map[position.x].size) return false
        return true
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

    fun print() {
        for (i in map.indices) {
            for (j in map[i].indices) {
                print(map[i][j])
            }
            println()
        }
    }
}

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

    fun get(x: Int, y: Int): Char {
        return map[x][y]
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

    fun find(value: Char): Position? {
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (get(i, j) == value) {
                    return Position(i, j)
                }
            }
        }
        return null
    }
}

class Direction(var current: Char) {

    fun nextClockwise(): Char? {
        if (current == 'N') return 'E'
        else if (current == 'E') return 'S'
        else if (current == 'S') return 'W'
        else if (current == 'W') return 'N'
        else return null
    }

    fun nextCounterClockwise(): Char? {
        if (current == 'N') return 'W'
        else if (current == 'W') return 'S'
        else if (current == 'S') return 'E'
        else if (current == 'E') return 'N'
        else return null
    }

}

class RectangularGrid(
    val maxX: Int, val maxY: Int,
    val obstacles: MutableSet<Position>
) {
    fun isValid(position: Position): Boolean {
        if (position.x < 0) return false
        if (position.x >= maxX) return false
        if (position.y < 0) return false
        if (position.y >= maxY) return false
        return true
    }

    fun get(position: Position): Char {
        return if (obstacles.contains(position)) '#' else '.'
    }

    fun get(x: Int, y: Int): Char {
        return get(Position(x, y))
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

    fun getNeighbours(position: Position): List<Position> {
        val neighbours = mutableListOf<Position>()

        up(position)?.let { neighbours.add(it) }
        down(position)?.let { neighbours.add(it) }
        left(position)?.let { neighbours.add(it) }
        right(position)?.let { neighbours.add(it) }

        return neighbours
    }
}