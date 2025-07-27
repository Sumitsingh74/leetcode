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
    static class pair{
        int x,y;
        pair(int x,int y){
            this.x=x;this.y=y;
        }
    }
    public static void checkSame(TreeNode p,int depth,ArrayList<pair> list){
        int left=2*depth+1;
        int right=2*depth+2;
        if(p==null)return;
        list.add(new pair(p.val,depth));
        if(p.left!=null){
            checkSame(p.left,left,list);
        }
        if(p.right!=null){
            checkSame(p.right,right,list);
        }
    }
    public static void checkSame1(TreeNode p,int depth,ArrayList<pair> list){
        int left=2*depth+2;
        int right=2*depth+1;
        if(p==null)return;
        list.add(new pair(p.val,depth));
        if(p.right!=null){
            checkSame1(p.right,right,list);
        }
        if(p.left!=null){
            checkSame1(p.left,left,list);
        }
    }
    public boolean isSymmetric(TreeNode root) {
        ArrayList<pair>list=new ArrayList<>();
        ArrayList<pair>list1=new ArrayList<>();
        checkSame(root.left,0,list);
        checkSame1(root.right,0,list1);
        // System.out.println(list.size()+"  "+list1.size());
        if(list.size()!=list1.size())return false;
    
        for(int i=0;i<list.size();i++){
            pair p1=list.get(i);
            pair p2=list1.get(i);
            if(p1.x!=p2.x||p1.y!=p2.y)return false;
        }
        return true;
    }
}