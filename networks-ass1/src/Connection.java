
public class Connection implements Comparable<Connection> {
	public String start;
	public String end;
	public double timestamp;
	public double duration;
	public int amount;
	
	public Connection(String router1, String router2, double t, double d, int a) {
		start = router1;
		end = router2;
		timestamp = t;
		duration = d;
		amount = a;
	}

	public double GetTime() {
		return timestamp;
	}
	
	public String GetStartRouter() 
	{
		return start;
	}
	
	public String GetEndRouter()
	{
		return end;
	}
	
	public double GetDuration() 
	{
		return duration;
	}
	
	@Override
	public int compareTo(Connection c) {
		if (timestamp > c.GetTime()) {
			return 1;
		}
		return -1;
	}
	
	
}
