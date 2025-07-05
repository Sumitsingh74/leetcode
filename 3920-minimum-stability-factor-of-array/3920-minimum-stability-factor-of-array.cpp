class Solution {
    vector<int> factor;
    bool check(int len, vector<int>& nums, int maxC){
        // Greedy check with O(n)
        int n = nums.size();
        int lastBreak = -1;
        for(int i=0; i<n; ++i){
            int curLen = min(factor[i], i - lastBreak);
            if(curLen > len){ // Need to break
                if(maxC > 0){
                    lastBreak = i;
                    --maxC;
                } else
                    return false;
            }
        }
        return true;
    }
public:
    int minStable(vector<int>& nums, int maxC) {
        int n = nums.size();
        // use binary lifting 2^17 > 1E5
        // gdcVal[i][j] = gcd value of (i-2^j+1 ~ i)
        vector<vector<int>> gcdVal(n, vector<int>(18));
        for(int i=0; i<n; ++i) gcdVal[i][0] = nums[i];
        for(int j=1; j<18; ++j){
            int shift = 1 << (j-1);
            for(int i=0; i<n; ++i){
                if(i >= shift){
                    gcdVal[i][j] = gcd(gcdVal[i][j-1], gcdVal[i-shift][j-1]); 
                } else { // i < shift
                    gcdVal[i][j] = 1; 
                }
            }
        }
        // factor[i] = length of longest stable array that stop at i;
        factor.resize(n, 1); 
        for(int i=0; i<n; ++i){
            if(nums[i] == 1) factor[i] = 0;
            else{
                // find the length that not change to 1.
                int len = 0;
                int curGcd = 0;
                int pos = i;
                for(int j=17; j>=0 && pos >= 0; --j){
                    int shift = 1<<j;
                    int nextGcd = gcd(curGcd, gcdVal[pos][j]);
                    if(nextGcd >= 2){
                        curGcd = nextGcd;
                        len += shift;
                        pos -= shift;
                    }
                }
                factor[i] = len;
            }
        }
        
        int left = 0, right = n;
        // binary search
        while(left < right){
            int mid = (left + right) / 2;
            if(check(mid, nums, maxC)){
                right = mid; // all length small than mid is accept;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
};