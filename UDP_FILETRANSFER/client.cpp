#include<sys/socket.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<iostream>
#include<fstream>
using namespace std;

int main()
{
	//1. Socket creation
	int socketid = socket(AF_INET,SOCK_DGRAM,0);

	//2. Binding 
	struct sockaddr_in addr;
	addr.sin_family = AF_INET;
	addr.sin_port = htons(8767);
	addr.sin_addr.s_addr = inet_addr("127.0.0.1");

	string filename;
	cout<<"\nEnter file name : ";
	cin>>filename;

	sendto(socketid,filename.c_str(),filename.length(),0,(struct sockaddr*)&addr , sizeof(addr));

	char buff[512];
	int n = recvfrom(socketid,buff,512,0,NULL,NULL);
	string status(buff,0,n);

	if(status == "NOT_FOUND")
	{
		cout<<endl<<filename<<" NOT FOUND !! \n";
	}
	cout<<endl<<filename<<" FOUND !! \n";

	ofstream fout(filename, ios::binary);
	int data;
	while(true)
	{
		recvfrom(socketid,(char*)&data,sizeof(data),0,NULL,NULL);
		if(data==-1)
			break;
		fout.put(data);
	}

	cout<<"\nFile Downloaded !!";
	fout.flush();
	fout.close();

}
