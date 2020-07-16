import java.io.*;
import java.util.*;
import java.net.*;

class thread implements Runnable
{
	Thread t;
	DatagramSocket ds;
	int flag;
	thread(int flag, DatagramSocket ds) throws Exception
	{
		t = new Thread(this);
		this.flag = flag;
		this.ds = ds;
		t.start();
	}

	public void run()
	{
		try
		{
				recv();
		}
		catch(Exception ex)
		{}
	}


	void recv() throws Exception
	{
		DatagramPacket dpkt;
		byte[] mesgbyte = new byte[512];
		int port;
		while(true)
		{	
			dpkt = new DatagramPacket(mesgbyte,512);
			ds.receive(dpkt);
			add(dpkt);
			sendmessage(dpkt);
		}
	}

	void add(DatagramPacket dpkt)
	{
		Boolean found=false;
		for(clienthandler ch : server.v)
		{
			if(dpkt.getPort() == ch.port())
				found=true;
		}
		if(!found)
		{
			clienthandler c = new clienthandler(dpkt.getPort(),dpkt.getAddress());
			server.v.add(c);
		}
	}

	void sendmessage(DatagramPacket dpkt) throws Exception
	{
		DatagramPacket dp;
		for(clienthandler ch : server.v)
		{
			if(dpkt.getPort() != ch.port())
			{
 				dp = new DatagramPacket(dpkt.getData(),dpkt.getLength(),ch.address(),ch.port());
 				System.out.println("hollLllLAAA");
 				ds.send(dp);
			}
		}
	}
	
}
class server
{
	static Vector<clienthandler> v = new Vector<> ();
	server(int port)throws Exception
	{
		DatagramSocket ds = new DatagramSocket(port);
		thread st = new thread(1,ds);
		thread rt = new thread(2,ds);
	}
	public static void main(String args[])throws Exception
	{
		new server(555);
	}
}

class clienthandler
{
	int portno;
	InetAddress ip;
	clienthandler(int portno , InetAddress ip)
	{
		this.portno = portno;
		this.ip = ip;
	}

	int port()
	{
		return this.portno;
	}

	InetAddress address()
	{
		return this.ip;
	}
}