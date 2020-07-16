#include<sys/socket.h>
#include<arpa/inet.h>
#include<iostream>
#include<unistd.h>
#include<string.h>
using namespace std;
int main()
{
	//1. Socket Creation
	int socketid = socket(AF_INET,SOCK_STREAM,0);

	//2. Connecting to server
	struct sockaddr_in addr;
	addr.sin_family = AF_INET;
	addr.sin_port = htons(7000);
	addr.sin_addr.s_addr = inet_addr("127.0.0.1");
	connect(socketid, (struct sockaddr*)&addr , sizeof(addr));

	char msg[40];
	while(true)
	{
		read(socketid , msg,40);
		cout<<"\nServer : "<<msg;
		bzero(msg,40);
		cout<<"\nClient : ";
		cin>>msg;
		send(socketid,msg,40,0);
		bzero(msg,40);
	}

}