package objs;

import java.util.List;

public class ShortestPath implements SPAlgorithm
{
	protected String src, dest;
	protected Graph graphObj;
	protected Graph graph;
	
	protected long time_ms;
    
    public ShortestPath(String source, String destination, Graph graph)
    {
    	src = source;
    	dest = destination;
    	this.graph = graph;
    	time_ms = System.currentTimeMillis();
    }
    
    public Route findShortestPath()
    {
    	return this.findShortestPath(this.src, this.dest);
    }
    
    public Route findShortestPath(String source, String destination)
    {
    	System.out.println("Not Implemented Yet\n");
    	return null;
    }
    
    public void startTimer()
    {
    	time_ms = System.currentTimeMillis();
    }
    public void stopTimer()
    {
    	time_ms = System.currentTimeMillis() - time_ms;
    }

    public long getTimeMS()
    {
    	return time_ms;
    }
    
    public String asString(Route shortestRoute)
    {
    	if(shortestRoute == null || shortestRoute.getAirports() == null || shortestRoute.getAirports().size() == 0)
    		return "No route found.\n";

    	List<String> ports = shortestRoute.getAirports();
    	String str = "Distance between " + ports.get(0) + " and " + ports.get(ports.size() - 1) + " = " + shortestRoute.getDistance() + " miles\n";
    	str += "Shortest route:\n";
    	Vertex prev = null;
    	long totalDist = 0;
        for(String code : shortestRoute.getAirports())
        {
    		str += graph.getVertex(code).toString();
    		if(prev != null)
        	{
        		totalDist += Edge.calculateDistance(graph.getVertex(code), prev);
        		str += "\n"+prev.getCode() + " -> " + code + " = " + Edge.calculateDistance(graph.getVertex(code), prev)+". Total Dist="+totalDist;
        	}
    		prev = graph.getVertex(code);

        	str += "\n";
        }
        str += "\n";
        return str;
    }
}
