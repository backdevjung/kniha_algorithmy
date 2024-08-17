/*
 * Problem URL : https://www.acmicpc.net/problem/2178
 *
 * Overview
 * Input
 * N M
 * B01 B02 B03 B04.. B0M
 * ...
 * BN1 BN2 BN3 BN4.. BNM
 *
 * Bij are either 0 or 1
 *
 * Output
 * D
 *
 * Matrix B is a representation of a maze.
 * 0 means a wall and 1 means an empty cell
 *
 * Print the minimum distance of travel from cell B01 to cell BNM
 *
 * Example
 * Input
 * 4 6
 * 101111
 * 101010
 * 101011
 * 111011
 *
 * Output
 * 15
 */

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.LinkedList;
public class BOJ2178
{
	static int[][] maze, dist;
	static int N, M;
	public static void main(String[] args) throws IOException
	{
		BufferedReader $r = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stk = new StringTokenizer($r.readLine());
		String str;
		N = Integer.parseInt(stk.nextToken());
		M = Integer.parseInt(stk.nextToken());
		stk = null;
		maze = new int[N][M];
		dist = new int[N][M];
		for (int i=0; i<N; ++i)
		{
			str = $r.readLine().strip();
			for (int j=0; j<M; ++j)
			{
				maze[i][j] = str.charAt(j) - '0';
				dist[i][j] = -1;
			}
		}
		System.out.println(solve());
	}

	static int solve()
	{
		LinkedList<Integer> xq = new LinkedList<>();
		LinkedList<Integer> yq = new LinkedList<>();
		xq.add(0); yq.add(0);
		dist[0][0] = 1;
		int x,y,d,x1,y1;
		while (xq.size() > 0)
		{
			x = xq.removeFirst(); y = yq.removeFirst(); d = dist[x][y];
			for (int[] t: new int[][]{{x+1,y}, {x-1,y}, {x,y+1}, {x,y-1}})
			{
				x1 = t[0]; y1 = t[1];
				if (x1>=0 && y1>=0 && x1<N && y1<M && dist[x1][y1]==-1 && maze[x1][y1]==1)
				{
					if (x1 == N-1 && y1 == M-1)
					{
						return d+1;
					}
					xq.add(x1); yq.add(y1);
					dist[x1][y1] = d+1;
				}
			}
		}
		return 0;
	}
}

// took 1.5 hrs
