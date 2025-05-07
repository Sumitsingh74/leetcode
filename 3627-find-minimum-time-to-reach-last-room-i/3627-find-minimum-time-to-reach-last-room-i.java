class Pair implements Comparable<Pair> {
    int x;
    int y;
    int parity;
    int time;

    public Pair(int x, int y, int time, int parity) {
        this.x = x;
        this.y = y;
        this.parity = parity;
        this.time = time;
    }

    @Override
    public int compareTo(Pair p) {
        return Integer.compare(this.time, p.time);
    }
}

class Solution {
    int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

    public int minTimeToReach(int[][] moveTime) {
        int n = moveTime.length, m = moveTime[0].length;

        int[][][] dist = new int[n][m][2];
        for (int[][] d : dist) {
            for (int i = 0; i < m; i++) {
                d[i][0] = Integer.MAX_VALUE;
                d[i][1] = Integer.MAX_VALUE;
            }
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(0, 0, 0, 0));
        dist[0][0][0] = 0;

        while (!pq.isEmpty()) {
            Pair top = pq.poll();
            int x = top.x, y = top.y;
            int time = top.time;
            int par = top.parity;

            if (time > dist[x][y][par]) {
                continue;
            }

            for (int[] d : dirs) {
                int nx = x + d[0], ny = y + d[1];

                if (nx >= 0 && nx < n && ny >= 0 && ny < m) {
                    int nt;
                    if (par == 0) {
                        nt = 1 + Math.max(time, moveTime[nx][ny]);
                    } else {
                        nt = 1 + Math.max(time, moveTime[nx][ny]);
                    }

                    if (nt < dist[nx][ny][1 - par]) {
                        dist[nx][ny][1 - par] = nt;
                        pq.offer(new Pair(nx, ny, nt, 1 - par));
                    }
                }
            }
        }
        return Math.min(dist[n - 1][m - 1][0], dist[n - 1][m - 1][1]);
    }
}