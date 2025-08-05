class Solution {
        public int maxBalancedShipments(int[] weight) {
        int res = 0, maxa = 0;
        for (int a : weight) {
            maxa = Math.max(maxa, a);
            if (a < maxa) {
                res++;
                maxa = 0;
            }
        }
        return res;
    }
}