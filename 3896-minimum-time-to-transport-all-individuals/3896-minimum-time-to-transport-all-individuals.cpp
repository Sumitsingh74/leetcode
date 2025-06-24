class Solution {
public:
    double dp[4096][5][2][4] = {};
int max_time[4096] = {};
double dfs(uint mask, int st, bool across, int singles, int k, int m, vector<int>& time, vector<double>& mul) {
    if (mask == 0)
        return 0;
    if (singles > 3)
        return DBL_MAX;
    if (dp[mask][st][across][singles] == 0) {
        double res = DBL_MAX;
        if (!across) {
            for (uint i = 1; i <= mask; ++i) {
                if ((i & mask) == i && popcount(i) <= k) {
                    if (max_time[i] == 0) {
                        for (int j = 0; j < time.size(); ++j)
                        if ((1 << j) & i)
                            max_time[i] = max(max_time[i], time[j]);                             
                    }
                    double took = mul[st] * max_time[i];
                    int next_st = (st + (int)floor(took)) % m;
                    res = min(res, took + dfs(mask - i, next_st, !across, singles + (popcount(i) == 1), k, m, time, mul));
                }
            }
        }
        else
            for (int i = 0; i < time.size(); ++i)
                if (((1 << i) & mask) == 0) {
                    double took = mul[st] * time[i];
                    int next_st = (st + (int)floor(took)) % m;
                    res = min(res, took + dfs(mask + (1 << i), next_st, !across, singles, k, m, time, mul));
                }
        dp[mask][st][across][singles] = res;
    }
    return dp[mask][st][across][singles];
}
double minTime(int n, int k, int m, vector<int>& time, vector<double>& mul) {                       
    if (k == 1 && n > 1)
        return -1;
    return dfs((1 << n) - 1, 0, false, 0, k, m, time, mul);
}
};