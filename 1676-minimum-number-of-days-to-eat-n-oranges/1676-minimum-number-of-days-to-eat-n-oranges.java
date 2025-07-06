class Solution {
    static HashMap<Integer,Integer> map=new HashMap<>();
	public static int minDays(int n) {
		if(n==1)return 1;
		if(n<=3)return 2;
		if(map.containsKey(n))return map.get(n);
		int ans=(int)1e7;
		if(n%2==0)ans=Math.min(ans,1+minDays(n/2));
		if(n%3==0)ans=Math.min(ans,1+minDays(n/3));
		int d=n-1;
		if(d%2==0)ans=Math.min(ans,2+minDays(d/2));
		if(d%3==0)ans=Math.min(ans,2+minDays(d/3));
		d=n-2;
		if(d%2==0)ans=Math.min(ans,3+minDays(d/2));
		if(d%3==0)ans=Math.min(ans,3+minDays(d/3));
		map.put(n,ans);
		return ans;
	}
}