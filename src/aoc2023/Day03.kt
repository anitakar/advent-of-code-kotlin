package aoc2023

import readInput

fun main() {

    fun createArray(input: List<String>): Array<Array<Char>> {
        val result = Array(input.size) { Array(input[0].length) { '.' } }
        for ((index, line) in input.withIndex()) {
            result[index] = line.toCharArray().toTypedArray()
        }
        return result
    }

    fun validIndex(row: Int, col: Int, array: Array<Array<Char>>): Boolean {
        return row >= 0 && row < array.size && col >= 0 && col < array[0].size
    }

    fun isSymbol(char: Char): Boolean {
        return char != '.'
    }

    fun adjacentToSymbol(row: Int, startCol: Int, endCol: Int, array: Array<Array<Char>>): Boolean {
        fun inNumber(i: Int, j: Int): Boolean {
            return i == row && j >= startCol && j <= endCol
        }

        var adjacentToSymbol = false
        for (i in row - 1..row + 1) {
            for (j in startCol - 1..endCol + 1) {
                if (validIndex(i, j, array) && isSymbol(array[i][j]) && !inNumber(i, j)) {
                    adjacentToSymbol = true
                }
            }
        }
        return adjacentToSymbol
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        val array = createArray(input)
        for (i in array.indices) {
            var withinNumber = false
            var start = -1
            var end = -1
            var number = 0
            for (j in 0 until array[0].size) {
                if (array[i][j].isDigit()) {
                    if (!withinNumber) {
                        withinNumber = true
                        start = j
                        end = j
                        number = array[i][j].digitToInt()
                    } else {
                        number = (number * 10) + array[i][j].digitToInt()
                        end = j
                    }
                } else {
                    if (withinNumber) {
                        withinNumber = false
                        if (adjacentToSymbol(i, start, end, array)) {
                            sum += number
                        }
                        start = -1
                        end = -1
                        number = 0
                    }
                }
            }
            if (withinNumber) {
                if (adjacentToSymbol(i, start, end, array)) {
                    sum += number
                }
            }
        }
        return sum
    }

    fun readNumberRight(row: Int, col: Int, array: Array<Array<Char>>): Int {
        var k = 1
        var number = 0
        do {
            number = number * 10 + array[row][col + k].digitToInt()
            k += 1
        } while (validIndex(row, col + k, array) && array[row][col + k].isDigit())
        return number
    }

    val exponentToPower = mapOf(0 to 1, 1 to 10, 2 to 100, 3 to 1000, 4 to 10000, 5 to 100000)

    fun readNumberLeft(row: Int, col: Int, array: Array<Array<Char>>): Int {
        var k = 1
        var number = 0

        if (!validIndex(row, col - k, array) || !array[row][col - k].isDigit()) {
            return number
        }

        do {
            number += array[row][col - k].digitToInt() * exponentToPower[k - 1]!!
            k += 1
        } while (validIndex(row, col - k, array) && array[row][col - k].isDigit())
        return number
    }

    fun processRow(row: Int, j: Int, array: Array<Array<Char>>): Pair<Int, Int> {
        var ratio = 1
        var numberOfAdjacent = 0
        if (validIndex(row, j - 1, array) && array[row][j - 1].isDigit()
            && validIndex(row, j + 1, array) && array[row][j + 1].isDigit()
            && !array[row][j].isDigit()
        ) {
            numberOfAdjacent += 2
            //println(readNumberRight(row, j, array))
            //println(readNumberLeft(row, j, array))
            ratio *= readNumberRight(row, j, array)
            ratio *= readNumberLeft(row, j, array)
        } else {
            if (validIndex(row, j - 1, array) && array[row][j - 1].isDigit()) {
                numberOfAdjacent += 1
                var k = j - 1
                while (validIndex(row, k, array) && array[row][k].isDigit()) {
                    k -= 1
                }
                val right = readNumberRight(row, k, array)
                val number = right
                //println(number)
                ratio *= number
            } else if (validIndex(row, j, array) && array[row][j].isDigit()) {
                numberOfAdjacent += 1
                //println(readNumberRight(row, j - 1, array))
                ratio *= readNumberRight(row, j - 1, array)
            } else if (validIndex(row, j + 1, array) && array[row][j + 1].isDigit()) {
                numberOfAdjacent += 1
                //println(readNumberRight(row, j, array))
                ratio *= readNumberRight(row, j, array)
            }
        }
        return Pair(numberOfAdjacent, ratio)
    }

    fun part2(input: List<String>): Long {
        var pairs = 0
        var sum = 0L
        val array = createArray(input)
        for (i in array.indices) {
            for (j in array[i].indices) {
                if (array[i][j] == '*') {
                    var numberOfAdjacent = 0
                    var ratio = 1
                    if (validIndex(i, j - 1, array) && array[i][j - 1].isDigit()) {
                        numberOfAdjacent += 1
                        val numLeft = readNumberLeft(i, j, array)
                        //println(numLeft)
                        ratio *= numLeft
                    }
                    if (validIndex(i, j + 1, array) && array[i][j + 1].isDigit()) {
                        numberOfAdjacent += 1
                        val numRight = readNumberRight(i, j, array)
                        //println(numRight)
                        ratio *= numRight
                    }
                    val (adjcUp, numUp) = processRow(i - 1, j, array)
                    numberOfAdjacent += adjcUp
                    ratio *= numUp
                    val (adjcDown, numDown) = processRow(i + 1, j, array)
                    numberOfAdjacent += adjcDown
                    ratio *= numDown

                    if (numberOfAdjacent == 2) {
                        sum += ratio
                        pairs += 1
                        //println(ratio)
                    }
                    //println("------")
                }
            }
        }
        //println(pairs)
        return sum
    }

    println(part1(readInput("aoc2023/Day03_test")))
    println(part1(readInput("aoc2023/Day03")))
    println(part2(readInput("aoc2023/Day03_test")))
//    println(part2(readInput("aoc2023/Day03_test1")))
//    println(part2(readInput("aoc2023/Day03_test2")))
//    println(part2(readInput("aoc2023/Day03_test3")))
//    println(part2(readInput("aoc2023/Day03_test4")))
//    println(part2(readInput("aoc2023/Day03_test5")))
//    println(part2(readInput("aoc2023/Day03_test6")))
    println(part2(readInput("aoc2023/Day03")))
}