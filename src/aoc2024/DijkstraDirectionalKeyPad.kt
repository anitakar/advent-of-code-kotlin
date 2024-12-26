package aoc2024

class DijkstraDirectionalKeyPad : DijkstraGeneric<Char>() {

    override fun getNeighbours(key: Char): List<Char> {
        return when(key) {
            '<' -> listOf('v')
            'v' -> listOf('<', '>', '^')
            '>' -> listOf('v', 'A')
            '^' -> listOf('A', 'v')
            'A' -> listOf('^', '>')
            else -> listOf()
        }
    }

    fun getDirection(from: Char, to: Char): Char {
        return when (Pair(from, to)) {
            Pair('<', 'v') -> '>'
            Pair('v', '<') -> '<'
            Pair('v', '>') -> '>'
            Pair('v', '^') -> '^'
            Pair('>', 'v') -> '<'
            Pair('>', 'A') -> '^'
            Pair('^', 'A') -> '>'
            Pair('^', 'v') -> 'v'
            Pair('A', '^') -> '<'
            Pair('A', '>') -> 'v'
            else -> throw IllegalArgumentException()
        }
    }

    override fun costUpdate(cur: Char, next: Char): Int {
        return 1
    }
}