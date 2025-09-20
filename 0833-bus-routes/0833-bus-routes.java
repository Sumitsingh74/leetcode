class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) return 0;

        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int bus = 0; bus < routes.length; bus++) {
            for (int station : routes[bus]) {
                map.putIfAbsent(station, new ArrayList<>());
                map.get(station).add(bus);
            }
        }

        Set<Integer> visitedBuses = new HashSet<>();
        Set<Integer> visitedStations = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);

        int count = 0;
    while (!queue.isEmpty()) {
        int size = queue.size();
        count++; // We're about to take one more bus
    
        for (int i = 0; i < size; i++) {
          int currStation = queue.poll();
        
           for (int bus : map.getOrDefault(currStation, new ArrayList<>())) {
              if (!visitedBuses.add(bus)) continue;
            
              for (int station : routes[bus]) {
                  if (station == target) return count;
                  if (visitedStations.add(station)) queue.offer(station);
              }
          }
      }
   }
        return -1;
    }
}