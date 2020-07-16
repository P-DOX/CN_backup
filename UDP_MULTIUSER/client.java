import java.io.*;
import java.util.*;
import java.net.*;

class thread implements Runnable
{
	Thread t;
	int flag;
	DatagramSocket ds;
	thread(int flag, DatagramSocket ds)throws Exception
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
			if(flag == 1)
				send();
			else if(flag==2)
				recv();
		}
		catch(Exception ex)
		{}
	}

	void send() throws Exception
	{
		DatagramPacket dpkt;
		InetAddress ia = InetAddress.getLocalHost();
		Scanner in = new Scanner(System.in);
		while(true)
		{
			System.out.print("Client : ");
			String mesg = in.nextLine();
			byte[] mesgbyte = mesg.getBytes();
			dpkt = new DatagramPacket(mesgbyte,mesgbyte.length,ia,555);
			ds.send(dpkt);	
		}
	}

	void recv()throws Exception
	{
		DatagramPacket dpkt;
		byte[] mesgbyte = new byte[512];
		while(true)
		{
			dpkt = new DatagramPacket(mesgbyte,512);
			ds.receive(dpkt);
			String mesg = new String(dpkt.getData(),0,dpkt.getLength());
			System.out.println("Server : "+mesg+dpkt.getAddress()+dpkt.getPort());
		}
	}

}

class client
{
	client()throws Exception
	{
		DatagramSocket ds = new DatagramSocket();
		thread st = new thread(1,ds);
		thread rt = new thread(2,ds);
	}

	public static void main(String args[]) throws Exception
	{
		new client();
	}
}