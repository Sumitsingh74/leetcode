class Solution {
    static class pe { int a;char c;pe(int x, char c){this.a=x;this.c=c;}}
   public static String removeSubstring(String s, int k) {
			String str="";
			Stack<pe> st=new Stack<pe>();
			for(int i=0;i<s.length();i++){
				char c=s.charAt(i);
				if(c=='('){
					if(st.isEmpty() ||st.peek().c==')'){
						st.push(new pe(1,c));
					}else {
						pe a=st.pop();
						st.push(new pe(a.a+1,c));
					}
				}else {
					if(st.size()==0){
						st.push(new pe(1,c));
					}else if(st.peek().c=='('){
						if(k==1){
							pe a=st.pop();
							if(a.a>1){
								st.push(new pe(a.a-1,'('));
							}
						}else {
							st.push(new pe(1,c));
						}
					} else {
						pe t=st.pop();
						pe b=new pe(t.a+1,c);
						if(st.size()>0 &&st.peek().c=='('&&st.peek().a>=k&&b.a>=k){
							pe d=st.pop();
							if(d.a>k){
								st.push(new pe(d.a-k,'('));
							}
							if(b.a>k){
								st.push(new pe(b.a-k,')'));
							}
						}else st.push(b);
					}
				}
			}
			StringBuilder sb = new StringBuilder();
while (!st.isEmpty()) {
    pe a = st.pop();
    for (int i = 0; i < a.a; i++) {
        sb.append(a.c);
    }
}
return sb.reverse().toString();

            // if(str.length()==0)return new String("");
			// return str;
		}
}