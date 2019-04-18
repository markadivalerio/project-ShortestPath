package impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import objs.Edge;
import objs.Graph;
import objs.Route;
import objs.ShortestPath;
import objs.Vertex;

public class BellmanFord extends ShortestPath
{
	private final long REALLY_BIG_NUMBER = 99999999999999L;
    
    public BellmanFord(String source, String destination, Graph g)
    {
    	super(source, destination, g);
    }
    
    @Override
    public Route findShortestPath(String source, String destination)
//    public Route findShortestPath2(String source, String destination)
    {
    	if(source.equals(destination))
    		return new Route();
    	Route shortestRoute = null;
    	HashMap<String, Long> shortestDistTo = new HashMap<String, Long>();
        HashMap<String, String> previous = new HashMap<String, String>();
        
        for(String portCode: graph.getRealVertices())
        {
        	shortestDistTo.put(portCode, REALLY_BIG_NUMBER);
        	previous.put(portCode, null);
        }
        shortestDistTo.put(source, 0L);
        
        Vertex src = graph.getVertex(source);
        ArrayList<Vertex> originVertexes = graph.getConnectedVertices(src);
        originVertexes.add(0, src);
        
        for(Vertex start: originVertexes)
        {

        	String startCode = start.getCode();

        	for(Edge e: graph.getConnectedEdges(startCode))
        	{
        		Vertex nextVertex = e.getDestination();

        		String nextVertexCode = nextVertex.getCode();
        		
        		Long proposedVertexDistance = shortestDistTo.get(startCode) + e.getWeight();


        		if(proposedVertexDistance < shortestDistTo.get(nextVertexCode))
            	{
        			@SuppressWarnings("unused")
					Long oldDist = shortestDistTo.put(nextVertexCode, proposedVertexDistance);
    				@SuppressWarnings("unused")
					String oldStartStr = previous.put(nextVertexCode, startCode);
//        			if(Arrays.asList("PIT", "CDG", "JFK").contains(nextVertexCode) || Arrays.asList("HOU", "PIT", "JFK").contains(startCode))
//        			{
//        				System.out.println(oldStartStr + "->" + nextVertexCode + "("+oldDist+") changed to " + previous.get(nextVertexCode) + "->"+nextVertexCode + "("+proposedVertexDistance+")");
//            		}
        		}
            }
        }
        
        if(shortestDistTo.get(destination) >= REALLY_BIG_NUMBER)
        {
        	System.out.println("Destination cannot be reached");
        	return shortestRoute; //null - cannot be reached
        }
        
        System.out.println("Done calculating all shortest path, checking for negatives...");
        
        for(String code: shortestDistTo.keySet())
        {
        	if(shortestDistTo.get(code) < 0)
        	{
        		System.out.println(code);
        		System.out.println(shortestDistTo.get(code));
        		throw new UnsupportedOperationException("Error - the graph contains a negative-weight cycle");
        	}
        }
        
      //Shortest route has been found, but need to reconstruct route (reversed)
        System.out.println("Shortest path found, reconstructing path...");

        shortestRoute = new Route();
        shortestRoute.setDistance(shortestDistTo.get(destination));
        ArrayList<String> reversed = new ArrayList<String>();
        reversed.add(destination);
        String currentAirport = previous.get(destination);
        while(!source.equals(currentAirport))
        {
        	reversed.add(currentAirport);
        	currentAirport = previous.get(currentAirport);
        }
        reversed.add(currentAirport); // adds source
        Collections.reverse(reversed);
        shortestRoute.setAirports(reversed);
    	
    	return shortestRoute;
    	
    }
}
