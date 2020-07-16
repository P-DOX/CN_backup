import java.util.*;
import java.io.*;

class linkstate
{
	static int nearest(int[] vis, int[] dis)
	{
		int min = Integer.MAX_VALUE;
		int ind=0;
		for(int i=0;i<5;i++)
		{
			if(vis[i]==0 && dis[i]<=min)
			{
				min=dis[i];
				ind = i ;
			}
		}
		return ind;
	}

	public static void main(String args[])
	{
		int adj[][] = {{0,3,2,0,0},{3,0,0,1,3},{2,0,0,1,0},{0,1,1,0,1},{0,3,0,1,0}};
		int n=5;
		int ind;

		int dis[] = new int[n];
		int vis[] = new int[n];
		int par[] = new int[n];

		for(int i=0;i<n;i++)
		{
			dis[i]=Integer.MAX_VALUE;
			par[i]=-2;
		}
		dis[0]=0;
		for(int i=0;i<n-1;i++)
		{
			ind=nearest(vis,dis);
			vis[ind]=1;

			for(int j=0;j<n;j++)
			{
				if((vis[j]==0) && (adj[ind][j]!=0) && (dis[ind]+adj[ind][j]<dis[j]))
				{
					dis[j]=dis[ind]+adj[ind][j];
					if(par[j]==-2)
						par[j]=-1;
					else if(par[j]=-1)
						par[j]=ind;
					else
						par[j]=par[ind];
				}
			}
		}
		


		for(int i=0;i<n;i++)
		{
			System.out.println(i+" "+dis[i]+" "+par[i]);
		}



	}
}