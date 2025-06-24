
class Solution:
    def pathExistenceQueries(self, n: int, nums: List[int], maxDiff: int, queries: List[List[int]]) -> List[int]:
        newNums = [(nums[i], i) for i in range(n)]
        newNums.sort()

        getI = [0] * n
        for i in range(n):
            getI[newNums[i][1]] = i

        st = [[0] * 18 for _ in range(n)]
        l = 0
        for r in range(n):
            while newNums[r][0] - newNums[l][0] > maxDiff:
                st[l][0] = r - 1
                l += 1
        while l < n:
            st[l][0] = n - 1
            l += 1

        for j in range(1, 18):
            for i in range(n):
                st[i][j] = st[st[i][j - 1]][j - 1]

        ans = [-1] * len(queries)
        for idx, (u, v) in enumerate(queries):
            a = getI[u]
            b = getI[v]
            if a > b:
                a, b = b, a
            if a == b:
                ans[idx] = 0
                continue

            curr = a
            steps = 0
            for j in reversed(range(18)):
                if st[curr][j] < b:
                    curr = st[curr][j]
                    steps += (1 << j)

            if st[curr][0] >= b:
                ans[idx] = steps + 1
            else:
                ans[idx] = -1

        return ans