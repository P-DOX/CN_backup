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
			if(flag==1)
				send();
			else if(flag==2)
				recv();
		}
		catch(Exception ex)
		{}
	}

	void send() throws Exception
	{
		Scanner in = new Scanner(System.in);
		DatagramPacket dpkt;
		InetAddress ia = InetAddress.getLocalHost();
		String mesg;
		while(true)
		{
			System.out.print("Server : ");
			mesg = in.nextLine();
			byte[] mesgbyte = mesg.getBytes();
			dpkt = new DatagramPacket(mesgbyte,mesgbyte.length,ia,222);
			ds.send(dpkt);
		}
	}

	void recv() throws Exception
	{
		DatagramPacket dpkt;
		byte[] mesgbyte = new byte[512];
		while(true)
		{	
			dpkt = new DatagramPacket(mesgbyte,512);
			ds.receive(dpkt);
			String mesg = new String(dpkt.getData(),0,dpkt.getLength());
			System.out.println("Client : "+mesg);
		}
	}
}
class server
{
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