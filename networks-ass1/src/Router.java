import java.util.ArrayList;

public class Router {
	public String name;
	private ArrayList<String> neighbours;

	public Router (String n)
	{
		name = n;
		neighbours = new ArrayList<String>();
	}
	
	public String ToString()
	{
		return name;
	}
	
	public void AddNeighbour(String r) 
	{
		if (!neighbours.contains(r)) 
		{
			neighbours.add(r);
		}
	}
	
	public ArrayList<String> GetNeighbours() 
	{
		return neighbours;
	}
}
