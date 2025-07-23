class Solution {
    public int wordCount(String[] startWords, String[] targetWords) {
        int count = 0;
        int n = targetWords.length;
        var set = new HashSet<String>();
        for (String start : startWords) {
            char[] ch = start.toCharArray();
            Arrays.sort(ch);
            String s = new String(ch);
            set.add(s);
        }

        for (String target : targetWords) {
            char[] ch = target.toCharArray();
            Arrays.sort(ch);
            String s = new String(ch);
            for (int i = 0; i < s.length(); i++) {
                String modified = s.substring(0, i) + s.substring(i + 1);
                if (set.contains(modified)) {
                    count++;
                    break;
                }

            }
        }

        return count;
    }
}