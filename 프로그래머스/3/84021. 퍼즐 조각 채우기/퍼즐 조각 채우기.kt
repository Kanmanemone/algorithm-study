const val BLANK = 0
const val PIECE = 1

class Solution {
    var boardLength = -1

    val blankList = ArrayList<Shape>()
    val pieceList = ArrayList<Shape>()

    var answer = 0

    fun solution(game_board: Array<IntArray>, table: Array<IntArray>): Int {
        boardLength = game_board.size

        memo(BLANK, game_board)
        for (blank in blankList) {
            blank.init(BLANK, game_board)
        }

        memo(PIECE, table)
        for (piece in pieceList) {
            piece.init(PIECE, table)
        }

        for (blank in blankList) {
            for ((pieceIndex, piece) in pieceList.withIndex()) {
                if (blank == piece) {
                    blank.sameSet.add(pieceIndex)
                }
            }
        }

        for (blank in blankList) {
            for (pieceIndex in blank.sameSet) {
                if (!pieceList[pieceIndex].used && !blank.used) {
                    pieceList[pieceIndex].used = true
                    blank.used = true
                    
                    answer += blank.volume
                }
            }
        }

        return answer
    }

    fun memo(target: Int, graph: Array<IntArray>) {
        val visited = Array(boardLength) { Array(boardLength) { false } }
        val queue = ArrayDeque<Pair<Int, Int>>()

        for (y: Int in 0..<boardLength) {
            for (x: Int in 0..<boardLength) {

                if (graph[y][x] == target && !visited[y][x]) {
                    queue.add(Pair(y, x))

                    var minY = 51
                    var minX = 51
                    var maxY = -1
                    var maxX = -1

                    while (!queue.isEmpty()) {
                        val vertex = queue.removeFirst()
                        val vY = vertex.first
                        val vX = vertex.second

                        if (vY !in 0..<boardLength || vX !in 0..<boardLength) {
                            continue
                        }

                        if (graph[vY][vX] != target) {
                            continue
                        }

                        if (visited[vY][vX]) {
                            continue
                        }

                        minY = minOf(minY, vY)
                        minX = minOf(minX, vX)
                        maxY = maxOf(maxY, vY)
                        maxX = maxOf(maxX, vX)

                        visited[vY][vX] = true

                        queue.add(Pair(vY + 1, vX))
                        queue.add(Pair(vY - 1, vX))
                        queue.add(Pair(vY, vX + 1))
                        queue.add(Pair(vY, vX - 1))
                    }

                    when (target) {
                        BLANK -> {
                            blankList.add(
                                Shape(minY..maxY, minX..maxX)
                            )
                        }

                        PIECE -> {
                            pieceList.add(
                                Shape(minY..maxY, minX..maxX)
                            )
                        }
                    }
                }
            }
        }
    }
}

data class Shape(
    val yRangeOnGraph: IntRange,
    val xRangeOnGraph: IntRange,
    var used: Boolean = false,
    val sameSet: MutableSet<Int> = mutableSetOf()
) {
    var volume = -1

    val ySize = yRangeOnGraph.last - yRangeOnGraph.first + 1
    val xSize = xRangeOnGraph.last - xRangeOnGraph.first + 1
    val value = Array(ySize) { Array(xSize) { false } }

    fun init(target: Int, graph: Array<IntArray>) {
        var count = 0
        for (y in yRangeOnGraph) {
            for (x in xRangeOnGraph) {
                if (graph[y][x] == target) {
                    count++
                    value[y - yRangeOnGraph.first][x - xRangeOnGraph.first] = true
                }
            }
        }
        volume = count
    }

    override fun equals(other: Any?): Boolean {
        if (other is Shape) {
            if (this.volume != other.volume) {
                return false
            }

            var otherValue = other.value
            repeat(4) {
                if (compare(this.value, otherValue)) {
                    return true
                }
                otherValue = rotate(otherValue)
            }

            return false
        } else {
            return false
        }
    }

    private fun rotate(original: Array<Array<Boolean>>): Array<Array<Boolean>> {
        val originalYSize = original.size
        val originalXSize = original[0].size

        val originalYRange = 0..<originalYSize
        val originalXRange = 0..<originalXSize

        val rotated = Array(originalXSize) {
            Array(originalYSize) { false }
        }

        for (originalY in originalYRange) {
            for (originalX in originalXRange) {
                val rotatedY = originalXRange.last - originalX
                val rotatedX = originalY
                rotated[rotatedY][rotatedX] = original[originalY][originalX]
            }
        }

        return rotated
    }

    private fun compare(a: Array<Array<Boolean>>, b: Array<Array<Boolean>>): Boolean {
        if (a.size != b.size || a[0].size != b[0].size) {
            return false
        }

        for (y in a.indices) {
            for (x in a[0].indices) {
                if (a[y][x] != b[y][x]) {
                    return false
                }
            }
        }
        return true
    }
}
