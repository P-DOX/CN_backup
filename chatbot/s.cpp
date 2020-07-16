#include<sys/socket.h>
#include<arpa/inet.h>
#include<iostream>
using namespace std;
int main()
{
	int socketid=socket(AF_INET,SOCK_STREAM,0);

	struct sockaddr_in server_addr;
	server_addr.sin_family=AF_INET;
	server_addr.sin_port=htons(7000);
	server_addr.sin_addr.s_addr=INADDR_ANY;

	bind(socketid,(struct sockaddr*)&server_addr,sizeof(server_addr));
	listen(socketid,5);

	int client = accept(socketid,NULL,NULL);

	char msg[100];
	cout<<"Enter message for client : ";
	cin>>msg;
	send(client,msg,40,0);
	cout<<"\nMessage sent to client";
}