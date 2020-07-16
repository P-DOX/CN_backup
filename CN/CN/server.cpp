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

int main(){

struct sockaddr_in servadr,clnadr;
int sid,nsid,f,clnlen;
 
servadr.sin_family=AF_INET;
servadr.sin_addr.s_addr=htonl(INADDR_ANY);
servadr.sin_port=htons(5490);

sid=socket(AF_INET,SOCK_STREAM,0);
f=bind(sid,(struct sockaddr*)&servadr,sizeof(servadr));
listen(sid,10);
cout<<"Server: Waiting for Client"<<endl;

nsid=accept(sid,(struct sockaddr *)&clnadr,(socklen_t*)&clnlen);
cout<<"Client connected\n"<<endl;
char buffer[200]={0};
char fname[20]={0};
int i=0;
cout<<"--------------------------------File Transfer------------------------------"<<endl;
cout<<endl;

read(nsid,fname,20);
if(strcmp(fname,"send.txt")!=0){
cout<<"File not found"<<endl;
return 0;
}
fstream file;
file.open("send.txt",ios::in);
while(!file.eof()){
file.get(buffer[i]);
i++;
}
write(nsid,buffer,strlen(buffer));
close(nsid);
close(sid);
return 0;
}
