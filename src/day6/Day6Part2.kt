package day6

import readInput

fun main() {
    val (time, distance) = readInput("day_6")

    val race = raceFrom(time, distance)

    val waysToWin = race.countWaysToWin().also { check(it == 46173809L) }
    println(waysToWin)
}

fun raceFrom(
    timeString: String,
    distanceString: String,
): Race {
    val time = timeString.substringAfter("Time:").number()
    val distance = distanceString.substringAfter("Distance:").number()

    return Race(time, distance)
}

private fun String.number() = replace("\\s+".toRegex(), "").toLong()
