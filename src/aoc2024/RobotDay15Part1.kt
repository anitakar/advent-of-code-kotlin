package aoc2024

class RobotDay15Part1(var position: Position, val map: MutableGrid) {
    fun up() {
        val nextPosition = map.up(position)
        if (nextPosition == null) {
            return
        }

        val value = map.get(nextPosition)
        if (value == '#') {
            return
        }

        if (value == '.') {
            position = nextPosition
            return
        }

        // value = O
        var afterEndOfBoxes = nextPosition
        while (map.get(afterEndOfBoxes!!) == 'O') {
            afterEndOfBoxes = map.up(afterEndOfBoxes)
        }
        if (map.get(afterEndOfBoxes) == '#') {
            return
        }

        // afterEndOfBoxes = .
        map.set(nextPosition, '.')
        position = nextPosition

        var next = nextPosition
        do {
            next = map.up(next!!)
            map.set(next!!, 'O')
        } while (next != afterEndOfBoxes)
    }

    fun down() {
        val nextPosition = map.down(position)
        if (nextPosition == null) {
            return
        }

        val value = map.get(nextPosition)
        if (value == '#') {
            return
        }

        if (value == '.') {
            position = nextPosition
            return
        }

        // value = O
        var afterEndOfBoxes = nextPosition
        while (map.get(afterEndOfBoxes!!) == 'O') {
            afterEndOfBoxes = map.down(afterEndOfBoxes!!)
        }
        if (map.get(afterEndOfBoxes!!) == '#') {
            return
        }

        // afterEndOfBoxes = .
        map.set(nextPosition, '.')
        position = nextPosition

        var next = nextPosition
        do {
            next = map.down(next!!)
            map.set(next!!, 'O')
        } while (next != afterEndOfBoxes)
    }

    fun left() {
        val nextPosition = map.left(position)
        if (nextPosition == null) {
            return
        }

        val value = map.get(nextPosition)
        if (value == '#') {
            return
        }

        if (value == '.') {
            position = nextPosition
            return
        }

        // value = O
        var afterEndOfBoxes = nextPosition
        while (map.get(afterEndOfBoxes!!) == 'O') {
            afterEndOfBoxes = map.left(afterEndOfBoxes!!)
        }
        if (map.get(afterEndOfBoxes!!) == '#') {
            return
        }

        // afterEndOfBoxes = .
        map.set(nextPosition, '.')
        position = nextPosition

        var next = nextPosition
        do {
            next = map.left(next!!)
            map.set(next!!, 'O')
        } while (next != afterEndOfBoxes)
    }

    fun right() {
        val nextPosition = map.right(position)
        if (nextPosition == null) {
            return
        }

        val value = map.get(nextPosition)
        if (value == '#') {
            return
        }

        if (value == '.') {
            position = nextPosition
            return
        }

        // value = O
        var afterEndOfBoxes = nextPosition
        while (map.get(afterEndOfBoxes!!) == 'O') {
            afterEndOfBoxes = map.right(afterEndOfBoxes!!)
        }
        if (map.get(afterEndOfBoxes!!) == '#') {
            return
        }

        // afterEndOfBoxes = .
        map.set(nextPosition, '.')
        position = nextPosition

        var next = nextPosition
        do {
            next = map.right(next!!)
            map.set(next!!, 'O')
        } while (next != afterEndOfBoxes)
    }
}