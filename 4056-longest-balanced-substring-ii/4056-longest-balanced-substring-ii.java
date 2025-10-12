class Solution {
    public static long hashcode(int[]a){
            return (long) ((long)a[0]*1e12+a[1]*1e6+a[2]);
        }
        public static int ch(String str,int c){
            int ans=0;
            int n=str.length();int[]arr=new int[n];
            HashSet<Character> set1=new HashSet<>();
            for(int i=0;i<n;i++){
                if(str.charAt(i)-'a'==c)arr[i]= (int) 1e8;
                else {
                    if(set1.size()==0){
                        set1.add(str.charAt(i));
                        arr[i]=1;
                    }else {
                        if(set1.contains(str.charAt(i)))arr[i]=1;
                        else arr[i]=-1;
                    }
                }
            }
            long sum=0;
            HashMap<Long,Integer> map=new HashMap<>();map.put(0L,-1);
            for(int i=0;i<n;i++){
                sum+=arr[i];
                if(map.containsKey(sum))ans=Math.max(ans,i-map.get(sum));
                else map.put(sum,i);
            }
            return ans;
        }
        public static int longestBalanced(String str) {
            int ans=0;
            HashMap<Long,Integer> map=new HashMap<>();
            int[]c=new int[3];
            map.put(hashcode(c),-1);
            for(int i=0;i<str.length();i++){
                c[str.charAt(i)-'a']++;
                int[]a=new int[3];int min=Math.min(c[0],Math.min(c[1],c[2]));
                if(min==0&&(c[0]==c[1]||c[0]==c[2]||c[1]==c[2]))ans=Math.max(ans,i+1);
                a[0]=c[0]-min;a[1]=c[1]-min;a[2]=c[2]-min;
//                dbg.print(a,"  ",map,"  ");
                if(map.containsKey(hashcode(a))){
                    ans=Math.max(i-map.get(hashcode(a)),ans);
                }
                else map.put(hashcode(a),i);
            }
            int i=0;
            while(i<str.length()){
                int cou=1;int j=i+1;
                while(j<str.length()&&str.charAt(j)==str.charAt(i)){
                    cou++;j++;
                }
                ans=Math.max(ans,cou);
                i=j;
            }
            ans=Math.max(ans,ch(str,0));
            ans=Math.max(ans,ch(str,1));ans=Math.max(ans,ch(str,2));

            return ans;
        }
}