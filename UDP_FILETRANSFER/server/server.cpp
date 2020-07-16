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
	addr.sin_addr.s_addr = INADDR_ANY;
	bind(socketid , (struct sockaddr*)&addr , sizeof(addr));

	struct sockaddr_in clientaddr;
	int clientaddrlen = sizeof(clientaddr);
	char buff[512];
	int n=0;
	n=recvfrom(socketid,buff,512,0,(struct sockaddr*)&clientaddr,(socklen_t*)&clientaddrlen);
	string filename(buff,0,n);

	string msg;
	ifstream fin(filename,ios::binary);
	if(fin.is_open())
		msg ="FOUND ";
	else
		msg = "NOT_FOUND";
	
	sendto(socketid,msg.c_str(),msg.length(),0,(struct sockaddr*)&clientaddr , sizeof(clientaddr));

	int data;
	while(true)
	{
		data = fin.get();
		sendto(socketid,(char*)&data , sizeof(data),0,(struct sockaddr*)&clientaddr ,sizeof(clientaddr));

		if(data==-1)
			break;
	}
	cout<<"\nFile sent successfully !!";

	fin.close();
}
