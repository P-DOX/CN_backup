import java.util.*;
import java.io.*;

class subnet
{
	static String cvttostring(String ip)
	{
		int numlen=0;
		String nums[] = ip.split("[.]");
		String num;
		String ips="";


		for(int i=0;i<nums.length;i++)
		{	
			num = Integer.toBinaryString(Integer.parseInt(nums[i]));
			numlen=num.length();
			for(int j=0 ; j<8-numlen; j++)
			{
				num = '0'+num;
			}
			ips=ips+num;
		}

		System.out.println("IP         : " + printip(ips));

		return ips;	
	}
	static String and(String ip, int mask)
	{
		String masks = "";
		for(int i=0;i<32;i++)
		{
			if(i<mask)
				masks = masks + '1';
			else
				masks = masks + '0';
		}
		System.out.println("Mask       : " + printip(masks));

		String id="";

		for(int i=0;i<32;i++)
		{
			if(masks.charAt(i)=='1' )
				id=id+ip.charAt(i);
			else
				id=id+'0';
		}
		System.out.println("Network Id : " + printip(id));

		return id;
	}

	static String printip(String ip)
	{
		String ips="";

		ips=ips+Integer.parseInt(ip.substring(0,8),2)+"."+Integer.parseInt(ip.substring(8,16),2)+".";
		ips=ips+Integer.parseInt(ip.substring(16,24),2)+"."+Integer.parseInt(ip.substring(24,32),2);

		return ips;
	}

	public static void main(String args[])
	{
		Scanner in = new Scanner(System.in);
		String ip;
		int mask=24,n=6;

		System.out.print("Enter IP : ");
		ip = in.nextLine();
		// ip = "192.18.217.156";
		System.out.print("Enter mask : ");
		mask=in.nextInt();
		System.out.print("Number of subnets required : ");
		n=in.nextInt();
		System.out.println();

		String ips = cvttostring(ip);
		String nid = and(ips,mask);

		System.out.println();
		
		int ns = (int)Math.ceil(Math.log(n)/Math.log(2));

		String sip,fip,sid;
		for(int i=0;i<n;i++)
		{
			System.out.println("Subnet " + (i+1) +" : ");

			sid=Integer.toBinaryString(i);
			int sidlen = sid.length();
			for(int j=0;j<ns-sidlen;j++)
				sid='0'+sid;
			
			sip=nid.substring(0,mask)+sid;
			fip=nid.substring(0,mask)+sid;

			int temp = sip.length();

			for(int j=0;j<32-temp;j++)
			{
				sip=sip+'0';
				fip=fip+'1';
			}

			System.out.println("First IP   : \n"+printip(sip)+"\n"+sip);
			System.out.println("Last IP    : \n"+printip(fip)+"\n"+fip);


		}


	}
}