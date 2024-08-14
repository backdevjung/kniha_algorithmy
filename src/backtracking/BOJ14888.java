/*
 * Problem in URL: https://www.acmicpc.net/problem/14888
 * Problem Overview
 * Input Format:
 * N
 * A1 A2 ... AN
 * B1 B2 B3 B4
 *
 * Output Format:
 * MAX
 * MIN
 *
 * Integer sequence A of length N, and integer sequence B of length 4 are given.
 *
 * We are going to make a value by putting operator (+,-,×,÷) in between each numbers of sequence A.
 *
 * Sequence B indicates how many of each operator signs we have initially.
 *
 * The sum of the 4 numbers in sequence B equals N-1.
 *
 * The goal of this program is to find a maximum possible result from such equations and a minimum result.
 *
 * Example1:
 * Input
 * 2
 * 5 6
 * 0 0 1 0
 *
 * Output
 * 30
 * 30
 *
 * Example2:
 * Input
 * 3
 * 3 4 5
 * 1 0 1 0
 *
 * Output
 * 35
 * 17
 *
 * Example3:
 * Input
 * 6
 * 1 2 3 4 5 6
 * 2 1 1 1
 * 
 * Ouput
 * 54
 * -24
 */

package backtracking;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;
public class BOJ14888
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader $r = new BufferedReader(new InputStreamReader(System.in));
		final int N = Integer.parseInt($r.readLine().strip());
		StringTokenizer stk = new StringTokenizer($r.readLine());
		final int[] A = new int[N], B = new int[4];
		for (int i=0; i<N; ++i) A[i] = Integer.parseInt(stk.nextToken());
		stk = new StringTokenizer($r.readLine());
		for (int i=0; i<4; ++i) B[i] = Integer.parseInt(stk.nextToken());
		$r.close(); $r = null;
		BackTrack14888 sol = new BackTrack14888(A, B);
		System.out.println(sol.max());
		System.out.println(sol.min());
	}
}

/*
 * State variables:
 * 1. int val <- calculated value so far
 * 2. int rem 1~4 <- remaining operators (+,-,×,÷) : [2,3,0,0] means there are 2 +s and 3 -s left.
 * 3. int index <- which index are we at. N-1 would be the leaf node of the state tree.
 * -> All the state variables will be stored in a Tuple.
 *
 * Main loop:
 * DFS using a stack
 *
 * Note: Assume ÷ performs type conversion from floating point to number to decimal integer after division.
 */

class BackTrack14888
{
	private int[] A, B;
	private boolean isSolved;
	private int max, min;

	{
		isSolved = false;
		max = Integer.MIN_VALUE;
		min = Integer.MAX_VALUE;
	}

	BackTrack14888(int[] A, int[] B)
	{
		this.A = A; this.B = B;
	}

	private void sol()
	{
		if (isSolved) return;
		isSolved = true;
		final int N = A.length;
		LinkedList<StateTuple> stack = new LinkedList<>();
		stack.add(new StateTuple(A[0], B[0], B[1], B[2], B[3], 0));
		int val, rem1, rem2, rem3, rem4, index;
		while (stack.size()>0)
		{
			StateTuple stp = stack.removeLast();
			val = stp.val; rem1 = stp.rem1; rem2 = stp.rem2;
			rem3 = stp.rem3; rem4 = stp.rem4; index = stp.index;

			if (index == N-1)
			{
				max = (max < val) ? val : max;
				min = (min < val) ? min : val;
				continue;
			}

			if (rem1 > 0)
			{
				stack.addLast(new StateTuple(val+A[index+1], rem1-1, rem2, rem3, rem4, index+1));
			}

			if (rem2 > 0)
			{
				stack.addLast(new StateTuple(val-A[index+1], rem1, rem2-1, rem3, rem4, index+1));
			}

			if (rem3 > 0)
			{
				stack.addLast(new StateTuple(val*A[index+1], rem1, rem2, rem3-1, rem4, index+1));
			}

			if (rem4 > 0 && A[index+1] != 0)
			{
				stack.addLast(new StateTuple(val/A[index+1], rem1, rem2, rem3, rem4-1, index+1));
			}
		}
	}

	int max()
	{
		sol();
		return max;
	}

	int min()
	{
		sol();
		return min;
	}

	class StateTuple
	{
		final int val, rem1, rem2, rem3, rem4, index;

		StateTuple(int v, int r1, int r2, int r3, int r4, int i)
		{
			val = v;
			rem1 = r1; rem2 = r2; rem3 = r3; rem4 = r4;
			index = i;
		}
	}
}
