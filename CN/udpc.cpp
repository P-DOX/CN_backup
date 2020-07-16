#include<iostream>
#include<fstream>
#include<string.h>
#include<arpa/inet.h>
#include<netinet/in.h>
#include<sys/socket.h>
#include<unistd.h>
using namespace std;

int main(int argc, char const * argv[]){
	struct sockaddr_in addrport,clientAddr;
	int addrlen  =sizeof(addrport);
	int sockid = socket(AF_INET,SOCK_DGRAM,0);
	if(sockid < 0 ){
		perror("Socket Failed!");
		exit(EXIT_FAILURE);
	}
	addrport.sin_family = AF_INET;
	addrport.sin_port = htons(8830);
	addrport.sin_addr.s_addr = htonl(INADDR_ANY);

	
	unsigned int l=sizeof(clientAddr);
	char msg[1024];
	cout<<"Enter File : ";
	cin>>msg;
	int size;
	sendto(sockid,&msg,sizeof(msg),0,(struct sockaddr *) &addrport,sizeof(addrport));
	char a[500]; 
	
	
	ofstream file;	
	file.open("file",ios::binary);
	while(recvfrom(sockid,(char *)a,500,0 ,(struct sockaddr *) &clientAddr,&l)){
		file.write((char*)&a,sizeof(a));
	}
	file.close();
	close(sockid);
	
	return 0;
}
