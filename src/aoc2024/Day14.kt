package aoc2024

import readInput


fun main() {

    class Grid2(val maxX: Int, val maxY: Int)

    class Robot(var position: Position, val velocity: Position, val grid: Grid2) {
        fun move() {
            var nextX = position.x + velocity.x
            if (nextX >= grid.maxX) {
                nextX = nextX % grid.maxX
            }
            if (nextX < 0) {
                nextX = grid.maxX + nextX
            }

            var nextY = position.y + velocity.y
            if (nextY >= grid.maxY) {
                nextY = nextY % grid.maxY
            }
            if (nextY < 0) {
                nextY = grid.maxY + nextY
            }

            position = Position(nextX, nextY)
        }
    }

    fun print(grid: Grid2, robots: List<Robot>) {
        for (i in 0 until grid.maxX) {
            for (j in 0 until  grid.maxY) {
                val containsRobot = robots
                    .map { it.position.x == i && it.position.y == j }
                    .map { if(it) 1 else 0 }
                    .sum() > 0
                if (containsRobot) {
                    print("*")
                } else {
                    print(" ")
                }
            }
            println()
        }
    }

    fun parseRobot(line: String, grid: Grid2): Robot {
        val regex = "p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)".toRegex()
        val match = regex.matchEntire(line)
        val position = Position(match!!.groupValues.get(2).toInt(), match!!.groupValues.get(1).toInt())
        val velocity = Position(match!!.groupValues.get(4).toInt(), match!!.groupValues.get(3).toInt())
        return Robot(position, velocity, grid)
    }

    fun simulate(robot: Robot, grid: Grid2, iterations: Int) {
        for (i in 0 until iterations) {
            robot.move()
        }
    }

    fun part1(input: List<String>, gridSizeX: Int, gridSizeY: Int, iterations: Int): Int {
        val grid = Grid2(gridSizeX, gridSizeY)
        val robots = mutableListOf<Robot>()
        for (line in input) {
            robots.add(parseRobot(line, grid))
        }
        for (robot in robots) {
            simulate(robot, grid, iterations)
        }

        var quad1 = 0
        var quad2 = 0
        var quad3 = 0
        var quad4 = 0
        for (robot in robots) {
            if (robot.position.x < gridSizeX / 2 && robot.position.y < gridSizeY / 2) {
                quad1++
            } else if (robot.position.x > gridSizeX / 2 && robot.position.y < gridSizeY / 2) {
                quad2++
            } else if (robot.position.x < gridSizeX / 2 && robot.position.y > gridSizeY / 2) {
                quad3++
            }  else if (robot.position.x > gridSizeX / 2 && robot.position.y > gridSizeY / 2) {
                quad4++
            }
        }
        return quad1 * quad2 * quad3 * quad4
    }

    fun part2(input: List<String>, gridSizeX: Int, gridSizeY: Int) {
        val grid = Grid2(gridSizeX, gridSizeY)
        val robots = mutableListOf<Robot>()
        for (line in input) {
            robots.add(parseRobot(line, grid))
        }

        for (i in 0 .. 10000) {
            for (robot in robots) {
                simulate(robot, grid, 1)
            }
            println("Iteration: " + (i + 1))
            print(grid, robots)
        }
    }



//    println(part1(readInput("aoc2024/Day14_test"), 7, 11, 100))
//    println(part1(readInput("aoc2024/Day14"), 103, 101, 100))
//    part2(readInput("aoc2024/Day14_test"), 7, 11)
    part2(readInput("aoc2024/Day14"), 103, 101)
}