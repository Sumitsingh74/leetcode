class Solution {
    public long maxAlternatingSum(int[] nums) {
    long ans = 0;
    for(int i = 0; i < nums.length; ++i){
        nums[i] = Math.abs(nums[i]);
    }
    Arrays.sort(nums); 
    for(int i = 0; i < nums.length/2; ++i){
        ans += nums[nums.length - i - 1]*nums[nums.length - i - 1] - nums[i]*nums[i];
    }
    if(nums.length %2 == 1) ans += nums[nums.length/2]*nums[nums.length/2];
    return ans;
}
}