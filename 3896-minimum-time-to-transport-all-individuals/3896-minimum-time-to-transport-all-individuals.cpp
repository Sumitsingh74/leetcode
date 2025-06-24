double dp[1 << 12][5][2];
bool inqueue[1 << 12][5][2];
int mxTime[1 << 12];
class Solution {
public:
    double minTime(int n, int k, int m, vector<int>& time, vector<double>& mul) {
        int fmask = (1 << n) - 1;
        for (int i = 0; i < (1 << n); i++)
            for (int j = 0; j < m; j++)
                for (int k = 0; k < 2; k++)
                    dp[i][j][k] = DBL_MAX, inqueue[i][j][k] = false;
        for (int mask = 1; mask <= fmask; mask++) {
            int cur = -1;
            for (int i = 0; i < n; i++) if ((mask >> i) & 1) cur = max(cur, time[i]);
            mxTime[mask] = cur;
        }
        dp[0][0][0] = 0;
        queue<pair<int,pair<int,int>>> q;
        q.push({0, {0, 0}});
        inqueue[0][0][0] = 1;
        while (q.size()) {
            auto p = q.front();
            q.pop();
            int state, stage, side;
            pair<int,int> p2;
            tie(state, p2) = p;
            tie(stage, side) = p2;

            inqueue[state][stage][side] = 0;
            if (side == 0) {
                for (int mask = 1; mask <= fmask; mask++) {
                    if ((mask & state) || __builtin_popcount(mask) > k) continue;
                    double cost = mxTime[mask] * mul[stage];
                    int nmask = state ^ mask;
                    int nstage = (stage + ((int) cost)) % m;
                    if (dp[nmask][nstage][1] > dp[state][stage][0] + cost) {
                        dp[nmask][nstage][1] = dp[state][stage][0] + cost;
                        if (!inqueue[nmask][nstage][1]) {
                            inqueue[nmask][nstage][1] = 1;
                            q.push({nmask, {nstage, 1}});
                        }
                    }
                }
            } else {
                for (int i = 0; i < n; i++) if ((state >> i) & 1) {
                    double cost = time[i] * mul[stage];
                    int nmask = state ^ (1 << i);
                    int nstage = (stage + ((int) cost)) % m;
                    if (dp[nmask][nstage][0] > dp[state][stage][1] + cost) {
                        dp[nmask][nstage][0] = dp[state][stage][1] + cost;
                        if (!inqueue[nmask][nstage][0]) {
                            inqueue[nmask][nstage][0] = 1;
                            q.push({nmask, {nstage, 0}});
                        }
                    }
                }
            }
        }
        double ans = DBL_MAX;
        for (int j = 0; j < m; j++) ans = min(ans, dp[fmask][j][1]);
        return ans == DBL_MAX ? -1 : ans;
    }
};