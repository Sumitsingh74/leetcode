 class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        HashMap<String, Integer> hm = new HashMap<>();

        Queue<Pair> q = new ArrayDeque<>();
        q.add(new Pair(beginWord, 1));

        HashSet<String> visited = new HashSet<>();
        visited.add(beginWord);

        while (!q.isEmpty()) {
            Pair rem = q.remove();
            String word = rem.word;
            int steps = rem.steps;
            hm.put(word, steps);

            if (endWord.equals(word)) break;

            for (String trav : wordList) {
                if (!visited.contains(trav) && isDiffOne(word, trav)) {
                    visited.add(trav);
                    q.add(new Pair(trav, steps + 1));
                }
            }
        }

        List<String> arl = new ArrayList<>();
        List<List<String>> ans = new ArrayList<>();

        if (!hm.containsKey(endWord)) return ans;

        dfs(endWord, beginWord, wordList, hm, arl, ans);

        return ans;
    }

    public void dfs(String end, String begin, List<String> wordList, HashMap<String, Integer> hm, List<String> arl, List<List<String>> ans) {
        if (end.equals(begin)) {
            arl.add(0, begin);
            List<String> temp = new ArrayList<>(arl);
            ans.add(temp);
            arl.remove(0);
            return;
        }

        arl.add(0, end);
        for (String trav : hm.keySet()) {
            if (hm.get(trav) < hm.get(end) && isDiffOne(end, trav)) dfs(trav, begin, wordList, hm, arl, ans);
        }
        arl.remove(0);
    }

    public boolean isDiffOne(String s1, String s2) {
        if (s1.equals(s2)) return false;

        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) count++;
            if (count >= 2) return false;
        }

        return count == 1;
    }

    class Pair {
        String word;
        int steps;

        Pair(String word, int steps) {
            this.word = word;
            this.steps = steps;
        }
    }
}