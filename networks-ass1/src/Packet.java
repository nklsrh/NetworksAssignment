
public class Packet implements Comparable<Packet> {

	public double timestamp;
	public String start;
	public String end;
	
	
	public Packet(double t, String r1, String r2)
	{
		timestamp = t;
		start = r1;
		end = r2;
	}
	
	public double GetTime()
	{
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
	
	@Override
	public int compareTo(Packet p) 
	{
		if (timestamp > p.GetTime()) {
			return 1;
		}
		return -1;
	}

	public String ToString() {
		return (start + "-" + end + "\t" + "Timestamp: " + timestamp);
	}

}
