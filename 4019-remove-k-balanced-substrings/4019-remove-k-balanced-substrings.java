class Solution {
    public String removeSubstring(String s, int k) {
        Stack<Pair> st = new Stack<>();

        for (char c : s.toCharArray()) {
            if (st.isEmpty() || st.peek().ch != c) {
                st.push(new Pair(c, 1));
            } else {
                st.peek().count++;
            }
            if (st.size() >= 2) {
                Pair top = st.pop();
                Pair prev = st.pop();

                if (prev.ch == '(' && prev.count >= k && top.ch == ')' && top.count >= k) {
                    prev.count -= k;
                    top.count -= k;

                    if (prev.count > 0) st.push(prev);
                    if (top.count > 0) st.push(top);
                } else {
                    st.push(prev);
                    st.push(top);
                }
            }
        }
        StringBuilder res = new StringBuilder();
        for (Pair p : st) {
            for (int i = 0; i < p.count; i++) res.append(p.ch);
        }

        return res.toString();
    }

    public class Pair {
        char ch;
        int count;
        Pair(char ch, int count) {
            this.ch = ch;
            this.count = count;
        }
    }
}