package chapter7.dataStructure;

import java.util.Stack;

/**
 * 84. Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.


Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].


The largest rectangle is shown in the shaded area, which has area = 10 unit.

For example,
Given heights = [2,1,5,6,2,3],
return 10.
 * @author Lei
 *
 */
public class LargestRectangleInHistogram {
    // Brute Force
    public int largestRectangleAreaBruteForce(int[] heights) {
        int maxSquare = 0;
        for (int i = 0; i < heights.length; i++) { // Start, include i
            for (int j = i + 1; j <= heights.length; j++) { // End, not include j
                int minHeight = heights[i]; // Initialization
                for (int k = i; k < j; k++) { // Find the lowest between [i, j)
                    minHeight = Math.min(minHeight, heights[k]);
                }
                
                // After we got min height, calculate square
                maxSquare = Math.max(maxSquare, (j - i) * minHeight);
            }
        }
        
        return maxSquare;
    }
    
    // With 2 for loops
    public int largestRectangleAreaBetter(int[] heights) {
        int maxSquare = 0;
        for (int i = 0; i < heights.length; i++) { // Start, include i
            int minHeight = heights[i];
            for (int j = i + 1; j <= heights.length; j++) { // End, not include j
                minHeight = Math.min(minHeight, heights[j - 1]);
                
                // After we got min height, calculate square
                maxSquare = Math.max(maxSquare, (j - i) * minHeight);
            }
        }
        
        return maxSquare;
    }
    
    /**
     * Maintain an incremental 
stack:
(1) if a[i] > stack top: 
push a[i] to stack
(2) if a[i] <= stack top: 
keep poping element out from 
stack until the top of stack is 
smaller than current.
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        int max = 0;
        
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i <= heights.length; i++) { // Include the very end, make it special as -1
            int curHeight = (i == heights.length) ? -1 : heights[i];

            while (!stack.isEmpty() && curHeight <= heights[stack.peek()]) { // 下降趋势，左边的比当前的高, 进行计算
                int h = heights[stack.pop()]; // Must not be empty this time
                
                // stack empty means 左边的全都比当前的高. stack.peek()代表的是左边的一个，所以-1
                int w = stack.isEmpty() ? i : i - stack.peek() - 1;
                
                max = Math.max(max, h * w);
            }
            
            stack.push(i);
        }
        
        return max;
    }
}
