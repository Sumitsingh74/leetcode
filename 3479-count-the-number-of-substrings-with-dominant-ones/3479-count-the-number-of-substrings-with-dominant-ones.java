import java.util.Deque;
import java.util.LinkedList;

public class Solution {
    public int numberOfSubstrings(String s) {
        int n = s.length();
        int result = 0;
        
        // Iterate through possible zero counts (1 to sqrt(n))
        for (int k = 1; k <= Math.sqrt(n); k++) {
            Deque<Integer> zeros = new LinkedList<>();  // Queue to store positions of zeros
            int lastZero = -1;    // Position of the zero before the first zero in our window
            int ones = 0;         // Count of ones in our current window
            
            // Scan through the string
            for (int right = 0; right < n; right++) {
                if (s.charAt(right) == '0') {
                    zeros.add(right);
                    // If we have more than k zeros, remove the leftmost one
                    while (zeros.size() > k) {
                        ones -= (zeros.peekFirst() - lastZero - 1);  // Subtract ones between lastZero and the removed zero
                        lastZero = zeros.pollFirst();
                    }
                } else {
                    ones += 1;
                }
                
                // If we have exactly k zeros and at least k^2 ones
                if (zeros.size() == k && ones >= k * k) {
                    // Add the minimum of:
                    // 1. Number of ways to extend to the left (zeros.peekFirst() - lastZero)
                    // 2. Number of ways to extend to the right (ones - k^2 + 1)
                    result += Math.min(zeros.peekFirst() - lastZero, ones - k * k + 1);
                }
            }
        }
        
        // Handle all-ones substrings
        int i = 0;
        while (i < n) {
            if (s.charAt(i) == '0') {
                i += 1;
                continue;
            }
            int sz = 0;
            while (i < n && s.charAt(i) == '1') {
                sz += 1;
                i += 1;
            }
            // Add number of all-ones substrings
            result += (sz * (sz + 1)) / 2;
        }
        
        return result;
    }
}
