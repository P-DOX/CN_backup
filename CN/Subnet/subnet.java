import java.io.*;
import java.net.*;
import java.util.*;

class subnet
{
	 static String ip=null;
	 static int n;
	 static int mask;
	
	subnet(String ip , int n,int mask)
	{
		this.n=n;
		this.mask=mask;
		
		String[] arr = ip.split("[.]");
		//int[] ar = new int[4];
		for (int i=0;i<4;i++)
		{			
			String num=Integer.toBinaryString(Integer.parseInt(arr[i]));
			int l=num.length();				
			for (int j=0;j<8-l;j++)
				num="0"+num;
			
			if(i==0)
				this.ip=num;
			else			
				this.ip=this.ip+num;		
			
			//System.out.println(num);	
		}	
		//System.out.println();
		//System.out.println(this.ip);
	}

	static String printip(String ip)
	{
		String str="";
		str=Integer.parseInt(ip.substring(0,8),2)+"."+Integer.parseInt(ip.substring(8,16),2)+".";
		str=str+Integer.parseInt(ip.substring(16,24),2)+"."+Integer.parseInt(ip.substring(24,32),2);
		return str;
	}

	static String and(String a,String b)
	{
		String str="";
		for(int i=0;i<32;i++)
		{
			if(a.charAt(i)==b.charAt(i) && a.charAt(i)=='1')
				str=str+"1";
			else
				str=str+"0";
		}
		return str;	
	}

	public static void main(String args[])
	{
		Scanner in = new Scanner(System.in);
		System.out.print("Enter IP address : ");
		//String ip = in.nextLine();
		String ip = "192.12.34.54";
		System.out.print("Enter number of subnets : ");
		//int n = in.nextInt();
		int n=6;
		System.out.print("Enter newtwork mask : ");
		//int mask = in.nextInt();
		int mask=26;
		subnet s = new subnet(ip,n,mask);
		
		String nmask="1";
		for(int i=1;i<32;i++)
		{
			if(i<mask)
				nmask=nmask+"1";
			else
				nmask=nmask+"0";
		}


		String sip=and(nmask,s.ip);
		
		System.out.println();

		//System.out.println(s.ip);
		System.out.println(nmask);
		System.out.println(ip);
		System.out.println(printip(s.ip));

		int subnetmask =mask+(int)Math.ceil((Math.log(n) / Math.log(2)));	
		

	}
}
