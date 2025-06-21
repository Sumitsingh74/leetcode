import java.util.*;

public class Solution {
    public long maxArea(int[][] pts) {
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
        Map<Integer,Integer> rowCnt = new HashMap<>();
        Map<Integer,Integer> colCnt = new HashMap<>();
        Map<Integer,Integer> rowMin = new HashMap<>();
        Map<Integer,Integer> rowMax = new HashMap<>();
        Map<Integer,Integer> colMin = new HashMap<>();
        Map<Integer,Integer> colMax = new HashMap<>();
        
        for (int[] p : pts) {
            int x = p[0], y = p[1];
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
            
            rowCnt.put(y, rowCnt.getOrDefault(y, 0) + 1);
            if (!rowMin.containsKey(y)) {
                rowMin.put(y, x);
                rowMax.put(y, x);
            } else {
                rowMin.put(y, Math.min(rowMin.get(y), x));
                rowMax.put(y, Math.max(rowMax.get(y), x));
            }
            
            colCnt.put(x, colCnt.getOrDefault(x, 0) + 1);
            if (!colMin.containsKey(x)) {
                colMin.put(x, y);
                colMax.put(x, y);
            } else {
                colMin.put(x, Math.min(colMin.get(x), y));
                colMax.put(x, Math.max(colMax.get(x), y));
            }
        }
        
        long best = 0;
        for (var e : rowCnt.entrySet()) {
            int y = e.getKey(), cnt = e.getValue();
            if (cnt < 2) continue;
            long w = rowMax.get(y) - rowMin.get(y);
            if (w > 0) {
                long h = Math.max(maxY - y, y - minY);
                best = Math.max(best, w * h);
            }
        }
        
        for (var e : colCnt.entrySet()) {
            int x = e.getKey(), cnt = e.getValue();
            if (cnt < 2) continue;
            long h = colMax.get(x) - colMin.get(x);
            if (h > 0) {
                long w = Math.max(maxX - x, x - minX);
                best = Math.max(best, h * w);
            }
        }
        
        return best > 0 ? best : -1;
    }
}
