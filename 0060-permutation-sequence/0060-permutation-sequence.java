import java.util.*;

class Solution {
    public String getPermutation(int n, int k) {

        List<Integer> digits = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            digits.add(i);
        }

        int[] factorial = new int[n];
        factorial[0] = 1; 
        for (int i = 1; i < n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }

        StringBuilder sb = new StringBuilder();

        k--;
        for (int i = n; i >= 1; i--) {
            int blockSize = factorial[i - 1];
            int index = k / blockSize;

            sb.append(digits.get(index));
            digits.remove(index);
            k = k % blockSize;
        }

        return sb.toString();
    }
}