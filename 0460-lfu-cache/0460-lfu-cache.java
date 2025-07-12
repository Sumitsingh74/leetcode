class LFUCache {

    class Node {
			int key, val,cou;
			Node prev, next;
			Node(int k, int v,int cou) {
				key = k;
				val = v;
				this.cou=cou;
			}

			@Override
			public String toString() {
				return key+"  "+val+"  "+cou;
			}
		}


		private Node head = new Node(-1, -1,0);
		private Node tail = new Node(-1, -1,1);
		private int cap;
		private Map<Integer, Node> map = new HashMap<>();
		private TreeMap<Integer,Node> cnt=new TreeMap<>();


		public LFUCache(int capacity) {
			cap = capacity;
			head.next = tail;
			tail.prev = head;
			cnt.put(1,tail);
		}


		private void addNode(Node node) {
//			System.out.println(node.toString());
			int d=cnt.lowerKey(node.cou+1);
			Node h=cnt.get(d);
			Node temp = h.prev;
			h.prev = node;
			node.prev = temp;
			node.next = h;
			temp.next = node;
			cnt.put(node.cou,node);
		}


		private void deleteNode(Node node) {
			if(cnt.containsKey(node.cou)&&cnt.get(node.cou)==node){
				cnt.remove(node.cou);
				if(node.next.cou==node.cou){
					cnt.put(node.cou,node.next);
				}
			}
			Node prevv = node.prev;
			Node nextt = node.next;
			prevv.next = nextt;
			nextt.prev = prevv;
		}


		public int get(int key) {
			if (map.containsKey(key)) {
				Node node = map.get(key);
				deleteNode(node);
				Node nnode=new Node(node.key,node.val,node.cou+1);
				addNode(nnode);
				map.put(key, nnode);
				return node.val;
			}
			return -1;
		}


		public void put(int key, int value) {
			int cou=0;
			if (map.containsKey(key)) {
				Node existing = map.get(key);
				cou=existing.cou;
				deleteNode(existing);
				map.remove(key);
			}
			cou++;


			if (map.size() == cap) {
				map.remove(tail.prev.key);
				deleteNode(tail.prev);
			}

			Node newNode = new Node(key, value,cou);
			addNode(newNode);
			map.put(key,newNode);
		}
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */