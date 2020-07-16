import java.net.*; 
import java.util.*; 

class IPDemo 
{ 
	public static void main(String[] args){ 
		String host; 
		Scanner input = new Scanner(System.in); 
		
		
		System.out.print("\n Enter host name: "); 
		
		
		
		host = input.nextLine(); 
		
		try { 
			     InetAddress address[] = InetAddress.getAllByName(host);
			
	               for(int i=0;i<address.length ;i++){
				System.out.println("IP address: " + address[i].getHostAddress());
				System.out.println("Host name : " + address[i].getHostName());  
				
				
				//System.out.println("Host name and IP address: " + address[i].toString()); 
	                }
 		 
               } 
		catch (UnknownHostException ex) {
		     System.out.println("Could not find " + host); 
		}
	} 
}
