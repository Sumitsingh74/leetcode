class Solution {
    public int maxPoints(int[][] points) {
        int n = points.length;
        if (n <= 2) return n;

        int result = 0;

        for (int i = 0; i < n; i++) {
            Map<Double, Integer> angleMap = new HashMap<>();
            int duplicates = 1;
            int localMax = 0;

            for (int j = 0; j < n; j++) {
                if (i == j) continue;

                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];

                if (dx == 0 && dy == 0) {
                    duplicates++;
                } else {
                    double angle = Math.atan2(dy, dx);
                    angleMap.put(angle, angleMap.getOrDefault(angle, 0) + 1);
                    localMax = Math.max(localMax, angleMap.get(angle));
                }
            }

            result = Math.max(result, localMax + duplicates);
        }

        return result;
    }
}