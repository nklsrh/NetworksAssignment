import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;


public class RoutingPerformance {
	
	private static Network n;
	private static int rate;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("ROUTING PERFORMANCE TRACKER 9000 INITIALISED");
		System.out.println("--------------------------------------------");
		System.out.println("Network scheme: " + args[0]);
		System.out.println("Routing scheme: " + args[1]);
		System.out.println("Topology file:  " + args[2]);
		System.out.println("Workload file:  " + args[3]);
		System.out.println("Packet rate:    " + args[4]);
		System.out.println("--------------------------------------------\n");
		System.out.println("Reading Topology");
		System.out.println("--------------------------------------------");
		
		InputStream topoFile = null;
		InputStream workFile = null;
		try {
			topoFile = new FileInputStream(args[2]);
			workFile = new FileInputStream(args[3]);
			rate = Integer.parseInt(args[4]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Reading topology from: " + topoFile);
	    //use a second Scanner to parse the content of each line 
		n = new Network();
	    Scanner scanner = new Scanner(topoFile);
	    Scanner ssc = null;
	    while (scanner.hasNextLine()){
			try {
				String line = scanner.nextLine();
			    ssc = new Scanner(line);
				ssc.useDelimiter(" ");
				String r1 = ssc.next();
				String r2 = ssc.next();
				String delay = ssc.next();
				String capacity = ssc.next();
				//Separated adding links and routers for code clarity
				//Routers now contain information on which neighbours they're linked to
				n.AddRouter(r1);
				n.AddRouter(r2);
				n.AddLink(r1, r2, Integer.parseInt(delay), Integer.parseInt(capacity));
			}
			catch (Exception e) 
			{
			}
	    }
	    
		System.out.println("--------------------------------------------\n");
		System.out.println("Reading Workload");
		System.out.println("--------------------------------------------");
	    //reuse scanners to parse workload file
	    scanner = new Scanner(workFile);
	    ssc = null;
	    while (scanner.hasNextLine()){
			try {
				String line = scanner.nextLine();
			    ssc = new Scanner(line);
				ssc.useDelimiter(" ");
				double time = Double.parseDouble(ssc.next());
				String r1 = ssc.next();
				String r2 = ssc.next();
				double duration = Double.parseDouble(ssc.next());
				int totalpackets = (int) Math.ceil(duration * rate);
				for (double i = 0; i < totalpackets; i++)
				{
					//Add packets using the establishment time of each individual packet
					n.AddPacket((time + i * 1d/rate), r1, r2);
				}
			}
			catch (Exception e) 
			{
			}
	    }
	    n.Print();
	    System.out.println("Test SHP A to D: " + n.GetSHP("A", "D"));
		System.out.println("--------------------------------------------");
		System.out.println("GOODBYE!");
	}

}
