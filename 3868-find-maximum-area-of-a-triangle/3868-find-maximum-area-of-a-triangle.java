import java.util.*;

public class Solution {
    public long maxArea(int[][] pts) {
        int xLow = Integer.MAX_VALUE, xHigh = Integer.MIN_VALUE;
        int yLow = Integer.MAX_VALUE, yHigh = Integer.MIN_VALUE;
        Map<Integer,int[]> rows = new HashMap<>();
        Map<Integer,int[]> cols = new HashMap<>();
        
        for (int[] p : pts) {
            int x = p[0], y = p[1];
            if (x < xLow) xLow = x;
            if (x > xHigh) xHigh = x;
            if (y < yLow) yLow = y;
            if (y > yHigh) yHigh = y;
            
            rows.computeIfAbsent(y, k -> new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE});
            int[] rx = rows.get(y);
            if (x < rx[0]) rx[0] = x;
            if (x > rx[1]) rx[1] = x;
            
            cols.computeIfAbsent(x, k -> new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE});
            int[] cy = cols.get(x);
            if (y < cy[0]) cy[0] = y;
            if (y > cy[1]) cy[1] = y;
        }
        
        long bestArea2 = 0;
        
        for (var entry : rows.entrySet()) {
            int yCoord = entry.getKey();
            int spanX = entry.getValue()[1] - entry.getValue()[0];
            if (spanX > 0) {
                int above = yHigh - yCoord;
                int below = yCoord - yLow;
                int height = above > below ? above : below;
                long area2 = (long) spanX * height;
                if (area2 > bestArea2) bestArea2 = area2;
            }
        }
        
        for (var entry : cols.entrySet()) {
            int xCoord = entry.getKey();
            int spanY = entry.getValue()[1] - entry.getValue()[0];
            if (spanY > 0) {
                int right = xHigh - xCoord;
                int left  = xCoord - xLow;
                int width = right > left ? right : left;
                long area2 = (long) spanY * width;
                if (area2 > bestArea2) bestArea2 = area2;
            }
        }
        
        return bestArea2 > 0 ?  bestArea2 : -1;
    }
}