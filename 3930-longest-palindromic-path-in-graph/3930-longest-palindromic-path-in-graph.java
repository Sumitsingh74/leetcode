import java.util.ArrayList;

class Solution {
    private Integer[][][] memo;
    
    public int maxLen(int n, int[][] edges, String label) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];

            adj.get(from).add(to);
            adj.get(to).add(from);
        }
        memo = new Integer[n][n][1 << n];
        int max = 0;
        for (int i = 0; i < n; i++) {
            int mask = (1<<n)-1;
            mask = mask ^ (1 << i); 
            max = Math.max(max, 1+dfs(adj, i, i, mask, label));
        }
        for(int[] edge:edges){
            int from = edge[0];
            int to = edge[1];
            if (label.charAt(from) != label.charAt(to)) {
                continue; 
            }
            int mask = (1<<n)-1;
            mask = mask ^ (1 << from);
            mask = mask ^ (1 << to);
            max = Math.max(max, 2+dfs(adj, from, to, mask, label));
        }
        return max;

    }

    public int dfs(ArrayList<ArrayList<Integer>> adj, int nodeLeft,int nodeRight, int mask, String label ) {
        if (memo[nodeLeft][nodeRight][mask] != null) {
            return memo[nodeLeft][nodeRight][mask];
        }
        int max=0;
        ArrayList<Integer> adjOfLeft=adj.get(nodeLeft);
        ArrayList<Integer> adjOfRight=adj.get(nodeRight);

        for(int i=0 ; i<adjOfLeft.size() ; i++){
            int currMask=1<<adjOfLeft.get(i);
            if ((currMask&mask)==0) {
                continue;
            }
            for(int j=0 ; j<adjOfRight.size() ; j++){
                if (((1<<adjOfRight.get(j))&mask)==0) {
                    continue;
                }
                int adjLeft=adjOfLeft.get(i);
                int adjRight=adjOfRight.get(j);
                
                if (adjLeft==adjRight) {
                    continue;
                }
                if (label.charAt(adjLeft)==label.charAt(adjRight)) {
                    mask=mask^1<<adjRight;
                    mask=mask^1<<adjLeft;
                    max=Math.max(max, 2+dfs(adj, adjLeft, adjRight, mask, label));
                    mask = mask | (1 << adjRight); 
                    mask = mask | (1 << adjLeft); 
                }

            }
        }
        memo[nodeLeft][nodeRight][mask] = max;
        return max;
    }



    
}