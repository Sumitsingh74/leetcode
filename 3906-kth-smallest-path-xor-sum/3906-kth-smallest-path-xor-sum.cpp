#include <bits/stdc++.h>
#include <ext/pb_ds/assoc_container.hpp>
#include <ext/pb_ds/tree_policy.hpp>
using namespace std;
using namespace __gnu_pbds;
using ost = tree<int, null_type, less<int>, rb_tree_tag, tree_order_statistics_node_update>;

class Solution {
public:
    map<int, vector<int>> umap;
    map<pair<int,int>, int> ans;

    ost* dfs(int node, int parent, int prefixXor, vector<vector<int>>& adj, vector<int>& val) {
        int currXor = prefixXor ^ val[node];
        ost* mySet = new ost();
        mySet->insert(currXor);
        for (int c : adj[node]) {
            if (c == parent) continue;
            ost* childSet = dfs(c, node, currXor, adj, val);
            if (childSet->size() > mySet->size()) 
                swap(mySet, childSet);
            for (auto x : *childSet) 
                mySet->insert(x);
        }
        for (int k : umap[node]) {
            ans[{node,k}] = k > (int)mySet->size() ? -1 : *mySet->find_by_order(k-1);
        }
        return mySet;
    }

    vector<int> kthSmallest(vector<int>& par, vector<int>& val, vector<vector<int>>& queries) {
        int n = val.size();
        umap.clear();
        ans.clear();
        vector<vector<int>> adj(n);
        for (auto &q : queries) umap[q[0]].push_back(q[1]);
        for (int i = 0; i < n; ++i) {
            if (par[i] != -1) 
                adj[par[i]].push_back(i);
        }
        dfs(0, -1, 0, adj, val);
        vector<int> res;
        res.reserve(queries.size());
        for (auto &q : queries) 
            res.push_back(ans[{q[0], q[1]}]);
        return res;
    }
};