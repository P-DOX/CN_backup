import java.io.*;
import java.net.*;
import java.util.*;

class c
{
	static void receive(DatagramSocket dc)throws Exception
	{
		byte[] bytes= new byte[512];
		DatagramPacket dpkt = new DatagramPacket(bytes,512);
		dc.receive(dpkt);
		String msg = new String(dpkt.getData(),0,dpkt.getLength());
		System.out.println("Message : "+msg);
	}
	public static void main(String args[])
	{	
		try
		{
		DatagramSocket dc = new DatagramSocket(555);
		receive(dc);
		}
		catch(Exception ex)
		{}

	}
}