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
class Solution {
    public static int[]dr(TreeNode root){
        int[]arr=new int[2];
        arr[0]+=root.val;
        if(root.right!=null){
            int[]t=dr(root.right);
            arr[0]+=t[1];
            arr[1]+=Math.max(t[0],t[1]);
        }
        if(root.left!=null){
            int[]t=dr(root.left);
            arr[0]+=t[1];
            arr[1]+=Math.max(t[0],t[1]);
        }
        return arr;
    } 
    public static int rob(TreeNode root) {
        int[]arr=dr(root);
        return Math.max(arr[0],arr[1]);
    }
}