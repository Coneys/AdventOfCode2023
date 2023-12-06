package day6

data class Race(val time: Long, val record: Long) {
    fun countWaysToWin(): Long {
        val first =
            (1..time).first { loadingTime ->
                val swimmingTime = time - loadingTime
                swimmingTime * loadingTime > record
            }

        val last =
            (1..time).last { loadingTime ->
                val swimmingTime = time - loadingTime
                swimmingTime * loadingTime > record
            }

        return 1 + last - first
    }
}
