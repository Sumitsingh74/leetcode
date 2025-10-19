class Solution {
    public int missingMultiple(int[] nums, int k) {    
        int n=nums.length,min=(n+1)*k;
        HashSet<Integer> set=new HashSet<>();
        for(int i=0;i<n;i++){
            set.add(nums[i]);
        }
        int i=1;
        while(set.contains(k*i)){
            i++;
        }
        return k*i;
    }
}