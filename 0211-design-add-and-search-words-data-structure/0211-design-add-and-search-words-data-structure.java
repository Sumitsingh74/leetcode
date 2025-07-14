class WordDictionary {

   static class Node{
			char val;
			Node[]alfa=new Node[26];
		}
		private final Node root=new Node();

		public WordDictionary() {
			
		}

		public void addWord(String word) {
			Node cur = root;
			for(int i=0;i<word.length();i++){
				char c=word.charAt(i);
				if(cur.alfa[c-'a']==null){
					cur.alfa[c-'a']=new Node();
				}
				if(i==word.length()-1) cur.alfa[c-'a'].val='1';
				cur=cur.alfa[c-'a'];
			}
		}
		public boolean recur(int ind,int n,String str,Node cur){
			boolean ans=false;
			if(ind==n)return cur.val == '1';
			char c=str.charAt(ind);
			if(c=='.'){
				for(int i=0;i<26;i++){
					if(cur.alfa[i]==null)continue;
					Node t=cur.alfa[i];
					ans=recur(ind + 1, n, str, t);
					if(ans)break;
				}
			}else {
				if((c-'a')>=0&&(c-'a')<=25&&cur.alfa[c-'a']!=null){
					Node t=cur.alfa[c-'a'];
					ans|=recur(ind+1,n,str,t);
				}
			}

			return ans;
		}

		public boolean search(String word) {
			Node cur=root;
			return 	recur(0,word.length(),word,cur);
		}
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */