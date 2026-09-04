import kotlin.math.abs

lateinit var towers: Array<Tower>
lateinit var adj: Array<Array<Boolean>>

class Solution {
    var answer: Int = -1

    fun solution(n: Int, wires: Array<IntArray>): Int {
        // towers[0] 및 adj[0]은 미사용
        towers = Array(n + 1) { Tower() }
        adj = Array(n + 1) { Array(n + 1) { false } }

        // adj 완성
        for (wire in wires) {
            val a = wire[0]
            val b = wire[1]
            adj[a][b] = true
            adj[b][a] = true
        }

        // Tower.childIndexes 완성
        // 아무 노드를 선택하고 그걸 탑 노드라고 생각
        // 그 탑 노드를 기준으로 bfs 수행하면서 부모-자식 관계 주입
        val topNodeIndex = 1
        memoChildren(topNodeIndex)

        // 정답 반환
        for ((index, tower) in towers.withIndex()) {
            if (index == 0) {
                continue
            }

            tower.childIndexes.forEach { childIndex ->
                val detachedElectric = towers[childIndex].electric
                val gap = abs(
                    (n - detachedElectric) - detachedElectric
                )

                println(
                    """
                        분리: $index -> $childIndex
                        전체 전력: $n
                        덩어리 1의 전력: ${n - detachedElectric}
                        덩어리 2의 전력: $detachedElectric
                        차이: $gap
                        ---
                    """.trimIndent()
                )

                answer = if (answer == -1) {
                    gap
                } else {
                    minOf(answer, gap)
                }
            }
        }

        return answer
    }

    fun memoChildren(topNodeIndex: Int) {
        val queue = ArrayDeque<Int>()
        val visited = mutableSetOf<Int>()
        queue.add(topNodeIndex)
        visited.add(topNodeIndex)

        while (!queue.isEmpty()) {
            val nodeIndex = queue.removeFirst()
            for ((nextIndex, isConnected) in adj[nodeIndex].withIndex()) {
                if (isConnected && !visited.contains(nextIndex)) {
                    towers[nodeIndex].childIndexes.add(nextIndex)

                    queue.add(nextIndex)
                    visited.add(nextIndex)
                }
            }
        }
    }
}

data class Tower(
    val childIndexes: ArrayList<Int> = ArrayList<Int>(),
) {
    val electric: Int
        get() {
            var tmp = 0
            val queue = ArrayDeque<Int>(childIndexes)

            while (!queue.isEmpty()) {
                val childIndex = queue.removeFirst()
                val child = towers[childIndex]
                tmp += child.electric
            }

            return 1 + tmp
        }
}