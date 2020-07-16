#include<unistd.h>
#include<stdio.h>
#include<sys/socket.h>
#include<stdlib.h>
#include<netinet/in.h>
#include<string.h>
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
	char buffer[1024]={0};
	char ch='1';

	printf("\n***		CHAT_BOT	***\n");
	while(ch=='1')
	{
	read(client_socket,buffer,40);
	printf("\nClient :   %s",buffer);
	printf("\nServer :   ");
	scanf("%s",msg);
	send(client_socket,msg,40,0);
	bzero(msg,50);	
	bzero(buffer,50);
	
	}


	printf("\nMessage sent from server ...\n");
}
