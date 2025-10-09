class Solution {
    public static long minTime(int[] skill, int[] mana) {
            long ans=0;
            int n=skill.length;int m=mana.length;long[]arr=new long[n];
            for(int i=0;i<m;i++){
                long x=mana[i];long[]brr=new long[n];
                long start=0;
                for(int j=0;j<n;j++){
                    if(start<arr[j])start=arr[j];
                    start+=(long)x*skill[j];
                    if(j+1<n&&arr[j+1]>start)start=arr[j+1];
                    brr[j]=start;
                }
                arr[n-1]=brr[n-1];
                for(int j=n-2;j>=0;j--)arr[j]=arr[j+1]-(long)x*skill[j+1];
            }
            ans=arr[n-1];
            return ans;
        }
}