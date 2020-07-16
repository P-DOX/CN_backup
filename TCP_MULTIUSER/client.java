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
			System.out.print("Client : ");
			message=in.nextLine();
			dout.writeUTF(message);
			if(message.equals("exit"))
				client.close();
		}
	}

	void recv()throws Exception
	{
		DataInputStream din = new DataInputStream(client.getInputStream());
		while(true)
		{
			String message = din.readUTF();
			System.out.println("Server : "+message);
			if(message.equals("exit"))
				client.close();
		}
	}

}


class client
{
	Socket s;

	client(String ip, int portno)throws Exception
	{
		s = new Socket(ip,portno);
		System.out.println("Connection Established !!");
		new thread(1,s);
		new thread(2,s);
	}

	public static void main(String args[]) throws Exception
	{
		new client("127.0.0.1",9705);
	}
}