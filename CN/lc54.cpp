#include<iostream>
#include<vector>
using namespace std;
int main()
{
	vector<vector<int>>v {{1,2,3},
						  {4,5,6},
						  {7,8,9}};
	int i=0,j=0,total;
	int flag=0;
	total=v.size()*v[0].size();

	int r_start=0,c_start=0,r_end=v.size(),c_end=v[0].size();

	while(total>0) 
		{
			cout<<v[i][j]<<" ";
			if(flag==0)
			{
				if(j==c_end)
				{	
					j--;
					flag=1;
					c_end--;
				}
				j++;
			}

			if(flag==1)
			{
				if(i==r_end)
				{	
					i--;
					flag=2;
					r_end--;
				}
				i++;
			}

			if(flag==2)
			{
				if(j==c_start)
				{	
					j++;
					flag=3;
					c_start++;
				}
				j--;
			}

			if(flag==3)
			{
				if(j==r_start)
				{	
					i++;;
					flag=0;
					r_start++;
				}
				i--;
			}
			
			total--;				
		}
	}
