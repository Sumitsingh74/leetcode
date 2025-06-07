class Solution {
    static const int MOD = 1e9 + 7;
    vector<vector<int>> tree;
    vector<int> dp;
    unordered_map<int, vector<int>> nodeValues;
    int memo[501][1 << 10];
    bool seen[501][1 << 10];

public:
    int goodSubtreeSum(vector<int>& values, vector<int>& parent) {
        int n = values.size();
        dp.resize(n, 0);
        tree.resize(n);

        for (int i = 1; i < n; ++i) {
            tree[i].push_back(parent[i]);
            tree[parent[i]].push_back(i);
        }

        traverse(0, -1, values);

        long long total = 0;
        for (int val : dp) {
            total = (total + val) % MOD;
        }

        return static_cast<int>(total);
    }

private:
    void traverse(int node, int parent, const vector<int>& values) {
        vector<int> combined = {values[node]};
        for (int child : tree[node]) {
            if (child == parent) continue;
            traverse(child, node, values);
            combined.insert(combined.end(), nodeValues[child].begin(), nodeValues[child].end());
        }
        nodeValues[node] = combined;
        dp[node] = findMaxSubset(combined);
    }

    int findMaxSubset(const vector<int>& nums) {
        int n = nums.size();
        memset(memo, -1, sizeof(memo));
        memset(seen, 0, sizeof(seen));
        return solve(0, 0, nums);
    }

    int solve(int index, int usedMask, const vector<int>& nums) {
        if (index == nums.size()) return 0;
        if (seen[index][usedMask]) return memo[index][usedMask];

        int exclude = solve(index + 1, usedMask, nums);

        int number = nums[index];
        int tmp = number;
        int newMask = usedMask;
        bool ok = true;

        while (tmp > 0) {
            int digit = tmp % 10;
            if ((newMask >> digit) & 1) {
                ok = false;
                break;
            }
            newMask |= (1 << digit);
            tmp /= 10;
        }

        int include = 0;
        if (ok) {
            include = number + solve(index + 1, newMask, nums);
        }

        seen[index][usedMask] = true;
        return memo[index][usedMask] = max(include, exclude);
    }
};
