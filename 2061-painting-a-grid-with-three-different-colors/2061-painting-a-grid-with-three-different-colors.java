
class Solution {
    private static final int MOD = 1_000_000_007;

    public int colorTheGrid(int m, int n) {
        List<List<Integer>> states = new ArrayList<>();
        generateStates(m, new ArrayList<>(), states);

        int size = states.size();
        List<List<Integer>> transitions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            transitions.add(new ArrayList<>());
            for (int j = 0; j < size; j++) {
                if (isCompatible(states.get(i), states.get(j))) {
                    transitions.get(i).add(j);
                }
            }
        }

        long[] dp = new long[size];
        Arrays.fill(dp, 1);

        for (int col = 1; col < n; col++) {
            long[] newDp = new long[size];
            for (int i = 0; i < size; i++) {
                for (int j : transitions.get(i)) {
                    newDp[j] = (newDp[j] + dp[i]) % MOD;
                }
            }
            dp = newDp;
        }

        long result = 0;
        for (long val : dp) {
            result = (result + val) % MOD;
        }
        return (int) result;
    }

    private void generateStates(int m, List<Integer> curr, List<List<Integer>> states) {
        if (curr.size() == m) {
            states.add(new ArrayList<>(curr));
            return;
        }
        for (int color = 1; color <= 3; color++) {
            if (curr.isEmpty() || curr.get(curr.size() - 1) != color) {
                curr.add(color);
                generateStates(m, curr, states);
                curr.remove(curr.size() - 1);
            }
        }
    }

    private boolean isCompatible(List<Integer> a, List<Integer> b) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals(b.get(i))) return false;
        }
        return true;
    }
}