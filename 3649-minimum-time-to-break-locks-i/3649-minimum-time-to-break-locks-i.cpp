class Solution {
public:
    int ans = 1e9;
    void f(vector<int>& s, int mask, int x, int k, int temp) {
        int n = s.size();
        if(mask == (1<<n)-1) {
            ans = min(ans, temp);
            return;
        }
        int sum = x, add = 0;
        for(int i=0; i<n; i++) {
            if(mask & (1<<i)) continue;
            add = (s[i] + x - 1)/x;
            f(s, mask | (1<<i), x + k, k, temp + add);
        }
    }
    int findMinimumTime(vector<int>& strength, int K) {
        sort(strength.begin(), strength.end());
        f(strength, 0, 1, K, 0);
        return ans;
    }
};