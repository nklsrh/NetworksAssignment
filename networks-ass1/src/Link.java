
public class Link {
	public int propogationDelay;
	public int capacity;
	public Router r1;
	public Router r2;
	
	public Link (Router router1, Router router2, int delay, int cap)
	{
		r1 = router1;
		r2 = router2;
		propogationDelay = delay;
		capacity = cap;
	}
	
	public String ToString()
	{
		return (r1.ToString() + "-" + r2.ToString() + "\t Delay " + propogationDelay + "\t Capacity " + capacity);
	}
}
