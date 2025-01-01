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



    println(part1Test())
    println(part1())
    println(part2Test(listOf(0,3,5,4,3,0)))
    println(part2Test(listOf(2,4,1,7,7,5,0,3,4,0,1,7,5,5,3,0)))
}