package aoc2024

class ComputerDay17(var A: Int, var B: Int, var C: Int) {
    var instructionPointer = 0

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

    private fun adv(operand: Int): Int {
        val result = A / (1 shl comboOperand(operand))
        A = result
        return result
    }

    private fun bxl(operand: Int): Int {
        val result = B xor operand
        B = result
        return result
    }

    private fun bst(operand: Int): Int {
        val result = comboOperand(operand) % 8
        B = result
        return result
    }

    private fun jnz(operand: Int): Int {
        if (A == 0) return 0

        instructionPointer = operand
        return 0
    }

    private fun bxc(operand: Int): Int {
        val result = B xor C
        B = result
        return result
    }

    private fun out(operand: Int): Int {
        val result = comboOperand(operand) % 8
        return result
    }

    private fun bdv(operand: Int): Int {
        val result = A / (1 shl comboOperand(operand))
        B = result
        return result
    }

    private fun cdv(operand: Int): Int {
        val result = A / (1 shl comboOperand(operand))
        C = result
        return result
    }

    fun calculate(program: List<Int>): List<Int> {
        var result = mutableListOf<Int>()
        return result
    }
}