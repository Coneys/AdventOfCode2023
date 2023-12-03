package day3

import readInput

fun main() {
    val input = readInput("day_3")

    println(part1(input))
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val regex = "(?<=\\D?)\\d+(?=\\D?)".toRegex()

    return input.mapIndexed { index, line ->
        regex.findAll(line).sumOf {
            val diagonalRange = IntRange(it.range.first - 1, it.range.last + 1)

            val hasSymbolAbove = input.getOrNull(index - 1)?.safeSubstring(diagonalRange).isASymbol()
            val hasSymbolBelow = input.getOrNull(index + 1)?.safeSubstring(diagonalRange).isASymbol()
            val hasSymbolBehind = line.getOrNull(it.range.first - 1).isASymbol()
            val hasSymbolAfter = line.getOrNull(it.range.last + 1).isASymbol()

            val isEnginePart = hasSymbolAbove || hasSymbolBelow || hasSymbolBehind || hasSymbolAfter

            if (isEnginePart) {
                it.value.toInt()
            } else {
                0
            }
        }
    }.sum()
}

private fun part2(input: List<String>): Int {
    return input.flatMapIndexed { index, line ->
        line.mapIndexed { charIndex, char ->
            if (char == '*') {
                val behind = line.numberBehind(charIndex)
                val after = line.numberAfter(charIndex + 1)

                val above = input.getOrNull(index - 1).extractNumbersAdjacentTo(charIndex)
                val below = input.getOrNull(index + 1).extractNumbersAdjacentTo(charIndex)

                val adjacentNumbers = listOfNotNull(behind, after) + above + below

                if (adjacentNumbers.size == 2) {
                    adjacentNumbers[0] * adjacentNumbers[1]
                } else {
                    0
                }
            } else {
                0
            }
        }
    }.sum()
}

private fun String?.extractNumbersAdjacentTo(index: Int): List<Int> {
    this ?: return emptyList()

    val middleMarkIsDigit = get(index).isDigit()

    return if (middleMarkIsDigit) {
        val numberStart = substring(0, index).takeLastWhile { it.isDigit() }
        val numberEnd = substring(index, length).takeWhile { it.isDigit() }
        listOf((numberStart + numberEnd).toInt())
    } else {
        val valueBehindIsDigit = get(index - 1).isDigit()
        val valueAfterIsDigit = get(index + 1).isDigit()

        buildList {
            if (valueBehindIsDigit) {
                add(substring(0, index).takeLastWhile { it.isDigit() }.toInt())
            }

            if (valueAfterIsDigit) {
                add(substring(index + 1, length).takeWhile { it.isDigit() }.toInt())
            }
        }
    }
}

private fun String.numberBehind(charIndex: Int): Int? {
    return substring(0, charIndex).takeLastWhile { it.isDigit() }.toIntOrNull()
}

private fun String.numberAfter(charIndex: Int): Int? {
    return substring(charIndex).takeWhile { it.isDigit() }.toIntOrNull()
}

fun String.safeSubstring(intRange: IntRange): String {
    return substring(intRange.first.coerceAtLeast(0), (intRange.last + 1).coerceAtMost(length))
}

fun String?.isASymbol(): Boolean {
    this ?: return false
    return any { it.isASymbol() }
}

fun Char?.isASymbol(): Boolean {
    this ?: return false
    return isLetterOrDigit().not() && this != '.'
}
