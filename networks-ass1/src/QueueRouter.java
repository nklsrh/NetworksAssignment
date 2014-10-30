import java.util.ArrayList;


public class QueueRouter implements Comparable<QueueRouter> {
	private String name;
	private double cost;
	private ArrayList<String> path;
	
	public QueueRouter(ArrayList<String> prevPath, String n, double c)
	{
		path = new ArrayList<String>();
		path.addAll(prevPath);
		path.add(n);
		name = n;
		cost = c;
	}
	
	public String GetName() 
	{
		return name;
	}
	
	public double GetCost()
	{
		return cost;
	}
	
	public double SetCost(int c)
	{
		cost = c;
		return cost;
	}
	
	public ArrayList<String> GetPath()
	{
		return path;
	}
	
	public void AddRouter (String r)
	{
		path.add(r);
	}
	
	@Override
	public int compareTo(QueueRouter r)
	{
		if (cost > r.GetCost()) {
			return 1;
		}
		return -1;
	}
}
