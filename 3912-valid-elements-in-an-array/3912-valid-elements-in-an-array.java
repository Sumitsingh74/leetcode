class Solution {
    public List<Integer> findValidElements(int[] nums) {
        List<Integer>res=new ArrayList<>();
        for(int i=0;i<nums.length;i++){
            boolean f1=true,f2=true;
            for(int j=0;j<i;j++){
                if(nums[j]>=nums[i]){
                    f1=false;
                    break;
                }
            }
            for(int j=i+1;j<nums.length;j++){
                if(nums[j]>=nums[i]){
                    f2=false;
                    break;
                    }
                }
            if(f1||f2)res.add(nums[i]);
        }
        
        return res;
    }
}