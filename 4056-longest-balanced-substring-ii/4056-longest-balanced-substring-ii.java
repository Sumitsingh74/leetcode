import java.util.*;

class Solution {
    private int n; 
    private long key(long[] cnt) {
        return cnt[0] + cnt[1] * (n + 1L) + cnt[2] * (n + 1L) * (n + 1L);
    }

    public int longestBalanced(String s) {
        n = s.length();
        long[] cnt = new long[3];      
        long[] cur = new long[3];
        int ans = 0; 
        Map<Long, Integer>[] mp = new HashMap[8];
        for (int j = 0; j < 8; j++) {
            mp[j] = new HashMap<>();
            mp[j].put(0L, -1);          
        }

        for (int i = 0; i < n; i++) {
            int x = s.charAt(i) - 'a';
            cnt[x]++;
 
            for (int m = 7; m > 0; m--) {
                long mind = n;
                for (int b = 0; b < 3; b++) {
                    int bit = 1 << b;
                    if ((bit & m) != 0) mind = Math.min(mind, cnt[b]);
                    cur[b] = cnt[b];
                }

                for (int b = 0; b < 3; b++) {
                    int bit = 1 << b;
                    if ((bit & m) != 0) cur[b] -= mind;
                }

                long k = key(cur);
                if (mp[m].containsKey(k)) {
                    ans = Math.max(ans, i - mp[m].get(k));
                } else {
                    mp[m].put(k, i);
                }
            }
        }

        return ans;
    }
}