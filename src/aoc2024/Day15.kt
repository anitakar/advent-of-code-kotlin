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

        }

        fun down() {
        }

        fun left() {
        }

        fun right() {
        }
    }

    fun part1(input: List<String>): Int {
        val map = MutableGrid(input.subList(0, input.indexOfFirst { input.isEmpty() }))
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }


    println(part1(readInput("aoc2024/Day15_test")))
    println(part1(readInput("aoc2024/Day15")))
    println(part2(readInput("aoc2024/Day15_test")))
    println(part2(readInput("aoc2024/Day15")))
}