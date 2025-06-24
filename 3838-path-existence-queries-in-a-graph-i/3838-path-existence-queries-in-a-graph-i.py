class Solution:
    def pathExistenceQueries(self, n: int, nums: List[int], maxDiff: int, queries: List[List[int]]) -> List[bool]:

        #Freidy the frog (SORTED hence..)

        cluster_list = [0] * n
        cluster = 0
        res = []
        for i in range(1, n):
            if abs(nums[i]- nums[i-1]) > maxDiff:
                cluster +=1
            cluster_list[i] = cluster

        for u,v in queries:
            res.append(cluster_list[u] == cluster_list[v])
        return res