package aoc2023

import readInput
import kotlin.math.abs

fun main() {

    fun convertToMaze(lines: List<String>): Array<Array<Char>> {
        val maze = Array(lines.size) { Array(lines[0].length) { '.' } }
        for (i in lines.indices) {
            maze[i] = lines[i].toCharArray().toTypedArray()
        }
        return maze
    }

    fun expand(maze: Array<Array<Char>>): Array<Array<Char>> {
        val emptyRowIndices = maze.withIndex().filter { (_, row) -> row.all { it == '.' } }.map { it.index }
        val emptyColumnIndices = mutableListOf<Int>()
        for (j in maze[0].indices) {
            var allEmpty = true
            for (i in maze.indices) {
                if (maze[i][j] != '.') allEmpty = false
            }
            if (allEmpty) emptyColumnIndices.add(j)
        }

        val expanded = Array(maze.size + emptyRowIndices.size) {
            Array(maze[0].size + emptyColumnIndices.size) { '.' }
        }

        var shiftRows = 0
        for (i in maze.indices) {
            if (emptyRowIndices.contains(i)) shiftRows += 1
            var shiftCols = 0
            for (j in maze[0].indices) {
                if (emptyColumnIndices.contains(j)) shiftCols += 1
                expanded[i + shiftRows][j + shiftCols] = maze[i][j]
            }
        }

        return expanded
    }

    fun findGalaxies(maze: Array<Array<Char>>): List<Pair<Int, Int>> {
        val galaxies = mutableListOf<Pair<Int, Int>>()
        for (i in maze.indices) {
            for (j in maze[0].indices) {
                if (maze[i][j] == '#') galaxies.add(Pair(i, j))
            }
        }
        return galaxies
    }

    fun expandGalaxies(maze: Array<Array<Char>>, galaxies: List<Pair<Int, Int>>, expansion: Int): List<Pair<Long, Long>> {
        val emptyRowIndices = maze.withIndex().filter { (_, row) -> row.all { it == '.' } }.map { it.index }
        val emptyColumnIndices = mutableListOf<Int>()
        for (j in maze[0].indices) {
            var allEmpty = true
            for (i in maze.indices) {
                if (maze[i][j] != '.') allEmpty = false
            }
            if (allEmpty) emptyColumnIndices.add(j)
        }

        val expandedGalaxies = mutableListOf<Pair<Long, Long>>()
        var shiftRows = 0L
        for (i in maze.indices) {
            if (emptyRowIndices.contains(i)) shiftRows += (expansion - 1)
            var shiftCols = 0L
            for (j in maze[0].indices) {
                if (emptyColumnIndices.contains(j)) shiftCols += (expansion - 1)
                if (galaxies.contains(Pair(i, j))) {
                    expandedGalaxies.add(Pair(i + shiftRows, j + shiftCols))
                }
            }
        }
        return expandedGalaxies
    }

    fun Pair<Int, Int>.manhattanDistance(other: Pair<Int, Int>): Int {
        return abs(this.first - other.first) + abs(this.second - other.second)
    }

    fun Pair<Long, Long>.manhattanDistance(other: Pair<Long, Long>): Long {
        return abs(this.first - other.first) + abs(this.second - other.second)
    }

    fun part1(lines: List<String>): Int {
        val maze = convertToMaze(lines)
        val expanded = expand(maze)
        val galaxies = findGalaxies(expanded)
        var sumDistances = 0
        for (i in 0 until galaxies.size - 1) {
            for (j in i + 1 until galaxies.size) {
                sumDistances += galaxies[i].manhattanDistance(galaxies[j])
            }
        }
        return sumDistances
    }

    fun part2(lines: List<String>, expansion: Int): Long {
        val maze = convertToMaze(lines)
        val galaxies = findGalaxies(maze)
        val expandedGalaxies = expandGalaxies(maze, galaxies, expansion)
        var sumDistances = 0L
        for (i in 0 until expandedGalaxies.size - 1) {
            for (j in i + 1 until expandedGalaxies.size) {
                sumDistances += expandedGalaxies[i].manhattanDistance(expandedGalaxies[j])
            }
        }
        return sumDistances
    }

    println(part1(readInput("aoc2023/Day11_test")))
    println(part1(readInput("aoc2023/Day11")))
    println(part2(readInput("aoc2023/Day11_test"), 10))
    println(part2(readInput("aoc2023/Day11"), 1_000_000))
}