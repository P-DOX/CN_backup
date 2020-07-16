#include<iostream>
#include<fstream>
using namespace std;
int main()
{
	fstream fin;
	fin.open("s.png", ios::binary | ios::in);

	fstream fout;
	fout.open("t.png",ios::binary | ios::out);

	int* p;
	p=new int[1024];

	int d[1024];	
	cout<<sizeof(p)<<"   "<<sizeof(d);

	while(fin)
	{
		fin.read((char*)&d,sizeof(d));
		fout.write((char*)&d,sizeof(d));
	}
	

	//p = new int[8];

	//p = new int[8];

		//p=fin.read(&p,8); 
}