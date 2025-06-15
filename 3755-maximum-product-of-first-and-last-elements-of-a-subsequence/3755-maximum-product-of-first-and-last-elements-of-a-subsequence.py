class Solution:
      def maximumProduct(self, A: List[int], m: int) -> int:
        ma = mi = A[0]
        res = -inf
        for i in range(m - 1, len(A)):
            ma = max(ma, A[i - m + 1])
            mi = min(mi, A[i - m + 1])
            res = max(res, mi * A[i], ma * A[i])
        return res