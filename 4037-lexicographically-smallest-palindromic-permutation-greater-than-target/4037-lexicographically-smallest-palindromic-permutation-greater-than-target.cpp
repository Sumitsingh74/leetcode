class Solution {
public:
    string lexPalindromicPermutation(string s, string t) {
        int n = s.size();
        
        vector<int> freq(26);
        for (char c : s) {
            freq[c-'a']++;
        }
        int odd_freq = -1;
        for (int i=0; i<26; i++) {
            if (freq[i] & 1) {
                if (odd_freq != -1) return "";
                else odd_freq = i;
            }
        }
        string res(n, '\0');
        if (odd_freq != -1) {
            res[n/2] = odd_freq + 'a';
            freq[odd_freq]--;
        }

        auto fill_rest = [&](int pos) {
            for (int i=0; i<26; i++) {
                while (freq[i]) {
                    res[pos] = i+'a';
                    res[n-pos-1] = i+'a';
                    freq[i] -= 2;
                    pos++;
                }
            }  
        };
        
        int i = 0;
        while (i < n / 2) {
            char need = t[i];
            if (freq[need-'a'] > 0) {
                res[i] = need;
                res[n-i-1] = need;
                freq[need-'a'] -= 2;
                i++;
                continue;
            }

            int pick = -1;
            for (int ch = need-'a'+1; ch < 26; ch++) {
                if (freq[ch] > 0) {
                    pick = ch;
                    break;
                }
            }

            if (pick != -1) {
                res[i] = pick + 'a';
                res[n-i-1] = pick + 'a';
                freq[pick] -= 2;
                fill_rest(i+1);
                return res;
            }

            while (i > 0) {
                i--;
                freq[res[i]-'a'] += 2;

                int pick2 = -1;
                for (int ch = t[i]-'a'+1; ch < 26; ch++) {
                    if (freq[ch] > 0) {
                        pick2 = ch;
                        break;
                    }
                }

                if (pick2 != -1) {
                    res[i] = pick2 + 'a';
                    res[n-i-1] = pick2 + 'a';
                    freq[pick2] -= 2;
                    fill_rest(i+1);
                    return res;
                }
            }

            return "";
        }

        for (int j=n/2; j<n; j++) {
            if (res[j] < t[j]) break;
            if (res[j] > t[j]) return res;
        }

        while (i > 0) {
            i--;
            freq[res[i]-'a'] += 2;

            int pick = -1;
            for (int ch=t[i]-'a'+1; ch<26; ch++) {
                if (freq[ch] > 0) {
                    pick = ch;
                    break;
                }
            }

            if (pick != -1) {
                res[i] = pick+'a';
                res[n-i-1] = pick+'a';
                freq[pick] -= 2;
                fill_rest(i+1);
                return res;
            }
        }

        return "";
    }
};