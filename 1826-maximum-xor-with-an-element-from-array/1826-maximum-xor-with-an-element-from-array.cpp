struct Node {
    Node* links[2];
    bool containsKey(int bit) {
        return links[bit] != NULL;
    }
};

class Trie {
    Node* root = new Node();
public:
    void insert(int n) {
        Node* node = root;
        for(int i=31; i>=0; i--) {
            int bit = (n >> i) & 1;
            if(!node -> containsKey(bit)) {
                node -> links[bit] = new Node();
            }
            node = node -> links[bit];
        }
    }

    int getMax(int n) {
        Node* node = root;
        int maxi = 0;
        for(int i=31; i>=0; i--) {
            int bit = (n >> i) & 1;
            if(node -> containsKey(1 - bit)) {
                maxi = maxi | (1 << i);
                node = node -> links[1 - bit];
            }
            else if(node -> containsKey(bit)) node = node -> links[bit];
            else return -1;
        }

        return maxi;
    }
};

class Solution {
public:
    vector<int> maximizeXor(vector<int>& nums, vector<vector<int>>& queries) {
        Trie* trie = new Trie();
        vector<tuple<int, int, int>> q;

        for(int i=0; i<queries.size(); i++) q.push_back({queries[i][0], queries[i][1], i});

        sort(nums.begin(), nums.end());
        sort(q.begin(), q.end(), [&](auto a, auto b) {
            auto [q01, q11, i1] = a;
            auto [q02, q12, i2] = b;

            return q11 < q12;
        });

        int i = 0, n = nums.size();
        vector<int> ans(queries.size());
        for(auto& x : q) {
            auto [q0, q1, j] = x;
            int maxi = 0;
            while(i < n && nums[i] <= q1) {
                trie -> insert(nums[i]);
                i++;
            }

            ans[j] = trie -> getMax(q0);
        }

        return ans;
    }
};