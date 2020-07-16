import java.io.*;
import java.net.*;
import java.util.*;

class c
{
	static void receive(Socket client)throws Exception
	{
		DataInputStream din = new DataInputStream(client.getInputStream());
		DataOutputStream dout = new DataOutputStream(client.getOutputStream());

		String msg = din.readUTF();
		System.out.println("Message : "+msg);
	}
	public static void main(String args[])
	{
		try
		{
		Scanner in = new Scanner(System.in);
		Socket client = new Socket("127.0.0.1",222);
		
		receive(client);		
		}
		catch(Exception ex)
		{}
	}
}