class Solution:
    def minTravelTime(self, l: int, n: int, k: int, position: List[int], time: List[int]) -> int:
        @cache
        def recourse(cidx, speed, extra_speed, previous_hop, used):
            if (cidx == n - 1):
                if used == 0:
                    return (l - previous_hop) * (speed)
                return 10**20
            
            if (used > 0):
                
                cost = (speed) * (position[cidx] - previous_hop)
                take = cost + recourse(cidx + 1, time[cidx] + extra_speed, 0, position[cidx], used)
                not_take = recourse(cidx + 1, speed, extra_speed + time[cidx], previous_hop, used - 1)
                
                
                return min(take, not_take)
            cost = (speed) * (position[cidx] - previous_hop)
            take = cost + recourse(cidx + 1, time[cidx] + extra_speed, 0, position[cidx], used)
            return take
            
        return recourse(1, time[0], 0, 0, k)
            
        