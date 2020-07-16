import java.io.*;
import java.utl.*;
import java.net.*;

class subnet
{
	public static void main(String args[])
	{
		Scanner in = new Scanner(System.in);
		System.out.print("Enter IP address : ");
		String ip = in.nextLine();
		System.out.print("Network mask : ");
		int mask = in.nextInt();
		System.out.println("Number of subnets : ");
		int n = in.nextInt();
	}
}