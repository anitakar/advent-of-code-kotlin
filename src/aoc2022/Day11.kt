package aoc2022

import println
import readInput
import java.util.function.UnaryOperator

fun main() {

    class Monkey(
        val items: MutableList<Long>, val op: UnaryOperator<Long>, val test: Int,
        val trueConditionMonkey: Int, val falseConditionMonkey: Int,
        var itemsInspected: Long = 0, var modulo: Long = 1
    ) {
        fun play(monkeys: MutableList<Monkey>) {
            for (item in items) {
                var newItem = op.apply(item) / 3
                if (newItem % test == 0L) {
                    monkeys[trueConditionMonkey].items.add(newItem)
                } else {
                    monkeys[falseConditionMonkey].items.add(newItem)
                }
                itemsInspected += 1
            }
            items.clear()
        }

        fun play2(monkeys: MutableList<Monkey>) {
            for (item in items) {
                var newItem = op.apply(item % modulo)
                if (newItem % test == 0L) {
                    monkeys[trueConditionMonkey].items.add(newItem)
                } else {
                    monkeys[falseConditionMonkey].items.add(newItem)
                }
                itemsInspected += 1
            }
            items.clear()
        }
    }

    fun parseMonkeys(input: List<String>): MutableList<Monkey> {
        val monkeys: MutableList<Monkey> = mutableListOf()
        var items: MutableList<Long> = mutableListOf()
        var op = { a: Long -> a + 0 }
        var test: Int = 2
        var trueConditionMonkey: Int = 0
        var falseConditionMonkey: Int = 0
        for (line in input) {
            if (line.startsWith("Monkey"))
                continue;

            if (line.startsWith("  Starting items: ")) {
                items = line
                    .substring("  Starting items: ".length)
                    .split(", ")
                    .map { e -> e.toLong() }
                    .toMutableList()
            }

            if (line.startsWith("  Operation: new = old * ")) {
                val operand = line.substring("  Operation: new = old * ".length)
                if (operand == "old") {
                    op = { a: Long -> a * a }
                } else {
                    op = { a: Long -> a * operand.toLong() }
                }
            }

            if (line.startsWith("  Operation: new = old + ")) {
                val operand = line.substring("  Operation: new = old + ".length).toInt()
                op = { a: Long -> a + operand }
            }

            if (line.startsWith("  Test: divisible by ")) {
                test = line.substring("  Test: divisible by ".length).toInt()
            }

            if (line.startsWith("    If true: throw to monkey ")) {
                trueConditionMonkey = line.substring("    If true: throw to monkey ".length).toInt()
            }

            if (line.startsWith("    If false: throw to monkey ")) {
                falseConditionMonkey = line.substring("    If false: throw to monkey ".length).toInt()
            }

            if (line.isBlank()) {
                monkeys.add(Monkey(items, op, test, trueConditionMonkey, falseConditionMonkey))
            }

        }
        monkeys.add(Monkey(items, op, test, trueConditionMonkey, falseConditionMonkey))
        val allModulos = monkeys.map { m -> m.test }.reduce { acc, v -> acc * v }.toLong()
        monkeys.forEach { m -> m.modulo = allModulos }
        return monkeys
    }

    fun part1(input: List<String>): Long {
        val monkeys = parseMonkeys(input)

        for (round in 0..19) {
            for (monkey in monkeys) {
                monkey.play(monkeys)
            }
        }

        monkeys.sortBy { m -> m.itemsInspected }

        return monkeys[monkeys.size - 1].itemsInspected * monkeys[monkeys.size - 2].itemsInspected
    }

    fun part2(input: List<String>): Long {
        val monkeys = parseMonkeys(input)

        for (round in 0 until 10000) {
            for (monkey in monkeys) {
                monkey.play2(monkeys)
            }
        }

        monkeys.sortBy { m -> m.itemsInspected }

        return monkeys[monkeys.size - 1].itemsInspected * monkeys[monkeys.size - 2].itemsInspected
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
