import java.util.ArrayList;

public class QueueLink implements Comparable<QueueLink> {
	public ArrayList<String> path;
	//public String r1;
	//public String r2;
	public double timestamp;
	public boolean establish;
	public double duration;
	public int amount;
	
	public QueueLink(ArrayList<String> p, double time, boolean estab, double d)
	{
		path = p;
		//r1 = a;
		//r2 = b;
		timestamp = time;
		establish = estab;
		duration = d;
	}

	@Override
	public int compareTo(QueueLink l) {
		if (timestamp > l.timestamp) {
			return 1;
		}
		return -1;
	}
	
	
}
