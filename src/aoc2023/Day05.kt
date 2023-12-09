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

    // intersect on ranges goe OOM as it does not evaluate to range but list of elements :(
    fun LongRange.intersect(other: LongRange): LongRange? {
        var first: LongRange
        var second: LongRange
        if (this.first < other.first) {
            first = this
            second = other
        } else {
            first = other
            second = this
        }

        if (first.last() < second.first()) {
            return null
        }

        return second.first() .. Math.min(first.last(), second.last())
    }

    fun part2(lines: List<String>): Long {
        val currentlyTranslating = mutableListOf<Pair<LongRange, LongRange>>()

        var afterHeader = false
        var locationMapping = true
        for (i in lines.size - 1 downTo 4) {
            if (lines[i].isBlank() || !lines[i][0].isDigit()) {
                afterHeader = true
                continue
            }
            if (afterHeader) {
                afterHeader = false
                locationMapping = false
            }

            if (locationMapping) {
                val mapping = lines[i].split(" ").map { it.toLong() }
                currentlyTranslating.add(
                    Pair(
                        (mapping[0] until mapping[0] + mapping[2]), // location
                        (mapping[1] until mapping[1] + mapping[2])
                    )
                )
            } else {
                val mapping = lines[i].split(" ").map { it.toLong() }
                val from = (mapping[1] until mapping[1] + mapping[2])
                val to = (mapping[0] until mapping[0] + mapping[2])
                val iterator = currentlyTranslating.iterator()
                val toAdd = mutableListOf<Pair<LongRange, LongRange>>()
                while (iterator.hasNext()) {
                    val toTranslate = iterator.next()
                    if (toTranslate.second.intersect(to) != null) {
                        iterator.remove()
                        val intersection = toTranslate.second.intersect(to)!!
                        val fromBeginning = intersection.first() - toTranslate.second.first
                        val fromEnd = toTranslate.second.last - intersection.last()
                        if (fromBeginning > 0) {
                            toAdd.add(
                                Pair(
                                    toTranslate.first.first .. toTranslate.first.first + fromBeginning - 1,
                                    toTranslate.second.first .. toTranslate.second.first + fromBeginning - 1
                                )
                            )
                        }
                        if (fromEnd > 0) {
                            toAdd.add(
                                Pair(
                                    toTranslate.first.last - (fromEnd - 1)..toTranslate.first.last,
                                    toTranslate.second.last - (fromEnd - 1) ..toTranslate.second.last
                                )
                            )
                        }
                        val fromBeginningSource = intersection.first() - to.first
                        val fromEndSource = to.last - intersection.last()
                        toAdd.add(
                            Pair(
                                toTranslate.first.first + fromBeginning .. toTranslate.first.last - fromEnd,
                                from.first + fromBeginningSource .. from.last - fromEndSource
                            )
                        )
                    }
                }
                currentlyTranslating.addAll(toAdd)
                toAdd.clear()
            }
        }

        val seedRanges = lines[0].substring("seeds: ".length).trim().split(" ").map { it.toLong() }
        val seeds = mutableListOf<LongRange>()
        for (i in seedRanges.indices step 2) {
            seeds.add(seedRanges[i] until seedRanges[i] + seedRanges[i + 1])
        }

        currentlyTranslating.sortWith { a, b -> (a.first.first - b.first.first).toInt() }

        for (location in currentlyTranslating) {
            for (seed in seeds) {
                if (location.second.intersect(seed) != null) {
                    val intersection = location.second.intersect(seed)!!
                    val fromBeginning = intersection.first() - location.second.first
                    return location.first.first + fromBeginning
                }
            }
        }

        return -1
    }

    println(part1(readInput("aoc2023/Day05_test")))
    println(part1(readInput("aoc2023/Day05")))
//    println((0L .. 10L).intersect(11L .. 11L))
//    println((11L .. 12L).intersect(0L .. 10L))
//    println((0L .. 10L).intersect(10L .. 11L))
//    println((10L .. 11L).intersect(10L .. 10L))
//    println((0L .. 10L).intersect(5L .. 7L))
//    println((5L .. 7L).intersect(0L .. 10L))
    println(part2(readInput("aoc2023/Day05_test")))
    println(part2(readInput("aoc2023/Day05")))
}