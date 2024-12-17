package aoc2024

class ComputerDay17(var A: Long, var B: Long, var C: Long, var instructionPointer: Int = 0) {

    private fun comboOperand(value: Long): Long {
        return when(value) {
            0L -> 0
            1L -> 1
            2L -> 2
            3L -> 3
            4L -> A
            5L -> B
            6L -> C
            else -> throw IllegalArgumentException()
        }
    }

    private fun adv(operand: Long): Long? {
        val result = A / (1L shl comboOperand(operand).toInt())
        A = result
        instructionPointer += 2
        return null
    }

    private fun bxl(operand: Long): Long? {
        val result = B xor operand
        B = result
        instructionPointer += 2
        return null
    }

    private fun bst(operand: Long): Long? {
        val result = comboOperand(operand) % 8
        B = result
        instructionPointer += 2
        return null
    }

    private fun jnz(operand: Long): Long? {
        if (A == 0L) {
            instructionPointer += 2
            return null
        }

        instructionPointer = operand.toInt()
        return null
    }

    private fun bxc(operand: Long): Long? {
        val result = B xor C
        B = result
        instructionPointer += 2
        return null
    }

    private fun out(operand: Long): Long {
        val result = comboOperand(operand) % 8
        instructionPointer += 2
        return result
    }

    private fun bdv(operand: Long): Long? {
        val result = A / (1L shl comboOperand(operand).toInt())
        B = result
        instructionPointer += 2
        return null
    }

    private fun cdv(operand: Long): Long? {
        val result = A / (1L shl comboOperand(operand).toInt())
        C = result
        instructionPointer += 2
        return null
    }

    fun nextOp(program: List<Long>): Long? {
        if (instructionPointer + 1 >= program.size)
            return null

        val opcode = program[instructionPointer]
        val operand = program[instructionPointer + 1]

        val result = when(opcode) {
            0L -> adv(operand)
            1L -> bxl(operand)
            2L -> bst(operand)
            3L -> jnz(operand)
            4L -> bxc(operand)
            5L -> out(operand)
            6L -> bdv(operand)
            7L -> cdv(operand)
            else -> throw IllegalArgumentException()
        }

        return result
    }

    fun calculate(program: List<Long>): List<Long> {
        instructionPointer = 0
        var result = mutableListOf<Long>()
        while (instructionPointer < program.size) {
            val opResult = nextOp(program)
            if (opResult != null) {
                result.add(opResult)
            }
        }
        return result
    }
}