class Solution {
public:
    int maxProduct(int num) {
        int res = 1, count = 0;
        vector<int> mpp(10, 0);
        while(num > 0) {
            mpp[num % 10]++;
            num /= 10;
        }
        for(int i = 9; i >= 0; --i) {
            if (count == 2) break;
            if (mpp[i] >= 2 && count == 0) {
                res = i * i;
                count += 2;
            } 
            else if (mpp[i] == 1 || (mpp[i] >= 2 && count == 1)) {
                res *= i;
                count += 1;
            }
        }
        return res;
    }
};