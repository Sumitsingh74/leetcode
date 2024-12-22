class Solution 
{
    public int beautifulSplits(int[] nums) 
    {
        int n = nums.length;
        if (n < 3)
        {
            return 0; // We need at least 3 elements to make a valid split
        }
        
        long mod = 1_000_000_007;
        long base = 31;
        
        // Compute prefix hashes
        long[] prefixHash = new long[n + 1]; // Hash for prefix [0..i)
        long[] pow = new long[n + 1]; // Store powers of base
        
        pow[0] = 1;
        for (int i = 0; i < n; i++) 
        {
            pow[i + 1] = (pow[i] * base) % mod;
            prefixHash[i + 1] = (prefixHash[i] * base + nums[i]) % mod;
        }

        int count = 0;

        // Iterate over possible i and j such that 1 <= i < j < n
        for (int i = 1; i < n - 1; i++) 
        {
            for (int j = i + 1; j < n; j++) 
            {
                boolean valid = false;
                
                // Check if nums1 is a prefix of nums2
                if (isPrefix(prefixHash, 0, i, i, j, mod, pow)) 
                {
                    valid = true;
                }
                
                // Check if nums2 is a prefix of nums3
                if (!valid && isPrefix(prefixHash, i, j, j, n, mod, pow)) 
                {
                    valid = true;
                }

                if (valid) 
                {
                    count++;
                }
            }
        }

        return count;
    }

    // Helper function to compare hashes of two subarrays
    private boolean isPrefix(long[] hash, int start1, int end1, int start2, int end2, long mod, long[] pow) 
    {
        int len1 = end1 - start1;
        int len2 = end2 - start2;

        if (len1 > len2)
        {
            return false;
        }

        long hash1 = (hash[end1] - (hash[start1] * pow[len1]) % mod + mod) % mod;
        long hash2 = (hash[start2 + len1] - (hash[start2] * pow[len1]) % mod + mod) % mod;

        return hash1 == hash2;
    }
}