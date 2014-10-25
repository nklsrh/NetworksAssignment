import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import com.sun.tools.javac.util.List;


public class RoutingPerformance {

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
		try {
			topoFile = new FileInputStream(args[2]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Reading topology from: " + Paths.get("") + "/" + topoFile);
	    //use a second Scanner to parse the content of each line 
		Network n = new Network();
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
				Link link0 = new Link(new Router(r1), new Router(r2), Integer.parseInt(delay), Integer.parseInt(capacity));
				n.Add(link0);
			}
			catch (Exception e) 
			{
			}
	    }
	    n.Print();
		System.out.println("--------------------------------------------");
		System.out.println("GOODBYE!");
	}

}
