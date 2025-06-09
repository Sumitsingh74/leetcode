#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    int n;
    vector<int> memo;

    // Count operations needed to turn s1 into s2 using replace/swap
    int countOps(const string& s1, const string& s2) {
        int save[26][26] = {};
        int ops = 0;

        for (int i = 0; i < s1.size(); i++) {
            if (s1[i] == s2[i]) continue;

            if (save[s2[i] - 'a'][s1[i] - 'a'] > 0) {
                save[s2[i] - 'a'][s1[i] - 'a']--;
            } else {
                save[s1[i] - 'a'][s2[i] - 'a']++;
                ops++;
            }
        }

        return ops;
    }

    int dfs(int i, string& word1, string& word2) {
        if (i == n) return 0;

        if (memo[i] != -1) return memo[i];

        int minOps = INT_MAX;

        for (int j = i; j < n; j++) {
            string s1 = word1.substr(i, j - i + 1);
            string s2 = word2.substr(i, j - i + 1);

            // Case 1: without reverse
            int op1 = countOps(s1, s2);

            // Case 2: with reverse
            reverse(s1.begin(), s1.end());
            int op2 = countOps(s1, s2);  // Need to do +1 for reverse operation

            // Recurse for remaining part of the string
            int remainingPart = dfs(j + 1, word1, word2);

            minOps = min( minOps, remainingPart+min(op1 , 1+op2));
        }

        return memo[i] = minOps;
    }

    int minOperations(string word1, string word2) {
        n = word1.size();
        memo.assign(n, -1);
        return dfs(0, word1, word2);
    }
};