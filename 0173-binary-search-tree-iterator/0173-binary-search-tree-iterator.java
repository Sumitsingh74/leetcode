import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; }
}

class BSTIterator {
    private List<Integer> inorderList;
    private int index;

    public BSTIterator(TreeNode root) {
        inorderList = new ArrayList<>();
        index = 0;
        inorder(root);
    }

    private void inorder(TreeNode node) {
        if (node == null) return;
        inorder(node.left);
        inorderList.add(node.val);
        inorder(node.right);
    }

    public int next() {
        return inorderList.get(index++);
    }

    public boolean hasNext() {
        return index < inorderList.size();
    }
}
