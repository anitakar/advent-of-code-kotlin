package aoc2023

import readInput

fun main() {
    fun moveNorth(dish: Array<Array<Char>>) {
        var moved: Boolean
        do {
            moved = false
            for (i in 1 until dish.size) {
                for (j in dish[i].indices) {
                    if (dish[i][j] == 'O' && dish[i - 1][j] == '.') {
                        moved = true
                        dish[i - 1][j] = 'O'
                        dish[i][j] = '.'
                    }
                }
            }
        } while (moved)
    }

    fun moveSouth(dish: Array<Array<Char>>) {
        var moved: Boolean
        do {
            moved = false
            for (i in dish.size - 2 downTo 0) {
                for (j in dish[i].indices) {
                    if (dish[i][j] == 'O' && dish[i + 1][j] == '.') {
                        moved = true
                        dish[i + 1][j] = 'O'
                        dish[i][j] = '.'
                    }
                }
            }
        } while (moved)
    }

    fun moveWest(dish: Array<Array<Char>>) {
        var moved: Boolean
        do {
            moved = false
            for (j in 1 until dish[0].size) {
                for (i in dish.indices) {
                    if (dish[i][j] == 'O' && dish[i][j - 1] == '.') {
                        moved = true
                        dish[i][j] = '.'
                        dish[i][j - 1] = 'O'
                    }
                }
            }
        } while (moved)
    }

    fun moveEast(dish: Array<Array<Char>>) {
        var moved: Boolean
        do {
            moved = false
            for (j in dish[0].size - 2 downTo 0) {
                for (i in dish.indices) {
                    if (dish[i][j] == 'O' && dish[i][j + 1] == '.') {
                        moved = true
                        dish[i][j] = '.'
                        dish[i][j + 1] = 'O'
                    }
                }
            }
        } while (moved)
    }

    fun totalLoad(dish: Array<Array<Char>>): Int {
        var sum = 0
        for (i in dish.indices) {
            sum += dish[i].count { it == 'O' } * (dish.size - i)
        }
        return sum
    }

    fun readDish(lines: List<String>): Array<Array<Char>> {
        val dish = Array(lines.size) { Array(lines[0].length) { '.' } }
        for (i in lines.indices) {
            dish[i] = lines[i].toCharArray().toTypedArray()
        }
        return dish
    }

    fun part1(lines: List<String>): Int {
        val dish = readDish(lines)
        moveNorth(dish)
        return totalLoad(dish)
    }

    fun part2(lines: List<String>): Int {
        val dish = readDish(lines)

        for (i in 0..300) {
            moveNorth(dish)
            moveWest(dish)
            moveSouth(dish)
            moveEast(dish)
            println("" + totalLoad(dish) + " " + i)
        }

        val index = (1000000000 - 116) % (200 - 116) + 116
        println(index)

        val index2 = (183 - 116) % (200 - 116) + 116
        println(index2)

        val index3 = (297 - 116) % (200 - 116) + 116
        println(index3)

        return totalLoad(dish)
    }

//    println(part1(readInput("aoc2023/Day14_test")))
//    println(part1(readInput("aoc2023/Day14")))
//    part2(readInput("aoc2023/Day14_test"))
    part2(readInput("aoc2023/Day14"))
}