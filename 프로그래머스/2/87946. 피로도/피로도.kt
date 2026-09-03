class Solution {
    var answer = 0
    lateinit var classedDungeons: List<Dungeon>

    fun solution(k: Int, dungeons: Array<IntArray>): Int {
        // [2] 문제 조건 받기
        classedDungeons = dungeons.map {
            Dungeon(it[0], it[1])
        }

        // [3] 던전 갯수는 최대 8개이므로, 완전 탐색을 해도 시간이 남을 것이다.
        bruteForce(k)

        return answer
    }

    // [3]
    fun bruteForce(startK: Int) {
        for (startDungeon in classedDungeons) {
            dfs(startK, startDungeon)
        }
    }

    // [4] 무한 탐색이 되지 않게 하기 위해, 상태 참조와 그걸 되돌리는 백트래킹이 필요하다
    fun dfs(k: Int, dungeon: Dungeon) {
        // [5] 방문만하는 경우와 클리어까지 하는 경우의 2분기가 생기므로, 2개의 상태를 관리한다.
        if (!dungeon.visited) {
            dungeon.visited = true

            var updatedK: Int? = null
            if (!dungeon.completed) {
                if (k >= dungeon.least) {
                    updatedK = k - dungeon.consume
                    dungeon.completed = true
                    answer = maxOf(answer, classedDungeons.count { it.completed })
                }
            }

            // 완전 탐색
            for (next in classedDungeons) {
                dfs(updatedK ?: k, next)
            }

            // [6] 2가지 상태 각각에 대해 백트래킹(초기화)
            dungeon.completed = false
            dungeon.visited = false
        }
    }
}

// [1] 헷갈림 방지를 위해 클래스 선언
data class Dungeon(
    val least: Int,
    val consume: Int,
    var visited: Boolean = false,
    var completed: Boolean = false
)