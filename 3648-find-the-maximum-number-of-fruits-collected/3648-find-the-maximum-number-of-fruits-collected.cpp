class Solution {
public:
    int maxCollectedFruits(vector<vector<int>>& fruits) {
        
        int n = fruits.size(), count = 0;

        if(n==2)
        return fruits[0][0]+fruits[0][1]+fruits[1][0]+fruits[1][1];

        vector<vector<int>> matrix(n, vector<int>(n, 0));
        for (int i=0;i<n-1;i++) 
        for (int j=0;j<n-i-1;j++) 
        matrix[i][j]=1;

        for (int i=0;i<n;i++) 
        {
            count+=fruits[i][i];
            fruits[i][i]=0;
            matrix[i][i]=1;
        }

        int a,b,c,i,j;  

        for (i=1;i<n-1;i++) {
            j=n-1;
            while(matrix[i][j]==0) {  
                a = 0, b = 0, c = 0;
                if(matrix[i-1][j-1]==0) a=fruits[i-1][j-1];
                if(matrix[i-1][j]==0) b=fruits[i-1][j];
                if(j+1<=n-1 && matrix[i-1][j+1]==0) c=fruits[i-1][j+1];
                fruits[i][j]+=max({a, b, c});
                j--;
            }
        }

        for (j=1;j<n-1;j++) {  
            i=n-1;
            while(matrix[i][j]==0) {  
                a = 0, b = 0, c = 0;
                if(matrix[i-1][j-1]==0) a=fruits[i-1][j-1];
                if(matrix[i][j-1]==0) b=fruits[i][j-1];
                if(i+1<=n-1 && matrix[i+1][j-1]==0) c=fruits[i+1][j-1];
                fruits[i][j]+=max({a, b, c});
                i--;
            }
        }

        count+=fruits[n-1][n-2]+fruits[n-2][n-1];

        return count;
    }
};