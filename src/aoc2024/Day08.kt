package aoc2024

import readInput


fun main() {
    fun part1(input: List<String>): Int {
        val map = Grid(input)
        val nodesLocations = mutableMapOf<Char, MutableList<Position>>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == '.')
                    continue
                if (!nodesLocations.contains(input[i][j])) {
                    nodesLocations[input[i][j]] = mutableListOf()
                }
                nodesLocations[input[i][j]]!!.add(Position(i, j))
            }
        }

        val uniqueAntinodes = mutableSetOf<Position>()
        for (node in nodesLocations) {
            for (i in node.value.indices) {
                for (j in (i + 1) until node.value.size) {
                    val first = node.value[i]
                    val second = node.value[j]

                    val firstAntinode = Position(
                        first.x - (second.x - first.x),
                        first.y - (second.y - first.y)
                    )
                    if (map.isValid(Position(firstAntinode.x, firstAntinode.y)))
                        uniqueAntinodes.add(firstAntinode)

                    val secondAntinode = Position(
                        second.x + (second.x - first.x),
                        second.y + (second.y - first.y)
                    )
                    if (map.isValid(Position(secondAntinode.x, secondAntinode.y)))
                        uniqueAntinodes.add(secondAntinode)
                }
            }
        }
        return uniqueAntinodes.size
    }

    fun part2(input: List<String>): Int {
        val map = Grid(input)
        val nodesLocations = mutableMapOf<Char, MutableList<Position>>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == '.')
                    continue
                if (!nodesLocations.contains(input[i][j])) {
                    nodesLocations[input[i][j]] = mutableListOf()
                }
                nodesLocations[input[i][j]]!!.add(Position(i, j))
            }
        }

        val uniqueAntinodes = mutableSetOf<Position>()
        uniqueAntinodes.addAll(nodesLocations.filter { it.value.size > 1 }.values.flatten())
        for (node in nodesLocations) {
            for (i in node.value.indices) {
                for (j in (i + 1) until node.value.size) {
                    val first = node.value[i]
                    val second = node.value[j]

                    var firstAntinode = Position(
                        first.x - (second.x - first.x),
                        first.y - (second.y - first.y)
                    )
                    while (map.isValid(Position(firstAntinode.x, firstAntinode.y))) {
                        uniqueAntinodes.add(firstAntinode)
                        firstAntinode = Position(
                            firstAntinode.x - (second.x - first.x),
                            firstAntinode.y - (second.y - first.y)
                        )
                    }

                    var secondAntinode = Position(
                        second.x + (second.x - first.x),
                        second.y + (second.y - first.y)
                    )
                    while (map.isValid(Position(secondAntinode.x, secondAntinode.y))) {
                        uniqueAntinodes.add(secondAntinode)
                        secondAntinode = Position(
                            secondAntinode.x + (second.x - first.x),
                            secondAntinode.y + (second.y - first.y)
                        )
                    }
                }
            }
        }
        return uniqueAntinodes.size
    }

    println(part1(readInput("aoc2024/Day08_test")))
    println(part1(readInput("aoc2024/Day08")))
    println(part2(readInput("aoc2024/Day08_test")))
    println(part2(readInput("aoc2024/Day08")))
}