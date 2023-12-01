package aoc2022

import println
import readInput

fun main() {

    data class Position(val x: Int, val y: Int)

    fun part1(input: List<String>): Int {
        val moveRegex = """(\w) (\d+)""".toRegex()

        var head = Position(0, 0)
        var tail = Position(0, 0)

        val unique = mutableSetOf<Position>()
        unique.add(Position(0, 0))

        for (line in input) {
            val match = moveRegex.find(line)
            val (dir, steps) = match!!.destructured

            val xInc = when (dir) {
                "R" -> 1
                "L" -> -1
                else -> 0
            }
            val yInc = when (dir) {
                "U" -> 1
                "D" -> -1
                else -> 0
            }

            for (i in 0 until steps.toInt()) {
                head = Position(head.x + xInc, head.y + yInc)
                if (tail.y == head.y && head.x - tail.x == 2) {
                    tail = Position(tail.x + 1, tail.y)
                } else if (tail.y == head.y && head.x - tail.x == -2) {
                    tail = Position(tail.x - 1, tail.y)
                } else if (tail.x == head.x && head.y - tail.y == 2) {
                    tail = Position(tail.x, tail.y + 1)
                } else if (tail.x == head.x && head.y - tail.y == -2) {
                    tail = Position(tail.x, tail.y - 1)
                } else if (Math.abs(tail.x - head.x) + Math.abs(tail.y - head.y) > 2) {
                    if (head.x > tail.x) {
                        tail = Position(tail.x + 1, tail.y)
                    } else {
                        tail = Position(tail.x - 1, tail.y)
                    }

                    if (head.y > tail.y) {
                        tail = Position(tail.x, tail.y + 1)
                    } else {
                        tail = Position(tail.x, tail.y - 1)
                    }
                }
                unique.add(tail)
            }
        }

        return unique.size
    }

    fun part2(input: List<String>): Int {
        val moveRegex = """(\w) (\d+)""".toRegex()

        var knots = mutableListOf<Position>(
            Position(0, 0), Position(0, 0),
            Position(0, 0), Position(0, 0),
            Position(0, 0), Position(0, 0),
            Position(0, 0), Position(0, 0),
            Position(0, 0), Position(0, 0),
        )

        val unique = mutableSetOf<Position>()
        unique.add(Position(0, 0))

        for (line in input) {
            val match = moveRegex.find(line)
            val (dir, steps) = match!!.destructured

            val xInc = when (dir) {
                "R" -> 1
                "L" -> -1
                else -> 0
            }
            val yInc = when (dir) {
                "U" -> 1
                "D" -> -1
                else -> 0
            }

            for (i in 0 until steps.toInt()) {
                knots[0] = Position(knots[0].x + xInc, knots[0].y + yInc)
                for (i in 1 until knots.size) {
                    var head = knots[i - 1]
                    var tail = knots[i]
                    if (tail.y == head.y && head.x - tail.x == 2) {
                        tail = Position(tail.x + 1, tail.y)
                    } else if (tail.y == head.y && head.x - tail.x == -2) {
                        tail = Position(tail.x - 1, tail.y)
                    } else if (tail.x == head.x && head.y - tail.y == 2) {
                        tail = Position(tail.x, tail.y + 1)
                    } else if (tail.x == head.x && head.y - tail.y == -2) {
                        tail = Position(tail.x, tail.y - 1)
                    } else if (Math.abs(tail.x - head.x) + Math.abs(tail.y - head.y) > 2) {
                        if (head.x > tail.x) {
                            tail = Position(tail.x + 1, tail.y)
                        } else {
                            tail = Position(tail.x - 1, tail.y)
                        }

                        if (head.y > tail.y) {
                            tail = Position(tail.x, tail.y + 1)
                        } else {
                            tail = Position(tail.x, tail.y - 1)
                        }
                    }
                    knots[i] = tail
                }
                unique.add(knots[9])
            }
        }

        return unique.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)
    val testInput2 = readInput("Day09_test2")
    check(part2(testInput2) == 36)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
