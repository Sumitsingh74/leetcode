class Solution {
public:
    int score(vector<string>& cards, char x) {
        map<char,int> first, second ;
        int count = 0 ;
        for (const string &c : cards) {
            if (c[0] == c[1] and c[0] == x) ++count ; 
            else if (c[0] == x) ++first[c[1]] ; 
            else if (c[1] == x) ++second[c[0]] ; 
        }
        int a = 0, m = 0 , u = 0 ;
        for (auto &[v,f] : first) a += f, m = max(m,f) ; 
        u = a ;                             
        int ans = min(a / 2,a - m) ; 
        int rem = a -2*ans ; 
        a = 0, m = 0;
        for (auto &[v,f] : second) a += f, m = max(m,f) ;
        u += a ;
        int k = min(a/2, a- m) ; 
        ans += min(a/2,a - m);                      
        a = 0 ;
        for (int i = 0 ; i <= ans ; ++i) a = max(a,i + min(count,max(0,u - 2*i))) ;
        return a ; 
    }
};