class Solution:
       def specialTriplets(self, A: List[int]) -> int:
        n = len(A)
        left, right = Counter(), Counter(A)
        res = 0
        for a in A:
            right[a] -= 1
            res += left[a * 2] * right[a * 2]
            left[a] += 1
        return res % (10 ** 9 + 7)