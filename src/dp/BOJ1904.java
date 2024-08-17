/*
 * Problem URL: https://www.acmicpc.net/problem/1904
 * Input
 * N
 * 
 * Output
 * R
 *
 * We are given an integer N. (1<=N<=1,000,000)
 * We need to find the number of ways to make a binary number of length N, using "00" and "1"s
 * For example, for N=1, only 1 is valid.
 * for N=2, both 00, 11 are valid.
 * for N=4, 0011, 0000, 1001, 1100, 1111 are valid.
 * The output should be (the number of ways to make n-digit binary numbers using 00, 1) % 15746
 * Be sure to do modulo 15746 calculation at the end.
 *
 * Samples
 * 16557 -> 13389, 40414 -> 585, 20106 -> 9549, 1595 -> 7924, 40537 -> 14147,
 * 7269 -> 4731, 12065 -> 1268, 99475 -> 10445, 64999 -> 5681 2829 -> 4075,
 * 58307 -> 11202, 43268 -> 1246, 37566 -> 7865, 64396 -> 8811
 */
package dp;
import java.util.Scanner;
public class BOJ1904
{
	public static void main(String[] args)
	{
		Scanner $s = new Scanner(System.in);
		final int N = $s.nextInt();
		$s.close(); $s = null;
		DP1904 solver = new DP1904(N);
		System.out.println(solver.solve(N) % 15746);
		solver = null;
	}
}

class DP1904
{
	private long[] ways;

	/*
	 * ways[n+2] = ways[n+1] + ways[n]
	 * We can think of 6 scenarios to build a string of length n+2
	 * 1) append 00 to a unique string of length n
	 * 2) append 11 to a unique string of length n
	 * 3) append 1 to a unique string of length (n+1)
	 * 
	 * Scenario 4~6 would be Scenario 1~3 but we append to the left, instead of right.
	 * However, Scenario 4~6 would be duplicates with Scenario 1~3. So throw them away.
	 * Also, Scenario 2) is duplicated in Scenario 3). So throw away Scenario 2).
	 * We add Scenario 1) and 3).
	 */

	DP1904(int n)
	{
		ways = new long[n];
		ways[0] = 1; ways[1] = 2;
		for (int i=0; i+2<n; ++i)
		{
			ways[i+2] = ways[i+1] + ways[i];
		}
	}

	long solve(int n) throws IndexOutOfBoundsException
	{
		return ways[n-1];
	}
}
