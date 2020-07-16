#include <iostream>
#include <pcap.h>
#include <net/ethernet.h>
#include <netinet/ip.h>
#include <netinet/in.h>
#include <netinet/tcp.h>
#include <arpa/inet.h>

using namespace std;

int tcpcount=0;
int udpcount=0;
int icmpcount=0;

static int packetCount = 0;

void packetHandler(u_char *userData, const struct pcap_pkthdr* pkthdr, const u_char* packet) {
  const struct ethhdr* ethernetHeader;
  const struct ip* ipHeader;
  const struct tcphdr* tcpHeader;
  char sourceIp[INET_ADDRSTRLEN];
  char destIp[INET_ADDRSTRLEN];
  u_int sourcePort, destPort;
  u_char *data;
  int dataLength = 0;
  string dataStr = "";

  ethernetHeader = (struct ethhdr*)packet;

  
     
      ipHeader = (struct ip*)(packet + sizeof(struct ether_header));
      inet_ntop(AF_INET, &(ipHeader->ip_src), sourceIp, INET_ADDRSTRLEN);
      inet_ntop(AF_INET, &(ipHeader->ip_dst), destIp, INET_ADDRSTRLEN);
      
          tcpHeader = (tcphdr*)(packet + sizeof(struct ether_header) + sizeof(struct ip));
          sourcePort = ntohs(tcpHeader->source);
          destPort = ntohs(tcpHeader->dest);
          data = (u_char*)(packet + sizeof(struct ether_header) + sizeof(struct ip) + sizeof(struct tcphdr));
          dataLength = pkthdr->len - (sizeof(struct ether_header) + sizeof(struct ip) + sizeof(struct tcphdr));


      if (ipHeader->ip_p == IPPROTO_TCP) {
          cout<<endl;
          cout<<"TYPE : TCP"<<endl; 
          tcpcount++;   
      }
      
     else if (ipHeader->ip_p == IPPROTO_UDP) {
          cout<<endl;
          cout<<"TYPE : UDP"<<endl; 
          udpcount++;
      }
      
     else if (ipHeader->ip_p == IPPROTO_ICMP) {
          cout<<endl;
          cout<<"TYPE : ICMP"<<endl; 
          icmpcount++;
        }
     
     
          cout<<"SOURCE IP : "<<sourceIp<<endl;
          cout<<"SOURCE PORT : "<< sourcePort<<endl;
          cout<<"DEST IP : "<< destIp <<endl;
          cout<<"DEST PORT : "<<destPort << endl;
          printf("SOURCE MAC : %.2X:%.2X:%.2X:%.2X:%.2X:%.2X",ethernetHeader->h_source[0],ethernetHeader->h_source[1],ethernetHeader->h_source[2],ethernetHeader->h_source[3],ethernetHeader->h_source[4],ethernetHeader->h_source[5]);
          printf("\nDESTINATION MAC : %.2X:%.2X:%.2X:%.2X:%.2X:%.2X",ethernetHeader->h_dest[0],ethernetHeader->h_dest[1],ethernetHeader->h_dest[2],ethernetHeader->h_dest[3],ethernetHeader->h_dest[4],ethernetHeader->h_dest[5]);
          cout<<endl;
  
    
}

int main() {
  char *dev;
  pcap_t *descr;
  char errbuf[PCAP_ERRBUF_SIZE];

  dev = pcap_lookupdev(errbuf);
  if (dev == NULL) {
      cout << "pcap_lookupdev() failed: " << errbuf << endl;
      return 1;
  }
  
  descr = pcap_open_live(dev, BUFSIZ, 0, -1, errbuf);
  if (descr == NULL) {
      cout << "pcap_open_live() failed: " << errbuf << endl;
      return 1;
  }

  if (pcap_loop(descr, 1000, packetHandler, NULL) < 0) {
      cout << "pcap_loop() failed: " << pcap_geterr(descr);
      return 1;
  }
  cout<<endl;
  cout<<"-----------SUMMARY-------------"<<endl;
  cout<<"TCP count : "<<tcpcount<<endl;
  cout<<"UDP count : "<<udpcount<<endl;
  cout<<"ICMP count : "<<icmpcount<<endl;
  

  cout << "capture finished" << endl;

  return 0;
}

