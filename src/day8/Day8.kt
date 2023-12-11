package day8

import lcm
import readInput

fun main() {
    val input = readInput("day_8")

    val directionMap = DirectionMap(input)
    println(part1(directionMap))
    println(part2(directionMap))
}

fun part1(directionMap: DirectionMap): Long {
    return directionMap.steps("AAA") { it == "ZZZ" }
}

fun part2(directionMap: DirectionMap): Long {
    val startNodes = directionMap.nodeMap.filter { it.key.endsWith("A") }
    return startNodes
        .map { directionMap.steps(it.key) { it.endsWith("Z") } }
        .run { lcm(this) }
}

enum class Direction {
    LEFT,
    RIGHT,
}

data class DirectionMap(val directions: List<Direction>, val nodeMap: Map<String, Node>) {
    fun steps(
        from: String,
        toPredicate: (String) -> Boolean,
    ): Long {
        var steps = 0
        var currentNode = nodeMap.getValue(from)
        while (toPredicate(currentNode.id).not()) {
            val nextDirection = directions[steps % directions.size]
            currentNode = currentNode.goTo(nextDirection)
            steps++
        }

        return steps.toLong()
    }

    private fun Node.goTo(nextDirection: Direction) =
        when (nextDirection) {
            Direction.LEFT -> nodeMap.getValue(left)
            Direction.RIGHT -> nodeMap.getValue(right)
        }
}

fun DirectionMap(input: List<String>): DirectionMap {
    val directions = input.first().map { if (it == 'R') Direction.RIGHT else Direction.LEFT }
    val nodes =
        input.drop(2).map {
            val id = it.substringBefore("=").trim()
            val (left, right) = it.substringAfter("(").substringBefore(")").split(",").map { it.trim() }
            Node(id, left, right)
        }
    return DirectionMap(directions, nodes.associateBy { it.id })
}

data class Node(val id: String, val left: String, val right: String)
