#include<stdio.h>
#include<sys/socket.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<string.h>
#include<iostream>
#include<fstream>
using namespace std;
#define PORT 8080

int main()
{
	//socket creation
	int socketid=socket(AF_INET,SOCK_STREAM,0);
	
	struct sockaddr_in server_addr;
	server_addr.sin_family=AF_INET;
	server_addr.sin_port=htons(PORT);
	server_addr.sin_addr.s_addr=INADDR_ANY;

	//convert IPV4 from text to binary mode
	//inet_pton(AF_INET,"127.0.0.1",&server_addr.sin_addr);

	connect(socketid,(struct sockaddr *)&server_addr, sizeof(server_addr));

	string str="Hello!! This is client program ...";
	cout<<str<<endl;
 	ifstream infile;
  	infile.open("as.jpg",ios::binary|ios::in);
  	int buffer[100];

  	 while(infile.read((char *)&buffer,sizeof(buffer)))
  	{
      	send(socketid,&buffer,sizeof(buffer),0);
	}


	//read(socketid,&result,sizeof(result));
	//printf("\nResult : %d\n",result);

	return 0;

}

