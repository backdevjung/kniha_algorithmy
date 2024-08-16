/*
 * Problem URL: https://www.acmicpc.net/problem/24416
 * Input
 * N
 * Output
 * R D
 * We are given 2 psuedo code algorithms for getting nth Fibonacci number.
 * First is recursive. Show how much times the function was called to get 
 * nth Fibonacci number.
 * Second is DP iterative solution. Show how much iterations it took to get
 * nth Fibonacci number.
 */
package dp;
import java.util.Scanner;
public class BOJ24416
{
	static int R, D;
	static
	{
		R = 0; D = 0;
	}
	public static void main(String[] args)
	{
		Scanner $s = new Scanner(System.in);
		final int N = $s.nextInt();
		$s.close(); $s = null;
		int i = fib(N);
		i = fibonacci(N);
		System.out.println(R + " " + D);
	}

	static int fib(int n)
	{
		if (n==1 || n==2)
		{
			++R;
			return 1;
		}
		return (fib(n-1)+fib(n-2));
	}

	static int fibonacci(int n)
	{
		int[] f = new int[n];
		f[0] = 1; f[1] = 1;
		for (int i=2; i<n; ++i)
		{
			D++;
			f[i] = f[i-1] + f[i-2];
		}
		return f[n-1];
	}
}
