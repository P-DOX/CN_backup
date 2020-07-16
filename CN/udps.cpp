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
	if(bind(sockid,(struct sockaddr *) &addrport,sizeof(addrport))<0){
		cout<<"Bind Failed!"<<endl;
		return -1;
	}
	
	char msg[1024]={0};
	unsigned int l=sizeof(clientAddr);
	recvfrom(sockid,&msg,1024,0,(struct sockaddr *) &clientAddr,&l);
	
	
	char a[500];
	ifstream file;
	file.open(msg,ios::binary);

	while(file.read((char *)&a,sizeof(a))){
		sendto(sockid,(char *)a,sizeof(a),0,(struct sockaddr *) &addrport,sizeof(addrport));
	}
	file.close();
	close(sockid);
	
	return 0;
}
