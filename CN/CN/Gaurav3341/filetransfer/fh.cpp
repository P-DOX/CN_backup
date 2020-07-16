#include<iostream>
#include<fstream>
using namespace std;
int main()
{
  ifstream infile;
  infile.open("as.jpg",ios::binary|ios::in);

  ofstream outfile;
  outfile.open("te.jpg",ios::binary|ios::out);

  int buffer[100];
  while(infile.read((char *)&buffer,sizeof(buffer)))
  {
      outfile.write((char *)&buffer,sizeof(buffer));
  }

  infile.close();
  outfile.close();
  return 0;
}