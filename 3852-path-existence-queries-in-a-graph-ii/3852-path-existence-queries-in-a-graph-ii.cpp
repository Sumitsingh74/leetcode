
class Solution {
public:
    vector<int> pathExistenceQueries(int n, vector<int>& nums, int maxDiff, vector<vector<int>>& queries) {
        vector<pair<int, int>> newNums(n);
        for (int i = 0; i < n; i++) {
            newNums[i] = { nums[i], i };
        }
        sort(newNums.begin(), newNums.end());

        vector<int> getI(n);
        for (int i = 0; i < n; i++) {
            getI[newNums[i].second] = i;
        }

        int st[100005][18];
        int l = 0;
        for (int r = 0; r < n; r++) {
            while (newNums[r].first - newNums[l].first > maxDiff) {
                st[l][0] = r - 1;
                l++;
            }
        }
        while (l < n) {
            st[l][0] = n - 1;
            l++;
        }

        for (int j = 1; j < 18; j++) {
            for (int i = 0; i < n; i++) {
                st[i][j] = st[st[i][j - 1]][j - 1];
            }
        }

        vector<int> ans(queries.size(), -1);
        for (int i = 0; i < queries.size(); i++) {
            int a = getI[queries[i][0]];
            int b = getI[queries[i][1]];
            if (a > b) swap(a, b);
            if (a == b) {
                ans[i] = 0;
                continue;
            }

            int curr = a;
            int steps = 0;
            for (int j = 17; j >= 0; j--) {
                if (st[curr][j] < b) {
                    curr = st[curr][j];
                    steps += (1 << j);
                }
            }

            if (st[curr][0] >= b) {
                ans[i] = steps + 1;
            } else {
                ans[i] = -1;
            }
        }

        return ans;
    }
};