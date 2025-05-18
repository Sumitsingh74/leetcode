class Solution {
    int sum(int num){
        int s = 0;
        while(num>0){
            s += num%10;
            num/=10;
        }
        return s;
    }
    public int smallestIndex(int[] nums) {
        for(int i =0;i<nums.length;i++){
            if(i == sum(nums[i])) return i;
        }
        return -1;
    }
}