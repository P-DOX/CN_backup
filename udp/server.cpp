//
// 				SERVER
// 
#include<iostream>
#include<sys/socket.h>
#include<netinet/in.h>
#include<string.h>
#include<unistd.h>
#include<fstream>

using namespace std;

string servDirectory = "C:/Users/GAURAV/Pictures/Screenshots";


void fileTransfer(int servSocket)
{
	sockaddr_in clientAddr;
	int addrLen = sizeof(clientAddr);
	char buff[1024];
	int n = 0;

	//receive file name
	n = recvfrom(servSocket,buff,1024,0,(sockaddr*)&clientAddr , (socklen_t*)&addrLen);	
	string filename(buff, 0 ,n);
	filename = servDirectory + "/" + filename;
	cout<<"File Requested: "<<filename<<endl;

	//send Status
	ifstream fin(filename,ios::binary);
	if(fin.is_open())
	{
		string msg = "FILE_FOUND";
		sendto(servSocket,msg.c_str(),msg.length(),0,(sockaddr*)&clientAddr, addrLen);
	}
	else
	{
		string msg = "FILE_NOT_FOUND";
		sendto(servSocket,msg.c_str(),msg.length(),0,(sockaddr*)&clientAddr, addrLen);
		return;
	}

	//send file
	int data;
	while(true)
	{
		data = fin.get();
		sendto(servSocket, (char*)&data , sizeof(data) , 0 ,(sockaddr*)&clientAddr , addrLen);
		// cout<<(char)data;
		if(data == -1)
			break;
	}

	//close
	cout<<"File sent Successfully..."<<endl;
	fin.close();
}

int main()
{
	int servSocket = socket(AF_INET,SOCK_DGRAM,0);
	if(servSocket == 0)
	{
		perror("Socket creation failed");
		return 1;
	}

	int opt = 1;
	setsockopt(servSocket, SOL_SOCKET, SO_REUSEADDR | SO_REUSEPORT,&opt, sizeof(opt));

	sockaddr_in port;
	port.sin_family = AF_INET;
	port.sin_port = htons(9898);
	port.sin_addr.s_addr = INADDR_ANY;

	int b = bind(servSocket, (sockaddr*)&port, sizeof(port));
	if(b<0)
	{
		perror("bind failed");
		return 1;
	}

	fileTransfer(servSocket);
	return 0;
}