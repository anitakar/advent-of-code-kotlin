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

        return line
    }

    fun part2(input: List<String>): Long {
        val map = Grid(input)
        val areas = findAreas(input)

        var cost = 0L
        for (area in areas) {
            val perimeter = mutableListOf<Position>()
            for (position in area.positions) {
                if (onPerimeter(position, map)) {
                    perimeter.add(position)
                }
            }

            var horizontalLines = mutableListOf<MutableList<Position>>()
            var verticalLines = mutableListOf<MutableList<Position>>()
            for (position in perimeter) {
                if (formsHorizontalLine(position, map)) {
                    horizontalLines.add(trackHorizontalLine(position, perimeter, map))
                }
                if (formsVerticalLine(position, map)) {
                    verticalLines.add(trackVerticalLine(position, perimeter, map))
                }
            }
        }

        return cost
    }

    println(part1(readInput("aoc2024/Day12_test")))
    println(part1(readInput("aoc2024/Day12")))
//    println(part2(readInput("aoc2024/Day12_test")))
//    println(part2(readInput("aoc2024/Day12")))
}