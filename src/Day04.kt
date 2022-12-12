fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val ranges = line.split(",")
            val range1 = ranges[0].split("-")
            val range2 = ranges[1].split("-")
            if ((range1[0].toInt() <= range2[0].toInt() && range1[1].toInt() >= range2[1].toInt()) ||
                (range2[0].toInt() <= range1[0].toInt() && range2[1].toInt() >= range1[1].toInt())
            ) sum += 1
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val ranges = line.split(",")
            val range1 = ranges[0].split("-")
            val range2 = ranges[1].split("-")
            if (!(range1[1].toInt() < range2[0].toInt()) && !(range2[1].toInt() < range1[0].toInt())) sum += 1
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
