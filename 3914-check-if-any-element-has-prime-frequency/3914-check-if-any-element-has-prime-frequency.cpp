class Solution {
public:
    bool isPrime(int n) {
        if (n <= 1)
            return false;
        if (n <= 3)
            return true;
        if (n % 2 == 0 || n % 3 == 0)
            return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    bool checkPrimeFrequency(vector<int>& nums) {
        unordered_map<int, int> hash;
        for (auto it : nums) {
            hash[it]++;
        }
        for (auto it : hash) {
            if (isPrime(it.second)) {
                return true;
            }
        }
        return false;
    }
};