class Solution {
    TrieNode root = new TrieNode();

    public int maximumStrongPairXor(int[] nums) {
        int res = 0;
        Arrays.sort(nums);
        int i = 0;
        for (int num : nums) {
            while(i < nums.length && nums[i] <= 2 * num)
             insertNode(nums[i++]);

            res = Math.max(res, get_max(num));
            delete(num);
        }

        return res;
    }

    private void insertNode(int num) {
        TrieNode tmp = root;
        for (int i = 20; i >= 0; i--) {
            int ind = (num & (1 << i)) > 0 ? 1 : 0;
            if (tmp.children[ind] == null) {
                tmp.children[ind] = new TrieNode();
            }
            tmp = tmp.children[ind];
            tmp.count++;
        }
    }

    private int get_max(int num) {
        TrieNode tmp = root;
        int max = 0;
        for (int i = 20; i >= 0; i--) {
            int ind = (num & (1 << i)) > 0 ? 1 : 0;
            int opp = ind == 0 ? 1 : 0;
            if (tmp.children[opp] != null && tmp.children[opp].count > 0) {
                max |= (1 << i);
                tmp = tmp.children[opp];
            } else {
                tmp = tmp.children[ind];
            }
        }
        return max;
    }
    private void delete(int num) {
        TrieNode tmp = root;
        for (int i = 20; i >= 0; i--) {
            int ind = (num & (1 << i)) > 0 ? 1 : 0;
            tmp = tmp.children[ind];
            tmp.count--;
        }
    }
}

class TrieNode {
    TrieNode[] children = new TrieNode[2];
    int count = 0;
}
