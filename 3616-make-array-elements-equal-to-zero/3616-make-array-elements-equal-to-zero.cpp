class Solution {
public:
    int countValidSelections(vector<int>& nums) {
    vector<int> ps(nums.size() + 1);
    partial_sum(begin(nums), end(nums), begin(ps) + 1);
    int res = 0;
    for (int i = 0; i < nums.size(); ++i)
        if (nums[i] == 0)
            if (ps.back() == 2 * ps[i])
                res += 2;
            else if (abs(ps.back() - 2 * ps[i]) == 1)
                res += 1;
    return res;
}
};