public class Solution 
{
    public int minimumDeletions(String word, int k) 
    {
        // Step 1: Count frequency of each character
        int[] count = new int[26];
        for (char c : word.toCharArray()) 
        {
            count[c - 'a']++;
        }

        // Step 2: Store non-zero frequencies in a list
        List<Integer> freqs = new ArrayList<>();
        for (int c : count) 
        {
            if (c > 0) freqs.add(c);
        }

        // Step 3: Sort the frequencies
        Collections.sort(freqs);
        int n = freqs.size();

        // Step 4: Create prefix sum array
        int[] prefix = new int[n + 1]; // prefix[i] = sum of first i freqs
        for (int i = 0; i < n; i++) 
        {
            prefix[i + 1] = prefix[i] + freqs.get(i);
        }

        int total = prefix[n]; // total sum of frequencies
        int minDeletions = Integer.MAX_VALUE;

        // Step 5: Try each frequency as the target (minimum allowed frequency)
        for (int i = 0; i < n; i++) 
        {
            int target = freqs.get(i);
            int maxAllowed = target + k;

            // Step 6: Find first index j such that freqs[j] > maxAllowed
            int left = i;
            int right = n;
            while (left < right) 
            {
                int mid = (left + right) / 2;
                if (freqs.get(mid) <= maxAllowed) 
                {
                    left = mid + 1;
                }
                else 
                {
                    right = mid;
                }
            }
            int j = left;

            // Step 7: Calculate deletions
            int deleteBelow = prefix[i]; // delete all smaller than target
            int deleteAbove = total - prefix[j] - (maxAllowed * (n - j)); // reduce larger ones

            int deletions = deleteBelow + deleteAbove;

            // Step 8: Track minimum deletions
            minDeletions = Math.min(minDeletions, deletions);
        }

        // Step 9: Return the minimum deletions found
        return minDeletions;
    }
}