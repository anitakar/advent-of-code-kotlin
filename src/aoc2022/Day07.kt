package aoc2022

import println
import readInput

fun main() {

    class DirNode(val name: String, val parent: DirNode?) {
        val subdirs = mutableMapOf<String, DirNode>()
        var immediateFilesSum: Int = 0
        var subdirsSum: Int? = null
    }

    fun calculateSum(node: DirNode): Int {
        if (node.subdirs.isEmpty()) {
            node.subdirsSum = node.immediateFilesSum;
            return node.subdirsSum!!;
        }

        var total = 0
        for (subdir in node.subdirs.values) {
            if (subdir.subdirsSum == null) {
                total += calculateSum(subdir)
            }
        }
        node.subdirsSum = total + node.immediateFilesSum

        return node.subdirsSum!!
    }

    fun readTree(input: List<String>): DirNode {
        val dirRegex = """dir (\w+)""".toRegex()
        val fileRegex = """(\d+) (.+)""".toRegex()
        val cdRegex = """\$ cd (\w+)""".toRegex()

        val root = DirNode("/", null)
        var curNode = root
        for (line in input) {
            if (line == "$ cd /") continue

            if (line == "$ ls") continue

            if (line == "$ cd ..") {
                curNode = curNode.parent!!
            }

            if (line.matches(cdRegex)) {
                val match = cdRegex.find(line)
                val (dirName) = match!!.destructured
                curNode = curNode.subdirs[dirName]!!
            }

            if (line.matches(dirRegex)) {
                val match = dirRegex.find(line)
                val (dirName) = match!!.destructured
                curNode.subdirs.put(dirName, DirNode(dirName, curNode))
            }

            if (line.matches(fileRegex)) {
                val match = fileRegex.find(line)
                val (size, name) = match!!.destructured
                curNode.immediateFilesSum += size.toInt()
            }

        }
        return root
    }

    fun part1(input: List<String>): Int {
        val root = readTree(input)
        calculateSum(root)
        var sum = 0
        val toCheck = mutableListOf<DirNode>(root)
        while (!toCheck.isEmpty()) {
            val next = toCheck.removeFirst()

            if (next.subdirsSum!! <= 100000) {
                sum += next.subdirsSum!!
            }

            toCheck.addAll(next.subdirs.values)
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val root = readTree(input)
        calculateSum(root)
        val total = 70000000
        val minUnused = 30000000
        val unused = total - root.subdirsSum!!
        val minSize = minUnused - unused

        var cur = root.subdirsSum!!
        val toCheck = mutableListOf<DirNode>(root)
        while (!toCheck.isEmpty()) {
            val next = toCheck.removeFirst()

            if (next.subdirsSum!! >= minSize && next.subdirsSum!! < cur) {
                cur = next.subdirsSum!!
            }

            toCheck.addAll(next.subdirs.values)
        }

        return cur
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
