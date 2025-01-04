package aoc2024


fun main() {

    fun part1Test(): List<Long> {
        val computer = ComputerDay17(729, 0, 0, 0)
        val output = computer.calculate(listOf(0,1,5,4,3,0))
        return output
    }

    fun part1(): List<Long> {
        val computer = ComputerDay17(62769524, 0, 0, 0)
        val output = computer.calculate(listOf(2,4,1,7,7,5,0,3,4,0,1,7,5,5,3,0))
        return output
    }

    fun part2Test(program: List<Long>): Long {
        for (A in 0 .. Long.MAX_VALUE) {
            //println(A)
            val computer = ComputerDay17(A, 0, 0, 0)
            var outputIndex = 0
            var iterationsCount = 0
            while (iterationsCount < 100000 && outputIndex < program.size) {
                iterationsCount++

                val cur = computer.nextOp(program)

                if (cur != null) {
                    if (program[outputIndex] == cur) {
                        outputIndex++
                    } else {
                        break
                    }
                }
            }

            if (outputIndex == program.size) {
                return A
            }
        }
        return 0
    }

    fun next3Bits(program: List<Long>, expected: Long, candidate: Long, numIters: Int = 7): List<Long> {
        val candidates = mutableListOf<Long>()
        for (i in 0 .. numIters) {
            val A = candidate + i
            val computer = ComputerDay17(A, 0, 0, 0)
            var result: Long? = null
            while (result == null) {
                result = computer.nextOp(program)
                if (result == expected) candidates.add(A)
            }
        }
        return candidates
    }

    fun part2(program: List<Long>): Long {
        val candidates = mutableListOf(1L)
        for (i in program.size - 1 downTo 0) {
            val elem = program[i]
            val nextCandidates = mutableListOf<Long>()
            for (A in candidates) {
                val nextA = next3Bits(program, elem, A)
                nextCandidates.addAll(nextA)
            }
            candidates.clear()
            candidates.addAll(nextCandidates.map { it shl 3 })
        }
        val finalCandidates = candidates.map { it shr 3 }
        val checkedCandidates = mutableListOf<Long>()
        for (candidate in finalCandidates) {
            val computer = ComputerDay17(candidate, 0, 0, 0)
            val result = computer.calculate(program)
            if (result == program) {
                checkedCandidates.add(candidate)
            }
        }
        return checkedCandidates.min()
    }


    println(part1Test())
    println(part1())
    println(part2Test(listOf(0,3,5,4,3,0)))
    println(part2(listOf(2,4,1,7,7,5,0,3,4,0,1,7,5,5,3,0)))
}