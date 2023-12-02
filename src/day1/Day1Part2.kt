package day1

import readInput

fun main() {
    val input = readInput("day_1")
    val sum =
        input.sumOf {
            val (firstDigit, lastDigit) = firstAndLastDigitFrom(it)
            val result = "$firstDigit$lastDigit"
            result.toInt()
        }
    println(sum)
}

val digitMap =
    mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9",
    )

fun firstAndLastDigitFrom(text: String): Pair<String, String> {
    val regexPattern = digitMap.keys.joinToString("|") + "|\\d"
    val reversedRegexPattern = digitMap.keys.joinToString("|") { it.reversed() } + "|\\d"

    val first = Regex(regexPattern).find(text)!!.value.asIntDigit()
    val last = Regex(reversedRegexPattern).find(text.reversed())!!.value.reversed().asIntDigit()
    return first to last
}

private fun String.asIntDigit(): String {
    return digitMap.getOrDefault(this, this)
}
