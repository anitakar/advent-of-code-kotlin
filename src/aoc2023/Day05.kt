package aoc2023

import readInput

fun main() {
    fun part1(lines: List<String>): Long {
        val seeds = lines[0].substring("seeds: ".length).trim().split(" ").map { it.toLong() }
        var currentlyTranslating = seeds.toTypedArray()
        var translated = Array(seeds.size) { 0L }

        var afterBlank = false
        for (i in 4 until lines.size) {
            if (lines[i].isBlank()) {
                afterBlank = true
                for ((index, cur) in translated.withIndex()) {
                    if (cur == 0L) {
                        translated[index] = currentlyTranslating[index]
                    }
                }
                currentlyTranslating = translated
                translated = Array(seeds.size) { 0 }
                continue
            }
            if (afterBlank) {
                afterBlank = false
                continue
            }

            for ((index, current) in currentlyTranslating.withIndex()) {
                val mapping = lines[i].split(" ").map { it.toLong() }
                if (current >= mapping[1] && current < mapping[1] + mapping[2]) {
                    translated[index] = mapping[0] + (current - mapping[1])
                }
            }
        }

        for ((index, cur) in translated.withIndex()) {
            if (cur == 0L) {
                translated[index] = currentlyTranslating[index]
            }
        }

        return translated.min()
    }

    // TODO write algo that discovers ranges going back
    fun part2(lines: List<String>): Long {
        val seedRanges = lines[0].substring("seeds: ".length).trim().split(" ").map { it.toLong() }
        val seeds = mutableListOf<Long>()
        for (i in 0 until seedRanges.size step 2) {
            for (j in 0 until seedRanges[i + 1] ) {
                seeds.add(seedRanges[i] + j)
            }
        }

        var currentlyTranslating = seeds.toTypedArray()
        var translated = Array(seeds.size) { 0L }

        var afterBlank = false
        for (i in 4 until lines.size) {
            if (lines[i].isBlank()) {
                afterBlank = true
                for ((index, cur) in translated.withIndex()) {
                    if (cur == 0L) {
                        translated[index] = currentlyTranslating[index]
                    }
                }
                currentlyTranslating = translated
                translated = Array(seeds.size) { 0 }
                continue
            }
            if (afterBlank) {
                afterBlank = false
                continue
            }

            for ((index, current) in currentlyTranslating.withIndex()) {
                val mapping = lines[i].split(" ").map { it.toLong() }
                if (current >= mapping[1] && current < mapping[1] + mapping[2]) {
                    translated[index] = mapping[0] + (current - mapping[1])
                }
            }
        }

        for ((index, cur) in translated.withIndex()) {
            if (cur == 0L) {
                translated[index] = currentlyTranslating[index]
            }
        }

        return translated.min()
    }

    println(part1(readInput("aoc2023/Day05_test")))
    println(part1(readInput("aoc2023/Day05")))
    println(part2(readInput("aoc2023/Day05_test")))
    //println(part2(readInput("aoc2023/Day05")))
}