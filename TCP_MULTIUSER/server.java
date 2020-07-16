import java.io.*;
import java.util.*;
import java.net.*;


class server
{
	ServerSocket port;
	static Vector<clienthandler> ch = new Vector<> ();
	static int i=0;

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
		
		while(flag)
		{
			client = port.accept();
			System.out.println("client 0 connected at "+client.getInetAddress()+" on "+client.getPort());
			clienthandler c = new clienthandler(client,"client "+i );
			ch.add(c);
			i++;
		}
	}


	public static void main(String args[]) throws Exception
	{
		new server(9705);
	}
}

class clienthandler implements Runnable
{
	Thread t;
	Socket client;
	DataInputStream din;
	DataOutputStream dout;
	String name;
	clienthandler(Socket client, String name) throws Exception
	{
		this.name = name;
		this.client = client;
		din = new DataInputStream(client.getInputStream());
		dout = new DataOutputStream(client.getOutputStream());
		t = new Thread(this);
		t.start();
	}

	public void run()
	{
		String message;
		String msg, addr ;
		while(true)
		{
			try
			{
				message = din.readUTF();
				String arr[]= message.split("@",2);
				msg = arr[0];
				addr = arr[1];
			

			for(clienthandler c : server.ch)
			{
				if(c.name.equals(addr))
					c.dout.writeUTF(msg+"@"+this.name);
			}

			}
			catch(Exception ex)
			{}
		}	
	}


}