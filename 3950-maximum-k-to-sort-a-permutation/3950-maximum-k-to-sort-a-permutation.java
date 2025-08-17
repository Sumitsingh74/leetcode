class Solution {
    public static int sortPermutation(int[] nums) {
		int ans=(1<<26)-1;int d=ans;
		for(int i=0;i<nums.length;i++){
			if(nums[i]!=i)ans&=nums[i];
		}
		return ans==d?0:ans;
	}
}