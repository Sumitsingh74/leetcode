import java.util.*;
import java.io.*;

class Solution {
    static class State {
        int r, c, e;
        BitSet mask;

        public State(int r, int c, int e, BitSet mask) {
            this.r = r;
            this.c = c;
            this.e = e;
            this.mask = mask;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return r == state.r && c == state.c && e == state.e && Objects.equals(mask, state.mask);
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c, e, mask);
        }
    }

    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        char[][] grid = new char[m][n];
        int startR = -1, startC = -1;
        List<int[]> litters = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            grid[i] = classroom[i].toCharArray();
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    startR = i;
                    startC = j;
                } else if (grid[i][j] == 'L') {
                    litters.add(new int[]{i, j});
                }
            }
        }
        int k = litters.size();
        int[][] litterIndex = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(litterIndex[i], -1);
        }
        for (int idx = 0; idx < k; idx++) {
            int[] pos = litters.get(idx);
            litterIndex[pos[0]][pos[1]] = idx;
        }

        int energyCap = Math.min(energy, 400);
        int[] dr = {0, 0, 1, -1};
        int[] dc = {1, -1, 0, 0};

        if (k == 0) {
            return 0;
        }

        if (k <= 20) {
            int[][][][] dist = new int[m][n][energyCap + 1][1 << k];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    for (int e = 0; e <= energyCap; e++) {
                        Arrays.fill(dist[i][j][e], Integer.MAX_VALUE);
                    }
                }
            }
            Queue<int[]> queue = new ArrayDeque<>();
            dist[startR][startC][energyCap][0] = 0;
            queue.add(new int[]{startR, startC, energyCap, 0, 0});
            int finalMask = (1 << k) - 1;

            while (!queue.isEmpty()) {
                int[] state = queue.poll();
                int r = state[0], c = state[1], e = state[2], mask = state[3], moves = state[4];
                if (mask == finalMask) {
                    return moves;
                }
                if (moves > dist[r][c][e][mask]) {
                    continue;
                }
                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n || grid[nr][nc] == 'X') {
                        continue;
                    }
                    int newE = e - 1;
                    if (newE < 0) {
                        continue;
                    }
                    char cell = grid[nr][nc];
                    int newMask = mask;
                    if (cell == 'L') {
                        int idx = litterIndex[nr][nc];
                        if (idx != -1 && (mask & (1 << idx)) == 0) {
                            newMask = mask | (1 << idx);
                        }
                    }
                    int nextEnergy = newE;
                    if (cell == 'R') {
                        nextEnergy = energyCap;
                    }
                    if (dist[nr][nc][nextEnergy][newMask] > moves + 1) {
                        dist[nr][nc][nextEnergy][newMask] = moves + 1;
                        queue.add(new int[]{nr, nc, nextEnergy, newMask, moves + 1});
                    }
                }
            }
            return -1;
        } else {
            Map<State, Integer> distMap = new HashMap<>();
            Queue<State> queue = new ArrayDeque<>();
            BitSet startMask = new BitSet(k);
            State startState = new State(startR, startC, energyCap, startMask);
            distMap.put(startState, 0);
            queue.add(startState);

            while (!queue.isEmpty()) {
                State state = queue.poll();
                int moves = distMap.get(state);
                if (state.mask.cardinality() == k) {
                    return moves;
                }
                for (int d = 0; d < 4; d++) {
                    int nr = state.r + dr[d];
                    int nc = state.c + dc[d];
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n || grid[nr][nc] == 'X') {
                        continue;
                    }
                    int newE = state.e - 1;
                    if (newE < 0) {
                        continue;
                    }
                    char cell = grid[nr][nc];
                    BitSet newMask = (BitSet) state.mask.clone();
                    if (cell == 'L') {
                        int idx = litterIndex[nr][nc];
                        if (idx != -1 && !newMask.get(idx)) {
                            newMask.set(idx);
                        }
                    }
                    int nextEnergy = newE;
                    if (cell == 'R') {
                        nextEnergy = energyCap;
                    }
                    State nextState = new State(nr, nc, nextEnergy, newMask);
                    if (!distMap.containsKey(nextState) || distMap.get(nextState) > moves + 1) {
                        distMap.put(nextState, moves + 1);
                        queue.add(nextState);
                    }
                }
            }
            return -1;
        }
    }
}