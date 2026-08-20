class Solution {
    var answer = 0

    fun solution(numbers: IntArray, target: Int): Int {
        find(0, numbers[0], numbers, target)
        find(0, -numbers[0], numbers, target)

        return answer
    }

    fun find(index: Int, sum: Int, numbers: IntArray, target: Int) {
        if (index == numbers.lastIndex && sum == target) {
            answer++
        } else {
            val next = index + 1
            if (next <= numbers.lastIndex) {
                find(next, sum + numbers[next], numbers, target)
                find(next, sum - numbers[next], numbers, target)
            }
        }
    }
}