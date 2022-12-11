fun main() {
    fun priority(c: Char): Int {
        if (c in 'A'..'Z') {
            return c - 'A' + 27
        } else if (c in 'a'..'z') {
            return c - 'a' + 1
        }
        return 0
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val first = line.substring(0, line.length / 2 + 1).toCharArray().toSet()
            val second = line.substring(line.length / 2).toCharArray().toSet()
            val elem = first.first { second.contains(it) }
            val priority = priority(elem)
            sum += priority
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (group in 0..input.size / 3 - 1) {
            val elf1 = input[3 * group].toCharArray().toSet()
            val elf2 = input[3 * group + 1].toCharArray().toSet()
            val elf3 = input[3 * group + 2].toCharArray().toSet()

            val elf1and2 = elf1.filter { it in elf2 }
            val elf1and3 = elf1.filter { it in elf3 }
            val allElves = elf1and2.first { it in elf1and3 }
            sum += priority(allElves)
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
