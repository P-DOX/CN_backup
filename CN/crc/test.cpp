#include<iostream>
#include<string.h>
using namespace std;

int* dectobin(int n)
{
	
	int* ar=new int[8];
	for(int j=0;j<8;j++)
		ar[j]=0;

	int i=0;
	while(n>0)
	{
		ar[i++]=n%2;
		n=n/2;
	}

	return ar;
}

int main()
{
	char str[10]="ABC";
	int* arr = new int[56];
	int k=0;
	for(int i=0;i<strlen(str);i++)
	{	
		int* arr1=dectobin(str[i]);
		for(int j=7;j>=0;j--)
			arr[k++]=arr1[j];
	}

	cout<<arr<<" "<<sizeof(arr)<<endl;

	for(int j=0;j<k;j++)
	{
		cout<<arr[j];
	}
}