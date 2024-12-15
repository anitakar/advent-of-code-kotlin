package aoc2024

class RobotDay15Part2(var position: Position, val map: MutableGrid) {
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

        // value = [ or ]
        val pointsToMove = mutableListOf<Position>()
        pointsToMove.add(nextPosition)
        pointsToMove.add(otherBoxPart(nextPosition))

        val nextPoints = mutableSetOf<Position>()
        nextPoints.addAll(pointsToMove)
        while (nextPoints.isNotEmpty()) {
            val point = nextPoints.iterator().next()
            nextPoints.remove(point)
            val next = map.up(point)
            val value = map.get(next!!)
            if (value == '#') {
                return
            } else if (value == '.') {
                continue
            } else if (nextPoints.contains(next)) {
                continue
            } else {
                nextPoints.add(next)
                nextPoints.add(otherBoxPart(next))
                pointsToMove.add(next)
                pointsToMove.add(otherBoxPart(next))
            }
        }

        pointsToMove.sortBy { it.x }
        for (toMove in pointsToMove) {
            map.set(map.up(toMove)!!, map.get(toMove))
            map.set(toMove, '.')
        }
        position = nextPosition
    }

    private fun otherBoxPart(box: Position): Position {
        if (map.get(box) == '[') {
            return Position(box.x, box.y + 1)
        } else if (map.get(box) == ']') {
            return Position(box.x, box.y - 1)
        }
        return Position(0,0)
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

        // value = [ or ]
        val pointsToMove = mutableListOf<Position>()
        pointsToMove.add(nextPosition)
        pointsToMove.add(otherBoxPart(nextPosition))

        val nextPoints = mutableSetOf<Position>()
        nextPoints.addAll(pointsToMove)
        while (nextPoints.isNotEmpty()) {
            val point = nextPoints.iterator().next()
            nextPoints.remove(point)
            val next = map.down(point)
            val value = map.get(next!!)
            if (value == '#') {
                return
            } else if (value == '.') {
                continue
            } else if (nextPoints.contains(next)) {
                continue
            } else {
                nextPoints.add(next)
                nextPoints.add(otherBoxPart(next))
                pointsToMove.add(next)
                pointsToMove.add(otherBoxPart(next))
            }
        }

        pointsToMove.sortBy { -it.x }
        for (toMove in pointsToMove) {
            map.set(map.down(toMove)!!, map.get(toMove))
            map.set(toMove, '.')
        }
        position = nextPosition
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

        // value = [ or ]
        val pointsToMove = mutableListOf<Position>()
        pointsToMove.add(nextPosition)
        pointsToMove.add(otherBoxPart(nextPosition))

        val nextPoints = mutableSetOf<Position>()
        nextPoints.addAll(pointsToMove)
        while (nextPoints.isNotEmpty()) {
            val point = nextPoints.iterator().next()
            nextPoints.remove(point)
            val next = map.left(point)
            val value = map.get(next!!)
            if (value == '#') {
                return
            } else if (value == '.') {
                continue
            } else if (nextPoints.contains(next)) {
                continue
            } else {
                nextPoints.add(next)
                nextPoints.add(otherBoxPart(next))
                pointsToMove.add(next)
                pointsToMove.add(otherBoxPart(next))
            }
        }

        pointsToMove.sortBy { it.y }
        for (toMove in pointsToMove) {
            map.set(map.left(toMove)!!, map.get(toMove))
            map.set(toMove, '.')
        }
        position = nextPosition
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

        // value = [ or ]
        val pointsToMove = mutableListOf<Position>()
        pointsToMove.add(nextPosition)
        pointsToMove.add(otherBoxPart(nextPosition))

        val nextPoints = mutableSetOf<Position>()
        nextPoints.addAll(pointsToMove)
        while (nextPoints.isNotEmpty()) {
            val point = nextPoints.iterator().next()
            nextPoints.remove(point)
            val next = map.right(point)
            val value = map.get(next!!)
            if (value == '#') {
                return
            } else if (value == '.') {
                continue
            } else if (nextPoints.contains(next)) {
                continue
            } else {
                nextPoints.add(next)
                nextPoints.add(otherBoxPart(next))
                pointsToMove.add(next)
                pointsToMove.add(otherBoxPart(next))
            }
        }

        pointsToMove.sortBy { -it.y }
        for (toMove in pointsToMove) {
            map.set(map.right(toMove)!!, map.get(toMove))
            map.set(toMove, '.')
        }
        position = nextPosition
    }
}