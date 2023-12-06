package day5

import readInput

fun main() {
    val input = readInput("day_5")
    println(part1(input))
}

fun part1(input: List<String>): Long {
    val almanac = Almanac.from(input)
    return almanac.lowestLastStepDestination()
}

data class Almanac(val seeds: List<Long>, val steps: MutableList<FoodProductionStep>) {
    companion object

    fun lastStepDestinationFor(seed: Long): Long {
        return steps.first().valueFor(seed)
    }

    fun lowestLastStepDestination(): Long {
        var lowest = Long.MAX_VALUE
        seeds.forEach {
            val destination = lastStepDestinationFor(it)
            if (destination < lowest) {
                lowest = destination
            }
        }
        return lowest
    }
}

fun Almanac.Companion.from(input: List<String>): Almanac {
    val seeds =
        input[0].substringAfter("seeds:")
            .split(" ")
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .map { it.toLong() }

    val steps = createSteps(input)

    return Almanac(seeds, steps)
}
