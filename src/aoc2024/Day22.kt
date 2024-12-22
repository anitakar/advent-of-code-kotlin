package aoc2024

import readInput


fun main() {

    fun nextSecret(secret: Long): Long {
        var secret = (secret xor (secret * 64)) % 16777216
        secret = (secret xor (secret / 32)) % 16777216
        secret = (secret xor (secret * 2048)) % 16777216
        return secret
    }

    fun part1(input: List<String>): Long {
        var result = 0L
        for (line in input) {
            var secret = line.toLong()
            for (i in 0 until 2000) {
                secret = nextSecret(secret)
            }
            result += secret
        }
        return result
    }

    fun part2(initialSecrets: List<String>): Long {
        val buyerToBananas: MutableList<MutableMap<String, Int>> = mutableListOf()
        for (buyer in initialSecrets.indices) {
            buyerToBananas.add(mutableMapOf())
            var last5 = mutableListOf<Int>()
            var secret = initialSecrets[buyer].toLong()
            last5.add((secret % 10).toInt())
            for (j in 0 until 2000) {
                secret = nextSecret(secret)
                val bananas = (secret % 10).toInt()
                last5.add(bananas)
                if (last5.size >= 5) {
                    val digit4 = last5[4] - last5[3]
                    val digit3 = last5[3] - last5[2]
                    val digit2 = last5[2] - last5[1]
                    val digit1 = last5[1] - last5[0]
                    val sequence = "$digit1,$digit2,$digit3,$digit4"
                    if (!buyerToBananas[buyer].contains(sequence)) {
                        buyerToBananas[buyer][sequence] = bananas
                    }
                    last5.removeAt(0)
                }
            }
        }
        val distinctSequences: Set<String> = buyerToBananas.map { it.keys }.flatten().toSet()
        var maxBananas = 0L
        for (sequence in distinctSequences) {
            var bananas = 0L
            for (buyer in initialSecrets.indices) {
                bananas += (buyerToBananas[buyer][sequence] ?: 0)
            }
            if (bananas > maxBananas) {
                maxBananas = bananas
            }
        }
        return maxBananas
    }

//    println(nextSecret(123))
//    println(nextSecret(nextSecret(123)))

//    println(part1(readInput("aoc2024/Day22_test")))
//    println(part1(readInput("aoc2024/Day22")))
    println(part2(readInput("aoc2024/Day22_test2")))
    println(part2(readInput("aoc2024/Day22")))
}