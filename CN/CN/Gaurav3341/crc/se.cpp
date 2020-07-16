#include<unistd.h>
#include<stdio.h>
#include<sys/socket.h>
#include<stdlib.h>
#include<netinet/in.h>
#include<string.h>
#include<iostream>
#include<fstream>
#define PORT 8080

using namespace std;

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
	
	ofstream outfile;
  	outfile.open("te.jpg",ios::binary|ios::out);

	string msg="Hello!! This is server side ...";
	cout<<msg<<endl;

	int buffer[100];
	while(read(client_socket,buffer,100))
	{
		outfile.write((char *)&buffer,sizeof(buffer));
	}
	
	
	printf("\nMessage sent from server ...\n");
}