import java.io.*;
import java.net.*;
import java.util.Scanner;

class thread implements Runnable
{
	Thread t;
	int flag;
	Scanner in = new Scanner(System.in);
	DataInputStream din;
	DataOutputStream dout;

	thread(int f, Socket s)
	{
		t=new Thread(this);
		this.flag=flag;
		t.start();
		try
		{
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	public void run()
	{
		if(flag==1)
			send();
		else if(flag==2)
			recv(); 
	}

	void send()
	{
		String text;
		while(true)
		{
			try
			{
				System.out.print("Server : ");
				text=in.nextLine();
				dout.writeUTF(text);
				if(text.equals("exit"))
					break;
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
		}
	}

	void recv()
	{
		String text=null;
		while(true)
		{
			try
			{
				if((text=din.readUTF())!=null)
					System.out.println("Client : " + text);
				if(text.equals("exit"))
					break;
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
		}
	}
}
class server implements Runnable
{
	Thread mythread;
	ServerSocket port;
	Boolean flag;
	server(int portno) throws Exception
	{
		port = new ServerSocket(portno);
		mythread = new Thread(this);
		mythread.start();
	}

	public void run()
	{
		acceptconnection();
		//portclose();
	}

	void acceptconnection()
	{
		Socket client;
		flag=true;
		while(flag)
		{
			try
			{
			System.out.println("Searching for client ...");
			client=port.accept();
			thread se = new thread(1,client);
			thread re = new thread(2,client);
			System.out.println("Client connected ... ");
			}
			catch(Exception ex)
			{
				System.out.println(ex);
			}
		}
	}

	void portclose()
	{
		try
		{
		port.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	} 

	public static void main(String args[])
	{
		try
		{
		new server(9705);
		}
		catch(Exception ex)
		{
		System.out.println(ex.getMessage());
		}
	}
}