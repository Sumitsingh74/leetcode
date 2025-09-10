import java.util.*;

class Solution {
    public int minimumTeachings(int n, int[][] languages, int[][] friendships) {
        int m = languages.length;
        List<Set<Integer>> known = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            Set<Integer> s = new HashSet<>();
            for (int lang : languages[i]) s.add(lang);
            known.add(s);
        }

        Set<Integer> need = new HashSet<>();
        for (int[] f : friendships) {
            int u = f[0] - 1, v = f[1] - 1;
            Set<Integer> su = known.get(u), sv = known.get(v);
            boolean ok = false;
            if (su.size() < sv.size()) {
                for (int lang : su) { if (sv.contains(lang)) { ok = true; break; } }
            } else {
                for (int lang : sv) { if (su.contains(lang)) { ok = true; break; } }
            }
            if (!ok) { need.add(u); need.add(v); }
        }

        if (need.isEmpty()) return 0;

        int ans = Integer.MAX_VALUE;
        for (int lang = 1; lang <= n; lang++) {
            int cnt = 0;
            for (int person : need) {
                if (!known.get(person).contains(lang)) cnt++;
            }
            ans = Math.min(ans, cnt);
        }
        return ans;
    }
}
