class Solution {
    public int distinctPoints(String s, int k) {
        int l = s.length();
        int dx[] = new int[l + 1];
        int dy[] = new int[l + 1];
        for (int i = 0; i < l; i++) {
            char ch = s.charAt(i);
            dx[i + 1] = dx[i];
            dy[i + 1] = dy[i];
            switch (ch) {
                case 'U':
                    dy[i + 1]++;
                    break;
                case 'D':
                    dy[i + 1]--;
                    break;
                case 'L':
                    dx[i + 1]--;
                    break;
                case 'R':
                    dx[i + 1]++;
                    break;
            }
        }

        Set<String> pos = new HashSet<>();

        for (int i = 0; i <= l - k; i++) {
            int x=dx[i]+(dx[l]-dx[i+k]);
            int y=dy[i]+(dy[l]-dy[i+k]);

            String p=x+","+y;

            pos.add(p);
        }
        return pos.size();
    }
}