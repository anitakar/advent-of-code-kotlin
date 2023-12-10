package aoc2023

import readInput

fun main() {

    data class Point(val x: Int, val y: Int)
    data class Move(val nextPoint: Point, val dir: Char)

    fun Point.isValid(maze: Array<Array<Char>>): Boolean {
        return x >= 0 && x < maze.size && y >= 0 && y < maze[0].size
    }

    fun Point.nextStep(symbol: Char, dir: Char): Move? {
        if (symbol == '|' && dir == 'N') return Move(Point(x - 1, y), 'N')
        else if (symbol == '|' && dir == 'S') return Move(Point(x + 1, y), 'S')
        else if (symbol == '-' && dir == 'E') return Move(Point(x, y + 1), 'E')
        else if (symbol == '-' && dir == 'W') return Move(Point(x, y - 1), 'W')
        else if (symbol == 'L' && dir == 'S') return Move(Point(x, y + 1), 'E')
        else if (symbol == 'L' && dir == 'W') return Move(Point(x - 1, y), 'N')
        else if (symbol == 'J' && dir == 'S') return Move(Point(x, y - 1), 'W')
        else if (symbol == 'J' && dir == 'E') return Move(Point(x - 1, y), 'N')
        else if (symbol == '7' && dir == 'N') return Move(Point(x, y - 1), 'W')
        else if (symbol == '7' && dir == 'E') return Move(Point(x + 1, y), 'S')
        else if (symbol == 'F' && dir == 'N') return Move(Point(x, y + 1), 'E')
        else if (symbol == 'F' && dir == 'W') return Move(Point(x + 1, y), 'S')
        else return null
    }

    fun findCycle(start: Point, maze: Array<Array<Char>>): List<Point>? {
        for (dir in listOf('N', 'E', 'S', 'W')) {
            var curDir = dir
            val cycle = mutableListOf<Point>()
            cycle.add(start)
            var cur: Point? = when (curDir) {
                'N' -> Point(start.x - 1, start.y)
                'S' -> Point(start.x + 1, start.y)
                'E' -> Point(start.x, start.y + 1)
                'W' -> Point(start.x, start.y - 1)
                else -> null
            }
            if (cur == null || !cur.isValid(maze)) continue
            do {
                cycle.add(cur!!)
                val next = cur.nextStep(maze[cur.x][cur.y], curDir)
                if (next != null && next.nextPoint.isValid(maze)) {
                    cur = next.nextPoint
                    curDir = next.dir
                } else {
                    cur = null
                }
            } while (cur != start && cur != null)
            if (cur != null && cycle.size > 1) {
                return cycle
            }
        }
        return null
    }

    fun part1(lines: List<String>): Int {
        val maze = Array(lines.size) { Array(lines[0].length) { '.' } }
        for ((index, line) in lines.withIndex()) {
            maze[index] = line.toCharArray().toTypedArray()
        }
        val startX = maze.indexOfFirst { it.contains('S') }
        val startY = maze[startX].indexOfFirst { it == 'S' }
        val cycle = findCycle(Point(startX, startY), maze)
        return (cycle!!.size + 1) / 2
    }

    // TODO This only works for loops that go clockwise
    // It does not go for loops going counter clockwise
    // It also adds all neighbours of counter clockwise loop
    fun determineEnclosedCandidateClockwiseLoop(from: Point, to: Point): Point {
        if (to.y - from.y == 1) {
            // going east
            return Point(to.x + 1, to.y)
        } else if (from.y - to.y == 1) {
            // going west
            return Point(to.x - 1, to.y)
        } else if (from.x - to.x == -1) {
            // going south
            return Point(to.x, to.y - 1)
        } else {
            // going north
            return Point(to.x, to.y + 1)
        }
    }

    fun determineEnclosedCandidateCounterClockwiseLoop(from: Point, to: Point): Point {
        if (to.y - from.y == 1) {
            // going east
            return Point(to.x - 1, to.y)
        } else if (from.y - to.y == 1) {
            // going west
            return Point(to.x + 1, to.y)
        } else if (from.x - to.x == -1) {
            // going south
            return Point(to.x, to.y + 1)
        } else {
            // going north
            return Point(to.x, to.y - 1)
        }
    }

    fun findNeighbours(point: Point): List<Point> {
        return listOf(
            Point(point.x - 1, point.y),
            Point(point.x + 1, point.y),
            Point(point.x, point.y - 1),
            Point(point.x, point.y + 1)
        )
    }

    fun outsideLoop(cycle: List<Point>, maze: Array<Array<Char>>): Set<Point> {
        val outside = mutableSetOf<Point>()
        for (i in maze[0].indices) {
            if (!cycle.contains(Point(0, i))) {
                outside.add(Point(0, i))
            }
            if (!cycle.contains(Point(maze.size - 1, i))) {
                outside.add(Point(maze.size - 1, i))
            }
        }
        for (i in maze.indices) {
            if (!cycle.contains(Point(i, 0))) {
                outside.add(Point(i, 0))
            }
            if (!cycle.contains(Point(i, maze[0].size - 1))) {
                outside.add(Point(i, maze[0].size - 1))
            }
        }
        val toAdd = mutableListOf<Point>()
        do {
            outside.addAll(toAdd)
            toAdd.clear()
            for (point in outside) {
                for (neighbour in findNeighbours(point)) {
                    if (neighbour.isValid(maze) && !cycle.contains(neighbour) && !outside.contains(neighbour)) {
                        toAdd.add(neighbour)
                    }
                }
            }
        } while (toAdd.isNotEmpty())
        return outside
    }

    fun markEnclosed(cycle: List<Point>, maze: Array<Array<Char>>): Set<Point> {
        val enclosed = mutableSetOf<Point>()
        for (i in 0..cycle.size - 2) {
            val from = cycle[i]
            val to = cycle[i + 1]
            val enclosedCandidate = determineEnclosedCandidateClockwiseLoop(from, to)
            if (enclosedCandidate.isValid(maze) && !cycle.contains(enclosedCandidate)) {
                enclosed.add(enclosedCandidate)
            }
            val enclosedCandidate2 = determineEnclosedCandidateCounterClockwiseLoop(from, to)
            if (enclosedCandidate2.isValid(maze) && !cycle.contains(enclosedCandidate2)) {
                enclosed.add(enclosedCandidate2)
            }
        }
        enclosed.removeAll(outsideLoop(cycle, maze))
        val toAdd = mutableListOf<Point>()
        do {
            enclosed.addAll(toAdd)
            toAdd.clear()
            for (point in enclosed) {
                for (neighbour in findNeighbours(point)) {
                    if (neighbour.isValid(maze) && !cycle.contains(neighbour) && !enclosed.contains(neighbour)) {
                        toAdd.add(neighbour)
                    }
                }
            }
        } while (toAdd.isNotEmpty())
        return enclosed
    }

    fun part2(lines: List<String>): Int {
        val maze = Array(lines.size) { Array(lines[0].length) { '.' } }
        for ((index, line) in lines.withIndex()) {
            maze[index] = line.toCharArray().toTypedArray()
        }
        val startX = maze.indexOfFirst { it.contains('S') }
        val startY = maze[startX].indexOfFirst { it == 'S' }
        val cycle = findCycle(Point(startX, startY), maze)
        val enclosed = markEnclosed(cycle!!, maze)
        return enclosed.size
    }

    println(part1(readInput("aoc2023/Day10_test")))
    println(part1(readInput("aoc2023/Day10_test2")))
    println(part1(readInput("aoc2023/Day10_test3")))
    println(part1(readInput("aoc2023/Day10_test4")))
    println(part1(readInput("aoc2023/Day10")))
    println(part2(readInput("aoc2023/Day10_test5")))
    println(part2(readInput("aoc2023/Day10_test6")))
    println(part2(readInput("aoc2023/Day10_test7")))
    println(part2(readInput("aoc2023/Day10_test8")))
    println(part2(readInput("aoc2023/Day10")))
}