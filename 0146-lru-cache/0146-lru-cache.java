class LRUCache {
    class Node {
        int key, val;
        Node prev, next;
        Node(int k, int v) {
            key = k;
            val = v;
        }
    }


    private Node head = new Node(-1, -1);
    private Node tail = new Node(-1, -1);
    private int cap;
    private Map<Integer, Node> map = new HashMap<>();


    public LRUCache(int capacity) {
        cap = capacity;
        head.next = tail;
        tail.prev = head;
    }


    private void addNode(Node node) {
        Node temp = head.next;
        head.next = node;
        node.prev = head;
        node.next = temp;
        temp.prev = node;
    }


    private void deleteNode(Node node) {
        Node prevv = node.prev;
        Node nextt = node.next;
        prevv.next = nextt;
        nextt.prev = prevv;
    }


    public int get(int key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            deleteNode(node);
            addNode(node);
            map.put(key, head.next);
            return node.val;
        }
        return -1;
    }


    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node existing = map.get(key);
            deleteNode(existing);
            map.remove(key);
        }


        if (map.size() == cap) {
            map.remove(tail.prev.key);
            deleteNode(tail.prev);
        }


        Node newNode = new Node(key, value);
        addNode(newNode);
        map.put(key, head.next);
    }
}