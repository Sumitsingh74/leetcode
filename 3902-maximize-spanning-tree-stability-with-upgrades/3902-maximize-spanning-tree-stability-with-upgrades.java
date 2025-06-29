class DisjointSet {
    int n;
    int[] size;
    int[] parent;

    public DisjointSet(int n) {
        this.n = n;
        this.size = new int[n];
        this.parent = new int[n];
        for(int i = 0; i < n; i++) {
            size[i] = 1;
            parent[i] = i;
        }
    }

    public int findUParent(int node) {
        // base case
        if(parent[node] == node) return node;

        parent[node] = findUParent(parent[node]);
        return parent[node];
    }

    public void makeUnion(int u, int v) {
        int uPar = findUParent(u);
        int vPar = findUParent(v);

        if(uPar == vPar) return;

        if(size[uPar] >= size[vPar]) {
            parent[vPar] = uPar;
            size[uPar] += size[vPar];
        } else {
            parent[uPar] = vPar;
            size[vPar] += size[uPar];
        }
    }
}


class Solution {
    public int maxStability(int n, int[][] edges, int k) {

        // dsu for strong edges
        int result = Integer.MAX_VALUE;
        int edgeCnt = 0;
        DisjointSet dsu = new DisjointSet(n);
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[2] - a[2]);
        for(int[] edge : edges) {
            if(edge[3] == 1) {
                int uPar = dsu.findUParent(edge[0]);
                int vPar = dsu.findUParent(edge[1]);

                if(uPar == vPar) return -1;
                dsu.makeUnion(edge[0], edge[1]);
                result = Math.min(result, edge[2]);
                edgeCnt++;
            } else {
                maxHeap.add(edge);
            }
        }

        // dsu for weak edges if possible
        List<Integer> weakEdgeList = new ArrayList<Integer>();
        while(!maxHeap.isEmpty() && edgeCnt < (n-1)) {
            int[] edge = maxHeap.remove();
            int u = edge[0], v = edge[1], wt = edge[2], m = edge[3];

            if(m == 1) continue;

            // already connected
            int uPar = dsu.findUParent(u);
            int vPar = dsu.findUParent(v);

            if(uPar == vPar) continue;
            
            edgeCnt++;
            weakEdgeList.add(wt); // add into list
            dsu.makeUnion(u, v);
        }

        // check if all connected component
        Set<Integer> st = new HashSet<Integer>();

        for(int i = 0; i < n; i++) {
            int par = dsu.findUParent(i);
            st.add(par);
        }
        
        if(st.size() != 1) return -1;

        // count result from weak
        // we have peak edge from max heap so it is increasing order (reversed while adding element)
        int len = weakEdgeList.size();
        int inx = len - 1;

        while(inx >= 0) {
            int wt = weakEdgeList.get(inx);
            if(k > 0) {
                result = Math.min(result, 2*wt);
                k--;
            } else {
                result = Math.min(result, wt);
            }
            inx--;
        }
        
        return result;
    }
}