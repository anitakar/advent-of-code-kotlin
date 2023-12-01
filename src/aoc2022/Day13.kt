package aoc2022

import org.json.JSONArray
import println
import readInput

fun main() {

    fun inRightOrder(left: List<Any>, right: List<Any>): Pair<Boolean, Boolean> {
        for (i in left.indices) {
            if (i >= right.size) {
                return Pair(false, true)
            }

            if (left[i] is Int && right[i] is Int) {

                if ((left[i] as Int) < (right[i] as Int)) {
                    return Pair(true, true)
                }

                if ((left[i] as Int) > (right[i] as Int)) {
                    return Pair(false, true)
                }
            }

            if (left[i] is List<*> && right[i] is List<*>) {

                val (inOrder, conclusive) = inRightOrder(left[i] as List<Any>, right[i] as List<Any>)
                if (conclusive) return Pair(inOrder, true)
            }

            if (left[i] is Int && right[i] is List<*>) {

                val (inOrder, conclusive) = inRightOrder(listOf(left[i]), right[i] as List<Any>)
                if (conclusive) return Pair(inOrder, true)
            }

            if (left[i] is List<*> && right[i] is Int) {

                val (inOrder, conclusive) = inRightOrder(left[i] as List<Any>, listOf(right[i]))
                if (conclusive) return Pair(inOrder, true)
            }
        }

        if (left.size < right.size) return Pair(true, true)

        return Pair(true, false)
    }

    fun parseArray(jsonArray: JSONArray): List<Any> {
        val result = mutableListOf<Any>()
        for (i in 0 until jsonArray.length()) {
            if (jsonArray[i] is Int) {
                result.add(jsonArray.getInt(i))
            } else if (jsonArray[i] is JSONArray) {
                if (jsonArray.getJSONArray(i).isEmpty) {
                    result.add(mutableListOf<Any>())
                } else {
                    result.add(parseArray(jsonArray.getJSONArray(i)))
                }
            }
        }
        return result
    }

    fun parseArray(line: String, start: Int): Pair<List<Any>, Int> {
        val result = mutableListOf<Any>()
        var i = start
        while (i < line.length) {
            if (line[i] == ']') {
                return Pair(result, i + 1)
            }

            if (line[i] == '[') {
                val (arr, j) = parseArray(line, i + 1)
                result.add(arr)
                i = j
            } else {
                var next = i
                while (line[next].isDigit()) {
                    next += 1
                }
                if (next != i) {
                    result.add(line.substring(i, next).toInt())
                    i = next
                } else {
                    i = next + 1
                }
            }
        }
        return Pair(result, line.length)
    }

    fun parseLine(line: String): List<Any> {
        return parseArray(line, 1).first
    }

    fun part1(input: List<String>): Int {
        var left: List<Any>? = null
        var right: List<Any>? = null

        var sumIndicesCorrect = 0
        for (i in input.indices) {
            val line = input[i]
            if (line.trim().isBlank()) {
                val correct = inRightOrder(left!!, right!!)
                if (correct.first) {
                    sumIndicesCorrect += (i + 1) / 3
                }
                left = null
                right = null
                continue
            }

            val parsed = parseLine(line)
            if (left == null) left = parsed else right = parsed
        }

        if (left != null && right != null) {
            val correct = inRightOrder(left!!, right!!)
            if (correct.first) {
                sumIndicesCorrect += (input.size + 1) / 3
            }
        }

        return sumIndicesCorrect
    }

    fun part2(input: List<String>): Int {
        val newList = mutableListOf<List<Any>>()
        newList.add(parseLine("[[2]]"))
        newList.add(parseLine("[[6]]"))
        input.filter { it.isNotBlank() }.forEach { newList.add(parseLine(it)) }

        newList.sortWith { e1, e2 ->
            val (smaller, conclusive) = inRightOrder(e1, e2)
            if (!conclusive) return@sortWith 0;
            if (smaller) return@sortWith -1;
            if (!smaller) return@sortWith 1;
            return@sortWith 0;
        }
        var product = 1
        for (i in newList.indices) {
            if (newList[i] == mutableListOf<Any>(mutableListOf<Any>(2))) {
                product *= (i+1)
            }
            if (newList[i] == mutableListOf<Any>(mutableListOf<Any>(6))) {
                product *= (i+1)
            }
        }
        return product
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 140)

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
