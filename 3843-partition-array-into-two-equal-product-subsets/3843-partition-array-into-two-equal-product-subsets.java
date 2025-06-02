class Solution {
    public boolean checkEqualPartitions(int[] nums, long target) {
        long curr = target;
        for(int i = 0; i < nums.length; i++){
            if(target % (long)nums[i] != 0){
                return false;
            }
        }
        
        long pro = 1;
        for(long n : nums){
            pro *= n;
        }
        
        if(pro != target*target){
            return false;
        }

        return true;
        
    }
}