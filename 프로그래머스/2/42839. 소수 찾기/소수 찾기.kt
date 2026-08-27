class Solution {
    val cards = ArrayList<Card>()
    val set = mutableSetOf<String>()
    val isSosu = Array(9999999 + 1) { true }
    
    fun solution(numbers: String): Int {
        for(number in numbers) {
            cards.add(Card(number))
        }
        
        dfs("")
        
        initIsSosu()
        
        return set.filter { isSosu[it.toInt()] }.size
    }
    
    fun dfs(numbers: String, card: Card? = null) {
        if(numbers != "" && numbers[0] != '0') {
            set.add(numbers)
        }
        card?.used = true
        
        cards.filter { !it.used }.forEach {
            dfs(numbers + it.number, it)
        }
        
        card?.used = false // 백트래킹
    }
    
    fun initIsSosu() {
        isSosu[0] = false
        isSosu[1] = false
        
        for(number in 2..9999999) {
            if(isSosu[number]) {
                var baeSu = number * 2
                while(baeSu <= 9999999) {
                    isSosu[baeSu] = false
                    baeSu += number
                }
            }
        }
    }
}

data class Card(
    val number : Char,
    var used : Boolean = false,
)