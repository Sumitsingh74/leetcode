class Solution {
    private static long costToAlign(List<Integer> evens, int firstIdxParity) {
		long cost = 0;
		for (int k = 0; k < evens.size(); k++) {
			int target = firstIdxParity == 0 ? 2*k : 2*k + 1;
			cost += Math.abs(evens.get(k) - target);
		}
		return cost;
	}
	public static int minSwaps(int[] nums) {
		int n = nums.length;
		List<Integer> evens = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			if (nums[i] % 2 == 0) evens.add(i);
		}

		int cntEven = evens.size();
		int cntOdd  = n - cntEven;
		int dif     = Math.abs(cntEven - cntOdd);

		if (dif > 1) return -1;
		if (dif == 1 && n % 2 == 0) return -1;

		long ans = Long.MAX_VALUE;
		if (cntEven > cntOdd) {
			ans = Math.min(ans, costToAlign(evens, 0));
		}
		if (cntOdd > cntEven) {
			ans = Math.min(ans, costToAlign(evens, 1));
		}
		if (cntEven == cntOdd) {
			ans = Math.min(ans, costToAlign(evens, 0));
			ans = Math.min(ans, costToAlign(evens, 1));
		}

		return (int) ans;
	}
}