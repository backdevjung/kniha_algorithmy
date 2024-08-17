/*
 * Problem URL: https://www.acmicpc.net/problem/9184
 * Input
 * a0 b0 c0
 * a1 b1 c1
 * ...
 * ...
 * -1 -1 -1
 * 
 * Output
 * w(a0, b0, c0) = w0
 * w(a1, b1, c1) = w1
 * ...
 * ...
 *
 * w(a,b,c) is defined recursively. change it to better solution
 * and find w(a,b,c) for each line of input except the last line 
 * which is -1 -1 -1
 *
 * w(a,b,c) definition:
 * if a<=0 or b<=0 or c<=0, w(a,b,c)=1
 * if a>20 or b>20 or c>20, w(a,b,c)=w(20,20,20)
 * if a<b and b<c, w(a,b,c)=w(a,b,c-1)+w(a,b-1,c-1)-w(a,b-1,c)
 * else, w(a,b,c)=w(a-1,b,c)+w(a-1,b-1,c)+w(a-1,b,c-1)-w(a-1,b-1,c-1)
 */
package dp;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class BOJ9184
{
	static int[][][] w;
	static boolean[][][] done;

	static
	{
		w = new int[21][21][21];
		int i, j;
		for (int $=0; $<21*21; ++$)
		{
			i = $/21; j = $%21;
			w[0][i][j] = 1;
			w[i][0][j] = 1;
			w[i][j][0] = 1;
		}
		int a, b, c, t;
		for (int n=1; n<=20; ++n)
		{
			for (int $=0; $<21*21; ++$)
			{
				i = $/21; j = $%21;
				a = n; b = i; c = j;
				for (int x=0; x<3; ++x)
				{
					if (w[a][b][c] == 0) w[a][b][c] = (a<b && b<c) ? w[a][b][c-1]+w[a][b-1][c-1]-w[a][b-1][c] : w[a-1][b][c]+w[a-1][b-1][c]+w[a-1][b][c-1]-w[a-1][b-1][c-1];
					t = c; c = a; a = b; b = t; 
				}
			}
		}
	}

	static int compute(int a, int b, int c)
	{
		if (a<=0 || b<=0 || c<=0) return 1;
		if (a>20 || b>20 || c>20) return w[20][20][20];
		return w[a][b][c];
	}

	public static void main(String[] args) throws IOException
	{
		BufferedReader $r = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter $w = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer stk; int a,b,c;
		while (true)
		{
			stk = new StringTokenizer($r.readLine());
			a = Integer.parseInt(stk.nextToken()); b = Integer.parseInt(stk.nextToken()); c = Integer.parseInt(stk.nextToken());
			if (a==-1 && b==-1 && c==-1) break;
			$w.write("w("+a+", "+b+", "+c+") = "+compute(a,b,c)+"\n");
		}
		$w.flush(); $w.close();
		$r.close(); $r = null; $w = null;
	}
}
