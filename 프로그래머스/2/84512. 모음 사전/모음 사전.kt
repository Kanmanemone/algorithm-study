class Solution {
    val alphabets = arrayOf("A", "E", "I", "O", "U")
    val words = mutableSetOf<String>()
    
    fun solution(word: String): Int {
        dfs("")
        return words.toTypedArray().indexOf(word) + 1
    }
    
    fun dfs(word:String) {
        if(word.length > 5) {
            return
        }
        
        if(word != "") {
            words.add(word)
        }
        
        for(alphabet in alphabets) {
            dfs(word + alphabet)  
        }
    }
}