package day2

import readInput

fun main() {
    val games = readInput("day_2").map(Game::from)
    println(part2(games))
}

fun part1(games: List<Game>): Int {
    val existingCubes = mapOf("red" to 12, "green" to 13, "blue" to 14)

    return games
        .filter { it.possibleWith(existingCubes) }
        .sumOf { it.id }
}

fun part2(games: List<Game>): Int {
    return games.sumOf { it.power() }
}

data class Revelation(val cubes: Map<String, Int>) {
    val blueCubes get() = cubes.get("blue")
    val greenCubes get() = cubes.get("green")
    val redCubes get() = cubes.get("red")

    fun possibleWith(existingCubes: Map<String, Int>): Boolean {
        return cubes.all { existingCubes.getValue(it.key) >= it.value }
    }
}

data class Game(val id: Int, val revelations: List<Revelation>) {
    fun possibleWith(existingCubes: Map<String, Int>): Boolean {
        return revelations.all { it.possibleWith(existingCubes) }
    }

    fun power(): Int {
        val blue = revelations.fold(1) { acc, rev -> maxOf(acc, rev.blueCubes ?: 1) }
        val red = revelations.fold(1) { acc, rev -> maxOf(acc, rev.redCubes ?: 1) }
        val green = revelations.fold(1) { acc, rev -> maxOf(acc, rev.greenCubes ?: 1) }

        return blue * red * green
    }

    companion object {
        fun from(line: String): Game {
            val gameId = line.substringBefore(":").substringAfter("Game ")
            val revelationString = line.substringAfter(":").split(";")

            val revelations =
                revelationString.map {
                    val cubes =
                        it.split(",")
                            .map { it.trim() }
                            .associate {
                                val (count, color) = it.split(" ")
                                color to count.toInt()
                            }
                    Revelation(cubes)
                }
            return Game(gameId.toInt(), revelations)
        }
    }
}
