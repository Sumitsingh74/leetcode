class Solution {
public:
    int findClosest(int x, int y, int z) {
        if (abs(z - x) < abs(z - y)) {
            return 1;  // Person 1 is closer
        } else if (abs(z - y) < abs(z - x)) {
            return 2;  // Person 2 is closer
        } else {
            return 0;  // Both are equally close
        }
    }
};