#include<iostream>
#include<pcap.h>
#include<net/ethernet.h>
#include<netinet/ip.h>
#include<netinet/in.h>
#include<netinet/tcp.h>
#include<arpa/inet.h>
using namespace std;

int tcpcount=0;
int udpcount=0;
int icmpcount=0;


void packethandler(u_char *userdata, const pcap_pkthdr *pkthdr, const u_char *packet)
{
	const ethhdr *ethernetHeader;
	const tcphdr *tcpHeader;
	const ip *ipHeader;
	char sourceIP[INET_ADDRSTRLEN];
	char destIP[INET_ADDRSTRLEN];
	int sourcePort,destPort;

	// 1. Ethernet Header
	ethernetHeader = (ethhdr*)packet;
	
	// 2. IP Header
	ipHeader = (ip*)(packet + sizeof(ether_header));
	inet_ntop(AF_INET,&(ipHeader->ip_src),sourceIP, INET_ADDRSTRLEN);
	inet_ntop(AF_INET,&(ipHeader->ip_dst),destIP, INET_ADDRSTRLEN);

	//3. TCP Header
	tcpHeader = (tcphdr*)(packet + sizeof(ether_header) + sizeof(ipHeader));
	sourcePort = ntohs(tcpHeader->source);
	destPort = ntohs(tcpHeader->dest);

	// u_char* data = (u_char*)(packet+sizeof(ether_header) + sizeof(ipHeader)+sizeof(tcpHeader));
	// int datalen=pkthdr->len-(sizeof(ether_header) + sizeof(ipHeader)+sizeof(tcpHeader));

	if(ipHeader->ip_p == IPPROTO_TCP)
	{
		cout<<"TCP Packet"<<endl;
		tcpcount++;
	}
	else if(ipHeader->ip_p == IPPROTO_UDP)
	{
		cout<<"UDP Packet"<<endl;
		udpcount++;
	}
	else if(ipHeader->ip_p == IPPROTO_ICMP)
	{
		cout<<"ICMP Packet"<<endl;
		icmpcount++;
	}

	cout<<"Source IP : "<<sourceIP<<endl;
	cout<<"Source Port : "<<sourcePort<<endl;

	cout<<"Destination IP : "<<destIP<<endl;
	cout<<"Destination Port : "<<destPort<<endl;

	printf("SOURCE MAC : %.2X:%.2X:%.2X:%.2X:%.2X:%.2X",ethernetHeader->h_source[0],ethernetHeader->h_source[1],ethernetHeader->h_source[2],ethernetHeader->h_source[3],ethernetHeader->h_source[4],ethernetHeader->h_source[5]);
    printf("\nDESTINATION MAC : %.2X:%.2X:%.2X:%.2X:%.2X:%.2X",ethernetHeader->h_dest[0],ethernetHeader->h_dest[1],ethernetHeader->h_dest[2],ethernetHeader->h_dest[3],ethernetHeader->h_dest[4],ethernetHeader->h_dest[5]);
    cout<<endl;
	cout<<endl;

}


int main()
{
	char *dev;
	pcap_t *desc;
	char err[PCAP_ERRBUF_SIZE];

	dev = pcap_lookupdev(err);
	if(dev==NULL)
		cout<<"pcap_lookupdev() failed !! "<<err<<endl;

	desc = pcap_open_live(dev,BUFSIZ,0,-1,err);
	if(desc==NULL)
		cout<<"pcap_open_live() failed!! "<<err<<endl;

	if(pcap_loop(desc,100,packethandler,NULL)<0)
		cout<<"pcap_loop() failed !! "<<err<<endl;

	cout<<endl;
	cout<<"-----------SUMMARY-------------"<<endl;
  	cout<<"TCP count : "<<tcpcount<<endl;
  	cout<<"UDP count : "<<udpcount<<endl;
 	cout<<"ICMP count : "<<icmpcount<<endl;
  

}