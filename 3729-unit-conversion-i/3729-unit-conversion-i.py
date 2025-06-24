class Solution:
    def baseUnitConversions(self, conversions: List[List[int]]) -> List[int]:
        graph = defaultdict(list)

        for src, trg, fac in conversions:
            graph[src].append((trg, fac))

        res = [1] * (len(conversions) + 1)

        def dfs(source, prev):
            for target, factor in graph[source]:
                res[target] = (res[target] * prev * factor) % 1000000007
                dfs(target, res[target])
        dfs(0, 1)
        
        return res