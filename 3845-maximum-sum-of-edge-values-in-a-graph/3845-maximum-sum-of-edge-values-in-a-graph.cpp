class DSU{
  public:
    vector<int> parent,size,edges;
    DSU(int n){
        parent.resize(n);
        size.resize(n);
        edges.resize(n);
        for(int i=0;i<n;i++){
            parent[i] = i;
            size[i] = 1;
            edges[i] = 0;
        }
        
    }
    void connect(int u,int v){
        int a = findParent(u);
        int b = findParent(v);
        if(a == b){
            edges[a]++;
            return;
        }
        if(size[a] > size[b]){
            parent[b] = a;
            size[a] += size[b];
            edges[a] += edges[b] + 1;
        }else{
            parent[a] = b;
            size[b] += size[a];
            edges[b] += edges[a] + 1;
        }
    }
    int findParent(int u){
        if(u == parent[u]) return u;
        return parent[u] = findParent(parent[u]);
    }
};
class Solution {
public:
    long long maxScore(int n, vector<vector<int>>& edges) {
        // 42 35 30 3 12 8 2 ---> 
        // 42 30 20 28 6 3 2

        unordered_map<int,bool> vis;
        vector<vector<int>> adj(n);
        DSU d(n);
        for(auto i:edges){
            int u = i[0];
            int v = i[1];
            int a = d.findParent(u);
            int b = d.findParent(v);
            

            d.connect(u,v);
        }
        vector<int> v1,v2;
        for(int i=0;i<n;i++){
            int a = d.parent[i];
            if(a == i){
                int x = d.size[i];
                int y = d.edges[i];
                if(x == y) v1.push_back(x);
                else v2.push_back(x);
            }
            
        }
        sort(v1.begin(),v1.end());
        sort(v2.rbegin(),v2.rend());
        long long m = n;
        long long ans = 0;
        for (auto i : v1) {
            if (i == 1) continue;
            long long x = i;
            long long y = m - x + 1;
            long long sum = 0;

            for (long long j = y + 2; j <= m; j += 2)
                sum += j * (j - 2);
            for (long long j = y + 3; j <= m; j += 2)
                sum += j * (j - 2);

            sum += m * (m - 1);
            sum += y * (y + 1);
            ans += sum;
            m -= x;
        }

        for (auto i : v2) {
            if (i == 1) continue;
            long long x = i;
            long long y = m - x + 1;
            long long sum = 0;

            for (long long j = y + 2; j <= m; j += 2)
                sum += j * (j - 2);
            for (long long j = y + 3; j <= m; j += 2)
                sum += j * (j - 2);

            sum += m * (m - 1);
            ans += sum;
            m -= x;
        }

        return ans;
        
    }
};

// 42 35 12 3 8
// 42 30 28 6 3