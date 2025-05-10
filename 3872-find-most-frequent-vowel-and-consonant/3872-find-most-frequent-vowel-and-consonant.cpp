class Solution {
public:
    int maxFreqSum(string s) {
        // Define the vowel set
        vector<bool> vowel(26, false);
        for (char c : {'a', 'e', 'i', 'o', 'u'}) {
            vowel[c - 'a'] = true;
        }
        
        vector<int> vow(26, 0);
        vector<int> cons(26, 0);
        
        for (char ch : s) {
            int idx = ch - 'a';
            if (vowel[idx]) {
                vow[idx]++;
            } else {
                cons[idx]++;
            }
        }
        
        int maxVow = 0, maxCons = 0;
        
        for (int i = 0; i < 26; i++) {
            maxVow = max(maxVow, vow[i]);
            maxCons = max(maxCons, cons[i]);
        }
        
        return maxVow + maxCons;
    }
};
