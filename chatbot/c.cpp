#include<sys/socket.h>
#include<arpa/inet.h>
#include<iostream>

using namespace std;
int main()
{
	int socketid=socket(AF_INET,SOCK_STREAM,0);
	
	struct sockaddr_in server_addr;
	server_addr.sin_family = AF_INET;
	server_addr.sin_port = htons(7000);
	server_addr.sin_addr.s_addr = inet_addr("127.0.0.1");

	int server = connect(socketid,(struct sockaddr*)&server_addr,sizeof(server_addr));

	char msg[100];
	int c = read(socketid,msg,40);
	cout<<"Message : "<<msg<<endl;

}