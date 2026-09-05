class Solution {
    val alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    lateinit var nameCharIndexToAlphabetIndex: (Int) -> Int
    lateinit var nameIndices: IntRange

    var answer = -1

    fun solution(name: String): Int {
        nameCharIndexToAlphabetIndex = {
            val nameChar = name[it]
            alphabets.indexOf(nameChar)
        }
        nameIndices = name.indices

        val changeNeededIndexes = name.withIndex().filter {
            it.value != 'A'
        }.map {
            it.index
        }

        return if (changeNeededIndexes.isEmpty()) {
            0
        } else {
            dfs(changeNeededIndexes)
            answer
        }
    }

    fun dfs(vertexes: List<Int>) {
        val visited = mutableSetOf<Int>()
        vertexes.forEach {
            dfs(it, 0, 0, visited, vertexes)
        }
    }

    fun dfs(
        vertex: Int,
        cursorIndex: Int,
        allCount: Int,
        visited: MutableSet<Int>,
        vertexes: List<Int>
    ) {
        // [1] 방문 기록
        if (visited.contains(vertex)) {
            return
        }
        visited.add(vertex)

        // [2] 이 정점에서 할 일
        var updatedCount = allCount
        // 조이스틱 조작 - 커서 이동
        updatedCount += getMinMoveCount(cursorIndex, vertex, nameIndices)
        // 조이스틱 조작 - 알파벳 변경
        updatedCount += getMinMoveCount(
            0, // 'A'의 인덱스
            nameCharIndexToAlphabetIndex(vertex),
            alphabets.indices
        )

        // [3] 다음 정점 준비 및 이동
        val updatedCursorIndex = vertex
        vertexes.forEach {
            dfs(it, updatedCursorIndex, updatedCount, visited, vertexes)
        }

        // [4] 정답 메모
        if (visited.size == vertexes.size) {
            answer = if (answer == -1) {
                updatedCount
            } else {
                minOf(answer, updatedCount)
            }
        }

        // [5] 백트래킹
        visited.remove(vertex)
    }

    fun getMinMoveCount(a: Int, b: Int, range: IntRange): Int {
        val smaller = minOf(a, b)
        val larger = maxOf(a, b)

        return minOf(
            larger - smaller,
            (smaller - range.first) + (1) + (range.last - larger)
        )
    }
}