class Solution {
    vector<int> p, s;
    void join(int a, int b){
        if(s[b] > s[a])swap(a,b);
        p[a] = b;
        s[b]+=s[a];
    }
    int find(int a){
        if(p[a]==-1)return a;
        return p[a] = find(p[a]);
    }
    long long cyc(int n, int i){
        long long sm  = 0;
        long long cur1 = n-1, cur2 = n-2;
        sm= n*cur1 + n*cur2;
        i--;
        while(i>=2){
            i-=2;
            if(i==0){
                sm+=cur1*cur2;
            }else if(i==1){
                sm += cur1*(cur2-1) + cur2*(cur2-1);
            }else{
                sm += cur1*(cur2-1) + cur2*(cur2-2);
            }
            cur1 = cur2-1;
            cur2 = cur1-1;
        }
        return sm;
    }
    long long str(int n, int i){
        cout<<n<<"  "<<i<<endl;
        long long sm  = 0;
        if(i == 1)return 0;
        if(i==2)return n*(n-1);
        long long cur1 = n-1, cur2 = n-2;
        sm= n*cur1 + n*cur2;
        i--;
        while(i>=2){
            i-=2;
            if(i==0){
                continue;
            }else if(i==1){
                sm += cur1*(cur2-1);
            }else{
                sm += cur1*(cur2-1) + cur2*(cur2-2);
            }
            cur1 = cur2-1;
            cur2 = cur1-1;
        }
        return sm;
    }
public:
    long long maxScore(int n, vector<vector<int>>& edges) {
        p = vector<int>(n,-1);
        s = vector<int>(n, 1);
        int x = n;
        vector<int> res;
        vector<int> c;
        long long ans =0;
        for(auto i: edges){
            if(find(i[0]) == find(i[1])){
                c.push_back(s[find(i[1])]);
                p[find(i[1])] = -2;
            }else
            join(find(i[0]), find(i[1]));
        }

        sort(c.begin(), c.end(), greater<int>());
        for(auto i: c){
            ans+=cyc(n,i);
            n-=i;
        }

        c = vector<int>();
        for(int i=0; i<x; i++){
            if(p[i]==-1 && s[i]>1)c.push_back(s[i]);
        }

        sort(c.begin(), c.end(), greater<int>());
        for(auto i: c){
            ans+=str(n,i);
            n-=i;
        }

        return ans;
    }
};