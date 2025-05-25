class Solution {
    private static boolean ra(char a, char b) {
		int diff = Math.abs(a - b);
		return diff == 1 || diff == 25;
	}
    public String resultingString(String s) {
        Deque<Character> stack = new ArrayDeque<>();
		for (char c : s.toCharArray()) {
			if (!stack.isEmpty() && ra(stack.peek(), c)) {
				stack.pop();
			} else {
				stack.push(c);
			}
		}
		StringBuilder result = new StringBuilder();
		while (!stack.isEmpty()) {
			result.append(stack.removeLast());
		}
		if(result.isEmpty()){
			return new String("");
		}

		return result.toString();
    }
}