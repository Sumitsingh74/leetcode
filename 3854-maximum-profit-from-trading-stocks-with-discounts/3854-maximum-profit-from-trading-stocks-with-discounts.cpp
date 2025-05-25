#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    vector<int> fut,pre;
    vector<vector<int>> adj;
    int bud;
    
    vector<vector<int>> dp[2];
    vector<bool> calculated[2];
    
    //dp[i]
    //dp[i] = INF
    //if(dp[i] != INF) return dp[i]

    vector<int> ans(bool taken, int v) //returns the vector of costs and values
    //v[i] = maximum value i can get in the subtree of v if i spend i coins
    {
        if(calculated[taken][v]) {
            return dp[taken][v];
        }

        vector<int> an(bud + 1);
        int numchild = adj[v].size();
        int curprice = pre[v];
        if(taken) curprice = curprice / 2;
        
        //vth employee buys
        if(curprice <= bud)
        {
            vector<vector<int>> childans(numchild);
            for(int i = 0; i < numchild; i++) {
                childans[i] = ans(true, adj[v][i]);
            }

            vector<int> tempans(bud + 1, INT_MIN);
            //tempans[i] = max selling price if you spend exactly i in v's subtree
            tempans[curprice] = fut[v];

            for(int i = 0; i < numchild; i++) {
                vector<int> newans = tempans;
                for(int j = 0; j <= bud; j++) {
                    if(childans[i][j] == INT_MIN) continue;
                    for(int k = 0; k <= bud; k++) {
                        if(tempans[k] == INT_MIN) continue;
                        if(k + j <= bud) {
                            newans[k + j] = max(newans[k + j], tempans[k] + childans[i][j]);
                        }
                    }
                }
                tempans = newans;
            }

            an = tempans;
        }

        //vth employee doesn't buy
        {
            vector<vector<int>> childans(numchild);
            for(int i = 0; i < numchild; i++) {
                childans[i] = ans(false, adj[v][i]);
            }

            vector<int> tempans(bud + 1, INT_MIN);
            //tempans[i] = max selling price if you spend exactly i in v's subtree
            tempans[0] = 0;

            for(int i = 0; i < numchild; i++) {
                vector<int> newans(bud + 1, INT_MIN);
                for(int j = 0; j <= bud; j++) {
                    if(childans[i][j] == INT_MIN) continue;
                    for(int k = 0; k <= bud; k++) {
                        if(tempans[k] == INT_MIN) continue;
                        //we don't let the ith child take any budget
                        newans[k] = max(newans[k], tempans[k]);
                        //we let the ith child take j budget and allocate k budget to the rest
                        if(k + j <= bud) {
                            newans[k + j] = max(newans[k + j], tempans[k] + childans[i][j]);
                        }
                    }
                }
                swap(newans, tempans);
            }

            for(int i = 0; i <= bud; i++) {
                an[i] = max(an[i], tempans[i]);
            }
        }

        calculated[taken][v] = true;
        dp[taken][v] = an;

        return an;
    }
    
    int maxProfit(int n, vector<int>& present, vector<int>& future, vector<vector<int>>& hierarchy, int budget) {
        adj.assign(n,vector<int>());
        fut = future;
        pre = present;
        bud = budget;
        
        for(auto& edge: hierarchy) {
            adj[edge[0]-1].push_back(edge[1]-1);
        }

        for(int i = 0; i < 2; i++) {
            dp[i].assign(n,vector<int>(budget + 1,INT_MIN));
            calculated[i].assign(n,false);
        }

        vector<int> an = ans(false,0);
        int ans = 0;
        //an[i] = maximum selling price if you spend i coins
        for(int i = 0; i <= bud; i++) {
            if(an[i] != INT_MIN) {
                ans = max(ans, an[i] - i);
            }
        }
        return ans;
    }
};