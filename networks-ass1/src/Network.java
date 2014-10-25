import java.util.ArrayList;


public class Network {

	ArrayList<Link> links = null;
	ArrayList<Router> routers = null;
	
	public Network ()
	{
		links = new ArrayList<Link>();
		routers = new ArrayList<Router>();
	}
	
	public void Add (Link l)
	{
		links.add(l);
		AddRouter(l.r1);
		AddRouter(l.r2);
	}
	
	void AddRouter (Router r1)
	{
		boolean pass = true;
		for (Router r: routers)
		{
			if (r.name.equals(r1.name))
			{
				pass = false;
				return;		// for some reason this didn't work in test so I added the pass condition below
			}
		}
		if (pass)
		{
			routers.add(r1);
		}
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
		System.out.println("---------------------------\n");
	}
}
