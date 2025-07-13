class Solution {
    public static String minWindow(String str, String t) {
		String ans="";int n=str.length();
		int l=-1;int r=-1;
		int j=0;
		int[]fret=new int[100];
		int cou=100;
		for(char c:t.toCharArray()){
			if(fret[c-'A']==0)cou--;
			fret[c-'A']++;
		}
		int[]fres=new int[100];
		for(int i=0;i<str.length();i++){
			j=Math.max(j,i);
			while(j<n&&cou<100){
				fres[str.charAt(j)-'A']++;
				if(fres[str.charAt(j)-'A']==fret[str.charAt(j)-'A'])cou++;
				j++;
			}
			// dbg.print(cou+"  ",i+"  ",j);
			if(cou==100){
				if(l==-1){
					l=i;r=j;
				}else if(j-i<r-l){
					l=i;r=j;
				}
			}
			if(fres[str.charAt(i)-'A']==fret[str.charAt(i)-'A'])cou--;
			fres[str.charAt(i)-'A']--;
		}
		// System.out.println(l+"  "+r);
		if(l==-1) return ans;
		return str.substring(l,r);
	}
}