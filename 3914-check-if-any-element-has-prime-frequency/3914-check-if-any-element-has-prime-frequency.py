class Solution:
    def checkPrimeFrequency(self, nums: List[int]) -> bool:
        d=Counter(nums) # counting elements using Counter
        for i in d.values():
            if i==1: # if one count pass because it is not prime
                pass
            elif i==2: # if two count return True because it is prime
                return True
            else: # check prime or not from 3 counts 
                for j in range(2,i):
                    if i%j==0:
                        break
                else: # if no breaks it is prime so returned True
                    return True
# if no returns of true then only case to return False\U0001f601
        return False