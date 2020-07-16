import java.util.*;
import java.net.*;

class DNS
{
	public static void main(String args[]) throws Exception
	{
		Scanner in = new Scanner(System.in);
		// String host = in.nextLine();
		String host = "yahoo.in";

		InetAddress address[] = InetAddress.getAllByName(host);

		for(int i=0;i<address.length;i++)
			System.out.println(address[i].getHostAddress()+" "+address[i].getHostName());
	}
}