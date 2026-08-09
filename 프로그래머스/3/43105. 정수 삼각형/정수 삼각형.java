class Solution {
    public int solution(int[][] triangle) {
        int maxY = triangle.length - 1;

        for (int y = maxY - 1; 0 <= y; y--) {
            for (int x = 0; x < triangle[y].length; x++) {
                triangle[y][x] += Math.max(
                        triangle[y + 1][x],
                        triangle[y + 1][x + 1]
                );
            }
        }

        return triangle[0][0];
    }
}