import java.io.*;
import java.net.*;

class Client
{
 Socket s;
 Client(String ip, int port) throws Exception
 {
  //request a connection
  s = new Socket(ip, port);
 }

 static char getChar()
 {
  try
  {
   System.in.skip(System.in.available()); 
   return (char) System.in.read();
  }
  catch(Exception ex)
  {
   return ' '; 
  } 
 }//getChar

 static int getInt()
 {
  try
  {
   System.in.skip(System.in.available()); 
   byte arr[] = new byte[20];
   int n = System.in.read(arr);
   String s = new String(arr,0, n-2);
   return Integer.parseInt(s);
  }
  catch(Exception ex)
  {
   return 0; 
  } 
 }//getInt

 void interact() throws Exception
 {
  DataInputStream din = new DataInputStream(s.getInputStream());
  DataOutputStream dout = new DataOutputStream(s.getOutputStream());

  int a,b,c, p, x;
  String result;
  char ch;

  do
  {
   p = din.readInt();
   a = din.readInt();
   b = din.readInt();
   c = din.readInt();
  
   System.out.println("Welcome Vikas, lets play the game of luck");
   System.out.println("Current Score : " + p);
   System.out.println("\n" + a + " " + b + " " + c);
   System.out.println("Pick a Number ");
   x = getInt();
   dout.writeInt(x);
   result = din.readUTF();
   System.out.println("Result : " + result);
   System.out.println("Play More (y/n) : ");
   ch = getChar();
   if(ch == 'y' || ch == 'Y')
     dout.writeBoolean(true);
   else
     dout.writeBoolean(false);
  }while(ch == 'y' || ch == 'Y');

 }//interact
 
 void close() throws Exception
 {
   s.close();
 }
 public static void main(String args[])
 {
  try
  {
   Client c = new Client("127.0.0.1", 9876);
   c.interact();
   c.close();
  } 
  catch(Exception ex)
  {
   System.out.println(ex);
  }
 }
}