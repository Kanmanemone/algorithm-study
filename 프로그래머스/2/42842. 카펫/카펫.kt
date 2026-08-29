/*
brown = (width + height) * 2 - 4 = 2w + 2h -4
yellow = (width - 2) * (height - 2) = wh - 2w - 2h + 4

[연립] b + y = wh
[검증] y = (w - 2) * (h - 2)
*/
class Solution {
    fun solution(brown: Int, yellow: Int): IntArray {
        val bPlusY = brown + yellow

        for (wh in parseWidthAndHeights(bPlusY)) {
            val width = wh[0]
            val height = wh[1]
            if (yellow == (width - 2) * (height - 2)) {
                val longer = maxOf(width, height)
                val shorter = minOf(width, height)
                return intArrayOf(longer, shorter)
            }
        }

        return intArrayOf(-1)
    }

    fun parseWidthAndHeights(widthMultiplyHeight: Int): ArrayList<IntArray> {
        val parsed = ArrayList<IntArray>()

        var width = 3
        while (true) {
            if (widthMultiplyHeight % width == 0) {
                val height = widthMultiplyHeight / width
                parsed.add(intArrayOf(width, height))
            }

            width++
            if (width * width > widthMultiplyHeight) break
        }

        return parsed
    }
}