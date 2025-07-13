class Solution {
    public static String processStr(String str) {
		StringBuilder st=new StringBuilder();
		int l=0;
		for(char c:str.toCharArray()){
			if(c=='#'){
				l*=2;
				String sr=st.toString();
				st.append(sr);
			}else if(c=='*'){
				if(l>0){
					st.replace(l-1,l,"");l--;
				}
			}else if(c=='%'){
				st.reverse();
			}else {
				st.append(c);l++;
			}
		}
		return st.toString();
	}
}