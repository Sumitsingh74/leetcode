class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        return IntStream
            .rangeClosed(Arrays.stream(nums).min().getAsInt(), Arrays.stream(nums).max().getAsInt())
            .filter(x -> Arrays.stream(nums).noneMatch(n -> n == x))
            .boxed()
            .toList();
    }
}