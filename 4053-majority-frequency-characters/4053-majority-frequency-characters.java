class Solution {
    public String majorityFrequencyGroup(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray())
            freq[c - 'a']++;
        int[] unique = new int[101];
        int maj = 1;
        for (int i = 0; i < 26; i++) {
            if (freq[i] == 0) continue;
            unique[freq[i]]++;
            if (unique[freq[i]] > unique[maj] || 
               (unique[freq[i]] == unique[maj] && freq[i] > maj)) {
                maj = freq[i];
            }
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 26; i++)
            if (freq[i] == maj)
                res.append((char) (i + 'a'));
        return res.toString();
    }
}