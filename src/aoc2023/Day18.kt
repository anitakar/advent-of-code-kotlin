package aoc2023

import readInput

fun main() {
    data class Instruction(val direction: Char, val times: Int)
    data class Point(val x: Int, val y: Int)

    fun up(point: Point) = Point(point.x - 1, point.y)

    fun down(point: Point) = Point(point.x + 1, point.y)

    fun right(point: Point) = Point(point.x, point.y + 1)

    fun left(point: Point) = Point(point.x, point.y - 1)

    fun interpretInstruction(startPoint: Point, instruction: Instruction): List<Point> {
        val result = mutableListOf<Point>()
        var prev = startPoint
        for (i in 0 until instruction.times) {
            val next = when (instruction.direction) {
                'L' -> left(prev)
                'R' -> right(prev)
                'U' -> up(prev)
                'D' -> down(prev)
                else -> throw RuntimeException()
            }
            result.add(next)
            prev = next
        }
        return result
    }

    fun borderToMap(border: List<Point>): Array<Array<Char>> {
        val minX = border.minOfOrNull { it.x }!!
        val maxX = border.maxOfOrNull { it.x }!!
        val minY = border.minOfOrNull { it.y }!!
        val maxY = border.maxOfOrNull { it.y }!!

        val result = Array(maxX - minX + 1) { Array(maxY - minY + 1) { '.' } }
        for (point in border) {
            result[point.x - minX][point.y - minY] = '#'
        }
        return result
    }

    fun normalizeBorder(border: List<Point>): List<Point> {
        val minX = border.minOfOrNull { it.x }!!
        val minY = border.minOfOrNull { it.y }!!

        return border.map { Point(it.x - minX, it.y - minY) }
    }

    fun fill(border: List<Point>, map: Array<Array<Char>>): Set<Point> {
        val filled = mutableSetOf<Point>()

        for (pointIndex in 1 until border.size) {
            val point = border[pointIndex]
            val prevPoint = border[pointIndex - 1]
            val curDir = if (prevPoint.y + 1 == point.y) {
                'R'
            } else if (prevPoint.y - 1 == point.y) {
                'L'
            } else if (prevPoint.x + 1 == point.x) {
                'D'
            } else if (prevPoint.x - 1 == point.x) {
                'U'
            } else {
                throw RuntimeException()
            }

            when (curDir) {
                'R' -> {
                    var toFill = down(point)
                    while (map[toFill.x][toFill.y] == '.') {
                        filled.add(toFill)
                        toFill = down(toFill)
                    }
                }
                'D' -> {
                    var toFill = left(point)
                    while (map[toFill.x][toFill.y] == '.') {
                        filled.add(toFill)
                        toFill = left(toFill)
                    }
                }
                'L' -> {
                    var toFill = up(point)
                    while (map[toFill.x][toFill.y] == '.') {
                        filled.add(toFill)
                        toFill = up(toFill)
                    }
                }
                'U' -> {
                    var toFill = right(point)
                    while (map[toFill.x][toFill.y] == '.') {
                        filled.add(toFill)
                        toFill = right(toFill)
                    }
                }
            }
        }
        return filled
    }

    fun fill(border: List<Point>): Set<Point> {
        val filled = mutableSetOf<Point>()

        for (pointIndex in 1 until border.size) {
            val point = border[pointIndex]
            val prevPoint = border[pointIndex - 1]
            val curDir = if (prevPoint.y + 1 == point.y) {
                'R'
            } else if (prevPoint.y - 1 == point.y) {
                'L'
            } else if (prevPoint.x + 1 == point.x) {
                'D'
            } else if (prevPoint.x - 1 == point.x) {
                'U'
            } else {
                throw RuntimeException()
            }

            when (curDir) {
                'R' -> {
                    var toFill = down(point)
                    while (!border.contains(Point(toFill.x, toFill.y))) {
                        filled.add(toFill)
                        toFill = down(toFill)
                    }
                }
                'D' -> {
                    var toFill = left(point)
                    while (!border.contains(Point(toFill.x, toFill.y))) {
                        filled.add(toFill)
                        toFill = left(toFill)
                    }
                }
                'L' -> {
                    var toFill = up(point)
                    while (!border.contains(Point(toFill.x, toFill.y))) {
                        filled.add(toFill)
                        toFill = up(toFill)
                    }
                }
                'U' -> {
                    var toFill = right(point)
                    while (!border.contains(Point(toFill.x, toFill.y))) {
                        filled.add(toFill)
                        toFill = right(toFill)
                    }
                }
            }
        }
        return filled
    }

    fun visualize(map: Array<Array<Char>>) {
        for (x in 0 until map.size) {
            for (y in 0 until map[0].size) {
                print(map[x][y])
            }
            println()
        }
    }

    fun part1(lines: List<String>): Int {
        val instructions = lines.map {
            val splitted = it.split(' ')
            Instruction(splitted[0][0], splitted[1].toInt())
        }

        val border = mutableListOf<Point>()
        var startPoint = Point(0, 0)
        border.add(startPoint)

        for (instruction in instructions) {
            val toAdd = interpretInstruction(startPoint, instruction)
            startPoint = toAdd.last()
            border.addAll(toAdd)
        }

        val map = borderToMap(border)
        val filled = fill(normalizeBorder(border), map)

        return border.toSet().size + filled.size
    }

    fun part2(lines: List<String>): Int {
        val dirMapping = mapOf('0' to 'R', '1' to 'D', '2' to 'L', '3' to 'U')
        val instructions = lines.map {
            val splitted = it.split(' ')
            val times = splitted[2].substring(2,7).toInt(16)
            val dir = dirMapping[splitted[2][7]]!!
            Instruction(dir, times)
        }

        val border = mutableListOf<Point>()
        var startPoint = Point(0, 0)
        border.add(startPoint)

        for (instruction in instructions) {
            val toAdd = interpretInstruction(startPoint, instruction)
            startPoint = toAdd.last()
            border.addAll(toAdd)
        }

        val filled = fill(normalizeBorder(border))

        return border.toSet().size + filled.size
    }

    println(part1(readInput("aoc2023/Day18_test")))
    println(part1(readInput("aoc2023/Day18")))
    println(part2(readInput("aoc2023/Day18_test")))
}