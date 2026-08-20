/*
computers = [[1, 1, 0], [1, 1, 0], [0, 0, 1]]일 때,
-> computer의 번호 n의 범위는 0 ~ 2
-> computers[1][2] = 0 <- 의미: 1번 컴퓨터와 2번 컴퓨터는 연결되지 않음
*/
class Solution {
    var nodeCount = -1

    // 간접적으로 연결된 경우도 1로 표시하는 버전의 computers
    lateinit var computers2: Array<IntArray>

    fun solution(n: Int, computers: Array<IntArray>): Int {
        nodeCount = n
        computers2 = Array(computers.size) { IntArray(computers[0].size) }
        for (i in computers.indices) {
            for (j in computers[0].indices) {
                computers2[i][j] = computers[i][j]
            }
        }

        for (nodeNumber: Int in 0..<nodeCount) {
            val visited = mutableSetOf<Int>()
            dfs(nodeNumber, nodeNumber, visited)
        }

        return calNetworkCount(computers2)
    }

    fun dfs(initNodeNumber: Int, nodeNumber: Int, visited: MutableSet<Int>) {
        computers2[initNodeNumber][nodeNumber] = 1
        visited.add(nodeNumber)

        for ((nextNodeNumber, connected) in computers2[nodeNumber].withIndex()) {
            if (!visited.contains(nextNodeNumber) && connected == 1) {
                dfs(initNodeNumber, nextNodeNumber, visited)
            }
        }
    }
}

/* ~ 문제 속 문제 ~
간접적으로 연결된 경우도 1로 표시한 computers가 처음부터 주어졌을때,
네트워크의 개수를 구하라.
*/
fun calNetworkCount(computers2: Array<IntArray>): Int {
    val nodeCount = computers2.size
    var networkCount = 0
    val visited = mutableSetOf<Int>()

    for (nodeNumber: Int in 0..<nodeCount) {
        if (!visited.contains(nodeNumber)) {
            networkCount++
            for ((nextNodeNumber, connected) in computers2[nodeNumber].withIndex()) {
                if (connected == 1) {
                    visited.add(nextNodeNumber)
                }
            }
        }
    }

    return networkCount
}