 class MedianFinder {
		static ArrayList<Integer> list;
		public MedianFinder() {
		list=new ArrayList<>();
		}

		public void addNum(int num) {
			if(list.isEmpty())list.add(num);
			else if(list.get(0)>=num)list.add(0,num);
			else if (list.get(list.size()-1)<=num) {
				list.add(num);
			}else {
				int l=0;int r=list.size()-1;int an=0;
				while (l<=r){
					int mid=(l+r)/2;
					if(list.get(mid)<=num)l=mid+1;
					else {
						an=mid;r=mid-1;
					}
				}
				list.add(an,num);
			}
		}

		public double findMedian() {
			double ans=0;
			if(list.size()%2==1)ans=list.get(list.size()/2);
			else ans=(list.get(list.size()/2)+list.get(list.size()/2-1))/2.0;
			return ans;
		}
	}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */