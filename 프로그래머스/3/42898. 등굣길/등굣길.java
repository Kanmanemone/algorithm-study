

class Solution {
    int[][] memo;
    int maxY;
    int maxX;

    public int solution(int m, int n, int[][] puddles) {
        memo = new int[n][m]; // 모든 원소는 0으로 초기화됨
        maxY = n - 1; // 1-based indexing -> 0-based indexing
        maxX = m - 1;

        for (int[] puddle : puddles) {
            int pY = puddle[1] - 1; // 1-based indexing -> 0-based indexing
            int pX = puddle[0] - 1;
            memo[pY][pX] = -1;
        }

        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                if(memo[y][x] != -1) {
                    memo[y][x] = getMemo(y - 1, x) + getMemo(y, x - 1);
                    memo[y][x] %= 1000000007;
                }

                if (y == 0 & x == 0) {
                    memo[0][0] = 1;
                }
            }
        }

        return memo[maxY][maxX];
    }

    public int getMemo(int y, int x) {
        if (y < 0 || x < 0) {
            return 0;
        }

        if (memo[y][x] == -1) {
            return 0;
        }

        return memo[y][x];
    }
}