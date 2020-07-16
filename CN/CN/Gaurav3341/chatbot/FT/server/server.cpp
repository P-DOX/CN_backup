#include<unistd.h>
#include<stdio.h>
#include<sys/socket.h>
#include<stdlib.h>
#include<netinet/in.h>
#include<string.h>
#include<iostream>
#include<fstream>
using namespace std;

#define PORT 7000

int main()
{
	int socketid=socket(AF_INET,SOCK_STREAM,0);

	struct sockaddr_in server_addr;
	server_addr.sin_family=AF_INET;
	server_addr.sin_port=htons(PORT);
	server_addr.sin_addr.s_addr=INADDR_ANY;

	bind(socketid,(struct sockaddr *)&server_addr,sizeof(server_addr));
	listen(socketid,5);

	int client_socket=accept(socketid,NULL,NULL);

	char msg[100]="Hello!! This is server side ...";
	char buffer[512]={0};
	char y='1',n='2';
	int transfer[1024];

	ifstream file;
	
	cout<<"\n***		FILE TRANSFER	***\n";
	
	read(client_socket,buffer,40);
	file.open(buffer,ios::binary | ios::in);

	if(!file)
	{
		send(client_socket,&n,sizeof(n),0);
	}
	else
	{	
		send(client_socket,&y,sizeof(y),0);
		cout<<"Transfering "<<buffer<<" file to client...\n";
		while(file)
		{
			file.read((char*)&transfer,sizeof(transfer));
			send(client_socket,&transfer,sizeof(transfer),0);
			bzero(&transfer,sizeof(transfer));
		}
		
		file.close();
	}

	cout<<"\nMessage sent from server ...\n";
}
