package day7


import readInput

fun main() {
    val input = readInput("day_7")

    println(Game(input.map(::Hand)).calculateWinnings())
    println(Game(input.map(::HandForPart2)).calculateWinnings())

}

data class Card(val char: Char, val value: Int) : Comparable<Card> {
    companion object {
        fun createValueForPart1(char: Char): Int {
            val availableChars = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
            return availableChars.size - availableChars.indexOf(char)
        }

        fun createValueForPart2(char: Char): Int {
            val availableChars = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
            return availableChars.size - availableChars.indexOf(char)
        }
    }

    override fun compareTo(other: Card): Int {
        return value.compareTo(other.value)
    }
}

data class Hand(val tier: Int, val cards: List<Card>, val bid: Int) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        val tierCompareResult = tier.compareTo(other.tier)
        if (tierCompareResult == 0) {
            cards.forEachIndexed { index, card ->
                val cardCompareResult = card.compareTo(other.cards[index])
                if (cardCompareResult != 0) return cardCompareResult
            }
        } else return tierCompareResult

        error("Should not be equal ")
    }
}

class Game(val hands: List<Hand>) {
    fun calculateWinnings(): Int {
        val sortedBy = hands
            .sortedBy { it }
        return sortedBy
            .mapIndexed { index, hand ->
                val multiplier = index + 1
                hand.bid * multiplier
            }
            .sum()
    }
}

fun Hand(inputLine: String): Hand {

    fun calculateTier(cards: List<Card>): Int {
        val groups = cards.groupBy { it.char }.mapValues { it.value.toMutableList() }
        return determinateTier(groups)
    }

    val (cards, bid) = inputLine.split(" ")
    val cardsOnHand = cards.map { Card(it, Card.createValueForPart1(it)) }
    val tier = calculateTier(cardsOnHand)

    return Hand(tier, cardsOnHand, bid.toInt())
}

fun HandForPart2(inputLine: String): Hand {

    fun prepareGroups(cards: List<Card>): Map<Char, MutableList<Card>> {
        val initialGroups = cards.groupBy { it.char }.mapValues { it.value.toMutableList() }
        val biggestGroup = initialGroups.entries.filter { it.key != 'J' }
            .maxOfOrNull { it.value.size }
            ?: return initialGroups

        val b =
            initialGroups
                .filter { it.key != 'J' }
                .filter { it.value.size == biggestGroup }
                .entries.maxByOrNull { Card.createValueForPart2(it.key) }!!

        val groups = if (initialGroups.containsKey('J')) {
            val jokerGroup = initialGroups.getOrDefault('J', emptyList())
            b.value.addAll(jokerGroup)
            initialGroups.filter { it.key != 'J' }
        } else {
            initialGroups
        }
        return groups
    }


    fun calculateTier(cards: List<Card>): Int {
        val groups = prepareGroups(cards)
        return determinateTier(groups)
    }

    val (cards, bid) = inputLine.split(" ")
    val cardsOnHand = cards.map { Card(it, Card.createValueForPart2(it)) }
    val tier = calculateTier(cardsOnHand)

    return Hand(tier, cardsOnHand, bid.toInt())
}

fun determinateTier(groups: Map<Char, MutableList<Card>>): Int {
    return when {
        groups.size == 1 -> 7
        groups.any { it.value.size == 4 } -> 6
        groups.any { it.value.size == 3 } && groups.any { it.value.size == 2 } -> 5
        groups.any { it.value.size == 3 } -> 4
        groups.filter { it.value.size == 2 }.size == 2 -> 3
        groups.any { it.value.size == 2 } -> 2
        groups.size == 5 -> 1
        else -> error("Error for groups $groups")
    }
}
