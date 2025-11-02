class Solution {

    public static long gcd(long a, long b) {if (b==0) return a;return gcd(b,a%b);}
    
    public static boolean check(long mid,int[]d,int[]r){
            long cnt=0;
            long n1=mid/r[0];long n2=mid/r[1];long n1n2=mid/((r[0]*r[1])/gcd(r[0],r[1]));
            long e=mid-n1-n2+n1n2;
            boolean ans=(mid-n1>=d[0]&&mid-n2>=d[1]&&(2*mid-n1-n2-e>=d[0]+d[1]));
//            dbg.print(" mid ",mid,"  ",n1,"  ",n2,"  ",n1n2,"  ",ans,"  ");
            // 1 3 5 7 9 11
            // 1 2 4 5 7 8 10 11
            return ans;
        }
        public static long minimumTime(int[] d, int[] r) {
            long l=d[0]+d[1];long h=(long)2e10;long ans=0;
            while(l<=h){
                long mid=(l+h)/2;
                if(check(mid,d,r)){
                    ans=mid;h=mid-1;
                }else l=mid+1;
            }
            return ans;
        }

}