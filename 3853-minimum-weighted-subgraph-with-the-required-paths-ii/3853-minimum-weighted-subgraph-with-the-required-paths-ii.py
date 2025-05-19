class Solution:
    def minimumWeight(self, edges: List[List[int]], queries: List[List[int]]) -> List[int]:
        n = len(edges) + 1
        adj = [[] for _ in range(n)]
        for u, v, w in edges:
            adj[u].append((v, w))
            adj[v].append((u, w))

        # state for the size-based DSU
        parent = [*range(n)]
        size = [1] * n

        def find_set(v):
            while parent[v] != v:
                v, parent[v] = parent[v], parent[parent[v]]
            return v

        def union_sets(a, b):
            a, b = find_set(a), find_set(b)
            if a == b:
                return a
            if size[a] < size[b]:
                a, b = b, a
            parent[b] = a
            size[a] += size[b]
            return a

        # index the queries by each of the vertices
        queries_by_v = [[] for _ in range(n)]
        for i, (a, b, c) in enumerate(queries):
            queries_by_v[a].append((b, c, i))
            queries_by_v[b].append((c, a, i))
            queries_by_v[c].append((a, b, i))

        visited = [False] * n

        # ancestor[head of a DSU set] will be the only node of that set
        # that is on the current path from the root
        ancestor = [*range(n)]

        # sum of edge weights from root
        dist = [0] * n

        answer = [0] * len(queries)

        def dfs(v: int):
            visited[v] = True

            # update all relevant answers
            for b, c, i in queries_by_v[v]:
                answer[i] += dist[v]
                if visited[b]:
                    answer[i] -= dist[ancestor[find_set(b)]]
                if visited[c]:
                    answer[i] -= dist[ancestor[find_set(c)]]

            # visit subtrees
            for u, w in adj[v]:
                if visited[u]:
                    continue

                dist[u] = dist[v] + w
                dfs(u)

                # union the subtree with the current set
                # and keep the ancestor value for the set
                # (because the head might be different now)
                ancestor[union_sets(v, u)] = v

        dfs(0)

        return answer