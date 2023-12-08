package aoc2023

import readInput

fun main() {

    fun isFiveOfAKind(hand: String): Boolean {
        val grouped = hand.groupBy { it }.mapValues { it.value.size }
        return grouped.size == 1
    }

    fun isFiveOfAKindWithJoker(hand: String): Boolean {
        if (isFiveOfAKind(hand)) return true
        val grouped = hand.groupBy { it }.mapValues { it.value.size }
        return grouped.contains('J') && grouped.size == 2
    }

    fun isFourOfAKind(hand: String): Boolean {
        val grouped = hand.groupBy { it }.mapValues { it.value.size }
        return grouped.size == 2 && (grouped[hand[0]] == 4 || grouped[hand[0]] == 1)
    }

    fun isFourOfAKindWithJoker(hand: String): Boolean {
        if (isFourOfAKind(hand)) return true
        val grouped = hand.groupBy { it }.mapValues { it.value.size }
        if (grouped.contains('J')) {
            val jokers = grouped['J']!!
            for ((char, count) in grouped) {
                if (char != 'J' && count + jokers >= 4) {
                    return true
                }
            }
        }
        return false
    }

    fun isFullHouse(hand: String): Boolean {
        val grouped = hand.groupBy { it }.mapValues { it.value.size }
        return grouped.size == 2 && (grouped[hand[0]] == 2 || grouped[hand[0]] == 3)
    }

    fun isFullHouseWithJoker(hand: String): Boolean {
        if (isFullHouse(hand)) return true
        val grouped = hand.groupBy { it }.mapValues { it.value.size }
        if (grouped.contains('J')) {
            val jokers = grouped['J']!!
            if (jokers >= 3) return true
            if (jokers == 2) return grouped.size - 1 < 3
            if (jokers == 1) {
                if (grouped.size - 1 == 2) return true
            }
        }
        return false
    }

    fun isThreeOfAKind(hand: String): Boolean {
        val grouped = hand.groupBy { it }.mapValues { it.value.size }
        return grouped.size == 3 && (grouped[hand[0]] == 3 || grouped[hand[1]] == 3 || grouped[hand[2]] == 3)
    }

    fun isThreeOfAKindWithJoker(hand: String): Boolean {
        if (isThreeOfAKind(hand)) return true
        val grouped = hand.groupBy { it }.mapValues { it.value.size }
        if (grouped.contains('J')) {
            val jokers = grouped['J']!!
            if (jokers >= 2) return true
            if (grouped.values.contains(2)) return true
        }
        return false
    }

    fun isTwoPair(hand: String): Boolean {
        val grouped = hand.groupBy { it }.mapValues { it.value.size }
        return grouped.size == 3 && (grouped[hand[0]] != 3 || grouped[hand[1]] != 3 || grouped[hand[2]] != 3)
    }

    fun isTwoPairWithJoker(hand: String): Boolean {
        if (isTwoPair(hand)) return true
        val grouped = hand.groupBy { it }.mapValues { it.value.size }
        if (grouped.contains('J')) {
            val jokers = grouped['J']!!
            if (jokers > 1) return true
            if (grouped.values.contains(2)) return true
        }
        return false
    }

    fun isOnePair(hand: String): Boolean {
        val grouped = hand.groupBy { it }.mapValues { it.value.size }
        return grouped.size == 4
    }

    fun isOnePairWithJoker(hand: String): Boolean {
        if (isOnePair(hand)) return true
        return hand.contains('J')
    }

    fun rank(hand: String): Int {
        return if (isFiveOfAKind(hand)) 7
        else if (isFourOfAKind(hand)) 6
        else if (isFullHouse(hand)) 5
        else if (isThreeOfAKind(hand)) 4
        else if (isTwoPair(hand)) 3
        else if (isOnePair(hand)) 2
        else 1
    }

    fun rankWithJoker(hand: String): Int {
        return if (isFiveOfAKindWithJoker(hand)) 7
        else if (isFourOfAKindWithJoker(hand)) 6
        else if (isFullHouseWithJoker(hand)) 5
        else if (isThreeOfAKindWithJoker(hand)) 4
        else if (isTwoPairWithJoker(hand)) 3
        else if (isOnePairWithJoker(hand)) 2
        else 1
    }

    val cardStrength = mapOf(
        'A' to 14, 'K' to 13, 'Q' to 12, 'J' to 11, 'T' to 10, '9' to 9, '8' to 8, '7' to 7, '6' to 6, '5' to 5, '4' to 4, '3' to 3, '2' to 2
    )

    fun compareSameType(hand1: String, hand2: String): Int {
        return if (hand1[0] != hand2[0]) (cardStrength[hand1[0]]!! - cardStrength[hand2[0]]!!)
        else if (hand1[1] != hand2[1]) (cardStrength[hand1[1]]!! - cardStrength[hand2[1]]!!)
        else if (hand1[2] != hand2[2]) (cardStrength[hand1[2]]!! - cardStrength[hand2[2]]!!)
        else if (hand1[3] != hand2[3]) (cardStrength[hand1[3]]!! - cardStrength[hand2[3]]!!)
        else if (hand1[4] != hand2[4]) (cardStrength[hand1[4]]!! - cardStrength[hand2[4]]!!)
        else 0
    }

    val cardStrengthWithJoker = mapOf(
        'A' to 14, 'K' to 13, 'Q' to 12, 'J' to 1, 'T' to 10, '9' to 9, '8' to 8, '7' to 7, '6' to 6, '5' to 5, '4' to 4, '3' to 3, '2' to 2
    )

    fun compareSameTypeWithJoker(hand1: String, hand2: String): Int {
        return if (hand1[0] != hand2[0]) (cardStrengthWithJoker[hand1[0]]!! - cardStrengthWithJoker[hand2[0]]!!)
        else if (hand1[1] != hand2[1]) (cardStrengthWithJoker[hand1[1]]!! - cardStrengthWithJoker[hand2[1]]!!)
        else if (hand1[2] != hand2[2]) (cardStrengthWithJoker[hand1[2]]!! - cardStrengthWithJoker[hand2[2]]!!)
        else if (hand1[3] != hand2[3]) (cardStrengthWithJoker[hand1[3]]!! - cardStrengthWithJoker[hand2[3]]!!)
        else if (hand1[4] != hand2[4]) (cardStrengthWithJoker[hand1[4]]!! - cardStrengthWithJoker[hand2[4]]!!)
        else 0
    }

    fun compare(hand1: String, hand2: String): Int {
        val rank1 = rank(hand1)
        val rank2 = rank(hand2)
        if (rank1 != rank2) return rank1 - rank2

        return compareSameType(hand1, hand2)
    }

    fun compareWithJoker(hand1: String, hand2: String): Int {
        val rank1 = rankWithJoker(hand1)
        val rank2 = rankWithJoker(hand2)
        if (rank1 != rank2) return rank1 - rank2

        return compareSameTypeWithJoker(hand1, hand2)
    }

    fun part1(hands: List<String>): Long {
        val sorted = hands.sortedWith { hand1, hand2 -> compare(hand1.substring(0, 5), hand2.substring(0, 5)) }

        var total = 0L
        for ((index, hand) in sorted.withIndex()) {
            total += (index + 1) * hand.substring(6).trim().toInt()
        }
        return total
    }

    fun part2(hands: List<String>): Long {
        val sorted = hands.sortedWith { hand1, hand2 -> compareWithJoker(hand1.substring(0, 5), hand2.substring(0, 5)) }

        var total = 0L
        for ((index, hand) in sorted.withIndex()) {
            total += (index + 1) * hand.substring(6).trim().toInt()
        }
        return total
    }

    println(part1(readInput("aoc2023/Day07_test")))
    println(part1(readInput("aoc2023/Day07")))
    println(part2(readInput("aoc2023/Day07_test")))
    println(part2(readInput("aoc2023/Day07")))
}