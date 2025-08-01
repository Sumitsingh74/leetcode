<h2><a href="https://leetcode.com/problems/path-existence-queries-in-a-graph-i/">3838. Path Existence Queries in a Graph I</a></h2><h3>Medium</h3><hr><p>You are given an integer <code>n</code> representing the number of nodes in a graph, labeled from 0 to <code>n - 1</code>.</p>

<p>You are also given an integer array <code>nums</code> of length <code>n</code> sorted in <strong>non-decreasing</strong> order, and an integer <code>maxDiff</code>.</p>

<p>An <strong>undirected </strong>edge exists between nodes <code>i</code> and <code>j</code> if the <strong>absolute</strong> difference between <code>nums[i]</code> and <code>nums[j]</code> is <strong>at most</strong> <code>maxDiff</code> (i.e., <code>|nums[i] - nums[j]| &lt;= maxDiff</code>).</p>

<p>You are also given a 2D integer array <code>queries</code>. For each <code>queries[i] = [u<sub>i</sub>, v<sub>i</sub>]</code>, determine whether there exists a path between nodes <code>u<sub>i</sub></code> and <code>v<sub>i</sub></code>.</p>

<p>Return a boolean array <code>answer</code>, where <code>answer[i]</code> is <code>true</code> if there exists a path between <code>u<sub>i</sub></code> and <code>v<sub>i</sub></code> in the <code>i<sup>th</sup></code> query and <code>false</code> otherwise.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">n = 2, nums = [1,3], maxDiff = 1, queries = [[0,0],[0,1]]</span></p>

<p><strong>Output:</strong> <span class="example-io">[true,false]</span></p>

<p><strong>Explanation:</strong></p>

<ul>
	<li>Query <code>[0,0]</code>: Node 0 has a trivial path to itself.</li>
	<li>Query <code>[0,1]</code>: There is no edge between Node 0 and Node 1 because <code>|nums[0] - nums[1]| = |1 - 3| = 2</code>, which is greater than <code>maxDiff</code>.</li>
	<li>Thus, the final answer after processing all the queries is <code>[true, false]</code>.</li>
</ul>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">n = 4, nums = [2,5,6,8], maxDiff = 2, queries = [[0,1],[0,2],[1,3],[2,3]]</span></p>

<p><strong>Output:</strong> <span class="example-io">[false,false,true,true]</span></p>

<p><strong>Explanation:</strong></p>

<p>The resulting graph is:</p>

<p><img alt="" src="https://assets.leetcode.com/uploads/2025/03/25/screenshot-2025-03-26-at-122249.png" style="width: 300px; height: 170px;" /></p>

<ul>
	<li>Query <code>[0,1]</code>: There is no edge between Node 0 and Node 1 because <code>|nums[0] - nums[1]| = |2 - 5| = 3</code>, which is greater than <code>maxDiff</code>.</li>
	<li>Query <code>[0,2]</code>: There is no edge between Node 0 and Node 2 because <code>|nums[0] - nums[2]| = |2 - 6| = 4</code>, which is greater than <code>maxDiff</code>.</li>
	<li>Query <code>[1,3]</code>: There is a path between Node 1 and Node 3 through Node 2 since <code>|nums[1] - nums[2]| = |5 - 6| = 1</code> and <code>|nums[2] - nums[3]| = |6 - 8| = 2</code>, both of which are within <code>maxDiff</code>.</li>
	<li>Query <code>[2,3]</code>: There is an edge between Node 2 and Node 3 because <code>|nums[2] - nums[3]| = |6 - 8| = 2</code>, which is equal to <code>maxDiff</code>.</li>
	<li>Thus, the final answer after processing all the queries is <code>[false, false, true, true]</code>.</li>
</ul>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= n == nums.length &lt;= 10<sup>5</sup></code></li>
	<li><code>0 &lt;= nums[i] &lt;= 10<sup>5</sup></code></li>
	<li><code>nums</code> is sorted in <strong>non-decreasing</strong> order.</li>
	<li><code>0 &lt;= maxDiff &lt;= 10<sup>5</sup></code></li>
	<li><code>1 &lt;= queries.length &lt;= 10<sup>5</sup></code></li>
	<li><code>queries[i] == [u<sub>i</sub>, v<sub>i</sub>]</code></li>
	<li><code>0 &lt;= u<sub>i</sub>, v<sub>i</sub> &lt; n</code></li>
</ul>
