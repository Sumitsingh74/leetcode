class Solution {
    public int[] popcountDepth(long[] nums, long[][] queries) {
        final int maxDepth = 6; // since k (depth) can be max 5

        FenwickTree[] bt = new FenwickTree[maxDepth];
        for(int i=0; i<maxDepth; i++) bt[i] = new FenwickTree(nums.length);

        // update depth index BIT
        for(int i=0; i<nums.length; i++) {
            int depth = getPopcountDepth(nums[i]);
            bt[depth].update(i, 1);
        }

        List<Integer> resList = new ArrayList<>();
        for(long[] query : queries) {
            if(query[0] == 2) {
                // Update query
                int oldDepth = getPopcountDepth(nums[(int)query[1]]);
                bt[oldDepth].update((int)query[1], -1);

                nums[(int)query[1]] = query[2];
                int newDepth = getPopcountDepth(query[2]);
                bt[newDepth].update((int)query[1], 1);

            } else if(query[0] == 1) {
                // Range query
                int l = (int)query[1];
                int r = (int)query[2];
                int k = (int)query[3];

                resList.add(bt[k].query(r) - bt[k].query(l-1));
            }
        }
        // Convert List to array
        return resList.stream().mapToInt(Integer::intValue).toArray();
    }

    private int getPopcountDepth(long x) {
        // Get popcount depth
        int depth = 0;
        while(x != 1) {
            x = Long.bitCount(x);
            depth++;
        }
        return depth;
    }

    class FenwickTree {
        private int[] bit;
        private int n;

        public FenwickTree(int size) {
            this.n = size;
            bit = new int[size+1];
        }

        public void update(int i, int val) {
            i++;
            while(i <= n) {
                bit[i] += val;
                i += i & -i;
            }
        }

        public int query(int i) {
            i++;
            int sum = 0;
            while(i > 0) {
                sum += bit[i];
                i -= i & -i;
            }
            return sum;
        }
    }
}