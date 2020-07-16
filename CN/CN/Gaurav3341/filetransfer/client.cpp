#include<bits/stdc++.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <arpa/inet.h>
using namespace std;

int main(int argc,char* argv[]){

struct sockaddr_in servadr;
int sid,f;
 
servadr.sin_family=AF_INET;
inet_pton(AF_INET,argv[1],&servadr.sin_addr);
servadr.sin_port=htons(5490);

sid=socket(AF_INET,SOCK_STREAM,0);
f=connect(sid,(struct sockaddr*)&servadr,sizeof(servadr));
if(f<0){
cout<<"NOT CONNECTED";
return 0;
}
char fname[20];
char buffer[200]={0};
cout<<"--------------------------------File Transfer------------------------------"<<endl;
cout<<endl;
cout<<"Enter Name of the File: ";
cin>>fname;
write(sid,fname,strlen(fname));
if(strcmp(fname,"send.txt")!=0){
cout<<"File not found"<<endl;
return 0;
}
read(sid,buffer,300);
fstream file;
file.open("recv.txt",ios::out);
file.write((char*)buffer,strlen(buffer));
file.close();
close(sid);
return 0;
}
