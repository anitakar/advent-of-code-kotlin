package aoc2024

import readInput


fun main() {

    class Gate(
        var name: String, var value: Boolean? = null,
        val operand: String? = null, val operator1: String? = null, val operator2: String? = null
    ) {
        fun calculate(op1: Boolean, op2: Boolean): Boolean {
            val result = if (operand == "OR") op1 || op2
            else if (operand == "AND")  op1 && op2
            else op1 xor op2
            value = result
            return result
        }
    }

    class Node(gate: Gate, val predecesors: List<Node>)

    fun parseInput(input: List<String>): Map<String, Gate> {
        val result = mutableMapOf<String, Gate>()

        var i = 0
        while (input[i].isNotBlank()) {
            val (gate, value) = input[i].split(":")
            result[gate.trim()] = Gate(gate.trim(), if (value.trim().toInt() == 0) false else true)
            i++
        }
        i++
        while(i < input.size) {
            val (operation, gate) = input[i].split("->".toRegex())
            val operands = operation.trim().split(" ")
            result[gate.trim()] = Gate(
                gate.trim(),
                operand = operands[1].trim(), operator1 = operands[0].trim(), operator2 = operands[2].trim())
            i++
        }
        return result
    }

    fun part1(input: List<String>): Long {
        val gates = parseInput(input)
        val calculatedGates = mutableListOf<Gate>()
        calculatedGates.addAll(gates.values.filter { it.value != null})
        val calculatedGatesNames = mutableSetOf<String>()
        calculatedGatesNames.addAll(calculatedGates.map { it.name })

        do {
            val toAdd = gates.filter { !calculatedGatesNames.contains(it.value.name)
                    && calculatedGatesNames.contains(it.value.operator1)
                    && calculatedGatesNames.contains(it.value.operator2)
            }
            toAdd.forEach { it.value.value = it.value.calculate(gates[it.value.operator1!!]!!.value!!, gates[it.value.operator2!!]!!.value!!) }
            calculatedGates.addAll(toAdd.values)
            calculatedGatesNames.addAll(toAdd.keys)

        } while (calculatedGates.size != gates.size)

        val bytes = calculatedGates
            .filter { it.name.startsWith("z") }
            .sortedByDescending { it.name }
            .map { if (it.value == true) "1" else "0" }
            .joinToString(separator = "")
        return bytes.toLong(2)
    }

    fun toGraphviz(parsed: Map<String, Gate>) {
        for (gate in parsed.values) {
            if (gate.operator1 != null) {
                println(gate.operator1 + " -> " + gate.name + " [label=\"${gate.operand}\"] " + ";")
                println(gate.operator2 + " -> " + gate.name + " [label=\"${gate.operand}\"] " + ";")
            }
        }
    }

    fun swap(parsed: Map<String, Gate>, name1: String, name2: String) {
        parsed[name1]!!.name = name2
        parsed[name2]!!.name = name1
    }

    fun part2(input: List<String>) {
        val graph = parseInput(readInput("aoc2024/Day24"))
        //    toGraphviz(graph)
        swap(graph, "vkq", "z11")
        swap(graph, "mmk", "z24")
        swap(graph, "pvb", "qdq")
        swap(graph, "hqh", "z38")
        //hqh,mmk,pvb,qdq,vkq,z11,z24,z38
        toGraphviz(graph)
    }


    println(part1(readInput("aoc2024/Day24_test")))
    println(part1(readInput("aoc2024/Day24")))
    println(part2(readInput("aoc2024/Day24")))
}