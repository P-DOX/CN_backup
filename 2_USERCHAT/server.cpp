#include<sys/socket.h>
#include<arpa/inet.h>
#include<iostream>
#include<unistd.h>
#include<string.h>
using namespace std;
int main()
{
	//1. Socket creation
	int socketid = socket(AF_INET,SOCK_STREAM,0);

	//2. Binding address and port
	struct sockaddr_in addr;
	addr.sin_family = AF_INET;
	addr.sin_port = htons(7000);
	addr.sin_addr.s_addr = INADDR_ANY;
	bind(socketid, (struct sockaddr*)&addr, sizeof(addr));
	
	//3. Listening on the port
	listen(socketid,5);

	//4. Accepting connection from client
	int client = accept(socketid,NULL,NULL);

	char msg[512];
	while(true)
	{
		cout<<"\nServer : ";
		cin>>msg;
		send(client,msg,40,0);
		bzero(msg,40);
		read(client,msg,40);
		cout<<"\nClient : "<<msg;
		bzero(msg,40);
	}


}