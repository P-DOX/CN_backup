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
			
		}	
	}

	static String printip(String ip)
	{
		String str="";
		str=Integer.parseInt(ip.substring(0,8),2)+"."+Integer.parseInt(ip.substring(8,16),2)+".";
		str=str+Integer.parseInt(ip.substring(16,24),2)+"."+Integer.parseInt(ip.substring(24,32),2);
		return str;
	}
	
	static String ipformat(String ip)
	{
		String str="";
		str=ip.substring(0,8)+"."+ip.substring(8,16)+".";
		str=str+ip.substring(16,24)+"."+ip.substring(24,32);
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
		String ip = in.nextLine();
		//String ip = "130.34.12.64";
		System.out.print("Enter newtwork mask : ");
		int mask = in.nextInt();
		//int mask=16;
		System.out.print("Enter number of subnets : ");
		int n = in.nextInt();
		//int n=8;
		
		subnet s = new subnet(ip,n,mask);
		int nb = (int)Math.ceil((Math.log(n) / Math.log(2)));
		int subnetmask =mask+nb;
		String smask="1";

		String nmask="1";
		for(int i=1;i<32;i++)
		{
			if(i<mask)
				nmask=nmask+"1";
			else
				nmask=nmask+"0";

			if(i<subnetmask)
				smask=smask+"1";
			else
				smask=smask+"0";
		}
		

		String sip=and(nmask,s.ip);

		System.out.println();
		System.out.println("Network ID   : "+ipformat(sip));
		System.out.println("Network mask : "+ipformat(nmask));
		System.out.println("Subnet mask  : "+ipformat(smask));

		String fip,lip;	
		String id="";
		System.out.println();
		for(int i=0;i<n;i++)
		{	
			id=Integer.toBinaryString(i);
			int nbl = nb-id.length();
			for(int j=0;j<nbl;j++)
				id="0"+id;
			fip=sip.substring(0,mask)+id;
			int fipl=fip.length();
			for(int j=0;j<32-fipl;j++)
				fip=fip+"0";
			
			lip=sip.substring(0,mask)+id;
			for(int j=0;j<32-fipl;j++)
				lip=lip+"1";

			System.out.println("Subnet "+(i+1)+".  \n"+printip(fip)+"/"+subnetmask+" -> "+ printip(lip)+"/"+subnetmask);
			System.out.println(ipformat(fip)+" -> "+ipformat(lip));
			System.out.println();
		}
		



		

	}
}
