class Solution {
    public int findContentChildren(int[] g, int[] s) {
        int child=0, cookies=0;
        Arrays.sort(s);
        Arrays.sort(g);
        while(child < g.length && cookies < s.length){
            if(g[child] <= s[cookies]){
                child++;
            }
            cookies++;
        }

        return child;
        
    }
}