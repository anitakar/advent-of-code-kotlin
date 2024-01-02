package aoc2023

import readInput

fun main() {
    data class Part(val x: Int, val m: Int, val a: Int, val s: Int)

    abstract class Condition {
        abstract fun evaluate(part: Part): Boolean
    }

    class TrueCondition : Condition() {
        override fun evaluate(part: Part) = true
    }

    class Conditional(val variable: Char, val operator: Char, val value: Int) : Condition() {
        override fun evaluate(part: Part): Boolean {
            if (variable == 'x' && operator == '>') {
                return part.x > value
            } else if (variable == 'x' && operator == '<') {
                return part.x < value
            } else if (variable == 'm' && operator == '>') {
                return part.m > value
            } else if (variable == 'm' && operator == '<') {
                return part.m < value
            } else if (variable == 'a' && operator == '>') {
                return part.a > value
            } else if (variable == 'a' && operator == '<') {
                return part.a < value
            } else if (variable == 's' && operator == '>') {
                return part.s > value
            } else if (variable == 's' && operator == '<') {
                return part.s < value
            }

            throw RuntimeException()
        }
    }

    class SubRule(val condition: Condition, val action: String) {
        fun matches(part: Part): Boolean {
            return condition.evaluate(part)
        }
    }

    class Rule(val name: String, val subrules: List<SubRule>) {
        fun evaluate(part: Part): String {
            for (rule in subrules) {
                if (rule.matches(part)) {
                    return rule.action
                }
            }
            throw RuntimeException()
        }
    }

    fun parsePart(line: String): Part {
        val props = line.split(',')
        val x = props[0].substring(3).toInt()
        val m = props[1].substring(2).toInt()
        val a = props[2].substring(2).toInt()
        val s = props[3].substring(2, props[3].length - 1).toInt()
        return Part(x, m, a, s)
    }

    fun parseRule(line: String): Rule {
        val ruleStart = line.indexOfFirst { it == '{' }
        val ruleName = line.substring(0, ruleStart)

        val subRules = mutableListOf<SubRule>()
        val rules = line.substring(ruleStart + 1, line.length - 1).split(',')
        for (i in rules.indices) {
            if (i == rules.size - 1) {
                subRules.add(SubRule(TrueCondition(), rules[i]))
            } else {
                val variable = rules[i][0]
                val operator = rules[i][1]
                val colonIndex = rules[i].indexOfFirst { it == ':' }
                val value = rules[i].substring(2, colonIndex).toInt()
                val condition = Conditional(variable, operator, value)
                val action = rules[i].substring(colonIndex + 1)
                subRules.add(SubRule(condition, action))
            }
        }

        return Rule(ruleName, subRules)
    }

    fun part1(lines: List<String>): Int {
        val splitIndex = lines.indexOfFirst { it.isEmpty() }

        val rules = lines.subList(0, splitIndex).map { parseRule(it) }.groupBy { it.name }
        val parts = lines.subList(splitIndex + 1, lines.size).map { parsePart(it) }

        val accepted = mutableListOf<Part>()
        for (part in parts) {
            var action = "in"
            while (action != "A" && action != "R") {
                val rule = rules[action]!![0]
                action = rule.evaluate(part)
                if (action == "A") {
                    accepted.add(part)
                }
            }
        }

        return accepted.sumOf { it.x + it.m + it.a + it.s }
    }

    println(part1(readInput("aoc2023/Day19_test")))
    println(part1(readInput("aoc2023/Day19")))
}