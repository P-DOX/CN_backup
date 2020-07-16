#include<iostream>
using namespace std;
int main()
{
	int arr[15]={0,0,1,1,1,0,1,1,0,0,1,0,0,0,0};
	int d[5]={1,0,1,0,1};
	int p[5]={0,0,1,1,1};
	for(int i=0;i<11;i++)
	{
		if(p[0]==1)
		{
			cout<<"\n1\n";
			for(int j=0;j<4;j++)
				p[j]=(d[j+1]+p[j+1])%2;
			p[4]=arr[i+5];
			for(int k=0;k<5;k++)
				cout<<p[k];
			cout<<endl;
		}
		else
		{
			cout<<"\n0\n";
			for(int j=0;j<4;j++)
				p[j]=p[j+1];
			p[4]=arr[i+5];
			for(int k=0;k<5;k++)
				cout<<p[k];
			cout<<endl;
		}
	}

}