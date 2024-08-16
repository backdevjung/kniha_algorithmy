/*
 * Problem URL: https://www.acmicpc.net/problem/14889
 * Problem Overview
 *
 * Input
 * N
 * A11 A12 .... A1N
 * A21 A22 .... A2N
 * ...
 * AN1 AN2 .... ANN
 *
 * Output
 * MINDIFF
 *
 * We are given a N*N integer matrix. All the 'Aii'(major diagonal elements) are 0.
 * N is guaranteed to be an even number.
 * Divide N into 2 groups of equal size (N/2 and N/2)
 * and then find the difference of the 'scores' of the 2 groups and find the minimum of
 * such difference, considering all possible group combinations. (Difference is in absolute value form.)
 *
 * Definition of 'score' of group 1:
 * for any i, j that are in group 1,
 * add Aij to the score. The group 1 score is sum of Aij where i, j both belong to group 1.
 *
 * Assume 4<=N<=20
 *
 * Example 1
 * Input
 * 4
 * 0 1 2 3
 * 4 0 5 6
 * 7 1 0 2
 * 3 4 5 0
 *
 * Output
 * 0
 *
 * Example 2
 * Input
 * 6
 * 0 1 2 3 4 5
 * 1 0 2 3 4 5
 * 1 2 0 3 4 5
 * 1 2 3 0 4 5
 * 1 2 3 4 0 5
 * 1 2 3 4 5 0
 *
 * Output
 * 2
 *
 * Example 3
 * Input
 * 8
 * 0 5 4 5 4 5 4 5
 * 4 0 5 1 2 3 4 5
 * 9 8 0 1 2 3 1 2
 * 9 9 9 0 9 9 9 9
 * 1 1 1 1 0 1 1 1
 * 8 7 6 5 4 0 3 2
 * 9 1 9 1 9 1 0 9
 * 6 5 4 3 2 1 9 0
 *
 * Output
 * 1
 */

package backtracking;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BOJ14889
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader $r = new BufferedReader(new InputStreamReader(System.in));
		final int N = Integer.parseInt($r.readLine().strip());
		StringTokenizer stk;
		final int[][] A = new int[N][N]; 
		for (int i=0; i<N; ++i)
		{
			stk = new StringTokenizer($r.readLine());
			for (int j=0; j<N; ++j)
			{
				A[i][j] = Integer.parseInt(stk.nextToken());
			}
		}
		$r.close(); $r = null; stk = null;
		
		BackTrack14889 sol = new BackTrack14889(A);

		System.out.println(sol.min());
		sol = null;
	}
}

class BackTrack14889
{
	private final int[][] A;
	private int min = Integer.MAX_VALUE;

	BackTrack14889(int[][] a)
	{
		A = a;
	}

	int min()
	{
		sol();
		return min;
	}

	/*
	 * State variable 
	 * int sum1 <- sum of all Aij such that i, j are in group 1
	 * int sum2 <- sum of all Aij such that i, j are in group 2
	 * int rem1 <- counter that tracks how many more need to go in group   1
	 * int index <- shows upto which row/col index of A[][] was processed.
	 * (invariant: rem1 + rem2 + index == N-1)
	 * if index == N-1, that means we reached a leaf node
	 * int group1 <- shows which row/col are added in group1 (bitmap)
	 * we will be making a tuple of these 5 integers and push it to a stack.
	 */
	void sol()
	{
		final int SIZE = A.length/2;
		LinkedList<StateTuple> stack = new LinkedList<>();
		stack.add(new StateTuple(0, 0, SIZE-1, 0, 1)); /* row index 0 was put in group1 */
		stack.add(new StateTuple(0, 0, SIZE, 0, 0)); /* row index 0 was put in group2 */

		StateTuple stp;
		while (stack.size() > 0)
		{
			stp = stack.removeLast();
			final int sum1 = stp.sum1, sum2 = stp.sum2, rem1 = stp.rem1, index = stp.index, group1 = stp.group1;
			final int rem2 = 2*SIZE-1-rem1-index;
				
			if (index == 2*SIZE-1)
			{
				int diff;
				min = ((diff=Math.abs(sum1-sum2)) < min) ? diff : min;
				continue;
			}
			
			int delta1 = 0, delta2 = 0;
			for (int offset=0; offset<=index; ++offset)
			{
				if ((1<<offset & group1) > 0)
				{
					delta1 += A[offset][index+1] + A[index+1][offset];
				}
				else
				{
					delta2 += A[offset][index+1] + A[index+1][offset];
				}
			}
			if (rem1 > 0) stack.add(new StateTuple(sum1+delta1, sum2, rem1-1, index+1, (1<<(index+1) | group1)));
			if (rem2 > 0) stack.add(new StateTuple(sum1, sum2+delta2, rem1, index+1, group1));
		}
	}

	class StateTuple
	{
		final int sum1, sum2, rem1, index, group1;

		StateTuple(int s1, int s2, int r1, int i, int g1)
		{
			sum1 = s1; sum2 = s2; rem1 = r1; index = i; group1 = g1;
		}
	}
}
