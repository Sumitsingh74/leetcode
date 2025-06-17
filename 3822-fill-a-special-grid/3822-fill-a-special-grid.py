class Solution:
    def specialGrid(self, N: int) -> List[List[int]]:
        size = 1 << N
        grid = [[0] * size for _ in range(size)]
        val = 0

        def solve(rowStart, rowEnd, colStart, colEnd):
            nonlocal val
            sz = rowEnd - rowStart
            if sz == 1:
                grid[rowStart][colStart] = val
                val += 1
                return
            midRow = rowStart + sz // 2
            midCol = colStart + sz // 2

            solve(rowStart, midRow, midCol, colEnd) # top right
            solve(midRow, rowEnd, midCol, colEnd) # bottom right
            solve(midRow, rowEnd, colStart, midCol) # bottom left
            solve(rowStart, midRow, colStart, midCol) # top left

        solve(0, size, 0, size)
        return grid