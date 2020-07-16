import java.io.*;
import java.net.*;
import java.util.Scanner;

class thread implements Runnable
{
	Scanner in = new Scanner(System.in);
	Thread t;
	int flag;
	Socket s;
	DataInputStream din;
	DataOutputStream dout;

	thread(int f,Socket s)
	{
		t=new Thread(this);
		this.flag=f;
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
				System.out.print("Client : ");
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
				if((text=din.readUTF()) != null)
					System.out.println("Server : "+text);
				if(text.equals("exit"))
					break;
			}
			catch(Exception ex)
			{
				System.out.println(ex);
			}	
		}
	}	

}
class client
{
	Socket s;
	client(String ip,int port) throws Exception
	{
		s = new Socket(ip,port);
		thread se = new thread(1,s);
		thread re = new thread(2,s);
	}

	void portclose() throws Exception
	{
			System.out.println("Port closed !!");
			s.close();	
	}

	public static void main(String args[])
	{	
		
		try
		{
		client c = new client("127.0.0.1",9705);
		//c.portclose(); 
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}