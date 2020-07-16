import java.io.*;
import java.util.*;
import java.net.*;


class thread implements Runnable
{
	DatagramSocket dr;
	Scanner in = new Scanner(System.in);
	Thread t;
	int flag;
	thread(int f,DatagramSocket dr)
	{
		t=new Thread(this);
		this.flag=f;
		this.dr=dr;
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
			dpkt = new DatagramPacket(bytes,bytes.length,ia,555);
			dr.send(dpkt);
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
			dr.receive(dpkt);
            String str = new String(dpkt.getData(), 0, dpkt.getLength());

			System.out.println("Message : "+str);
		}
	}
}


class client
{
	DatagramSocket dr;
	client(int port) throws Exception
	{
		dr = new DatagramSocket(port);

		thread st = new thread(0,dr);
		thread rt = new thread(1,dr);
	}

	public static void main(String args[]) throws Exception
	{
		client c = new client(222);

	}
}