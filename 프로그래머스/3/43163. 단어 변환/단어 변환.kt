class Solution {
    lateinit var words2: ArrayList<String>
    lateinit var graph: Array<Array<Int>>
    var wordLength = -1

    fun solution(begin: String, target: String, words: Array<String>): Int {
        
        // 1: 변환할 수 없는 경우 가지치기
        if(!words.contains(target)) {
            return 0
        }
        
        // 2: begin까지 포함하는 버전의 words
        words2 = ArrayList(words.toList())
        if(!words2.contains(begin)) {
            words2.add(begin)
        }
        
        // 3: 인접 그래프 제작
        graph = Array(words2.size) { Array(words2.size) { 0 } }
        wordLength = words2[0].length
        
        for((i, w1) in words2.withIndex()) {
            for((j, w2) in words2.withIndex()) {
                
                var changeCount = 0
                for(k: Int in 0..<wordLength) {
                    if(w1[k] != w2[k]) {
                        changeCount++
                    }
                }
                
                if(changeCount == 1) {
                    graph[i][j] = 1
                    graph[j][i] = 1
                }
            }
        }
        
        // 4: bfs 수행
        val beginIndex = words2.indexOf(begin)
        val targetIndex = words2.indexOf(target)
        return bfs(beginIndex, targetIndex)
    }
    
    fun bfs(beginIndex:Int, targetIndex: Int) : Int {
        val queue = ArrayDeque<Pair<Int, Int>>()
        val visited = mutableSetOf<Int>()
        
        queue.add(Pair(beginIndex, 0))
        
        while(!queue.isEmpty()) {
            val pair = queue.removeLast()
            val wordIndex = pair.first
            val moveCount = pair.second
            
            visited.add(wordIndex)
            
            if(wordIndex == targetIndex) {
                return moveCount
            } else {
                for((nextIndex, connected) in graph[wordIndex].withIndex()) {
                    if(connected == 1 && !visited.contains(nextIndex)) {
                        queue.add(Pair(nextIndex, moveCount + 1))
                    }
                }
            }
        }
        
        return -1
    }
}