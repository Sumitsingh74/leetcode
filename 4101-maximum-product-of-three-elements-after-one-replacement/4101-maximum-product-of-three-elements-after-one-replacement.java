class Solution {
   long maxProduct(int[] nums) {
    Integer[] a = Arrays.stream(nums).boxed().toArray(Integer[]::new);
    Arrays.sort(a, (u, v) -> Integer.compare(Math.abs(v), Math.abs(u))); // sort by |x| desc
    return 100000L * (long)Math.abs(a[0]) * (long)Math.abs(a[1]);
}
}