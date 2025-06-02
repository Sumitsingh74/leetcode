#include <vector>
#include <set>
#include <algorithm>
#include <climits>
using namespace std;

const long MAXN = 101000;

class SegmentTree {
public:
    vector<int> tree, lazy;
    int n;

    SegmentTree(vector<int>& arr) {
        n = arr.size();
        tree.resize(4 * n, 0);
        lazy.resize(4 * n, 0);
        build(arr, 0, 0, n - 1);
    }

    void build(vector<int>& arr, int idx, int start, int end) {
        if (start == end) {
            tree[idx] = arr[start];
        } else {
            int mid = (start + end) / 2;
            build(arr, 2 * idx + 1, start, mid);
            build(arr, 2 * idx + 2, mid + 1, end);
            tree[idx] = max(tree[2 * idx + 1], tree[2 * idx + 2]);
        }
    }

    void updateRange(int idx, int start, int end, int l, int r, int delta) {
        if (lazy[idx] != 0) {
            tree[idx] += lazy[idx];
            if (start != end) {
                lazy[2 * idx + 1] += lazy[idx];
                lazy[2 * idx + 2] += lazy[idx];
            }
            lazy[idx] = 0;
        }

        if (start > r || end < l) return;

        if (start >= l && end <= r) {
            tree[idx] += delta;
            if (start != end) {
                lazy[2 * idx + 1] += delta;
                lazy[2 * idx + 2] += delta;
            }
            return;
        }

        int mid = (start + end) / 2;
        updateRange(2 * idx + 1, start, mid, l, r, delta);
        updateRange(2 * idx + 2, mid + 1, end, l, r, delta);
        tree[idx] = max(tree[2 * idx + 1], tree[2 * idx + 2]);
    }

    void updateRange(int l, int r, int delta) {
        if (l > r) return;
        l = max(l, 0);
        r = min(r, n - 1);
        if (l > r) return;
        updateRange(0, 0, n - 1, l, r, delta);
    }

    int queryRange(int idx, int start, int end, int l, int r) {
        if (lazy[idx] != 0) {
            tree[idx] += lazy[idx];
            if (start != end) {
                lazy[2 * idx + 1] += lazy[idx];
                lazy[2 * idx + 2] += lazy[idx];
            }
            lazy[idx] = 0;
        }

        if (start > r || end < l) {
            return INT_MIN;
        }

        if (start >= l && end <= r) {
            return tree[idx];
        }

        int mid = (start + end) / 2;
        int left_val = queryRange(2 * idx + 1, start, mid, l, r);
        int right_val = queryRange(2 * idx + 2, mid + 1, end, l, r);
        return max(left_val, right_val);
    }

    int queryRange(int l, int r) {
        if (l > r) return 0;
        l = max(l, 0);
        r = min(r, n - 1);
        if (l > r) return 0;
        return queryRange(0, 0, n - 1, l, r);
    }
};

class Solution {
public:
    vector<int> maximumCount(vector<int>& arr, vector<vector<int>>& qs) {
        static vector<long> sievediv(MAXN + 1, 0);
        static bool sieve_computed = false;
        if (!sieve_computed) {
            for (long i = 1; i <= MAXN; i++) {
                sievediv[i] = i;
            }
            for (long i = 4; i <= MAXN; i += 2) {
                sievediv[i] = 2;
            }
            for (long i = 3; i * i <= MAXN; i++) {
                if (sievediv[i] == i) {
                    for (long j = i * i; j <= MAXN; j += i) {
                        if (sievediv[j] == j) {
                            sievediv[j] = i;
                        }
                    }
                }
            }
            sievediv[1] = 0;
            sieve_computed = true;
        }

        int n = arr.size();
        vector<set<int>> sets(MAXN + 10);
        vector<int> pre(n, 0);
        int cou = 0;

        for (int i = 0; i < n; i++) {
            if (arr[i] <= MAXN && arr[i] > 1 && sievediv[arr[i]] == arr[i]) {
                if (sets[arr[i]].empty()) {
                    cou++;
                }
                sets[arr[i]].insert(i);
            }
            pre[i] = cou;
        }

        set<int> set1;
        cou = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] <= MAXN && arr[i] > 1 && sievediv[arr[i]] == arr[i] && set1.find(arr[i]) == set1.end()) {
                cou++;
                set1.insert(arr[i]);
            }
            if (i > 0) {
                pre[i - 1] += cou;
            }
        }

        SegmentTree sg(pre);
        vector<int> res;

        for (int i = 0; i < qs.size(); i++) {
            int ind = qs[i][0];
            int x = qs[i][1];
            int old_val = arr[ind];

            if (old_val <= MAXN && old_val > 1 && sievediv[old_val] == old_val) {
                set<int>& oldSet = sets[old_val];
                if (oldSet.find(ind) != oldSet.end()) {
                    int a0 = *oldSet.begin();
                    int b0 = *oldSet.rbegin();
                    if (ind == a0) {
                        oldSet.erase(ind);
                        if (oldSet.empty()) {
                            sg.updateRange(a0, n - 1, -1);
                        } else {
                            int a1 = *oldSet.begin();
                            sg.updateRange(a0, a1 - 1, -1);
                        }
                    } 
                     if (ind == b0) {
                        oldSet.erase(ind);
                        if (oldSet.empty()) {
                            if (b0 - 1 >= 0) {
                                sg.updateRange(0, b0 - 1, -1);
                            }
                        } else {
                            int b1 = *oldSet.rbegin();
                            sg.updateRange(b1, b0 - 1, -1);
                        }
                    } 
                     oldSet.erase(ind);
                }
            }

            if (x <= MAXN && x > 1 && sievediv[x] == x) {
                set<int>& newSet = sets[x];
                bool wasEmpty = newSet.empty();
                int a0 = -1, b0 = -1;
                if (!wasEmpty) {
                    a0 = *newSet.begin();
                    b0 = *newSet.rbegin();
                }
                newSet.insert(ind);
                if (wasEmpty) {
                    sg.updateRange(ind, n - 1, 1);
                    if (ind - 1 >= 0) {
                        sg.updateRange(0, ind - 1, 1);
                    }
                } else {
                    int a1 = *newSet.begin();
                    int b1 = *newSet.rbegin();
                    if (a1 == ind) {
                        sg.updateRange(ind, a0 - 1, 1);
                    }
                    if (b1 == ind) {
                        sg.updateRange(b0, ind - 1, 1);
                    }
                }
            }

            arr[ind] = x;
            res.push_back(sg.queryRange(0, n - 1));
        }

        return res;
    }
};