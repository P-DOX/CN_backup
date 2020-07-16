#include<stdio.h>
#include<sys/socket.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<string.h>
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
		printf("\nConnection Problem...\n");
		return 0;
	}
	char str[100]="Hello!! This is client program ...";
	char buffer[1024]={0},ch='1';
	int result;

	printf("\n***		CHAT_BOT	***\n");
	while(ch=='1')
	{
	printf("\nClient :   ");
	scanf("%s",str);
	send(socketid,str,40,0);
	read(socketid,buffer,30);
	printf("\nServer :   %s",buffer);
	bzero(str,50);	
	bzero(buffer,100);
	}
	//send(socketid,str,strlen(str),0);
	//printf("Message sent from client program ...");
	

	return 0;

}

