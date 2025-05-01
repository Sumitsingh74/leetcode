class Solution {
public:
    int maxTaskAssign(vector<int>& tasks, vector<int>& workers, int pills, int strength) {
        sort(tasks.begin(), tasks.end());
        sort(workers.begin(), workers.end());

        int left = 1, right = min((int)tasks.size(), (int)workers.size()), answer = 0;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (canAssign(mid, tasks, workers, pills, strength)) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return answer;
    }

private:
    bool canAssign(int k, const vector<int>& tasks, const vector<int>& workers, int pills, int strength) {
        multiset<int> availableWorkers;

        for (int i = 0; i < workers.size(); i++) {
            availableWorkers.insert(workers[i]);
        }

        for (int i = k - 1; i >= 0; i--) {
            int task = tasks[i];

            auto it = prev(availableWorkers.end());
            if (*it >= task) {
                availableWorkers.erase(it); 
            } else {
                if (pills == 0) return false;

                auto boostIt = availableWorkers.lower_bound(task - strength);
                if (boostIt == availableWorkers.end()) return false;

                availableWorkers.erase(boostIt);
                --pills;
            }
        }

        return true;
    }
};