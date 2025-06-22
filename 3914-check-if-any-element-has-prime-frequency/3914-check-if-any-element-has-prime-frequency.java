class Solution {
    boolean pr[]=new boolean[101];
    private void primenumber(){
        for(int i=2;i<101;i++){
            pr[i]=true;
        }
        for(int p=2;p*p<101;p++){
            if(pr[p]==true){
                for(int i=p*p;i<101;i+=p){
                    pr[i]=false;
                }
            }
        }
    }
    public boolean checkPrimeFrequency(int[] nums) {
        primenumber();
        int i,j,n=nums.length;
        int[] freq=new int[101];
        for(i=0;i<n;i++){
            freq[nums[i]]++;
        }
        for(i=0;i<101;i++){
            if(freq[i]!=0){
                if(pr[freq[i]])
                    return true;
            }
        }
        return false;
    }
}