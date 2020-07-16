import java.util.*;
import java.io.*;

class link_state_routing
{
	static Scanner in = new Scanner(System.in);

	static int nearest_node(int[] vis , int[] min)
	{
		int n = vis.length;
		int mini = Integer.MAX_VALUE;
		int ind=0;
		for(int i=0;i<n;i++)
		{
			if(min[i]<=mini && vis[i]==0)
			{
				mini = min[i];
				ind = i;
			}
		}
		return ind;
	}

	static void dijkstra(int[][] adj , int n)
	{
		int ind;
		int vis[] = new int[n];
		int dis[] = new int[n];
		int par[] = new int[n];

		for(int i=0;i<n;i++)
			dis[i] = Integer.MAX_VALUE;
		
		// vis[0]=1;
		dis[0]=0;
		for(int i=0;i<n-1;i++)
		{
			ind=nearest_node(vis,dis);
			vis[ind]=1;

			for(int j=0 ; j<n;j++)
			{
				if((vis[j]==0) && (adj[ind][j]!=0) && dis[ind]+adj[ind][j]<dis[j])
				{
					dis[j]=dis[ind]+adj[ind][j];
					par[j]=ind;
				}
			}
		}
		System.out.println("------------------------------");
		System.out.println(" Node |"+"| Distance |"+"| Neighbour");
		System.out.println("------------------------------");
		for(int i=0;i<n;i++)
			System.out.println(" "+i+"        "+dis[i]+"         "+par[i]);
	}

	public static void main(String args[])
	{
		System.out.print("Enter number of nodes : ");
		int n = in.nextInt();
		System.out.print("Enter number of edges : ");
		int m = in.nextInt();
		int adj[][] = new int[n][n];
		int s,e,v;
		System.out.println("Enter edges : ");
		for(int i=0 ; i<m ; i++)
		{
			s = in.nextInt();
			e = in.nextInt();
			v = in.nextInt();
			adj[s][e] = v;
			adj[e][s] = v;
		}

		dijkstra(adj,n);
	}

} 