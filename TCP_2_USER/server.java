import java.io.*;
import java.util.*;
import java.net.*;

class thread implements Runnable
{
	Thread t;
	int flag;
	Socket client;
	thread(int flag,Socket client) 
	{
		t = new Thread(this);
		this.flag = flag;
		this.client = client;
		t.start();
	}

	public void run()
	{
		try
		{
		if(flag==1)
			send();
		else if(flag==2)
			recv();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	void send()throws Exception
	{
		DataOutputStream dout = new DataOutputStream(client.getOutputStream());
		Scanner in = new Scanner(System.in);
		String message;
		while(true)
		{
			System.out.print("Server : ");
			message=in.nextLine();
			dout.writeUTF(message);
		}

	}

	void recv()throws Exception
	{
		DataInputStream din = new DataInputStream(client.getInputStream());
		while(true)
		{
			String message = din.readUTF();
			System.out.println("Client : "+message);
		}
	}

}
class server
{
	ServerSocket port;

	server(int portno)throws Exception
	{
		port = new ServerSocket(portno);
		System.out.println("Server is Ready to accept connection !!");
		acceptconnection(port);
	}

	static void acceptconnection(ServerSocket port)throws Exception
	{
		Socket client;
		Boolean flag = true;
		
		// while(flag)
		// {
			client = port.accept();
			System.out.println(client.getInetAddress()+" "+client.getPort());
			new thread(1,client);
			new thread(2,client);
		// }
	}


	public static void main(String args[]) throws Exception
	{
		new server(9705);
	}
}