
class Trie:
    def __init__(self):
        self.v = [0]
        self.lt = [-1]
        self.rt = [-1]
        self.id = 0

    def create(self):
        self.v.append(0)
        self.lt.append(-1)
        self.rt.append(-1)
        self.id += 1

    def add(self, x):
        node = 0
        for bit in range(30, -1, -1):
            b = x & (1 << bit)
            self.v[node] += 1

            if b == 0:
                if self.lt[node] == -1:
                    self.create()
                    self.lt[node] = self.id
                node = self.lt[node]
            else:
                if self.rt[node] == -1:
                    self.create()
                    self.rt[node] = self.id
                node = self.rt[node]
        self.v[node] += 1

    def maxxor(self, x):
        node = 0
        ret = 0
        for bit in range(30, -1, -1):
            b = (x >> bit) & 1

            if b == 0:
                if self.rt[node] != -1 and self.v[self.rt[node]] > 0:
                    ret += 1 << bit
                    node = self.rt[node]
                else:
                    node = self.lt[node]
            else:
                if self.lt[node] != -1 and self.v[self.lt[node]] > 0:
                    ret += 1 << bit
                    node = self.lt[node]
                else:
                    node = self.rt[node]
        return ret

    def erase(self, x):
        node = 0
        for bit in range(30, -1, -1):
            b = x & (1 << bit)
            self.v[node] -= 1

            if b == 0:
                node = self.lt[node]
            else:
                node = self.rt[node]
        self.v[node] -= 1


class Solution:
    def maximumStrongPairXor(self, nums):
        T = Trie()
        nums.sort(reverse=True)
        ans = 0
        ptr = 0
        for x in nums:
            T.add(x)
            while ptr < len(nums) and nums[ptr] > 2 * x:
                T.erase(nums[ptr])
                ptr += 1
            ans = max(ans, T.maxxor(x))
        return ans