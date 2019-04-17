package impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import objs.Edge;
import objs.Graph;
import objs.MinHeap;
import objs.Route;
import objs.ShortestPath;
import objs.Vertex;

public class AStar extends ShortestPath
{
	public AStar(String source, String destination, Graph graph)
	{
		super(source, destination, graph);
	}
	
	public Vertex GetLowestF(List<Vertex> lstVertex)
    {
    	double FValue=Double.POSITIVE_INFINITY;
    	Vertex returnVertex=null;
    	for(Vertex neighbor : lstVertex) 
    	{
    		Double f = (Double)neighbor.get("F"); 
			if(f != null && f < FValue)
			{
				FValue=f;
				returnVertex=neighbor;
			}
    	}
    	 return returnVertex;
    }
	
	public Vertex searchInList(List<Vertex> lstVertex, Vertex vertex)
	{
		for(Vertex someVertex: lstVertex)
		{
			if(someVertex.equals(vertex))
			{
				return someVertex;
			}
		}
		return null;
	}
	
	public Route shortestPath(String source, String destination)
	{
		Route resultRoute = null;
        MinHeap minHeap = new MinHeap();
        List<Vertex> OpenList=new ArrayList<Vertex>();
        List<Vertex> ClosedList=new ArrayList<Vertex>();
        
        Set<Vertex> visitedNodes = new HashSet<Vertex>();
        Vertex startVertex = graph.getVertex(source);
        Vertex endVertex = graph.getVertex(destination);
        long distance = 0;
        
        startVertex.set("G", Double.valueOf(0));
        startVertex.set("F", Double.valueOf(Edge.calculateDistance(startVertex, endVertex)));
//        startVertex.F = startVertex.G + this.calculateDistance(airports.get(source), airports.get(destination));
        OpenList.add(startVertex);
        
        while(!OpenList.isEmpty())
        {
        	
	        Vertex Current= GetLowestF(OpenList);
	        if(Current.equals(endVertex)) 
	        { // destination has been reached
	            System.out.println("Found shortest");
	            break;
	        }
	        
	        OpenList.remove(Current);
	        ClosedList.add(Current);
//	        List<Vertex> neighbors= graphObj.getEdges(airports.get(source));
	        List<Vertex> neighbors = graph.getConnectedVertices(startVertex);
	        for(Vertex neighbor : neighbors)
	        {
	        	System.out.println("In "+ neighbor.getCode());
				if(searchInList(ClosedList,neighbor) == null)  
				{
					Double g = (Double)neighbor.get("G", Double.valueOf(0));
					neighbor.set("F", g + Double.valueOf(Edge.calculateDistance(startVertex, endVertex)));
//					neighbor.F=neighbor.G+this.calculateDistance(neighbor,airports.get(destination));
					
					
					//Temp code
					if(neighbor.getCode().equals("LHR"))
					{
						System.out.println("ReachedLHR");
					}
					//End Temp

					if(searchInList(OpenList, neighbor)==null)
					{
						OpenList.add(neighbor);
					}
					else
					{
						Vertex openNeighbour=searchInList(OpenList, neighbor);
						Double g1 = (Double)neighbor.get("G");
						Double g2 = (Double)openNeighbour.get("G");
						if(g1 != null && g2 != null && g1<g2)
						{
							openNeighbour.set("G", g1);
							openNeighbour.previousParent = neighbor.previousParent;
						}
					}
				}
			}
        }
        
        System.out.println("No Path Exists");
        
        return null;
	}
}
