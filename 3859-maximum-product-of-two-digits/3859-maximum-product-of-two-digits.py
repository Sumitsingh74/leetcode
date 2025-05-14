class Solution:
    def maxProduct(self, num):
        res = 1
        count = 0
        mpp = [0] * 10
        while num > 0:
            mpp[num % 10] += 1
            num //= 10
        for i in range(9, -1, -1):
            if count == 2:
                break
            if mpp[i] >= 2 and count == 0:
                res = i * i
                count += 2
            elif mpp[i] == 1 or (mpp[i] >= 2 and count == 1):
                res *= i
                count += 1
        return res