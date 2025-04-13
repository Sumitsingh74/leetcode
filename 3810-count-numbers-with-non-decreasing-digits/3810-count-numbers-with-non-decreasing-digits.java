class Solution {
    static long[][][]cp;
    static long MoD=1000000007;
    public static String reminder(String str,int b){
		int remainder = 0;
		for (int i = 0; i < str.length(); i++) {
			int digit = str.charAt(i) - '0';
			remainder = (remainder * 10 + digit) % b;
		}
		return String.valueOf(remainder);
	}


	public static String ques(String numStr,int b){
		StringBuilder quotient = new StringBuilder();
		int remainder = 0;

		for (int i = 0; i < numStr.length(); i++) {
			int digit = numStr.charAt(i) - '0';
			int current = remainder * 10 + digit;
			quotient.append(current / b);
			remainder = current % b;
		}
		int i = 0;
		while (i < quotient.length() - 1 && quotient.charAt(i) == '0') {
			i++;
		}
		return quotient.substring(i);
	}
	public static String baseS(String str,int b){
		StringBuilder ans = new StringBuilder();
		int a=10;
		while (true){
			String qu=ques(str,b);
			String rem=reminder(str,b);
			System.out.println(" "+rem+"  "+qu);
			ans.append(rem);
			if(qu.equals("0"))break;;
			str=qu;
		}
		return ans.toString();
	}
	public static String subtractOne(String numStr) {
		StringBuilder result = new StringBuilder();
		int n = numStr.length();
		boolean borrow = true;

		for (int i = n - 1; i >= 0; i--) {
			int digit = numStr.charAt(i) - '0';

			if (borrow) {
				if (digit == 0) {
					result.append('9');
				} else {
					result.append(digit - 1);
					borrow = false;
				}
			} else {
				result.append(digit);
			}
		}
		while (result.length() > 1 && result.charAt(result.length() - 1) == '0') {
			result.setLength(result.length() - 1);
		}
		return result.reverse().toString();
	}
	public static long stick(int i,int l,int h,String str,int b,int n){
		long ans=0;
		if(i==n){
			return h^1;
		}
		if(cp[i][l][h]!=-1)return cp[i][l][h];
		int c=l;
		for(;c>=0;c--){
			int d=h;
			if(str.charAt(i)-'0'<c)d=1;
			if(str.charAt(i)-'0'>c)d=0;
			ans+=stick(i+1,c,d,str,b,n)%MoD;
			ans%=MoD;
		}
		return cp[i][l][h]=ans;
	}
	public static long ll(String str,int b){
		long ans=0;
		int n=str.length();
		cp=new long[n+1][b+1][2];
		for(int i=0;i<=n;i++){
			for(int j=0;j<=b;j++)Arrays.fill(cp[i][j],-1);
		}
		ans=stick(0,b-1,0,str,b,n)%MoD;
		System.out.println(ans+"  F GF  ans");
		return ans;
	}
	public static int countNumbers(String l, String r, int b) {
		long ans=0;
		l=subtractOne(l);
		System.out.println(l+"  ");
		r=baseS(r,b);l=baseS(l,b);

		System.out.println(" L  "+l+"  r "+r);
		ans=ll(r,b)-ll(l,b);
		ans=(ans+MoD)%MoD;

		return (int)ans;
	}
}