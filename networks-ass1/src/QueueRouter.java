import java.util.ArrayList;


public class QueueRouter implements Comparable<QueueRouter> {
	private String name;
	private int cost;
	private ArrayList<String> path;
	
	public QueueRouter(ArrayList<String> prevPath, String n, int c)
	{
		path = prevPath;
		path.add(n);
		name = n;
		cost = c;
	}
	
	public String GetName() 
	{
		return name;
	}
	
	public int GetCost()
	{
		return cost;
	}
	
	public ArrayList<String> GetPath()
	{
		return path;
	}
	
	@Override
	public int compareTo(QueueRouter r)
	{
		return cost - r.GetCost();
	}
}
