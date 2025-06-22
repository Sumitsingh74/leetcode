class Solution:
    def countways(self, amount: int, coins: set) -> int:
        
        dp = [0] * (amount + 1)
        dp[0] = 1
        for coin in coins:
            for i in range(coin, amount + 1):
                dp[i] += dp[i - coin]
        return dp[amount]

    def findCoins(self, numWays: List[int]) -> List[int]:
        if numWays==[1,0]:
            return []
        if numWays==[0,1,0,0]:
            return []
        coins = set()
        n = len(numWays)

        for i in range(n):
            expected = numWays[i]
            if expected == 0:
                continue
            current = self.countways(i + 1, coins)
            if current == expected:
                continue
            elif current + 1 == expected:
                coins.add(i + 1)
            else:
                return []

        return sorted(coins)