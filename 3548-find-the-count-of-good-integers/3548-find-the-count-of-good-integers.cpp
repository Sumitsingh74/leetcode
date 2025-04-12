using namespace std;
// Solution table:
// ll sol[11][10] { // [n-1,k-1]
//     {9,4,3,2,1,1,1,1,1},
//     {9,4,3,2,1,1,1,1,1},
//     {243,108,69,54,27,30,33,27,23},
//     {252,172,84,98,52,58,76,52,28},
//     {10935,7400,3573,4208,2231,2468,2665,2231,1191},
//     {10944,9064,3744,6992,3256,3109,3044,5221,1248},
//     {617463,509248,206217,393948,182335,170176,377610,292692,68739},
//     {617472,563392,207840,494818,237112,188945,506388,460048,69280},

//     {41457015,37728000,13726509,33175696,15814071,12476696,36789447,30771543,4623119},
//     {41457024,39718144,13831104,37326452,19284856,13249798,40242031,35755906,4610368}
// };
class Solution {
private:
    void incrementPalindrome(string& a) {
        int L = (int) (a.length()-1)/2, R = (int) a.length()/2;
        while(L >= 0) {
            if(a[L] == '9') {
                // Case It is a nine: we cannot increment it so move on to the next outer digit that we can (possibly) increment.
                // We set this to 0 because look at the following testcase: 2992
                // The highest is 3003, if we don't modify this to be 0 then it would return 3993
                // Basically set to the lowest possible digit
                a[L] = a[R] = '0';
                L--; R++;
            }else {
                // This is so nice there is no edge case needed for incrementPalindrome() unlike in decrementPalindrome()
                // Case Not a nine: increment and return
                a[L] = a[R] = (char) (a[L] + 1);
                return;
            }
        }
        // It's all 9s and everything has also been set to 0
        a[0] = '1';
        a += '1';
    }
    long long factorial(int n) { // you could also memoize this for faster results maybe
        long long result = 1;
        for(int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    long long permWithoutLeadingZero(string& a) {
        int freq[26] {};
        for(char c : a) {
            freq[c-'0']++;
        }
        // perms that doesnt have a leading zero is
        // for starting digit as 1 -> 9:
        //    freq[starting digit] --
        //    perms += perms(a[1...], freq)
        //    freq[starting digit] ++
        //    here ^^ we didnt account for duplicate permutations
        //    so we divide by the factorial(frequency of each character)
        long long perms = 0;
        long long fc = factorial(a.length()-1); // ALL perms(a[1...])
        for(int i = 1; i <= 9; i++) {
            if(freq[i] == 0) continue;
            // use as start
            freq[i]--;
            long long currPerms = fc;
            for(int j = 0; j <= 9; j++) {
                currPerms /= factorial(freq[j]); // filter out duplicates
            }
            perms += currPerms;
            freq[i]++;
        }
        return perms;
    }
public:
    long long countGoodIntegers(size_t n, int k) {
        if(n <= 2) { // Manual, case n = 1 is actually same as n = 2
            switch(k) {
            case 1:
                return 9;
            case 2:
                return 4;
            case 3:
                return 3;
            case 4:
                return 2;
            default:
                return 1;
            }
        }
        string s = string(1, '1') + string(n-2, '0') + string(1, '1');
        unordered_set<string> st;
        long long cnt = 0;
        while(s.length() == n) { // If the number has overflowed to the next digit,
                                // then we've looped through every palindrome
            if((stoll(s) % k) == 0) {
                string a = s;
                sort(a.begin(), a.end());
                // Check if we've gone through this permutation before, if yes ignore it.
                if(!st.count(a)) {
                    st.insert(a);
                    // do { // Works but too slow so TLE
                    //     if(a[0] != '0')
                    //         cnt++;
                    // }while(next_permutation(all(a)));
                    cnt += permWithoutLeadingZero(a);
                }
            }
            incrementPalindrome(s); // Go to the next palindrome number.
        }
        return cnt;
    }
};