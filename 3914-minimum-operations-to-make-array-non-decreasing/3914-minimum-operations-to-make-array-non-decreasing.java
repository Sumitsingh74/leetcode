class Solution {
  public long minOperations(int[] A) {
       long res = 0;
        for (int i = 0; i + 1 < A.length; i++) {
            res += Math.max(0, A[i] - A[i + 1]);
        }
        return res;
    }
}