class Solution {
public:
    bool check(vector<int> &v1,int &x){
        for(int i = 0; i <=31; i++){
            if(!v1[i] && (x&(1<<i))){
                return true;
            }
        }
        return false;
    }
    vector<int> smallestSubarrays(vector<int>& nums) {
        int n = nums.size();
        if(n==1)return {1};
        vector<int> v1(32,0);
        int i = 0, j = 0,k;
        vector<int> ans;
        vector<int> v2(n);
        v2[n-1] = nums[n-1];
        for(i = n-2; i >= 0; i--){
            v2[i] = (nums[i]|v2[i+1]);
        }
        // for(auto &i: v2)cout<<i<<" ";
        i = 0, j = 0;
        while(i<n){
            if(v2[i]==0){
                ans.push_back(1);
                i++;
                j++;
                continue;
            }
            while(j<n && check(v1,v2[i])){
                for(k = 0; k <= 31; k++){
                    if(nums[j]&(1<<k)){
                        v1[k]++;
                    }
                }
                j++;
            }
            ans.push_back(j-i);
            for(k = 0; k <= 31; k++){
                if(nums[i]&(1<<k)){
                    v1[k]--;
                }
            }
            i++;
        }
        return ans;
    }
};