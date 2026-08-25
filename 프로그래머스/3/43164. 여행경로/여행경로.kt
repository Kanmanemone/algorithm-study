class Solution {
    var ticketCount: Int = -1
    lateinit var indexToName: Array<String>
    lateinit var nameToIndex: MutableMap<String, Int>
    lateinit var ticketGraph: Array<Array<Int>>
    val path = ArrayList<Int>()
    var answer: Array<String> = Array(0) { "" }

    fun solution(tickets: Array<Array<String>>): Array<String> {
        ticketCount = tickets.size

        // 1: 알파벳 순 정렬
        val nameSet = mutableSetOf<String>()
        for (ticket in tickets) {
            val startName: String = ticket[0]
            val endName: String = ticket[1]
            nameSet.add(startName)
            nameSet.add(endName)
        }
        indexToName = nameSet.toTypedArray()
        indexToName.sort()

        nameToIndex = mutableMapOf()
        for (i in indexToName.indices) {
            val name = indexToName[i]
            nameToIndex[name] = i
        }

        // 2: 인접 그래프
        ticketGraph = Array(indexToName.size) { Array(indexToName.size) { -1 } }
        for (ticket in tickets) {
            val startName = ticket[0]
            val endName = ticket[1]

            val startIndex = nameToIndex[startName]!!
            val endIndex = nameToIndex[endName]!!
            if (ticketGraph[startIndex][endIndex] == -1) {
                ticketGraph[startIndex][endIndex] = 1
            } else {
                ticketGraph[startIndex][endIndex]++
            }
        }

        // 3: 탐색
        val icnIndex = indexToName.indexOf("ICN")
        dfs(icnIndex, 0)
        return answer
    }

    fun dfs(now: Int, usedTicketCount: Int) {
        path.add(now)

        if (usedTicketCount == ticketCount) {
            if (answer.isEmpty()) {
                answer = path.map { indexToName[it] }.toTypedArray()
            }
        }

        for (next in ticketGraph[now].indices) {
            if (ticketGraph[now][next] > 0) {
                ticketGraph[now][next]--

                dfs(next, usedTicketCount + 1)

                // 백트래킹
                path.removeAt(path.lastIndex)
                ticketGraph[now][next]++
            }
        }
    }
}
