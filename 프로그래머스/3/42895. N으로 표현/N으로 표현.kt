val queue = ArrayDeque<Node>()
val countMemo = mutableMapOf<Int, Int>() // number, count

class Solution {
    fun solution(N: Int, number: Int): Int {
        // 1
        for (i in 1..8) {
            queue.add(
                Node(getRepeatedNumber(N, i), i)
            )
            countMemo[getRepeatedNumber(N, i)] = i
        }
        bfs { from ->
            listOf(
                Node(from.number + N, from.count + 1),
                Node(from.number - N, from.count + 1),
                Node(from.number * N, from.count + 1),
                Node(from.number / N, from.count + 1)
            )
        }

        // 2
        queue.addAll(countMemo.map { Node(it.key, it.value) })
        bfs { from ->
            countMemo.flatMap { to ->
                listOfNotNull(
                    Node(from.number + to.key, from.count + to.value),
                    Node(from.number - to.key, from.count + to.value),
                    Node(from.number * to.key, from.count + to.value),
                    if (to.key != 0) {
                        Node(from.number / to.key, from.count + to.value)
                    } else {
                        null
                    },
                )
            }
        }

        return countMemo[number] ?: -1
    }
}

fun bfs(getNextNodes: (Node) -> List<Node>) {
    while (!queue.isEmpty()) {
        val node = queue.removeFirst()
        for (next in getNextNodes(node)) {
            queue.addIfNeeded(next)
        }
    }
}

fun ArrayDeque<Node>.addIfNeeded(node: Node) {
    if (8 < node.count) {
        return
    }

    if (
        (countMemo[node.number] == null)
        || (node.count < countMemo[node.number]!!)
    ) {
        this.add(node)
        countMemo[node.number] = node.count
    }
}

data class Node(
    val number: Int,
    val count: Int,
)

fun getRepeatedNumber(N: Int, count: Int): Int {
    var result = 0
    for (i: Int in 1..count) {
        var operand = 1
        repeat(i - 1) {
            operand *= 10
        }
        result += N * operand
    }
    return result
}