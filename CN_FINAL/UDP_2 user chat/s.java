import java.io.*;
import java.net.*;
import java.util.*;

class s
{
	static void send(DatagramSocket ds) throws Exception
	{
		DatagramPacket dpkt;
		Scanner in = new Scanner(System.in);

		System.out.println("Enter message to send to client : ");
		String msg=in.nextLine();
		byte[] msgbyte = msg.getBytes();
		InetAddress ia = InetAddress.getLocalHost();
		dpkt = new DatagramPacket(msgbyte,msgbyte.length,ia,555);
		ds.send(dpkt);
	}

	public static void main(String args[])
	{	
		try
		{
		DatagramSocket ds = new DatagramSocket(222);
		send(ds);
		}
		catch(Exception ex)
		{}
	}
}