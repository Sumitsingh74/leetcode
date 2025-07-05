def sieve(n):
    ans = [True] * (n + 1)
    ans[0] = ans[1] = False
    for i in range(2, n // 2 + 1):
        if ans[i]:
            for j in range(2 * i, n + 1, i):
                ans[j] = False
    return ans

primes = [i for i, b in enumerate(sieve(31607)) if b]

class Solution:
    def minStable(self, nums: List[int], maxC: int) -> int:
        if sum(x > 1 for x in nums) <= maxC:
            return 0
        indices = defaultdict(list)
        for i, x in enumerate(nums):
            for p in primes:
                if p ** 2 > x:
                    break
                has = False
                while not x % p:
                    x //= p
                    has = True
                if has:
                    indices[p].append(i)
            if x > 1:
                indices[x].append(i)
        s = set()
        for p, l in indices.items():
            end = start = -inf
            for i in l:
                if i - end == 1:
                    end = i
                else:
                    if end > start:
                        s.add((start, end))
                    start = end = i
            if end > start:
                s.add((start, end))
        seg = []
        last = -1
        for start, end in sorted(s):
            if end > last:
                seg.append((start, end))
                last = end
        high = max((end - start + 1 for start, end in seg), default=1)
        low = 1
        def check(mid, budget):
            last = -1
            for start, end in seg:
                start = max(start, last + 1)
                length = (end - start) + 1
                req = length // (mid + 1)
                if req:
                    if budget < req:
                        return False
                    budget -= req
                    last = start - 1 + req * (mid + 1)
            return True
        while low < high:
            mid = (low + high) // 2
            if check(mid, maxC):
                high = mid
            else:
                low = mid + 1
        return low        