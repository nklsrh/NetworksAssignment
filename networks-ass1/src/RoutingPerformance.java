import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;


public class RoutingPerformance {
	
	private static Network n;
	private static int rate;
	private static String network;
	private static String routing;
	private static PriorityQueue<Packet> packetQueue;
	private static PriorityQueue<Connection> connectionQueue;
	private static PriorityQueue<QueueLink> linkQueue;
	
	//Statistic variables
    private static int blocked = 0;
    private static int requests = 0;
    private static int packets = 0;
    private static double totalhops = 0;
    private static double totaldelay = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
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
		*/
		InputStream topoFile = null;
		InputStream workFile = null;
		try {
			network = args[0];
			routing = args[1];
			topoFile = new FileInputStream(args[2]);
			workFile = new FileInputStream(args[3]);
			rate = Integer.parseInt(args[4]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Reading topology from: " + topoFile);
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
	    /*
	    n.Print();
	    System.out.println("Test SHP A to D: " + n.GetSHP("A", "D"));
	    System.out.println("Test SDP A to D: " + n.GetSDP("A", "D"));
	    System.out.println("Test LLP A to D: " + n.GetLLP("A", "D"));
	    
		System.out.println("--------------------------------------------\n");
		System.out.println("Reading Workload");
		*/
	    //Parse workload and calculate packet information
	    scanner = new Scanner(workFile);
	    ssc = null;
		packetQueue = new PriorityQueue<Packet>();
		connectionQueue = new PriorityQueue<Connection>();
	    while (scanner.hasNextLine()){
			try {
				String line = scanner.nextLine();
			    ssc = new Scanner(line);
				ssc.useDelimiter(" ");
				double time = Double.parseDouble(ssc.next());
				String r1 = ssc.next();
				String r2 = ssc.next();
				double duration = Double.parseDouble(ssc.next());
				packets += (int) Math.ceil(duration * rate);
				if (network.equals("PACKET"))
				{
					int totalpackets = (int) Math.ceil(duration * rate);
					for (double i = 0; i < totalpackets; i++)
					{
						//Add packets using the establishment time of each individual packet
						packetQueue.add(new Packet((time + i * 1d/rate), r1, r2));
					}
				}
				else if (network.equals("CIRCUIT"))
				{
					//Only one circuit is used throughout the entire connection between r1 and r2
					connectionQueue.add(new Connection(r1, r2, time, duration, (int) Math.ceil(duration * rate)));
				}
			}
			catch (Exception e) 
			{
			}
			if (network.equals("PACKET"))requests = packetQueue.size();
			if (network.equals("CIRCUIT"))requests = connectionQueue.size();
	    }
	    
		//System.out.println("--------------------------------------------\n");
		//System.out.println("Processing Packets/Connections " + packetQueue.size() + "/" + connectionQueue.size());
	    //Start processing the stored packets/connections
		linkQueue = new PriorityQueue<QueueLink>();
	    if (network.equals("PACKET"))
	    {
		    //Using packet information to calculate network circuits for each packet
		    while (!packetQueue.isEmpty()) 
		    {
		    	Packet current = packetQueue.poll();
		    	ArrayList<String> circuit = null;
		    	if (routing.equals("SHP"))
		    	{
		    		circuit = n.GetSHP(current.GetStartRouter(), current.GetEndRouter());
		    		totalhops += circuit.size() - 1;
		    		totaldelay += n.GetDelay(circuit);
		    	}
		    	else if (routing.equals("SDP"))
		    	{
		    		circuit = n.GetSDP(current.GetStartRouter(), current.GetEndRouter());
		    		totalhops += circuit.size() - 1;
		    		totaldelay += n.GetDelay(circuit);
		    	}
		    	else if (routing.equals("LLP"))
		    	{
		    		circuit = new ArrayList<String>();
		    		circuit.add(current.GetStartRouter());
		    		circuit.add(current.GetEndRouter());
		    	}
    			linkQueue.add(new QueueLink(circuit, current.GetTime(), true, 1d/rate));
		    }
	    }
	    else if (network.equals("CIRCUIT"))
	    {
	    	//Using connection information to calculate network circuits for each connection
	    	while (!connectionQueue.isEmpty())
	    	{
	    		Connection current = connectionQueue.poll();
	    		ArrayList<String> circuit = null;
		    	if (routing.equals("SHP"))
		    	{
		    		circuit = n.GetSHP(current.GetStartRouter(), current.GetEndRouter());
		    		totalhops += circuit.size() - 1;
		    		totaldelay += n.GetDelay(circuit);
		    	}
		    	else if (routing.equals("SDP"))
		    	{
		    		circuit = n.GetSDP(current.GetStartRouter(), current.GetEndRouter());
		    		totalhops += circuit.size() - 1;
		    		totaldelay += n.GetDelay(circuit);
		    	}
		    	else if (routing.equals("LLP"))
		    	{
		    		circuit = new ArrayList<String>();
		    		circuit.add(current.GetStartRouter());
		    		circuit.add(current.GetEndRouter());
		    	}
		    	QueueLink temp = new QueueLink(circuit, current.GetTime(), true, current.duration);
		    	temp.amount = current.amount;
    			linkQueue.add(temp);
	    	}
	    }
	    
		//System.out.println("--------------------------------------------\n");
		//System.out.println("Processing Link Requests " + linkQueue.size());
	    
	    //Start processing the link requests
		//int i = 0;
	    while (!linkQueue.isEmpty())
	    {
	    	//i++;
	    	//if (i % 10000 == 0) System.out.println(i);
	    	QueueLink current = linkQueue.poll();
	    	//Check if the link request is a circuit establishment or removal
	    	if (current.establish)
	    	{
	    		if (routing.equals("LLP"))
	    		{
	    			current.path = n.GetLLP(current.path.get(0), current.path.get(1));
	    			totalhops += current.path.size() - 1;
	    			totaldelay += n.GetDelay(current.path);
	    		}
	    		if (n.AddConnToLink(current.path))
	    		{
	    			linkQueue.add(new QueueLink(current.path, current.timestamp + current.duration, false, 0));
	    		}
	    		else
	    		{
	    			//Requested link is already at full capacity, request is blocked
	    			if (network.equals("PACKET")) blocked++;
	    			if (network.equals("CIRCUIT")) blocked += current.amount;
	    		}
	    	}
	    	else
	    	{
	    		//If the link request was not blocked earlier
	    		//System.out.println(current.path);
	    		n.RemoveConnFromLink(current.path);
	    		
	    	}
	    }

		//System.out.println("--------------------------------------------\n");
		//System.out.println("GOODBYE! " + blocked);
	    DecimalFormat df = new DecimalFormat("#.##");
	    System.out.println("total number of virtual circuit requests: " + requests);
	    System.out.println("total number of packets: " + packets);
	    System.out.println("number of successfully routed packets: " + (packets - blocked));
	    System.out.println("percentage of successfully routed packets: " + (df.format(100d - 100d * (double)blocked / (double)packets)));
	    System.out.println("number of blocked packets: " + blocked);
	    System.out.println("percentage of blocked packets: " + (df.format(100d * (double)blocked / (double)packets)));
	    System.out.println("average number of hops per circuit: " + (df.format(totalhops / requests)));
	    System.out.println("average cumulative propagation delay per circuit: " + (df.format(totaldelay / requests)));
	}

}
