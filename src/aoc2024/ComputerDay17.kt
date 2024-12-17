package aoc2024

class ComputerDay17(var A: Int, var B: Int, var C: Int, var instructionPointer: Int = 0) {

    private fun comboOperand(value: Int): Int {
        return when(value) {
            0 -> 0
            1 -> 1
            2 -> 2
            3 -> 3
            4 -> A
            5 -> B
            6 -> C
            else -> throw IllegalArgumentException()
        }
    }

    private fun adv(operand: Int): Int? {
        val result = A / (1 shl comboOperand(operand))
        A = result
        instructionPointer += 2
        return null
    }

    private fun bxl(operand: Int): Int? {
        val result = B xor operand
        B = result
        instructionPointer += 2
        return null
    }

    private fun bst(operand: Int): Int? {
        val result = comboOperand(operand) % 8
        B = result
        instructionPointer += 2
        return null
    }

    private fun jnz(operand: Int): Int? {
        if (A == 0) {
            instructionPointer += 2
            return null
        }

        instructionPointer = operand
        return null
    }

    private fun bxc(operand: Int): Int? {
        val result = B xor C
        B = result
        instructionPointer += 2
        return null
    }

    private fun out(operand: Int): Int {
        val result = comboOperand(operand) % 8
        instructionPointer += 2
        return result
    }

    private fun bdv(operand: Int): Int? {
        val result = A / (1 shl comboOperand(operand))
        B = result
        instructionPointer += 2
        return null
    }

    private fun cdv(operand: Int): Int? {
        val result = A / (1 shl comboOperand(operand))
        C = result
        instructionPointer += 2
        return null
    }

    private fun op(program: List<Int>): Int? {
        if (instructionPointer + 1 >= program.size)
            return null

        val opcode = program[instructionPointer]
        val operand = program[instructionPointer + 1]

        val result = when(opcode) {
            0 -> adv(operand)
            1 -> bxl(operand)
            2 -> bst(operand)
            3 -> jnz(operand)
            4 -> bxc(operand)
            5 -> out(operand)
            6 -> bdv(operand)
            7 -> cdv(operand)
            else -> throw IllegalArgumentException()
        }

        return result
    }

    fun calculate(program: List<Int>): List<Int> {
        instructionPointer = 0
        var result = mutableListOf<Int>()
        while (instructionPointer < program.size) {
            val opResult = op(program)
            if (opResult != null) {
                result.add(opResult)
            }
        }
        return result
    }
}