import java.io.*;
import java.net.*;
import java.util.*;

class s
{
	static void send(Socket client)throws Exception
	{
		Scanner in = new Scanner(System.in);
		DataInputStream din = new DataInputStream(client.getInputStream());
		DataOutputStream dout = new DataOutputStream(client.getOutputStream());

		System.out.println("Enter ur message : ");
		String msg = in.nextLine();
		dout.writeUTF(msg);
	}
	public static void main(String args[])
	{
		try
		{
		ServerSocket server = new ServerSocket(222);
		Socket client= server.accept();
		
		send(client);		
		}
		catch(Exception ex)
		{}
	}
}