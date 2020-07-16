import java.io.*;
import java.util.*;
import java.net.*;


class thread implements Runnable
{
	DatagramSocket ds;
	Scanner in = new Scanner(System.in);
	Thread t;
	int flag;
	thread(int f,DatagramSocket ds)
	{
		t=new Thread(this);
		this.flag=f;
		this.ds=ds;
		t.start();
	}
	public void run()
	{
		try{
		if(flag==0)
			send();
		else
			recv();
		}
		catch(Exception ex)
		{
			System.out.println(ex);

		}
	}

	void send() throws Exception
	{
		System.out.println("Sending Thread Activated ");
		DatagramPacket dpkt;
		InetAddress ia = InetAddress.getLocalHost();
		while(true)
		{
			String msg = in.nextLine();
			byte[] bytes = msg.getBytes();
			dpkt = new DatagramPacket(bytes,bytes.length,ia,222);
			ds.send(dpkt);
		}

	}

	void recv()throws Exception
	{
		System.out.println("Receiving Thread Activated ");
		DatagramPacket dpkt;
		byte[] bytes = new byte[512];
		while(true)
		{
			dpkt = new DatagramPacket(bytes,512);
			ds.receive(dpkt);
            String str = new String(dpkt.getData(), 0, dpkt.getLength());
			System.out.println("Message : "+str);
		}
	}
}


class server
{
	DatagramSocket ds,dr;
	server(int port) throws Exception
	{
		ds = new DatagramSocket(port);

		thread st = new thread(0,ds);
		thread rt = new thread(1,ds);
	}

	public static void main(String args[]) throws Exception
	{
		server s = new server(555);

	}
}