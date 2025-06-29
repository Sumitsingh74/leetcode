class Solution(object):
    def longestCommonPrefix(self, words):
        def get_common_prefix_length(a, b):
            length = min(len(a), len(b))
            for i in range(length):
                if a[i] != b[i]:
                    return i
            return length

        n = len(words)
        if n == 1:
            return [0]

        common = [get_common_prefix_length(words[i], words[i + 1]) for i in range(n - 1)]
        prefix_max = [0] * (n - 1)
        suffix_max = [0] * (n - 1)

        prefix_max[0] = common[0]
        for i in range(1, n - 1):
            prefix_max[i] = max(prefix_max[i - 1], common[i])

        suffix_max[-1] = common[-1]
        for i in range(n - 3, -1, -1):
            suffix_max[i] = max(suffix_max[i + 1], common[i])

        res = [0] * n
        for i in range(n):
            max_len = 0
            if 0 < i < n - 1:
                max_len = get_common_prefix_length(words[i - 1], words[i + 1])
                if i - 2 >= 0:
                    max_len = max(max_len, prefix_max[i - 2])
                if i + 1 <= n - 2:
                    max_len = max(max_len, suffix_max[i + 1])
            elif i == 0 and n > 2:
                max_len = suffix_max[1]
            elif i == n - 1 and n > 2:
                max_len = prefix_max[n - 3]
            res[i] = max_len

        return res