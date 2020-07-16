//
// 				CLIENT
// 
#include<iostream>
#include<sys/socket.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<fstream>
#include<string.h>

using namespace std;

void fileTransfer(int client, sockaddr_in servAddress)
{
	//send File Name
	string file_name;
	cout << "Enter File Name: ";
	cin >> file_name;
	sendto(client, file_name.c_str() , file_name.length() , 0 , (sockaddr*)&servAddress , sizeof(servAddress));

	//receive status
	char buff[1024];
	int n = recvfrom(client,buff,1024,0,NULL,NULL);
	string status(buff,0,n);

	if(status == "FILE_NOT_FOUND")
	{
		cout<<"File not Found on server..."<<endl;
		return;
	}
	cout<<"File Found"<<endl;

	//receive file
	ofstream fout(file_name,ios::binary);
	long received = 0;
	int data;
	while(true)
	{
		recvfrom(client, (char*)&data ,sizeof(data),0 , NULL, NULL);
		if(data == -1)
			break;
			
		// cout<<(char)data;
		received++;
		fout.put(data);
	}
	//close
	cout<<"File Downloaded...: "<<received<<endl;
	fout.flush();
	fout.close();
}

int main()
{
	int client = socket(AF_INET,SOCK_DGRAM,0);
	if(client == 0)
	{
		perror("Socket creation failed");
		return 1;
	}

	sockaddr_in servAddress;
	servAddress.sin_family = AF_INET;
	servAddress.sin_port = htons(9898);
	servAddress.sin_addr.s_addr = inet_addr("127.0.0.1");
	

	fileTransfer(client,servAddress);
	return 0;
}