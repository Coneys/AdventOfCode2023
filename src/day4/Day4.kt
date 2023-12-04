package day4

import readInput
import kotlin.math.pow

fun main() {
    val cards = readInput("day_4").map(Card::from)

    println(part1(cards))
    println(part2(cards))
}

fun part1(cards: List<Card>) = cards.sumOf { it.points() }

fun part2(cards: List<Card>) =
    cards
        .onEach { it.checkWinnings(cards) }
        .sumOf { it.wonCopies }

data class Card(val cardNumber: Int, val winning: List<Int>, val our: List<Int>) {
    var wonCopies: Int = 1
        private set
    private val matches get() = our.count { it in winning }

    fun points(): Int {
        val matches = our.count { it in winning }
        return 2.0.pow(matches.toDouble() - 1).toInt()
    }

    fun checkWinnings(allCards: List<Card>) {
        allCards.subList(cardNumber, cardNumber + matches).onEach {
            it.wonCopies += wonCopies
        }
    }

    companion object {
        fun from(line: String): Card {
            fun String.asNumbers() =
                split(" ")
                    .map { it.trim() }
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }

            val cardNumber = line.substringAfter("Card ").substringBefore(":").trim().toInt()

            val (winning, our) = line.substringAfter(":").split("|")

            val winningNumbers = winning.asNumbers()
            val ourNumbers = our.asNumbers()

            return Card(cardNumber, winningNumbers, ourNumbers)
        }
    }
}
