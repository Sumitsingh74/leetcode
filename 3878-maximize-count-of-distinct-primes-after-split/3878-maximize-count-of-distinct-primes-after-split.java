import java.util.*;
import java.util.*;

class Solution {
    static boolean not_prime[] = new boolean[ 100000 + 1];
    static {
        not_prime[0] = true;
        not_prime[1] = true;
        for (int j = 2; j < not_prime.length; j++) {
            for (int i = 2 * j; i < not_prime.length; i += j) {
                not_prime[i] = true;
            }
        }

    }

    class segmentTree {
        int seg[];
        int lazy[];
        int ini;
        HashMap<Integer, TreeSet<Integer>> map = new HashMap<>();
        HashSet<Integer> h = new HashSet<>();

        segmentTree(int arr[]) {
            int n = arr.length;
            n = (int) Math.ceil(Math.log(n) / Math.log(2));
            n = (int) Math.pow(2, n);
            ini = n;
            n = 2 * n + 1;
            lazy = new int[n];
            seg = new int[n];
            for (int i = 0; i < arr.length; i++) {
                if (!not_prime[arr[i]]) {
                    if (map.get(arr[i]) == null) {
                        map.put(arr[i], new TreeSet<Integer>());
                    }
                    map.get(arr[i]).add(i);
                    h.add(arr[i]);
                }
            }
            for (int i : h) {
                if (map.get(i).size() > 1) {
                    updateUtil(1, 0, arr.length - 1, map.get(i).ceiling(-1), map.get(i).floor(arr.length + 1), 1,0);
                }
            }
        }

        int update(int arr[], int idx, int x) {
            TreeSet<Integer> t = map.get(arr[idx]);
            if (!not_prime[arr[idx]]) {
                if (t.size() == 1) {
//                    t.remove(idx);
                    h.remove(arr[idx]);
                } else if (t.size() == 2) {
                    updateUtil(1, 0, arr.length - 1, t.ceiling(-1), t.floor(arr.length + 1), -1,0);
//                    t.remove(idx);
                } else {
                    if (t.floor(arr.length + 1) == idx) {
                        updateUtil(1, 0, arr.length - 1, t.floor(idx - 1)+1, idx, -1,0);
//                        t.remove(idx);
                    }
                    if (t.ceiling(-1) == idx) {
                        updateUtil(1, 0, arr.length - 1, idx, t.ceiling(idx + 1)-1, -1,0);

                    }

                }
                t.remove(idx);
            }
            arr[idx] = x;
            if (!not_prime[arr[idx]]) {
                if (map.get(arr[idx]) == null) {
                    map.put(arr[idx], new TreeSet<Integer>());
                }
                h.add(arr[idx]);
                t = map.get(arr[idx]);

                if (t.size() == 0) {
                    t.add(idx);

                } else if (t.size() == 1) {
                    t.add(idx);
                    updateUtil(1, 0, arr.length - 1, t.ceiling(-1), t.floor(arr.length), 1,0);
                } else {

                    if (t.floor(arr.length + 1) < idx) {
                        updateUtil(1, 0, arr.length - 1, t.floor(idx - 1)+1, idx, 1,0);
//                        t.add(idx);
                    }
                    if (t.ceiling(-1) > idx) {
                        updateUtil(1, 0, arr.length - 1, idx, t.ceiling(idx + 1)-1, 1,0);

                    }
//                    t.add(idx);
                }
                t.add(idx);

            }

            return seg[1] + h.size()+lazy[1];

        }

        int updateUtil(int idx, int ss, int ee, int s, int e, int x,int la) {
            if (ss > e || s > ee) {
                lazy[idx]+=la;
                return seg[idx]+lazy[idx];
            } else if (ss == ee) {
                seg[idx] += x + lazy[idx]+la;
                lazy[idx] = 0;
            } else if (s<=ss && e>=ee) {
                lazy[idx] +=  x + la;
                return seg[idx] + lazy[idx];
            } else {
                int mid = ss + (ee - ss) / 2;
                seg[idx] = Math.max(updateUtil(2 * idx, ss, mid, s, e, x,la+ lazy[idx]),
                        updateUtil(2 * idx + 1, mid + 1, ee, s, e, x ,la+ lazy[idx]));
                lazy[idx]=0;
            }
            return seg[idx]+lazy[idx];
        }

    }

    public int[] maximumCount(int[] nums, int[][] qs) {
        segmentTree s = new segmentTree(nums);
        int res[] = new int[qs.length];
        for (int i = 0; i < qs.length; i++) {
            res[i] = s.update(nums, qs[i][0], qs[i][1]);
        }
        return res;
    }


}