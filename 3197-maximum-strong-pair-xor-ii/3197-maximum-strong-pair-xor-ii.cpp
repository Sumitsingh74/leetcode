
struct Trie {
    vector<int> v, lt, rt; // v stores the count of elements in the subtree rooted at the current node, lt stores the index of the left child, and rt stores the index of the right child.
    int id = 0; // id is used to assign a unique identifier to each node in the Trie.

    Trie() : v(1, 0), lt(1, -1), rt(1, -1) {} // Constructor initializes the Trie with a single node representing the root.

    void create() {
        v.emplace_back(0); // Create a new node and initialize its count to 0.
        lt.emplace_back(-1); // Initialize left child index to -1.
        rt.emplace_back(-1); // Initialize right child index to -1.
        ++id; // Increment the id for the newly created node.
    }

    void add(int x) {
        int node = 0; // Start from the root.
        for (int bit = 30; bit >= 0; --bit) { // Traverse each bit of the number from the most significant bit to the least significant bit.
            int b = x & (1 << bit); // Extract the bit at the current position.

            ++v[node]; // Increment the count of elements in the current node's subtree.

            if (b == 0) { // If the bit is 0, move to the left child.
                if (lt[node] == -1) {
                    create(); // Create a new node if the left child doesn't exist.
                    lt[node] = id; // Update the left child index.
                }
                node = lt[node];
            } else { // If the bit is 1, move to the right child.
                if (rt[node] == -1) {
                    create(); // Create a new node if the right child doesn't exist.
                    rt[node] = id; // Update the right child index.
                }
                node = rt[node];
            }
        }
        ++v[node]; // Increment the count for the leaf node corresponding to the inserted number.
    }

    int maxxor(int x) {
        int node = 0, ret = 0; // Start from the root, ret stores the result of the XOR operation.
        for (int bit = 30; bit >= 0; --bit) {
            int b = (x >> bit) & 1; // Extract the bit at the current position.

            if (b == 0) {
                if (rt[node] != -1 and v[rt[node]] > 0) { // If right child exists and has elements, update ret and move to the right child.
                    ret += 1 << bit;
                    node = rt[node];
                } else
                    node = lt[node]; // Move to the left child.
            } else {
                if (lt[node] != -1 and v[lt[node]] > 0) { // If left child exists and has elements, update ret and move to the left child.
                    ret += 1 << bit;
                    node = lt[node];
                } else
                    node = rt[node]; // Move to the right child.
            }
        }
        return ret;
    }

    void erase(int x) { // Assumes x exists
        int node = 0;
        for (int bit = 30; bit >= 0; --bit) {
            int b = x & (1 << bit);
            --v[node]; // Decrement the count of elements in the current node's subtree.

            if (b == 0)
                node = lt[node]; // Move to the left child.
            else
                node = rt[node]; // Move to the right child.
        }
        --v[node]; // Decrement the count for the leaf node corresponding to the erased number.
    }
};

class Solution {
public:
    int maximumStrongPairXor(vector<int>& nums) {
        Trie T;
        sort(nums.rbegin(), nums.rend()); // Sort the numbers in reverse order.
        int ans = 0;
        int ptr = 0;
        for (auto x : nums) {
            T.add(x); // Insert the current number into the Trie.
            while (ptr < nums.size() and nums[ptr] > 2 * x) {
                T.erase(nums[ptr]); // Erase numbers that are greater than 2*x.
                ++ptr;
            }
            ans = max(ans, T.maxxor(x)); // Update the maximum XOR result.
        }
        return ans;
    }
};