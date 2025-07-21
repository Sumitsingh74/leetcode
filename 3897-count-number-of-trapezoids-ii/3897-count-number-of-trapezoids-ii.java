import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Solution {

    private record Pair(long x, long y) {}
    private record Tuple(long a, long b, long c) {}

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public int countTrapezoids(int[][] points) {
        int n = points.length;

        Map<Pair, List<int[]>> slopeToPairs = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long dy = (long) points[j][1] - points[i][1];
                long dx = (long) points[j][0] - points[i][0];
                Pair slope;
                if (dx == 0) {
                    slope = new Pair(Long.MAX_VALUE, 1);
                } else if (dy == 0) {
                    slope = new Pair(0, 1);
                } else {
                    long commonDivisor = gcd(Math.abs(dy), Math.abs(dx));
                    dy /= commonDivisor;
                    dx /= commonDivisor;
                    if (dx < 0) {
                        dx = -dx;
                        dy = -dy;
                    }
                    slope = new Pair(dy, dx);
                }
                slopeToPairs.computeIfAbsent(slope, k -> new ArrayList<>()).add(new int[]{i, j});
            }
        }

        long N = 0;
        for (List<int[]> pairs : slopeToPairs.values()) {
            long k = pairs.size();
            if (k < 2) continue;
            long totalPairsForSlope = k * (k - 1) / 2;
            long sharedEndpointPairs = 0;
            Map<Integer, Integer> pointFreq = new HashMap<>();
            for (int[] p : pairs) {
                pointFreq.put(p[0], pointFreq.getOrDefault(p[0], 0) + 1);
                pointFreq.put(p[1], pointFreq.getOrDefault(p[1], 0) + 1);
            }
            for (int freq : pointFreq.values()) {
                sharedEndpointPairs += (long) freq * (freq - 1) / 2;
            }
            N += totalPairsForSlope - sharedEndpointPairs;
        }

        long P_total = 0;
        Map<Pair, Integer> midpointCounts = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Pair midSum = new Pair((long) points[i][0] + points[j][0], (long) points[i][1] + points[j][1]);
                midpointCounts.put(midSum, midpointCounts.getOrDefault(midSum, 0) + 1);
            }
        }
        for (int count : midpointCounts.values()) {
            P_total += (long) count * (count - 1) / 2;
        }

        Map<Tuple, Set<Integer>> lines = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long x1 = points[i][0], y1 = points[i][1];
                long x2 = points[j][0], y2 = points[j][1];

                long a = y2 - y1;
                long b = x1 - x2;
                long c = -a * x1 - b * y1;

                long commonDivisor = gcd(Math.abs(a), gcd(Math.abs(b), Math.abs(c)));
                
                a /= commonDivisor;
                b /= commonDivisor;
                c /= commonDivisor;

                if (a < 0 || (a == 0 && b < 0)) {
                    a = -a; b = -b; c = -c;
                }
                Tuple lineEq = new Tuple(a, b, c);
                lines.computeIfAbsent(lineEq, k -> new HashSet<>()).add(i);
                lines.computeIfAbsent(lineEq, k -> new HashSet<>()).add(j);
            }
        }
        
        long CQ = 0;
        long P_degenerate = 0;
        for (Set<Integer> pointIndicesSet : lines.values()) {
            int m = pointIndicesSet.size();
            if (m < 4) continue;

            CQ += (long)m * (m - 1) * (m - 2) * (m - 3) / 24;

            List<Integer> pointIndices = new ArrayList<>(pointIndicesSet);
            Map<Pair, Integer> lineMidpointCounts = new HashMap<>();
            for (int k = 0; k < m; k++) {
                for (int l = k + 1; l < m; l++) {
                    int p1_idx = pointIndices.get(k);
                    int p2_idx = pointIndices.get(l);
                    Pair midSum = new Pair((long)points[p1_idx][0] + points[p2_idx][0], 
                                          (long)points[p1_idx][1] + points[p2_idx][1]);
                    lineMidpointCounts.put(midSum, lineMidpointCounts.getOrDefault(midSum, 0) + 1);
                }
            }
            for (int count : lineMidpointCounts.values()) {
                P_degenerate += (long)count * (count - 1) / 2;
            }
        }

        return (int) (N - P_total - 3 * CQ + P_degenerate);
    }
}