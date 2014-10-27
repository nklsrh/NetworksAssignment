import java.util.ArrayList;


public class QueueRouter implements Comparable<QueueRouter> {
	private String name;
	private int cost;
	private ArrayList<String> path;
	
	public QueueRouter(ArrayList<String> prevPath, String n, int c)
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
	
	public int GetCost()
	{
		return cost;
	}
	
	public int SetCost(int c)
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
		return cost - r.GetCost();
	}
}
