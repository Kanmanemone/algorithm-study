class Solution {
    val supoAnswer1: IntArray = intArrayOf(1, 2, 3, 4, 5)
    val supoAnswer2: IntArray = intArrayOf(2, 1, 2, 3, 2, 4, 2, 5)
    val supoAnswer3: IntArray = intArrayOf(3, 3, 1, 1, 2, 2, 4, 4, 5, 5)

    fun solution(answers: IntArray): IntArray {
        val supoja1 = Supoja(1, supoAnswer1)
        val supoja2 = Supoja(2, supoAnswer2)
        val supoja3 = Supoja(3, supoAnswer3)
        val supojas: Array<Supoja> = arrayOf(supoja1, supoja2, supoja3)

        for (answer in answers) {
            for (supoja in supojas) {
                if (answer == supoja.getAnswer()) {
                    supoja.score++
                }
            }
        }

        val maxScore = supojas.maxOf { it.score }
        val goodSupojaIds = supojas
            .filter { it.score == maxScore }
            .map { it.id }
            .toIntArray()
        return goodSupojaIds
    }
}

data class Supoja(
    val id: Int,
    val fixedAnswers: IntArray,
    var score: Int = 0
) {
    var index = -1
    val arraySize = fixedAnswers.size

    fun getAnswer(): Int {
        index = ++index % arraySize
        return fixedAnswers[index]
    }
}