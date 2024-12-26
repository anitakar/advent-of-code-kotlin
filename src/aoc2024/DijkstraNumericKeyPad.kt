package aoc2024

class DijkstraNumericKeyPad : DijkstraGeneric<Char>() {

    override fun getNeighbours(key: Char): List<Char> {
        return when(key) {
            'A' -> listOf('3', '0')
            '0' -> listOf('2', 'A')
            '1' -> listOf('4', '2')
            '2' -> listOf('1', '5', '3', '0')
            '3' -> listOf('6', '2', 'A')
            '4' -> listOf('7', '5', '1')
            '5' -> listOf('4', '8', '6', '2')
            '6' -> listOf('9', '5', '3')
            '7' -> listOf('4', '8')
            '8' -> listOf('7', '5', '9')
            '9' -> listOf('8', '6')
            else -> listOf()
        }
    }

    fun getDirection(from: Char, to: Char): Char {
        return when(Pair(from, to)) {
            Pair('A', '3') -> '^'
            Pair('A', '0') -> '<'
            Pair('0', '2') -> '^'
            Pair('0', 'A') -> '>'
            Pair('1', '4') -> '^'
            Pair('1', '2') -> '>'
            Pair('2', '1') -> '<'
            Pair('2', '5') -> '^'
            Pair('2', '3') -> '>'
            Pair('2', '0') -> 'v'
            Pair('3', '6') -> '^'
            Pair('3', '2') -> '<'
            Pair('3', 'A') -> 'v'
            Pair('4', '7') -> '^'
            Pair('4', '5') -> '>'
            Pair('4', '1') -> 'v'
            Pair('5', '4') -> '<'
            Pair('5', '8') -> '^'
            Pair('5', '6') -> '>'
            Pair('5', '2') -> 'v'
            Pair('6', '9') -> '^'
            Pair('6', '5') -> '<'
            Pair('6', '3') -> 'v'
            Pair('7', '4') -> 'v'
            Pair('7', '8') -> '>'
            Pair('8', '7') -> '<'
            Pair('8', '5') -> 'v'
            Pair('8', '9') -> '>'
            Pair('9', '8') -> '<'
            Pair('9', '6') -> 'v'
            else -> throw IllegalArgumentException()
        }
    }

    override fun costUpdate(cur: Char, next: Char): Int {
        return 1
    }
}