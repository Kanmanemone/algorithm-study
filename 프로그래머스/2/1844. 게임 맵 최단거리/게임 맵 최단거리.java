import java.util.LinkedList;
import java.util.Queue;

class Node {
    int y;
    int x;
    int count;

    public Node(int y, int x, int count) {
        this.y = y;
        this.x = x;
        this.count = count;
    }
}

class Solution {
    int maxY;
    int maxX;
    int[][] memo;

    public int solution(int[][] maps) {
        maxY = maps.length - 1;
        maxX = maps[0].length - 1;
        memo = new int[maps.length][maps[0].length];

        return bfs(new Node(0, 0, 1), maps);
    }

    int bfs(Node startNode, int[][] maps) {
        int answer = -1;
        Queue<Node> queue = new LinkedList<>();

        queue.add(startNode);
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            int y = node.y;
            int x = node.x;
            int count = node.count;

            if (y > maxY || x > maxX || y < 0 || x < 0) {
                continue;
            }

            if (maps[y][x] == 0) {
                continue;
            }

            if (memo[y][x] == 0 || count < memo[y][x]) {
                memo[y][x] = count;

                if (y == maxY && x == maxX) {
                    answer = count;

                    if (answer == maxY + maxX + 1) {
                        return answer;
                    }
                }

                queue.add(new Node(y + 1, x, count + 1));
                queue.add(new Node(y - 1, x, count + 1));
                queue.add(new Node(y, x + 1, count + 1));
                queue.add(new Node(y, x - 1, count + 1));
            }
        }

        return answer;
    }
}