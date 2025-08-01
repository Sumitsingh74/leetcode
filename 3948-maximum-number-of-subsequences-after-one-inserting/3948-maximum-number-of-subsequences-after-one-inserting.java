class Solution {
    public long numOfSubsequences(String s) {
        int n = s.length();
        long[] prefix = new long[n + 1]; 
        long[] suffix = new long[n + 1]; 

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == 'L') {
                prefix[i + 1] = 1;
            }
            prefix[i + 1] += prefix[i];
        }

        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) == 'T') {
                suffix[i] = 1;
            }
            suffix[i] += suffix[i + 1];
        }

        long resWithC = 0;
        long bestPosForC = 0;
        long resWithL = 0;
        long resWithT = 0;

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == 'C') {
                resWithC += prefix[i] * suffix[i + 1];
                resWithL += (prefix[i] + 1) * suffix[i + 1];
                resWithT += prefix[i] * (suffix[i + 1] + 1);
            } else {
                bestPosForC = Math.max(prefix[i] * suffix[i], bestPosForC);
            }
        }

        resWithC += bestPosForC;
        return Math.max(resWithL, Math.max(resWithT, resWithC));
    }
}