package day1

import readInput

fun main() {
    val input = readInput("day_1")

    val sum =
        input.sumOf {
            var firstDigitChar: Char? = null
            var lastDigitChar: Char? = null
            it.forEach {
                if (it.isDigit()) {
                    if (firstDigitChar == null) firstDigitChar = it
                    lastDigitChar = it
                }
            }

            "$firstDigitChar$lastDigitChar".toInt()
        }
    println(sum)
}
