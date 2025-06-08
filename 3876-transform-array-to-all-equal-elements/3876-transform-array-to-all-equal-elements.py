class Solution:
    def canMakeEqual(self, nums: List[int], k: int) -> bool:
        count=nums.count(-1)
        if count%2!=0 and (len(nums)-count)%2!=0:
            return False
        def check(make):
            first=-1
            second=-1
            op=0
            for i in range(len(nums)):
                if nums[i]==make and first==-1:
                    first=i
                elif nums[i]==make and second==-1:
                    second=i
                if first!=-1 and second!=-1:
                    op+=second-first
                    first=-1
                    second=-1
            if first==-1 and second==-1:
                return op
            else:
                return float('inf')
        return min(check(1),check(-1))<=k
        
        
        
                
                