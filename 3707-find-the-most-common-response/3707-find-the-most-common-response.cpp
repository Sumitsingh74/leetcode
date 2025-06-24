class Solution {
public:
    string findCommonResponse(vector<vector<string>>& responses) {
        const int n=responses.size();
        unordered_map<string, bitset<1000>> mp;
        mp.reserve(n);// pure guess
        for(int i=0; i<n; i++){
            auto& w=responses[i];
            const int wz=w.size();
            for(int j=0; j<wz; j++)
                mp[w[j]][i]=1;
        }
        string s="";
        int f=0;
        for(auto& [w, K]: mp){
            const int B=K.count();
            if (B>f){
                s=w;
                f=B;
            }
            else if (B==f && w<s) s=w;
        }
        return s;
    }
};