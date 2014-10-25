import java.util.ArrayList;
import java.util.PriorityQueue;


public class Network {

	private ArrayList<Link> links;
	private ArrayList<Router> routers;
	private PriorityQueue<Packet> packetQueue;
	
	public Network ()
	{
		links = new ArrayList<Link>();
		routers = new ArrayList<Router>();
		packetQueue = new PriorityQueue<Packet>();
	}
	
	//Adds a link to the network, and adds each end of the link as a neighbour to the other end
	public void AddLink (String r1, String r2, int delay, int capacity)
	{
		for (Router r: routers)
		{
			if (r.ToString().equals(r1))
			{
				if (!r.GetNeighbours().contains(r2)) 
				{
					r.AddNeighbour(r2);
				}
			}
			else if (r.ToString().equals(r2))
			{
				if (!r.GetNeighbours().contains(r1)) 
				{
					r.AddNeighbour(r1);
				}
			}
		}
		Link link = new Link(r1, r2, delay, capacity);
		links.add(link);
	}
	
	//Adds a router to the network
	public void AddRouter (String r1)
	{
		boolean pass = true;
		for (Router r: routers)
		{
			if (r.ToString().equals(r1))
			{
				pass = false;
				return;
			}
		}
		if (pass)
		{
			Router router = new Router(r1);
			routers.add(router);
		}
	}
	
	public Router GetRouter(String r)
	{
		for (Router router: routers)
		{
			if (router.ToString().equals(r))
			{
				return router;
			}
		}
		return null;
	}
	
	//Returns the link between two routers given in any order, or null if no link exists
	public Link GetLink(String r1, String r2)
	{
		for (Router router: routers)
		{
			if (router.ToString().equals(r1) || router.ToString().equals(r2))
			{
				for (String neighbour : router.GetNeighbours())
				{
					if (neighbour.equals(r2) || neighbour.equals(r1))
					{
						for (Link link : links)
						{
							if (link.MatchRouters(r1, r2)) return link;
						}
					}
				}
			}
		}
		return null;
	}
	
	//Finds the shortest hop path between two routers and returns the path as a list of router names
	//********NOT SURE IF 100% CORRECT YET*********
	public ArrayList<String> GetSHP(String r1, String r2)
	{
		ArrayList<String> path = null;
		ArrayList<String> visited = new ArrayList<String>();
		PriorityQueue<QueueRouter> queue = new PriorityQueue<QueueRouter>();
		visited.add(r1);
		int cost = 1;
		for (String router : GetRouter(r1).GetNeighbours())
		{
			//Adding initial router neighbours into queue
			queue.add(new QueueRouter(new ArrayList<String>(), router, cost));
		}
		while (!queue.isEmpty())
		{
			//Visits the topmost router at the queue
			QueueRouter current = queue.remove();
			if (current.GetName().equals(r2))
			{
				//End router found
				path = current.GetPath();
				path.add(0, r1);
				return path;
			}
			//If not at the end router yet
			visited.add(current.GetName());
			cost = current.GetCost();
			for (String router : GetRouter(current.GetName()).GetNeighbours())
			{
				if (!visited.contains(router))
				{
					//Adding all unvisited neighbours into queue with cost increment
					queue.add(new QueueRouter(current.GetPath(), router, cost + 1));
				}
			}
		}
		return path;
	}
	
	//Adds packets to the priority queue, sorted by the establishment time of each packet
	public void AddPacket(double time, String r1, String r2)
	{
		packetQueue.add(new Packet(time, r1, r2));
	}
	
	public void Print ()
	{
		System.out.println("\n---------------------------");
		System.out.println("PRINTING NETWORK");
		System.out.println(":::Routers:::");
		System.out.println("---------------------------");
		for (int i = 0; i < routers.size(); i++)
		{
			System.out.println(routers.get(i).ToString());
		}
		System.out.println(":::Links:::");
		System.out.println("---------------------------");
		for (int i = 0; i < links.size(); i++)
		{
			System.out.println(links.get(i).ToString());
		}
		//DON'T ENABLE THIS FOR THE BIG WORKLOAD.TXT IT'LL TAKE YOU YEARS TO PRINT
		//USE FOR TESTING IF THE QUEUE IS ORDERING PACKETS CORRECTLY
		/*
		System.out.println(":::Packets:::");
		System.out.println("---------------------------");
		PriorityQueue<Packet> tempQueue = new PriorityQueue<Packet>(packetQueue);
		for (int i = 0; i < tempQueue.size(); i++)
		{
			System.out.println(tempQueue.remove().ToString());
		}
		*/
		System.out.println("---------------------------\n");
	}
}
