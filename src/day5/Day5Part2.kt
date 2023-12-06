package day5

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import readInput

fun main() {
    runBlocking {
        val input = readInput("day_5")
        println(part2(input))
    }
}

suspend fun part2(input: List<String>): Long {
    return coroutineScope {
        val (ranges, steps) = parseInput(input)
        ranges
            .map { async { smallestValueForRange(it, steps) } }
            .minOf { it.await() }
    }
}

private fun smallestValueForRange(
    it: LongRange,
    steps: MutableList<FoodProductionStep>,
): Long {
    var smol = Long.MAX_VALUE
    it.forEach {
        smol = minOf(smol, steps.first().valueFor(it))
    }
    return smol
}

fun parseInput(input: List<String>): Pair<List<LongRange>, MutableList<FoodProductionStep>> {
    val seedsRange =
        input[0].substringAfter("seeds:")
            .split(" ")
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .map { it.toLong() }
            .chunked(2) {
                val start = it[0]
                val range = it[1]
                (start..<start + range)
            }

    val steps = createSteps(input)

    return seedsRange to steps
}
