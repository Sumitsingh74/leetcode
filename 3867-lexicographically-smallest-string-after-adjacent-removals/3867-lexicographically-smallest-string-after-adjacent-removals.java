class Solution {
    private boolean areConsecutive(char a, char b) {
        if (a == ' ' || b == ' ') return false;
        int diff = Math.abs(a - b);
        return diff == 1 || diff == 25;
    }

    private String dfs(int i, char lastChar, String s, boolean[][] canRemove, Map<String, String> memo) {
        int n = s.length();
        if (i >= n) return "";
        String key = i + "," + lastChar;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        TreeSet<Integer> candidates = new TreeSet<>();
        char minChar = Character.MAX_VALUE;

        // Check all possible k from i to n (inclusive)
        for (int k = i; k <= n; k++) {
            boolean valid = false;
            if (k == n) {
                valid = (i <= n - 1) && canRemove[i][n - 1];
            } else {
                if (k == i) {
                    valid = true;
                } else {
                    valid = canRemove[i][k - 1];
                }
            }
            if (!valid) continue;
             if (k == n) {
                String candidate = dfs(n, lastChar, s, canRemove, memo);
                if (candidate.compareTo("") < 0) {
                }
                if (candidates.isEmpty()) {
                    String currentMin = candidate;
                    if (currentMin.compareTo("") == 0) {
                        memo.put(key, "");
                        return "";
                    }
                }
                continue;
            }

            char current = s.charAt(k);

            if (current < minChar) {
                minChar = current;
                candidates.clear();
                candidates.add(k);
            } else if (current == minChar) {
                candidates.add(k);
            }
        }

        String minStr = null;
        for (int k : candidates) {
            String next = dfs(k + 1, s.charAt(k), s, canRemove, memo);
            String candidate = s.charAt(k) + next;
            if (minStr == null || candidate.compareTo(minStr) < 0) {
                minStr = candidate;
            }
        }
        if (i <= n - 1 && canRemove[i][n - 1]) {
            String candidate = "";
            if (minStr == null || candidate.compareTo(minStr) < 0) {
                minStr = candidate;
            }
        }


        memo.put(key, minStr);
        return minStr;
    }


    public String lexicographicallySmallestString(String s) {
         int n = s.length();
        if (n == 0) return "";

        boolean[][] canRemove = new boolean[n][n];

        // Initialize base cases
        for (int i = 0; i < n; i++) {
            Arrays.fill(canRemove[i], false);
            canRemove[i][i] = false; // single character cannot be removed
            if (i > 0) {
                canRemove[i][i - 1] = true; // empty substring
            }
        }

        for (int len = 2; len <= n; len++) {
			for (int i = 0; i <= n - len; i++) {
				int j = i + len - 1;
				char a = s.charAt(i);
				char b = s.charAt(j);
				if (areConsecutive(a, b)) {
					if ((i + 2 <=j &&canRemove[i + 1][j-1])||(i+1==j)) {
						canRemove[i][j] = true;
						continue;
					}
				}
				for (int k = i; k < j; k++) {
					if (canRemove[i][k] && canRemove[k + 1][j]) {
						canRemove[i][j] = true;
						break;
					}
				}
			}
		}

        Map<String, String> memo = new HashMap<>();
        String result = dfs(0, ' ', s, canRemove, memo);

        // Post-process to remove any remaining consecutive pairs
        return result;
    }
    
}