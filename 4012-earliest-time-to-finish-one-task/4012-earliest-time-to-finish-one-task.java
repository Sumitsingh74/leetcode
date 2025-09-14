class Solution {
    public int earliestTime(int[][] tasks) {
        int min=Integer.MAX_VALUE;
        
        for(int i=0;i<tasks.length;i++){
            int sum=0;
           
                sum=tasks[i][0]+tasks[i][1];

            
            min=Math.min(sum,min);
            
        }
        return min;
    }
}