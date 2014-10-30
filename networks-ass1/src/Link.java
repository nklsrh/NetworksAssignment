
public class Link {
	public int propogationDelay;
	public int capacity;
	public int currentLoad;
	public String r1;
	public String r2;
	
	public Link (String router1, String router2, int delay, int cap)
	{
		r1 = router1;
		r2 = router2;
		propogationDelay = delay;
		capacity = cap;
		currentLoad = 0;
	}
	
	public int GetLinkLoad ()
	{
		// TODO: Implement Link load
		return currentLoad;
	}
	
	//Checks to see if the two given routers are indeed the two routers connected by this link
	public boolean MatchRouters(String m1, String m2)
	{
		if (r1.equals(m1) && r2.equals(m2)) return true;
		if (r2.equals(m1) && r1.equals(m2)) return true;
		return false;
	}
	
	public boolean IsFull()
	{
		return (capacity == currentLoad);
	}
	
	public void AddConnection()
	{
		currentLoad++;
	}
	
	public void RemoveConnection()
	{
		currentLoad--;
	}
	
	public String ToString()
	{
		return (r1 + "-" + r2 + "\t Delay " + propogationDelay + "\t Capacity " + capacity);
	}
}
