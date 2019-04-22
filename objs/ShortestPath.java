package objs;

import java.util.List;

public class ShortestPath implements SPAlgorithm
{
	protected String start, finish;
	protected Graph graph;
	
	protected long time_ns;
    
    public ShortestPath(String source, String destination, Graph graph)
    {
    	start = source;
    	finish = destination;
    	this.graph = graph;
    	time_ns = System.nanoTime();
    }
    
    public Route findShortestPath()
    {
    	return this.findShortestPath(start, finish);
    }
    
    public Route findShortestPath(String source, String destination)
    {
    	System.out.println("Not Implemented Yet\n");
    	return null;
    }
    
    public void startTimer()
    {
    	time_ns = System.nanoTime();
    }

    public void stopTimer()
    {
    	time_ns = System.nanoTime() - time_ns;
    }
    
    public String asString(Route shortestRoute)
    {
    	String str = "Time (nanoseconds): "+time_ns+"\n";
    	if(shortestRoute == null || shortestRoute.getNodes() == null || shortestRoute.getNodes().size() == 0)
    	{
    		str += "No route found.\n";
    		return str;
    	}

    	List<String> nodes = shortestRoute.getNodes();
//    	str += "Distance between " + ports.get(0) + " and " + ports.get(ports.size() - 1) + " = " + shortestRoute.getDistance() + " miles\n";

    	str += "Distance between " + nodes.get(0) + " and " + nodes.get(nodes.size() - 1) + " = " + shortestRoute.getDistance() + " ("+nodes.size() + " nodes)\n";
    	str += "Shortest route:\n";
    	Vertex prev = null;
        for(String code : nodes)
        {
    		str += graph.getVertex(code).toString();
    		if(prev != null)
        	{
//        		totalDist += Edge.calculateDistance(graph.getVertex(code), prev);
//        		str += "\n"+prev.getCode() + " -> " + code + " = " + Edge.calculateDistance(graph.getVertex(code), prev)+". Total Dist="+totalDist;
        	}
    		prev = graph.getVertex(code);

        	str += "\n";
        }
        str += "\n";
        return str;
    }
}
