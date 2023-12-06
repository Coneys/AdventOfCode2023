package day6

import readInput

fun main() {
    val (time, distance) = readInput("day_6")

    val waysToWin =
        racesFrom(time, distance)
            .fold(1L) { acc, race -> acc * race.countWaysToWin() }
            .also { check(it == 608902L) }

    println(waysToWin)
}

fun racesFrom(
    time: String,
    distance: String,
): List<Race> {
    val times = time.substringAfter("Time:").numberList()
    val distances = distance.substringAfter("Distance:").numberList()

    return times.zip(distances).map { Race(it.first, it.second) }
}

private fun String.numberList() =
    replace("\\s+".toRegex(), " ")
        .split(" ")
        .map { it.trim() }
        .filter { it.isNotBlank() }
        .map { it.toLong() }
