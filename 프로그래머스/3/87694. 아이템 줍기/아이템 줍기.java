class Solution {
    final int MAP_LENGTH_SIZE = 50;
    final int DOUBLED_SIZE = MAP_LENGTH_SIZE * 2;
    int[][] visited = new int[DOUBLED_SIZE + 1][DOUBLED_SIZE + 1];

    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        int[][] doubled_rectangles = new int[rectangle.length][4];
        int doubled_characterX = characterX * 2;
        int doubled_characterY = characterY * 2;
        int doubled_itemX = itemX * 2;
        int doubled_itemY = itemY * 2;

        for (int i = 0; i < doubled_rectangles.length; i++) {
            for (int j = 0; j < 4; j++) {
                doubled_rectangles[i][j] = rectangle[i][j] * 2;
            }
        }
        int[][] graph = getGraph(doubled_rectangles);

        // (BFS보다는) 코드를 좀 더 깔끔히 쓸 수 있는 DFS 선택
        dfs(graph, 1, doubled_characterY, doubled_characterX);

        return visited[doubled_itemY][doubled_itemX] / 2;
    }

    void dfs(int[][] graph, int length, int y, int x) {
        if (y < 1 || y > DOUBLED_SIZE || x < 1 || x > DOUBLED_SIZE) {
            return;
        }

        if (graph[y][x] == 0 || graph[y][x] == -1) {
            return;
        }

        if (visited[y][x] == 0 || length < visited[y][x]) {
            visited[y][x] = length;
        } else {
            return;
        }

        String log = "(" + x + ", " + y + ")" + " " + length;
        System.out.println(log);

        dfs(graph, length + 1, y + 1, x);
        dfs(graph, length + 1, y - 1, x);
        dfs(graph, length + 1, y, x + 1);
        dfs(graph, length + 1, y, x - 1);
    }

    // ~ 문제 속 문제 1 ~
    int[][] getGraph(int[][] rectangles) {
        int[][] graph = new int[DOUBLED_SIZE + 1][DOUBLED_SIZE + 1];
        for (int[] rectangle : rectangles) {
            updateGraph(graph, rectangle);
        }

        return graph;
    }

    // ~ 문제 속 문제 2 ~
    void updateGraph(int[][] graph, int[] rectangle) {
        int startY = rectangle[1];
        int endY = rectangle[3];
        int startX = rectangle[0];
        int endX = rectangle[2];

        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                if (y == startY || y == endY || x == startX || x == endX) {
                    if (graph[y][x] != -1) {
                        graph[y][x] = 1;
                    }
                } else {
                    graph[y][x] = -1;
                }
            }
        }
    }
}