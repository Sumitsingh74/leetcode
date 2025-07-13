class Solution {
    public static char processStr(String str, long k) {
		char ans='a';
		long l=0;
		ArrayList<Long>list=new ArrayList<>();
		ArrayList<Character> ch=new ArrayList<>();
		for(char c:str.toCharArray()){
			if(c=='#'){
				l*=2;
			}else if(c=='*'){
				if(l>0){
					l--;
				}
			}else if(c=='%'){

			}else {
				l++;
			}
			list.add(l);
			ch.add(c);
		}
		for(int i=list.size()-1;i>=0;i--){
			char c=ch.get(i);
			if(list.get(i)>=k+1){
				if(c=='#'){
					long e=list.get(i)/2;
					if(k<e)continue;
					else {
						k-=e;
					}
				}else if(c=='*'){

				}else if(c=='%'){
					long d=list.get(i)-1;
					k=d-k;
				}else {
					if(list.get(i)==k+1)return ch.get(i);
				}
			}else return '.';
//			dbg.print(i+"  "+list.get(i)+"  "+k);
		}

		return ans;
	}
}