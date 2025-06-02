class Solution {
public:
    int minMoves(vector<string>& c, int energy) {
        int n = c.size();
        int m = c[0].size();
        int si, sj;
        vector<pair<int, int>> l;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (c[i][j] == 'S') {
                    si = i;
                    sj = j;
                }
                if (c[i][j] == 'L')
                    l.push_back({i, j});
            }
        }
        int k = l.size();
        unordered_map<int, int> litmap;
        for (int j = 0; j < k; j++) {
            litmap[l[j].first * m + l[j].second] = j;
        }
        int f = (1 << k) - 1;
        queue<tuple<int, int, int, int, int>> q;
        q.emplace(si, sj, energy, 0, 0);
        static bool vis[20][20][1 << 10][51] = {};
        memset(vis, 0, sizeof(vis));
        vis[si][sj][0][energy] = true;
        vector<int> dx = {0, 0, 1, -1}, dy = {1, -1, 0, 0};
        while (!q.empty()) {
            auto [x, y, en, msk, step] = q.front();
            q.pop();
            if (msk == f) return step;
            if (en == 0 && c[x][y] != 'R') continue;
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i], ny = y + dy[i];
                int ne = en - 1;
                if (nx < 0 || ny < 0 || nx >= n || ny >= m || c[nx][ny] == 'X')
                    continue;
                if (c[nx][ny] == 'R') ne = energy;
                int id = nx * m + ny;
                int nmsk = msk;
                if (litmap.count(id)) {
                    nmsk |= (1 << litmap[id]);
                }
                if (!vis[nx][ny][nmsk][ne]) {
                    vis[nx][ny][nmsk][ne] = true;
                    q.emplace(nx, ny, ne, nmsk, step + 1);
                }
            }
        }
        return -1;
    }
};