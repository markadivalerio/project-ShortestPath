package impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import objs.Edge;
import objs.Graph;
import objs.MinHeap;
import objs.MinHeapAStar;
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
			if(neighbor.f != null && neighbor.f < FValue)
			{
				FValue=neighbor.f;
				returnVertex=neighbor;
			}
    	}
    	 return returnVertex;
    }
	
	public Vertex searchInList(List<Vertex> lstVertex, Vertex vertex)
	{
		if(vertex == null)
			return null;
		for(Vertex someVertex: lstVertex)
		{
			if(vertex.equals(someVertex))
			{
				return someVertex;
			}
		}
		return null;
	}
	

	
	public Route GetLowestFFromHeap(List<Route> lstRoute)
    {
    	double FValue=Double.POSITIVE_INFINITY;
    	Route returnRoute=null;
    	for(Route neighbor : lstRoute) 
    	{
			if(neighbor.getNodes().size()!=0 && neighbor.f < FValue)
			{
				FValue=neighbor.f;
				returnRoute=neighbor;
			}
    	}
    	return returnRoute;
    }
	 
	@Override
	public Route findShortestPath(String source, String destination)
	{
        Route resultRoute = null; 
      //  List<Route> minHeap = new ArrayList<Route>();
        MinHeapAStar minHeap = new MinHeapAStar();
        Set<Vertex> visitedNodes = new HashSet<Vertex>(); 
        Vertex startVertex = graph.getVertex(source);
        Vertex endVertex = graph.getVertex(destination);
        long distance = 0;

        List<Edge> edges = graph.getOutboundConnectedEdges(startVertex);
        for(Edge e: edges)
        {
            distance = e.getWeight();
            Edge nextEdge = graph.findEdge(e.getDestination(), endVertex);
            if(nextEdge != null)
            {
            	 minHeap.add(new Route(e.getWeight(), e.getOrigin().getCode(), e.getDestination().getCode(), (nextEdge.getWeight())));
            	
            }
            else
            {
            	minHeap.add(new Route(distance, e.getOrigin().getCode(), e.getDestination().getCode(), (distance)));
            }
        }
        visitedNodes.add(startVertex); // Add starting (source node) to the visited set
       // System.out.println(minHeap.toString());
        
        int visitCount=1;
        while(!minHeap.isEmpty())
        {
        	visitCount++;
        	 
        	Route shortestRoute =    minHeap.poll();// GetLowestFFromHeap(minHeap);
if(shortestRoute.size()==0 && minHeap.isEmpty())
{
break;
}
        	Vertex neighbor = graph.getVertex(shortestRoute.getLastItem());
            if (visitedNodes.contains(neighbor)){
                continue;
            }
        //    minHeap.clear();
             if(neighbor.equals(endVertex))
             {
            resultRoute=shortestRoute;
            break;
             
             }
           List<Edge> further_edges = graph.getOutboundConnectedEdges(neighbor);
           boolean isEndNode=true;                
            for(Edge further_edge : further_edges) {
            	Vertex further_neighbor = further_edge.getDestination();
                if(!visitedNodes.contains(further_neighbor)){

                   long new_distance = shortestRoute.getDistance() + further_edge.getWeight();

                    	Route newRoute = new Route();
                    	newRoute.f = new_distance ;//+ finalEdge.getWeight(); 
	                    newRoute.setDistance(new_distance);
	                    for(int i=0; i< shortestRoute.size();i++){
	                        newRoute.addNode(shortestRoute.getNodes().get(i)); // keep adding the airports in the route e..g SFO -> AUS -> DFW
	                    }
	                    newRoute.addNode(further_neighbor.getCode());
	                  //22  minHeap.remove(shortestRoute);
	                    minHeap.add(newRoute);
	                    isEndNode=false;
                   
                }
            }
            visitedNodes.add(neighbor); // Add to visited node to the set
            //if(minHeap.isEmpty())
            if(isEndNode)
            { 
            	shortestRoute.getNodes().remove(shortestRoute.size()-1); 
            	//22 minHeap.remove(shortestRoute);
            	//minHeap.add(shortestRoute);
            }
        }
        
        return resultRoute;
 
       
	}
}
