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

    fun compress(decoded: MutableList<Int>) {
        var  i = 0
        var  j = decoded.size - 1
        while (i <= j) {
            if (decoded[i] == -1) {
                while (decoded[j] == -1 && j >0) {
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

    fun checksum(compressed: MutableList<Int>): Long {
        var checksum = 0L
        for (i in compressed.indices) {
            if (compressed[i] == -1)
                continue

            checksum += i * compressed[i]
        }
        return checksum
    }

    fun part1(input: List<String>): Long {
        val decoded = decode(input[0])
        compress(decoded)
        return checksum(decoded)
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    println(part1(readInput("aoc2024/Day09_test")))
    println(part1(readInput("aoc2024/Day09")))
//    println(part2(readInput("aoc2024/Day09_test")))
//    println(part2(readInput("aoc2024/Day09")))
}