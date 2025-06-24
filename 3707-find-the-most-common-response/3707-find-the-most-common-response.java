class Solution {
    public String findCommonResponse(List<List<String>> responses) {
        Map<String, Integer> map = new TreeMap<>();
        int n = responses.size();
        for (int i = 0; i < n; i++) {
            HashSet<String> set = new HashSet<>();
            for (int j = 0; j < responses.get(i).size(); j++)
                set.add(responses.get(i).get(j));
            for (String it : set)
                map.put (it, map.getOrDefault(it, 0) + 1);
        }
        String ans = "";
        int max = 0;
        for (Map.Entry<String, Integer> em : map.entrySet()) {
            if (em.getValue() > max) {
                ans = em.getKey();
                max = em.getValue();
            }
        }
        return ans;
    }
}