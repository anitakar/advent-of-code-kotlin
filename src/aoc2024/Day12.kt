package aoc2024

import readInput


fun main() {

    data class Area(val positions: MutableSet<Position>, var perimeter: Int)

    fun findAreas(input: List<String>): MutableList<Area> {
        val map = Grid(input)

        val areas = mutableListOf<Area>()
        val visited = mutableSetOf<Position>()

        val seeds = mutableSetOf<Position>()
        seeds.add(Position(0,0))
        while(seeds.isNotEmpty()) {
            val seed = seeds.iterator().next()
            seeds.remove(seed)

            if (visited.contains(seed)) {
                continue
            }

            val currentArea = Area(mutableSetOf(), 0)
            areas.add(currentArea)

            val next = mutableSetOf<Position>()
            next.add(seed)

            while (next.isNotEmpty()) {
                val cur = next.iterator().next()
                next.remove(cur)
                visited.add(cur)
                currentArea.positions.add(cur)
                val neighs = map.getNeighbours(cur)
                currentArea.perimeter += (4 - neighs.size)
                for (neigh in neighs) {
                    if (map.get(cur) == map.get(neigh)) {
                        if (!visited.contains(neigh)) {
                            next.add(neigh)
                        }
                    } else {
                        currentArea.perimeter += 1
                        if (!visited.contains(neigh)) {
                            seeds.add(neigh)
                        }
                    }
                }
            }
        }
        return areas
    }

    fun part1(input: List<String>): Int {
        val areas = findAreas(input)

        var cost = 0
        for (area in areas) {
            cost += (area.perimeter * area.positions.size)
        }
        return cost
    }

    fun onPerimeter(position: Position, map: Grid): Boolean {
        val value = map.get(position)
        val neighs = map.getNeighbours(position)

        if (neighs.size < 4) {
            return true
        }

        for (neigh in neighs) {
            val neighValue = map.get(neigh)
            if (value != neighValue) {
                return true
            }
        }

        return false
    }

    fun formsHorizontalLine(position: Position, map: Grid): Boolean {
        val up = map.up(position)
        if (up == null) {
            return true
        }
        if (map.get(up) == map.get(position)) {
            return true
        }
        val down = map.down(position)
        if (down == null) {
            return true
        }
        if (map.get(down) == map.get(position)) {
            return true
        }
        return false
    }

    fun formsVerticalLine(position: Position, map: Grid): Boolean {
        val left = map.left(position)
        if (left == null) {
            return true
        }
        if (map.get(left) == map.get(position)) {
            return true
        }
        val right = map.right(position)
        if (right == null) {
            return true
        }
        if (map.get(right) == map.get(position)) {
            return true
        }
        return false
    }

    fun trackHorizontalLine(position: Position, perimeter: MutableList<Position>, map: Grid): MutableList<Position> {
        val line = mutableListOf<Position>()
        line.add(position)

        var left = map.left(position)
        while (left != null && perimeter.contains(left)) {
            line.add(left)
            left = map.left(left)
        }

        var right = map.right(position)
        while (right != null && perimeter.contains(right)) {
            line.add(right)
            right = map.right(right)
        }

        line.sortBy { -it.y }
        return line
    }

    fun trackVerticalLine(position: Position, perimeter: MutableList<Position>, map: Grid): MutableList<Position> {
        val line = mutableListOf<Position>()
        line.add(position)

        var up = map.up(position)
        while (up != null && perimeter.contains(up)) {
            line.add(up)
            up = map.up(up)
        }

        var down = map.down(position)
        while (down != null && perimeter.contains(down)) {
            line.add(down)
            down = map.down(down)
        }

        line.sortBy { -it.x }
        return line
    }

    fun part2(input: List<String>): Long {
        val map = Grid(input)
        val areas = findAreas(input)

        var cost = 0L
        for (area in areas) {
            val outsidePerimeterSet = mutableSetOf<Position>()
            for (position in area.positions) {
                if (onPerimeter(position, map)) {
                    val up = map.up(position)
                    if (up == null) outsidePerimeterSet.add(Position(-1, position.y))
                    else if (map.get(up) != map.get(position)) outsidePerimeterSet.add(up)

                    val down = map.down(position)
                    if (down == null) outsidePerimeterSet.add(Position(position.x + 1, position.y))
                    else if (map.get(down) != map.get(position)) outsidePerimeterSet.add(down)

                    val right = map.right(position)
                    if (right == null) outsidePerimeterSet.add(Position(position.x, position.y + 1))
                    else if (map.get(right) != map.get(position)) outsidePerimeterSet.add(right)

                    val left = map.left(position)
                    if (left == null) outsidePerimeterSet.add(Position(position.x, -1))
                    else if (map.get(left) != map.get(position)) outsidePerimeterSet.add(left)
                }
            }
            var outsidePerimeter = outsidePerimeterSet.toList()

            outsidePerimeter = outsidePerimeter.sortedWith { a, b ->
                if (a.x != b.x) a.x - b.x else a.y - b.y
            }
            var horizontalLinesCount = 1
            for (i in 1 until outsidePerimeter.size) {
                val prev = outsidePerimeter[i-1]
                val cur = outsidePerimeter[i]
                if (prev.x != cur.x || prev.y != cur.y - 1) horizontalLinesCount++
            }

            outsidePerimeter = outsidePerimeter.sortedWith { a, b ->
                if (a.y != b.y) a.y - b.y else a.x - b.x
            }
            var verticalLinesCount = 1
            for (i in 1 until outsidePerimeter.size) {
                val prev = outsidePerimeter[i-1]
                val cur = outsidePerimeter[i]
                if (prev.y != cur.y || prev.x != cur.x - 1) verticalLinesCount++
            }

            cost += (area.positions.size * (horizontalLinesCount + verticalLinesCount))
        }

        return cost
    }

//    println(part1(readInput("aoc2024/Day12_test")))
//    println(part1(readInput("aoc2024/Day12")))
    println(part2(readInput("aoc2024/Day12_test")))
//    println(part2(readInput("aoc2024/Day12")))
}