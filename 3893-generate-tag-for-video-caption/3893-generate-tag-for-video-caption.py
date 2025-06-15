class Solution:
    def generateTag(self, caption: str) -> str:
        if (caption.strip() == ""):
            return "#"
            
        res = ""
        arr = caption.split()

        first_word = arr[0].lower() # converting complete first word as lower

        # Modifying First Word
        res = "#" + first_word

        #Modifying rest words
        for i in range(1,len(arr)):
            res += arr[i][0].upper() + arr[i][1:].lower()
        return res[:100]