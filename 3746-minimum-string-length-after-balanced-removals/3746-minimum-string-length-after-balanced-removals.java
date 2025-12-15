class Solution {
    public int minLengthAfterRemovals(String s) {
        Deque<Character>dq  = new ArrayDeque<>();
        char[] t = s.toCharArray();
        for(int i=0;i<s.length();i++){
            if(t[i]=='a'){
                if(dq.isEmpty()||dq.peekFirst()=='a'){
                    dq.offerFirst('a');
                }
                else{
                    dq.pollFirst();
                }
            }
            if(t[i]=='b'){
                if(dq.isEmpty()||dq.peekFirst()=='b'){
                    dq.offerFirst('b');
                }
                else{
                    dq.pollFirst();
                }
            }
        }
        return dq.size();
      }
}