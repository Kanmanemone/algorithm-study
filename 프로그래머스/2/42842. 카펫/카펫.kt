/*
w * h == b + y
b == 2 * (w + h) - 4
y == (w - 2) * (h - 2)
*/

/*
b가 10이고 y가 2라 가정
w * h == 10
10 == 2 * (w + h) - 4

-> 두 식을 모두 만족하는 (w, h) 쌍 반환 (w > h)
*/
class Solution {
    fun solution(brown: Int, yellow: Int): IntArray {
        var answer = intArrayOf()
        val list = getCoupleList(brown + yellow)
        for(couple in list) {
            val w = couple[0]
            val h = couple[1]
            
            if(brown == 2 * (w + h) - 4) {
                val longer = maxOf(w, h)
                val shorter = minOf(w, h)
                return intArrayOf(longer, shorter)
            }
        }
        return intArrayOf()
    }
    
    fun getCoupleList(wh: Int): ArrayList<IntArray> {
        val list = ArrayList<IntArray>()
        for(w: Int in 3..(wh / 3)) {
            if(wh % w == 0) {
                list.add(intArrayOf(w, wh / w))
            }
        }
        return list
    }
}