class Solution {
public:

    long stick(int ind,int n,int cou,int k,vector<int>&arr,vector<vector<long>>&dp){
        cou=cou%2;
        if(ind==n){
            if(cou==0)return 0;
            else return -2e9;
        }
        if(dp[ind][cou]!=-1)return dp[ind][cou];
        long  notake=arr[ind]+ stick(ind+1,n,cou,k,arr,dp);
        long take = (long)(arr[ind]^k)+ stick(ind+1,n,cou+1,k,arr,dp);
        
        return dp[ind][cou]= max(notake,take);
    }

    long long maximumValueSum(vector<int>& arr, int k, vector<vector<int>>& edges) {
        int n=arr.size();
        vector<vector<long>> dp(n+1,vector<long>(2,-1));

        long ans=stick(0,n,0,k,arr,dp);
        return ans;

    }
};