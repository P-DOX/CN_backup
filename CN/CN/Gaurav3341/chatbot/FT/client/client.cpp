#include<stdio.h>
#include<sys/socket.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<string.h>
#include<iostream>
#include<fstream>
using namespace std;

#define PORT 7000

int main()
{
	//socket creation
	int socketid=socket(AF_INET,SOCK_STREAM,0);
	
	struct sockaddr_in server_addr;
	server_addr.sin_family=AF_INET;
	server_addr.sin_port=htons(PORT);
	server_addr.sin_addr.s_addr=inet_addr("127.0.0.1");	
	//server_addr.sin_addr.s_addr=INADDR_ANY;

	//convert IPV4 from text to binary mode
	//inet_pton(AF_INET,"127.0.0.1",&server_addr.sin_addr);

	int val=connect(socketid,(struct sockaddr *)&server_addr, sizeof(server_addr));
	if (val<0)
	{
		cout<<"\nConnection Problem...\n";
		return 0;
	}
	char str[100]="Hello!! This is client program ...";
	char buffer[1024]={0},ch='1';
	char reply;
	cout<<"\n***		FILE TRANSFER    	***\n";
	
	cout<<"\nEnter File name :  ";
	cin>>str;
	send(socketid,str,40,0);
	
	read(socketid,&reply,1);

	//cout<<reply<<endl;
	if(reply == '2')
	{
		cout<<"\nFile not found !! \n";
		return 0;
	}

	cout<<"\nProcessing your request ...\n";
	ofstream fout;
	fout.open(str,ios::binary | ios::out);

	int recv[1024];

	while(read(socketid,&recv,4096))
	{
		fout.write((char*)&recv, sizeof(recv));
		bzero(recv,sizeof(recv));
	}
	fout.close();
	cout<<"\nFile copied...\n";

	return 0;

}

