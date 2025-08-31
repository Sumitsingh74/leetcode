class Solution {
        public int[] recoverOrder(int[] order, int[] friends) {
        Set<Integer> friendSet = new HashSet<>();
        for (int friendId : friends) {
            friendSet.add(friendId);
        }
        List<Integer> res = new ArrayList<>();
        for (int o : order)
            if (friendSet.contains(o))
                res.add(o);
        return res.stream().mapToInt(Integer::intValue).toArray();
    }
}