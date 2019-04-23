package objs;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
		return this.asString(shortestRoute, false).toString();
    }
	public String asString(Route shortestRoute, boolean printOnlyVertexID)
	{

		StringBuilder sb = new StringBuilder();
		sb.append("Time (nano seconds): "+time_ns+"\n");
		sb.append("Time (micro seconds): " + TimeUnit.MICROSECONDS.convert(time_ns, TimeUnit.NANOSECONDS)+"\n");
		sb.append("Time (milli seconds): " + TimeUnit.MILLISECONDS.convert(time_ns, TimeUnit.NANOSECONDS)+"\n");
		sb.append("Time (seconds): " + TimeUnit.SECONDS.convert(time_ns,TimeUnit.NANOSECONDS)+"\n");
		sb.append("Time (minutes): " + TimeUnit.MINUTES.convert(time_ns,TimeUnit.NANOSECONDS)+"\n");

		if(shortestRoute == null || shortestRoute.getNodes() == null || shortestRoute.getNodes().size() == 0)
		{
			sb.append("No route found.\n");
			return sb.toString();
		}

		List<String> nodes = shortestRoute.getNodes();
//    	str += "Distance between " + ports.get(0) + " and " + ports.get(ports.size() - 1) + " = " + shortestRoute.getDistance() + " miles\n";

		sb.append("Distance between " + nodes.get(0) + " and " + nodes.get(nodes.size() - 1) + " = " + shortestRoute.getDistance() + " ("+nodes.size() + " nodes)\n");
		if(printOnlyVertexID) {
			sb.append("Shortest route: {");
		}else{
			sb.append("Shortest route: \n");
		}
		Vertex prev = null;
		for(String code : nodes)
		{
			if(printOnlyVertexID == true) { // Do not print the vertices info if not requested to avoid verbose output on the console
				sb.append(graph.getVertex(code).getCode() + ",");
			} else{
				sb.append(graph.getVertex(code).toString() + "\n");
			}

			//if(prev != null)
			//{
//        		totalDist += Edge.calculateDistance(graph.getVertex(code), prev);
//        		str += "\n"+prev.getCode() + " -> " + code + " = " + Edge.calculateDistance(graph.getVertex(code), prev)+". Total Dist="+totalDist;
			//}
			//prev = graph.getVertex(code);
		}
		if(printOnlyVertexID){
			sb.setLength(sb.length() - 1); // remove the trailing comma and add }
			sb.append("}");
		}
		//str += "\n";
		return sb.toString();
	}
}
