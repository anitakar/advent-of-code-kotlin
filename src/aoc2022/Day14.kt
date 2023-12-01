package aoc2022

import println
import readInput

fun main() {

    fun print(grid: Array<CharArray>) {
        for (i in grid.indices) {
            for (j in grid[0].indices) {
                print(grid[i][j])
            }
            println()
        }
    }

    fun part1(input: List<String>): Int {
        var minx = 500
        var maxx = 500
        var miny = 0
        var maxy = 0
        for (line in input) {
            val coords = line.split(" -> ")
            for (coord in coords) {
                val (x, y) = coord.split(",")
                minx = Math.min(minx, x.toInt())
                maxx = Math.max(maxx, x.toInt())
                miny = Math.min(miny, y.toInt())
                maxy = Math.max(maxy, y.toInt())
            }
        }
        val grid = Array<CharArray>(maxy - miny + 1) { CharArray(maxx - minx + 3) }
        for (i in grid.indices) {
            for (j in grid[0].indices) {
                grid[i][j] = '.'
            }
        }
        grid[0][500 - minx + 1] = '+'
        for (line in input) {
            val coords = line.split(" -> ")
            val (prevXs, prevYs) = coords[0].split(",")
            var prevX = prevXs.toInt() - minx + 1
            var prevY = prevYs.toInt() - miny
            for (i in 1 until coords.size) {
                val (xs, ys) = coords[i].split(",")
                val x = xs.toInt() - minx + 1
                val y = ys.toInt() - miny
                if (prevX == x) {
                    for (ny in Math.min(y, prevY)..Math.max(y, prevY)) {
                        grid[ny][x] = '#'
                    }
                }
                if (prevY == y) {
                    for (nx in Math.min(x, prevX)..Math.max(x, prevX)) {
                        grid[y][nx] = '#'
                    }
                }
                prevX = x
                prevY = y
            }
        }
        var grain = 1
        while (true) {
            var droplety = 0
            var dropletx = 500 - minx + 1
            var moved = true
            do {
                if (grid[droplety + 1][dropletx] == '.') {
                    droplety += 1
                } else if (grid[droplety + 1][dropletx - 1] == '.') {
                    droplety += 1
                    dropletx -= 1
                } else if (grid[droplety + 1][dropletx + 1] == '.') {
                    droplety += 1
                    dropletx += 1
                } else {
                    moved = false
                    grid[droplety][dropletx] = 'o'
                }
                if (droplety == maxy) return grain - 1
            } while (moved)
            grain += 1
        }
        return grain
    }

    fun part2(input: List<String>): Int {
        var minx = 500
        var maxx = 500
        var miny = 0
        var maxy = 0
        for (line in input) {
            val coords = line.split(" -> ")
            for (coord in coords) {
                val (x, y) = coord.split(",")
                minx = Math.min(minx, x.toInt())
                maxx = Math.max(maxx, x.toInt())
                miny = Math.min(miny, y.toInt())
                maxy = Math.max(maxy, y.toInt())
            }
        }
        val grid = Array<CharArray>(maxy - miny + 3) { CharArray(maxx - minx + 3 + 400) }
        for (i in grid.indices) {
            for (j in grid[0].indices) {
                grid[i][j] = '.'
            }
        }
        for (j in grid[0].indices) {
            grid[maxy + 2][j] = '#'
        }
        grid[0][500 - minx + 1 + 200] = '+'
        for (line in input) {
            val coords = line.split(" -> ")
            val (prevXs, prevYs) = coords[0].split(",")
            var prevX = prevXs.toInt() - minx + 1 + 200
            var prevY = prevYs.toInt() - miny
            for (i in 1 until coords.size) {
                val (xs, ys) = coords[i].split(",")
                val x = xs.toInt() - minx + 1 + 200
                val y = ys.toInt() - miny
                if (prevX == x) {
                    for (ny in Math.min(y, prevY)..Math.max(y, prevY)) {
                        grid[ny][x] = '#'
                    }
                }
                if (prevY == y) {
                    for (nx in Math.min(x, prevX)..Math.max(x, prevX)) {
                        grid[y][nx] = '#'
                    }
                }
                prevX = x
                prevY = y
            }
        }
        var grain = 1
        while (true) {
            var droplety = 0
            var dropletx = 500 - minx + 1 + 200
            var moved = true
            do {
                if (grid[droplety + 1][dropletx] == '.') {
                    droplety += 1
                } else if (grid[droplety + 1][dropletx - 1] == '.') {
                    droplety += 1
                    dropletx -= 1
                } else if (grid[droplety + 1][dropletx + 1] == '.') {
                    droplety += 1
                    dropletx += 1
                } else {
                    moved = false
                    grid[droplety][dropletx] = 'o'
                    if (droplety == 0) {
                        return grain
                    }
                }
            } while (moved)
            grain += 1
        }
        return grain
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
