class Solution {
public:
    vector<string> partitionString(string s) {
        int n = s.size();
        vector<string> segments;
        unordered_set<string> seen; // to track already used substrings
        int i = 0;

        while (i < n) {
            bool found = false;
            string cur;
            for (int j = i; j < n; j++) {
                cur.push_back(s[j]);
                if (seen.count(cur) == 0) {
                    // If the current substring hasn't been seen
                    found = true;
                    segments.push_back(cur);
                    seen.insert(cur);
                    i = j + 1;
                    break; // break as soon as a unique substring is found
                }
            }
            if (!found) break; // No more unique substrings can be formed
        }

        return segments;
    }
};