typedef long long ll;

class Solution {
public:
    ll func(vector<int>& nums, bool validity){
        int n=nums.size();
        vector<int>even,odd;
        for(int i=0;i<n;i++){
            if(nums[i]%2==0)even.push_back(i);
            else odd.push_back(i);
        }
        ll swaps=0;
        int even_idx=0;
        int odd_idx=0;
        for(int i=0;i<n;i++){
            if((i%2==0)==validity){
                if(even_idx>=even.size())return LLONG_MAX;
                swaps+=abs(i-even[even_idx++]);
            }else {
                if(odd_idx>=odd.size())return LLONG_MAX;
                swaps+=abs(i-odd[odd_idx++]);
            }
        }
        return swaps;
    }
    
    int minSwaps(vector<int>& nums) {
        int even=0;
        int odd=0;
        for(int i=0;i<nums.size();i++){
            if(nums[i]%2==0)even++;
            else odd++;
        }
        if(abs(even-odd)>1)return -1;
        
            ll res=LLONG_MAX;
        if(even>=odd)res=min(res,func(nums,true));
        if(odd>=even)res=min(res,func(nums,false));
        if(res==LLONG_MAX)return -1;
        return res/2;
        
    }
};