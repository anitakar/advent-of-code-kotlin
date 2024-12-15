package aoc2024

import readInput


fun main() {

    class Robot(var position: Position, val map: MutableGrid) {
        fun up() {
            val nextPosition = map.up(position)
            if (nextPosition == null) {
                return
            }

            val value = map.get(nextPosition)
            if (value == '#') {
                return
            }

            if (value == '.') {
                position = nextPosition
                return
            }

            // value = O
            var afterEndOfBoxes = nextPosition
            while (map.get(afterEndOfBoxes!!) == 'O') {
                afterEndOfBoxes = map.up(afterEndOfBoxes)
            }
            if (map.get(afterEndOfBoxes) == '#') {
                return
            }

            // afterEndOfBoxes = .
            map.set(nextPosition, '.')
            position = nextPosition

            var next = nextPosition
            do {
                next = map.up(next!!)
                map.set(next!!, 'O')
            } while (next != afterEndOfBoxes)
        }

        fun down() {
            val nextPosition = map.down(position)
            if (nextPosition == null) {
                return
            }

            val value = map.get(nextPosition)
            if (value == '#') {
                return
            }

            if (value == '.') {
                position = nextPosition
                return
            }

            // value = O
            var afterEndOfBoxes = nextPosition
            while (map.get(afterEndOfBoxes!!) == 'O') {
                afterEndOfBoxes = map.down(afterEndOfBoxes!!)
            }
            if (map.get(afterEndOfBoxes!!) == '#') {
                return
            }

            // afterEndOfBoxes = .
            map.set(nextPosition, '.')
            position = nextPosition

            var next = nextPosition
            do {
                next = map.down(next!!)
                map.set(next!!, 'O')
            } while (next != afterEndOfBoxes)
        }

        fun left() {
            val nextPosition = map.left(position)
            if (nextPosition == null) {
                return
            }

            val value = map.get(nextPosition)
            if (value == '#') {
                return
            }

            if (value == '.') {
                position = nextPosition
                return
            }

            // value = O
            var afterEndOfBoxes = nextPosition
            while (map.get(afterEndOfBoxes!!) == 'O') {
                afterEndOfBoxes = map.left(afterEndOfBoxes!!)
            }
            if (map.get(afterEndOfBoxes!!) == '#') {
                return
            }

            // afterEndOfBoxes = .
            map.set(nextPosition, '.')
            position = nextPosition

            var next = nextPosition
            do {
                next = map.left(next!!)
                map.set(next!!, 'O')
            } while (next != afterEndOfBoxes)
        }

        fun right() {
            val nextPosition = map.right(position)
            if (nextPosition == null) {
                return
            }

            val value = map.get(nextPosition)
            if (value == '#') {
                return
            }

            if (value == '.') {
                position = nextPosition
                return
            }

            // value = O
            var afterEndOfBoxes = nextPosition
            while (map.get(afterEndOfBoxes!!) == 'O') {
                afterEndOfBoxes = map.right(afterEndOfBoxes!!)
            }
            if (map.get(afterEndOfBoxes!!) == '#') {
                return
            }

            // afterEndOfBoxes = .
            map.set(nextPosition, '.')
            position = nextPosition

            var next = nextPosition
            do {
                next = map.right(next!!)
                map.set(next!!, 'O')
            } while (next != afterEndOfBoxes)
        }
    }

    fun findRobot(map: MutableGrid): Robot {
        for (i in map.map.indices) {
            for (j in map.map[i].indices) {
                if (map.get(i, j) == '@') {
                    map.set(i, j, '.')
                    return Robot(Position(i, j), map)
                }
            }
        }
        return Robot(Position(0, 0), map)
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

    fun part2(input: List<String>): Int {
        return 0
    }


    println(part1(readInput("aoc2024/Day15_test")))
    println(part1(readInput("aoc2024/Day15")))
//    println(part2(readInput("aoc2024/Day15_test")))
//    println(part2(readInput("aoc2024/Day15")))
}