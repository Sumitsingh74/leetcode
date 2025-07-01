class Solution {
    public int possibleStringCount(String word) {
         int n = word.length();
        if (n < 2) return 1;

        int count = 0;
        
        // Start from the second character and compare with the previous one
        for (int i = 1; i < n; i++) {
            if (word.charAt(i) == word.charAt(i - 1)) {
                count++;
            }
        }
        
        return count+1;
    }
}