package aoc2024

import readInput
import kotlin.math.abs
import kotlin.math.max


fun main() {

    fun decode(encoded: String): MutableList<Int> {
        val result = mutableListOf<Int>()
        var isBlock = true
        var blockIndex = 0
        for (elem in encoded) {
            if (isBlock) {
                val occurs = elem.toString().toInt()
                for (i in 0 until occurs) {
                    result.add(blockIndex)
                }
            } else {
                val occurs = elem.toString().toInt()
                for (i in 0 until occurs) {
                    result.add(-1)
                }
            }

            if (isBlock) blockIndex++
            isBlock = !isBlock
        }
        return result
    }

    fun decode2(encoded: String): MutableList<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        var isBlock = true
        var blockIndex = 0
        for (elem in encoded) {
            if (isBlock) {
                val occurs = elem.toString().toInt()
                result.add(Pair(blockIndex, occurs))
            } else {
                val occurs = elem.toString().toInt()
                result.add(Pair(-1, occurs))
            }

            if (isBlock) blockIndex++
            isBlock = !isBlock
        }
        return result
    }

    fun compress(decoded: MutableList<Int>) {
        var i = 0
        var j = decoded.size - 1
        while (i <= j) {
            if (decoded[i] == -1) {
                while (decoded[j] == -1 && j > 0) {
                    j--
                }
                if (i <= j) {
                    decoded[i] = decoded[j]
                    decoded.removeAt(j)
                    j--
                }
            }
            i++
        }
    }

    fun compress2(decoded: MutableList<Pair<Int, Int>>) {
        var fileToMoveIndex = decoded.size - 1
        while (fileToMoveIndex > 0) {
            if (decoded[fileToMoveIndex].first == -1) {
                fileToMoveIndex--
                continue
            }

            for (emptySpaceIndex in 0 until fileToMoveIndex) {
                if (decoded[emptySpaceIndex].first != -1)
                    continue

                if (decoded[emptySpaceIndex].second >= decoded[fileToMoveIndex].second) {
                    val removedSize = decoded[fileToMoveIndex].second
                    val removedElem = decoded[fileToMoveIndex].first
                    val sizeDiff = decoded[emptySpaceIndex].second - removedSize

                    decoded[fileToMoveIndex] = Pair(-1, removedSize)
                    if (fileToMoveIndex + 1 < decoded.size && decoded[fileToMoveIndex + 1].first == -1) {
                        decoded[fileToMoveIndex] =
                            Pair(-1, decoded[fileToMoveIndex].second + decoded[fileToMoveIndex + 1].second)
                        decoded.removeAt(fileToMoveIndex+1)
                    }
//                    if (decoded[fileToMoveIndex - 1].first == -1) {
//                        decoded[fileToMoveIndex] =
//                            Pair(-1, decoded[fileToMoveIndex].second + decoded[fileToMoveIndex - 1].second)
//                        decoded.removeAt(fileToMoveIndex - 1)
//                    }

                    decoded.removeAt(emptySpaceIndex)
                    decoded.add(emptySpaceIndex, Pair(removedElem, removedSize))
                    if (sizeDiff > 0) {
                        decoded.add(emptySpaceIndex + 1, Pair(-1, sizeDiff))
                    }
                    break
                }
            }
            fileToMoveIndex--
        }
    }

    fun checksum(compressed: MutableList<Int>): Long {
        var checksum = 0L
        for (i in compressed.indices) {
            if (compressed[i] == -1)
                continue

            checksum += i * compressed[i]
        }
        return checksum
    }

    fun checksum(compressed: MutableList<Pair<Int, Int>>): Long {
        var checksum = 0L
        var curIndex = 0
        for (i in compressed.indices) {
            if (compressed[i].first == -1) {
                curIndex += compressed[i].second
                continue
            }

            for (j in 0 until compressed[i].second) {
                checksum += (curIndex + j) * compressed[i].first
            }
            curIndex += compressed[i].second
        }
        return checksum
    }

    fun part1(input: List<String>): Long {
        val decoded = decode(input[0])
        compress(decoded)
        return checksum(decoded)
    }

    fun part2(input: List<String>): Long {
        val decoded = decode2(input[0])
        compress2(decoded)
        return checksum(decoded)
    }

    println(part1(readInput("aoc2024/Day09_test")))
    println(part1(readInput("aoc2024/Day09")))
    println(part2(readInput("aoc2024/Day09_test")))
    println(part2(readInput("aoc2024/Day09")))
}