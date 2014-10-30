import java.util.ArrayList;
import java.util.PriorityQueue;


public class Network {

	private ArrayList<Link> links;
	private ArrayList<Router> routers;
	
	public Network ()
	{
		links = new ArrayList<Link>();
		routers = new ArrayList<Router>();
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
		for (Link link : links)
		{
			if (link.MatchRouters(r1, r2)) return link;
		}
		return null;
	}
	
	public int GetDelay(ArrayList<String> path)
	{
		int delay = 0;
		for (int i = 0; i < path.size() - 1; i++)
		{
			delay += GetLink(path.get(i), path.get(i+1)).propogationDelay;
		}
		return delay;
	}
	
	public boolean AddConnToLink(ArrayList<String> path)
	{
		for (int i = 0; i < path.size() - 1; i++)
		{
			if (GetLink(path.get(i), path.get(i+1)).IsFull())
			{
				return false;
			}
		}
		for (int i = 0; i < path.size() - 1; i++)	
		{
			GetLink(path.get(i), path.get(i+1)).AddConnection();
			//System.out.println("Added connection to " + path.get(i) + " and " + path.get(i+1) + " new load is " + link.currentLoad);
		}
		return true;
	}
	
	public void RemoveConnFromLink(ArrayList<String> path)
	{
		for (int i = 0; i < path.size() - 1; i++)
		{
			GetLink(path.get(i), path.get(i+1)).RemoveConnection();
			//System.out.println("Removed connection from " + path.get(i) + " and " + path.get(i+1) + " new load is " + link.currentLoad);
		}
	}
	
	//Finds the shortest delay path between two routers [cost != 1, cost = delay(r1, r2)] and returns the path as a list of router names
	public ArrayList<String> GetSDP(String r1, String r2)
	{
		ArrayList<String> path = null;
		ArrayList<String> visited = new ArrayList<String>();
		PriorityQueue<QueueRouter> queue = new PriorityQueue<QueueRouter>();
		visited.add(r1);
//		System.out.println("\nSDP from " + r1 + " to " + r2);
		double cost = 0;
		for (String router : GetRouter(r1).GetNeighbours())
		{
			//Adding initial router neighbours into queue
			queue.add(new QueueRouter(new ArrayList<String>(), router, GetLink(r1, router).propogationDelay));
//			System.out.println("add " + router);
		}
		while (!queue.isEmpty())
		{
			//Visits the topmost router at the queue
			QueueRouter current = queue.remove();
//			System.out.println("Remove this from queue " + current.GetPath());
			
			if (current.GetName().equals(r2))
			{
				//End router found
				path = new ArrayList<String>();
				path.addAll(current.GetPath());
				path.add(0, r1);
				return path;
			}
			//If not at the end router yet
			visited.add(current.GetName());
//			System.out.println("Add to Visited " + current.GetPath() + " cost " + current.GetCost());
			cost = current.GetCost();
			for (String router : GetRouter(current.GetName()).GetNeighbours())
			{
				if (!visited.contains(router))
				{
					//Adding all unvisited neighbours into queue with cost increment
					QueueRouter q = new QueueRouter(current.GetPath(), router, cost + GetLink(current.GetName(), router).propogationDelay);
					queue.add(q);
//					System.out.println("Add to Queue " + q.GetPath() + " cost " + q.GetCost());

				}
			}
		}
		return path;
	}
	
	//Finds the shortest hop path between two routers and returns the path as a list of router names
	//********NOT SURE IF 100% CORRECT YET*********
	public ArrayList<String> GetSHP(String r1, String r2)
	{
		ArrayList<String> path = null;
		ArrayList<String> visited = new ArrayList<String>();
		PriorityQueue<QueueRouter> queue = new PriorityQueue<QueueRouter>();
		visited.add(r1);
//		System.out.println("SHP from " + r1 + " to " + r2);
		double cost = 1;
		for (String router : GetRouter(r1).GetNeighbours())
		{
			//Adding initial router neighbours into queue
			queue.add(new QueueRouter(new ArrayList<String>(), router, cost));
//			System.out.println("add " + router);
		}
		
		while (!queue.isEmpty())
		{
			//Visits the topmost router at the queue
			QueueRouter current = queue.remove();
//			System.out.println("Remove this from queue " + current.GetPath());
			
			if (current.GetName().equals(r2) )
			{
//				System.out.println("Found Goal " + r2 + " cost " + current.GetCost());
				//End router found
				path = new ArrayList<String>();
				path.addAll(current.GetPath());
				path.add(0, r1);
				return path;
			}
			//If not at the end router yet
			visited.add(current.GetName());
//			System.out.println("Add to Visited " + current.GetPath() + " cost " + current.GetCost());
			cost = current.GetCost();
			for (String router : GetRouter(current.GetName()).GetNeighbours())
			{
				if (!visited.contains(router))
				{
					//Adding all unvisited neighbours into queue with cost increment
					QueueRouter q = new QueueRouter(current.GetPath(), router, cost + 1);
					queue.add(q);
//					System.out.println("Add to Queue " + q.GetPath() + " cost " + q.GetCost());
				}
			}
		}
		return path;
	}

	//Finds the least loaded path between two routers and returns the path as a list of router names
	//********NOT SURE IF 100% CORRECT YET*********
	public ArrayList<String> GetLLP(String r1, String r2)
	{
		ArrayList<String> path = null;
		ArrayList<String> visited = new ArrayList<String>();
		PriorityQueue<QueueRouter> queue = new PriorityQueue<QueueRouter>();
		visited.add(r1);
		for (String router : GetRouter(r1).GetNeighbours())
		{
			//Adding initial router neighbours into queue
			queue.add(new QueueRouter(new ArrayList<String>(), router, (double)GetLink(r1, router).GetLinkLoad() / (double)GetLink(r1, router).capacity));
		}
		
		while (!queue.isEmpty())
		{
			//Visits the topmost router at the queue
			QueueRouter current = queue.remove();	
			if (current.GetName().equals(r2) )
			{
				//End router found
				path = current.GetPath();
				path.add(0, r1);
				
				/*
				System.out.println("LLP:");
				for (int i = 0; i < path.size() - 1; i++)
				{
					System.out.println(path.get(i) + "-" + path.get(i+1) + " " + GetLink(path.get(i), path.get(i+1)).GetLinkLoad() + "/" + GetLink(path.get(i), path.get(i+1)).capacity);
				}
				*/
				
				return path;
			}
			//If not at the end router yet
			visited.add(current.GetName());
			for (String router : GetRouter(current.GetName()).GetNeighbours())
			{
				if (!visited.contains(router))
				{
					//Adding all unvisited neighbours into queue with load as cost
					QueueRouter q = new QueueRouter(current.GetPath(), router, (double)GetLink(router, current.GetName()).GetLinkLoad() / (double)GetLink(router, current.GetName()).capacity);
					queue.add(q);
				}
			}
		}
		return path;
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
