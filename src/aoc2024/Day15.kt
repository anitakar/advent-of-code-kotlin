package aoc2024

import readInput


fun main() {

    fun findRobot(map: MutableGrid): RobotDay15Part1 {
        for (i in map.map.indices) {
            for (j in map.map[i].indices) {
                if (map.get(i, j) == '@') {
                    map.set(i, j, '.')
                    return RobotDay15Part1(Position(i, j), map)
                }
            }
        }
        return RobotDay15Part1(Position(0, 0), map)
    }

    fun part1(input: List<String>): Long {
        val map = MutableGrid(input.subList(0, input.indexOfFirst { it.isEmpty() }))
        val robot = findRobot(map)
        for (line in input.subList(input.indexOfFirst { input.isEmpty() } + 1, input.size)) {
            for (char in line) {
                if (char == '^') robot.up()
                else if (char == 'v') robot.down()
                else if (char == '>') robot.right()
                else if (char == '<') robot.left()
            }
        }
        var total = 0L
        for (i in map.map.indices) {
            for (j in map.map[i].indices) {
                if (map.get(i, j) == 'O') {
                    total += ((100*i) + j)
                }
            }
        }
        return total
    }

    fun enlargeMap(initial: MutableGrid): MutableGrid {
        val edited = mutableListOf<MutableList<Char>>()
        for (i in initial.map.indices) {
            edited.add(mutableListOf())
            for (j in initial.map[i].indices) {
                val char = initial.get(i, j)
                if (char == '#') {
                    edited[i].add('#')
                    edited[i].add('#')
                } else if (char == '.') {
                    edited[i].add('.')
                    edited[i].add('.')
                } else if (char == '@') {
                    edited[i].add('@')
                    edited[i].add('.')
                } else if (char == 'O') {
                    edited[i].add('[')
                    edited[i].add(']')
                }
            }
        }

        return MutableGrid().apply { map = edited }
    }

    fun findRobot2(map: MutableGrid): RobotDay15Part2 {
        for (i in map.map.indices) {
            for (j in map.map[i].indices) {
                if (map.get(i, j) == '@') {
                    map.set(i, j, '.')
                    return RobotDay15Part2(Position(i, j), map)
                }
            }
        }
        return RobotDay15Part2(Position(0, 0), map)
    }

    fun part2(input: List<String>): Long {
        val initialMap = MutableGrid(input.subList(0, input.indexOfFirst { it.isEmpty() }))
        val map = enlargeMap(initialMap)
        val robot = findRobot2(map)
        for (line in input.subList(input.indexOfFirst { input.isEmpty() } + 1, input.size)) {
            for (char in line) {
                if (char == '^') robot.up()
                else if (char == 'v') robot.down()
                else if (char == '>') robot.right()
                else if (char == '<') robot.left()
            }
        }
        map.print()
        var total = 0L
        for (i in map.map.indices) {
            for (j in map.map[i].indices) {
                if (map.get(i, j) == '[') {
                    total += ((100*i) + j)
                }
            }
        }
        return total
    }


    println(part1(readInput("aoc2024/Day15_test")))
    println(part1(readInput("aoc2024/Day15")))
    println(part2(readInput("aoc2024/Day15_test2")))
//    println(part2(readInput("aoc2024/Day15")))
}