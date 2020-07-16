import java.net.*;
import java.io.*;
import java.util.Random;

class Server implements Runnable
{
 boolean flag;
 Thread connectionThread;
 ServerSocket port;
 NumberGame game;

 Server(int x) throws Exception
 {
  //open a port
  port = new ServerSocket(x); 
  //success
  game = new NumberGame();
  //limit accept block time to 5000 milliseconds 
  port.setSoTimeout(5000); 

  connectionThread = new Thread(this);
  connectionThread.start();
 }//Server(int)

 public void run()//connectionThread
 {
  acceptConnections();
  shutDown();
 }

 void acceptConnections()
 {
  Socket clnt;
  flag = true;

  while(flag)
  {
   try
   {
     //accept a client connection
     //1. Blocks the program control until 
     //1.1. A client requests a connection
     //1.2. timeout
     //2. On request, form a connection
     //3. Return a Socket object that represents connection with the client 
 
     System.out.println("Server waiting for client connection request ...");
     clnt = port.accept();
     System.out.println("Server formed a client connection");
     new ProcessConnection(clnt, game);
   }
   catch(Exception ex)
   {}
  }//while

 }//acceptConnections

 void shutDown() 
 {
   try
   {
    port.close();
   }
   catch(Exception ex)
   {}
 }

 public static void main(String args[])
 {
  try
  {
   new Server(9876);
   //...job ahead 
  }
  catch(Exception ex)
  {
   System.out.println(ex); 
  }
 }//main

}//Server


class ProcessConnection extends Thread
{
 Socket clnt;
 NumberGame game;
 
 ProcessConnection(Socket s, NumberGame ng)
 {
   clnt = s;
   game = ng;
   start();//activate the thread
 }

 public void run()
 {
  try
  {
   //fetch the i/o streams
   DataInputStream din = new DataInputStream( clnt.getInputStream() );
   DataOutputStream dout = new DataOutputStream( clnt.getOutputStream() );

   int arr[];
   int points  = 0;
   int x;
   boolean ch;
   do
   {
     arr = game.play();
     dout.writeInt(points);
     dout.writeInt(arr[0]);
     dout.writeInt(arr[1]);
     dout.writeInt(arr[2]);
     x = din.readInt();

     if(x == arr[arr[3]])
     {
       points+= 10;
       dout.writeUTF("Win");
     }
     else
     {
       dout.writeUTF("Loose, Win number was : " + arr[arr[3]]);
     }
 
     ch = din.readBoolean();

   }while(ch);

   clnt.close();

  }//try
  catch(IOException ex)
  {
   ex.printStackTrace();
   System.out.println(ex);
  }

 }//run

}//ProcessConnection

class NumberGame
{
 Random r; 

 NumberGame()
 {
   r = new Random();
 }

 int [] play()
 {
  int i, x;
  int arr[] = new int[4];

  arr[0] = r.nextInt(100); 
  i = 1;
  while(i < 3) 
  {
    x = r.nextInt(100); 
    if(isUnique(arr, i, x))
    {
      arr[i] = x;
      i++;
    }
  }//while   

  //set the win number
  arr[3] = r.nextInt(3);

  return arr; 
 }

 boolean isUnique(int arr[], int size, int x)
 {
  int i;
  for(i =0 ; i < size; i++)
     if(arr[i] == x)
        return false; 
  return true;
 }
}//NumberGame

