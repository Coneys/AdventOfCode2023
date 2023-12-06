package day5

fun createSteps(input: List<String>): MutableList<FoodProductionStep> {
    val steps = mutableListOf<FoodProductionStep>()

    var currentAssembler: StepAssembler? = null

    input.forEachIndexed { index, s ->
        if (s.endsWith("map:")) {
            currentAssembler?.assembly()?.also {
                steps.lastOrNull()?.nextStep = it
                steps.add(it)
            }
            currentAssembler = StepAssembler(s.substringBefore("map:"))
        }
        if (s.contains("\\d".toRegex())) {
            currentAssembler?.addRecipe(s)
        }
    }

    currentAssembler?.assembly()?.also {
        steps.lastOrNull()?.nextStep = it
        steps.add(it)
    }
    return steps
}

class StepAssembler(val label: String) {
    var recipes: MutableList<StepRecipe> = mutableListOf()

    fun addRecipe(line: String) {
        val (dstStart, srcStart, range) = line.split(" ").map { it.trim().toLong() }
        recipes.add(StepRecipe(dstStart, srcStart, range))
    }

    fun assembly(): FoodProductionStep {
        return FoodProductionStep(label, recipes)
    }
}

data class StepRecipe(val destinationStart: Long, val sourceStart: Long, val rangeSize: Long) {
    private val sourceRange = sourceStart..<sourceStart + rangeSize

    fun hasDestinationFor(source: Long) = source in sourceRange

    fun destinationFor(source: Long): Long? {
        return if (hasDestinationFor(source)) {
            val destinationDiff = source - sourceStart
            destinationStart + destinationDiff
        } else {
            null
        }
    }
}

data class FoodProductionStep(val label: String, val recipes: MutableList<StepRecipe>) {
    fun valueFor(source: Long): Long {
        val destination = recipes.firstOrNull { it.hasDestinationFor(source) }?.destinationFor(source) ?: source
        return nextStep?.valueFor(destination) ?: destination
    }

    var nextStep: FoodProductionStep? = null
}
