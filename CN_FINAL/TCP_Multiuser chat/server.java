import java.io.*;
import java.net.*;
import java.util.*;

class server implements Runnable
{
	boolean flag;
	Thread connection;
	ServerSocket port;

	static Vector <clienthandler> ch = new Vector<> ();
	static int i=0;

	server(int portno) throws Exception
	{
		port = new ServerSocket(portno);
		connection = new Thread(this); 
		connection.start();
	}

	public void run()
	{
		connect();
		portclose();
	}

	void connect()
	{
		flag=true;
		Socket client;
		while(flag)
		{	
			try
			{
				System.out.println("Waiting for connection ...");
				client=port.accept();
				DataInputStream din = new DataInputStream(client.getInputStream());
				DataOutputStream dout = new DataOutputStream(client.getOutputStream());
				System.out.println("Connection formed with "+client.getInetAddress()+" on port "+client.getPort()+"...");
				clienthandler c = new clienthandler(client,"client "+i,din,dout);
				ch.add(c);
				i++;
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
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
			server s = new server(9705);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}

class clienthandler implements Runnable
{
	Thread t;
	Socket client;
	String name;
	DataInputStream din;
	DataOutputStream dout;
	clienthandler(Socket cl,String s,DataInputStream din,DataOutputStream dout)
	{
		this.client = cl;
		this.name=s;
		this.din=din;
		this.dout=dout;
		t=new Thread(this);
		t.start();
	}

	public void run()
	{
		String mesg,sendto;

		System.out.println("Entered in run of clienthandler ...");
		while(true)
		{
		try
		{
			mesg=din.readUTF();

			String[] ar=mesg.split("@" , 2);
			mesg=ar[0];
			sendto=ar[1];

			for(clienthandler c : server.ch)
			{
				if(c.name.equals(sendto))
					{
						c.dout.writeUTF(name+" : "+mesg);
						System.out.println(sendto +" -> "+mesg + "-> "+this.name);
						break;
					}
			}

		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		}
	}
}