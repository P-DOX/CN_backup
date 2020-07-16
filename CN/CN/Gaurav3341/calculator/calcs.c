#include<unistd.h>
#include<stdio.h>
#include<sys/socket.h>
#include<stdlib.h>
#include<netinet/in.h>
#include<string.h>
#define PORT 6502

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
	char buffer[1024]="\nEnter expression in the form num1operatornum2 \n eg: 45+854";
	
	
	
while(1)
{
	read(client_socket,buffer,100);
	printf("%s\n",buffer);
	int i=0,a=0,b=0,res;

	while(buffer[i]>=48 && buffer[i]<=57)
	{
		a=a*10+(buffer[i]-48);
		i++;
	}
	char ch=buffer[i];
	i++;

	while(buffer[i]>=48 && buffer[i]<=57)
	{
		b=b*10+(buffer[i]-48);
		i++;
	}
	
	if(ch=='*')
		res=a*b;
	else if(ch=='/')
		res=a/b;
	else if(ch=='+')
		res=a+b;
	else if(ch=='-')
		res=a-b;

	printf("Result : %d\n",res);

	send(client_socket,&res,sizeof(res),0);
	res=0;
}
	printf("\nMessage sent from server ...\n");
	close(client_socket);
	close(socketid); 
}
