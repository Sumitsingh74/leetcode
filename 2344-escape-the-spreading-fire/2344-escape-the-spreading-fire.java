import java.util.*;
class Solution {
    public static int maximumMinutes(int[][] grid) {
		int n=grid.length;int m=grid[0].length;
		int[][]arr=new int[n][m];
		for(int i=0;i<n;i++)Arrays.fill(arr[i],Integer.MAX_VALUE);
		PriorityQueue<int[]> pq=new PriorityQueue<>(new Comparator<int[]>(){
			@Override
			public int compare(int[]a,int[]b){
				if(a[0]>b[0])return 1;
				else if(a[0]==b[0])return 0;
				else return -1;
			}
		});
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				if(grid[i][j]==1){
					arr[i][j]=0;
					pq.add(new int[]{0,i,j});
				}
			}
		}
		int[][] dir = {{-1,0} , {0,-1} , {0,1}, {1, 0}};
		while(pq.size()>0){
			int[]a=pq.poll();
			int time=a[0],x=a[1],y=a[2];
			for(int[] dire : dir) {
				int nrow = x + dire[0];
				int ncol = y + dire[1];
				if(nrow>=0&&nrow<n&&ncol>=0&&ncol<m&&arr[nrow][ncol]>time+1&&grid[nrow][ncol]!=2){
					arr[nrow][ncol]=time+1;
					pq.add(new int[]{time+1,nrow,ncol});
				}
			}
		}
		// if(arr[n-1][m-1]<=n+m-2)return -1;
		int ans=-1;int l=0;int r=1000000000;
		IntPredicate ok = x -> {
			if(arr[0][0]<=x)return false;
			PriorityQueue<int[]> pq1=new PriorityQueue<>(new Comparator<int[]>(){
				@Override
				public int compare(int[]a,int[]b){
					if(a[0]>b[0])return 1;
					else if(a[0]==b[0])return 0;
					else return -1;
				}
			});
			pq1.add(new int[]{x,0,0});
			int[][]arr1=new int[n][m];
			for(int i=0;i<n;i++)Arrays.fill(arr1[i],Integer.MAX_VALUE);
			while(pq1.size()>0){
				int[]a=pq1.poll();
				int time=a[0],x1=a[1],y1=a[2];
				if(x1==n-1&&y1==m-1)return true;
				for(int[] dire : dir) {
					int nrow = x1 + dire[0];
					int ncol = y1 + dire[1];
					if(nrow>=0&&nrow<n&&ncol>=0&&ncol<m&&arr1[nrow][ncol]>time+1&&grid[nrow][ncol]!=2&&(time+1<arr[nrow][ncol]||(nrow==n-1&&ncol==m-1&&time+1==arr[nrow][ncol]))){
						arr1[nrow][ncol]=time+1;
						pq1.add(new int[]{time+1,nrow,ncol});
					}
				}
			}

			return false;
		};

		while(l<=r){
			int mid=(l+r)>>>1;
			if(ok.test(mid)){
				ans=mid;l=mid+1;
			}else r=mid-1;
		}

		return ans;
	}
}