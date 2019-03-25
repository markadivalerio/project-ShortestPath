package impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import objs.Edge;
import objs.Graph;
import objs.Route;
import objs.Vertex;

public class BellmanFord extends ShortestPath
{
	private final long REALLY_BIG_NUMBER = 99999999999999L;
	
	public BellmanFord()
	{
		super();
	}
	
	public BellmanFord(Graph g)
	{
		super(g);
	}

    public BellmanFord(String source, String destination)
    {
    	super(source, destination);
    }
    
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
        
//        for(Edge e1: graph.getConnectedEdges(source))
        for(Vertex start: originVertexes)
//        for(String startCode:graph.getRealVertices())
        {
//        	Vertex start = e1.getDestination();
//        	Vertex start = graph.getVertex(startCode);
        	String startCode = start.getCode();
//        	if(startCode.equals(source))
//        		continue;
//        	for(String nextVertexCode: graph.getRealVertices())
//            {
//        		Edge e = graph.getRealEdge(startCode, nextVertexCode);
//        		
//        		if(e == null) {
//        			continue;
//        		}
//        	for(Edge e: graph.getConnectedEdges(startCode))
//        	for(Vertex nextVertex: graph.getRealVertices())
//        	System.out.print('.');
        	for(Edge e: graph.getConnectedEdges(startCode))
        	{
//        		String nextVertexCode = nextVertex.getCode();
//        		Edge e = graph.getRealEdge(edgeCode);
        		

        		Vertex nextVertex = e.getDestination();
//        		if(nextVertex.equals(start))
//        			continue;
        		String nextVertexCode = nextVertex.getCode();
        		
        		Long proposedVertexDistance = shortestDistTo.get(startCode) + e.getDistance();

//        		if(Arrays.asList("PIT", "CDG", "JFK").contains(nextVertexCode) && Arrays.asList("HOU", "PIT", "JFK").contains(startCode))
//        		{
//        			System.out.println(e);
//        			System.out.println(shortestDistTo.get(startCode) + " - " + shortestDistTo.get(nextVertexCode) + " - " + proposedVertexDistance);
//        		}
        		if(proposedVertexDistance < shortestDistTo.get(nextVertexCode))
            	{
        			Long oldDist = shortestDistTo.put(nextVertexCode, proposedVertexDistance);
    				String oldStartStr = previous.put(nextVertexCode, startCode);
//        			if(Arrays.asList("PIT", "CDG", "JFK").contains(nextVertexCode) || Arrays.asList("HOU", "PIT", "JFK").contains(startCode))
//        			{
//        				System.out.println(oldStartStr + "->" + nextVertexCode + "("+oldDist+") changed to " + previous.get(nextVertexCode) + "->"+nextVertexCode + "("+proposedVertexDistance+")");
//            		}
        		}
            }
        }
        
//        ArrayList<String> test = new ArrayList<String>(Arrays.asList("HOU","PIT","CDG","JFK"));
//        for(String key: test)
//		{
//        	System.out.println(key + " - " + shortestDistTo.get(key) + " - " + previous.get(key));
//		}
        
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

//    @Override
//    public Route findShortestPath(String source, String destination)
    public Route findShortestPath2(String source, String destination)
    {
    	if(source.equals(destination))
    	{
    		return new Route();
    	}
    	Route shortestRoute = null;
//    	Vertex startVertex = airports.get(source);
//        Vertex endVertex = airports.get(destination);
        HashMap<String, Long> shortestDistTo = new HashMap<String, Long>();
        HashMap<String, String> previous = new HashMap<String, String>();
        
        for(String code: airports.keySet())
        {
        	shortestDistTo.put(code, REALLY_BIG_NUMBER);
        	previous.put(code, null);
        }
        shortestDistTo.put(source, 0L);
    	
//        for(String start: airports.keySet())
//        for(Vertex start: graphObj.getEdges(airports.get(source)))
//        ArrayList<Vertex> neighbors = new ArrayList<Vertex>(graphObj.getVertices());
//        Vertex src = airports.get(source);
//        airports.
//        neighbors.add(src);
        ArrayList<String> originVertexes = new ArrayList<String>(airports.keySet());
        originVertexes.add(0, source);
        for(String startCode: originVertexes)
        {
        	Vertex start = airports.get(startCode);
//        	if(start.equals(source)) continue;
//        	List<Vertex> neighbors = graphObj.getEdges(airports.get(start.getCode()));
//            for(Vertex nextVertex: neighbors)
//        	for(String nextVertexCode: airports.keySet())
        	for(Vertex nextVertex: graphObj.getEdges(start))
            {
        		String nextVertexCode = nextVertex.getCode();
//        		Vertex nextVertex = airports.get(nextVertexCode);
//            	long test = calculateDistance(start, nextVertex);
            	long proposedVertexDistance = shortestDistTo.get(startCode) + calculateDistance(start, nextVertex);
//            	System.out.println(start.getCode() + " -> " + nextVertex.getCode() + " = " + nextVertexDistance + " (vs "+shortestDistTo.get(start.getCode())+")");
            	if(proposedVertexDistance < shortestDistTo.get(nextVertexCode))
            	{
            		shortestDistTo.put(nextVertexCode, proposedVertexDistance);
            		previous.put(nextVertexCode, startCode);
            	}
//            	graphObj.getEdges(airports.get(code)
//                long distance = calculateDistance(airports.get(source), neighbor);
//                minHeap.add(new Route(distance, source, neighbor.getCode()));
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
        	if(shortestDistTo.get(code) < REALLY_BIG_NUMBER)
        	{
        		String prev = previous.get(code);
        		if(prev == null && !code.equals(source))
        		{
        			System.out.println(code+" "+shortestDistTo.get(code)+" "+prev);
        		}
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
