/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

//approach 1 - bfs
class Solution {

   class Pair {
       TreeNode node;
       int index;

       Pair(TreeNode node, int index) {
           this.node = node;
           this.index = index;
       }

   }

   public int widthOfBinaryTree(TreeNode root) {

       Queue<Pair> que = new LinkedList<>();
       if (root == null)
           return 0;

       que.offer(new Pair(root, 0));

       int maxWidth = 0;

       while (!que.isEmpty()) {
           int minIdx = que.peek().index;
           int n = que.size();
           for (int i = 0; i < n; i++) {
               Pair front = que.poll();
               TreeNode node1 = front.node;
               int normalizedIdx = front.index - minIdx;
               if (node1.left != null) {
                   que.offer(new Pair(node1.left, (normalizedIdx * 2) + 1));
               }
               if (node1.right != null) {
                   que.offer(new Pair(node1.right, (normalizedIdx * 2) + 2));
               }

               if (i == n - 1) {
                   maxWidth = Math.max(maxWidth, front.index - minIdx + 1);
               }

           }

       }

       return maxWidth;

   }
}

 