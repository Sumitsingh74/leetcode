class Solution {
    // maximum lazy update
    class SegmentTree{
        int n;
        vector<int> nodes;
        vector<int> lazy;
        // 0 index -> 2*n+1 and 2*n+2
        void build(int cur, int left, int right, vector<int>& init){
            if(left == right){ // base
                nodes[cur] = init[left];
                return;
            }
            int mid = (left + right) / 2;
            build(2*cur+1, left, mid, init);
            build(2*cur+2, mid+1, right, init);
            nodes[cur] = max(nodes[2*cur+1], nodes[2*cur+2]);
        }
        void push(int cur, int left, int right){
            if(lazy[cur] != 0){ // need update
                nodes[cur] += lazy[cur];
                if(left != right){
                    lazy[2*cur+1] += lazy[cur];
                    lazy[2*cur+2] += lazy[cur];
                }
                lazy[cur] = 0;
            }
        }
        void update(int cur, int qL, int qR, int val, int l, int r){
            push(cur, l, r);
            if(r < qL || qR < l) return;
            if(qL <= l && r <= qR){
                lazy[cur] += val;
                push(cur, l ,r);
                return;
            }
            int mid = (l + r)/2;
            update(2*cur+1, qL, qR, val, l, mid);
            update(2*cur+2, qL, qR, val, mid+1, r);
            nodes[cur] = max(nodes[2*cur+1], nodes[2*cur+2]);
        }
    public:
        SegmentTree(int n, vector<int>& init){
            this->n = n;
            nodes.resize(4*n);
            lazy.resize(4*n, 0);
            build(0, 0, n-1, init);
        }
        int query(){ // special case: only need the max 0~n-1
            push(0, 0, n-1);
            return nodes[0];
        }
        void update(int qL, int qR, int val){
            update(0, qL, qR, val, 0, n-1);
        }
    };
    const int MAX_NUM = 100000;
public:
    vector<int> maximumCount(vector<int>& nums, vector<vector<int>>& queries) {
        // 1. calculate all prime from 1 - 1E5 with sieve
        bool prime[MAX_NUM+1];
        fill(prime, prime+MAX_NUM+1, true);
        prime[1] = false;
        for(int i=2; i<=sqrt(MAX_NUM); ++i){
            if(prime[i]){
                for(int val = i*i; val <= MAX_NUM; val += i){
                    prime[val] = false;
                }
            }
        }
        // 2. precomputing inital state and construct hashMap: prime -> map index
        int n = nums.size();
        unordered_map<int, set<int>> primeToInd;
        for(int i=0; i<n; ++i){
            if(prime[ nums[i] ] == true){
                primeToInd[nums[i]].insert(i);
            }
        }
        vector<int> delta(n+1, 0);
        // 2.1 Choose k will split [0~k-1] [k~n-1]
        for(auto& [val, indMap] : primeToInd){
            if(indMap.size() >= 2){
                delta[ (*indMap.begin())+1 ] += 1;
                delta[ (*indMap.rbegin())+1 ] -= 1;
            }
        }
        for(int i=1; i<=n; ++i){
            delta[i] += delta[i-1];
        }
        // 3. solve each query
        SegmentTree seg(n, delta);
        vector<int> ans;
        for(auto& q : queries){
            int ind = q[0];
            int newVal = q[1];
            int oldVal = nums[ind];
            nums[ind] = newVal;
            // remove the oldVal
            if(prime[oldVal]){
                if(primeToInd[oldVal].size() >= 2){
                    set<int>& indSet = primeToInd[oldVal];
                    int left = *(indSet.begin()) + 1, right = *(indSet.rbegin());
                    if(ind == left-1 || ind == right){ // it is boundary
                        seg.update(left, right, -1);
                        indSet.erase(ind); // remove pos
                        if(indSet.size() >= 2){
                            // add back the range
                            left = *(indSet.begin()) + 1; right = *(indSet.rbegin());
                            seg.update(left, right, 1);
                        }
                    } else { // not boundary
                        indSet.erase(ind); 
                    }
                } else { // size == 1
                    primeToInd.erase(oldVal);
                }
            }
            // add the newVal
            if(prime[newVal]){ 
                if(primeToInd.count(newVal)){ // at least one index
                    set<int>& indSet = primeToInd[newVal];
                    int left = *(indSet.begin()) + 1, right = *(indSet.rbegin());
                    if(ind < left-1 || ind > right){ // if out of range
                        if(indSet.size() >= 2){ // remove the old range
                            left = *(indSet.begin()) + 1, right = *(indSet.rbegin());
                            seg.update(left, right, -1);
                        }
                        indSet.insert(ind);
                        left = *(indSet.begin()) + 1; right = *(indSet.rbegin());
                        seg.update(left, right, 1);
                    } else
                        indSet.insert(ind);
                } else {
                    primeToInd[newVal].insert(ind);
                }
            }
            ans.push_back(primeToInd.size() + seg.query());
        }
        return ans;
    }
};