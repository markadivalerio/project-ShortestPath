package impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import objs.Edge;
import objs.Graph;
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
//    		Double f = (Double)neighbor.get("F"); 
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
	
//	@Override
//	public Route findShortestPath(String source, String destination)
//	{
//		Route resultRoute = null;
//        MinHeap minHeap = new MinHeap();
//        List<Vertex> OpenList=new ArrayList<Vertex>();
//        List<Vertex> ClosedList=new ArrayList<Vertex>();
//        
//        Set<Vertex> visitedNodes = new HashSet<Vertex>();
//        Vertex startVertex = graph.getVertex(source);
//        Vertex endVertex = graph.getVertex(destination);
//        long distance = 0;
//        
//        startVertex.g = Double.valueOf(0);
//        Edge e = graph.findEdge(startVertex, endVertex);
//        if(e != null)
//        	startVertex.f = startVertex.g + e.getWeight();
//        else
//        	startVertex.f = startVertex.g;
////        startVertex.set("G", Double.valueOf(0));
////        startVertex.set("F", Double.valueOf(Edge.calculateDistance(startVertex, endVertex)));
////        startVertex.F = startVertex.G + this.calculateDistance(airports.get(source), airports.get(destination));
//        OpenList.add(startVertex);
//        
//        while(!OpenList.isEmpty())
//        {
//        	
//	        Vertex Current= GetLowestF(OpenList);
//	        if(endVertex.equals(Current)) 
//	        { // destination has been reached
//	            System.out.println("Found shortest");
//	            break;
//	        }
//	        
//	        OpenList.remove(Current);
//	        ClosedList.add(Current);
////	        List<Vertex> neighbors= graphObj.getEdges(airports.get(source));
//	        List<Vertex> neighbors = graph.getConnectedVertices(startVertex);
//	        for(Vertex neighbor : neighbors)
//	        {
////	        	System.out.println("In "+ neighbor.getCode());
//				if(searchInList(ClosedList, neighbor) == null)  
//				{
////					Double g = (Double)neighbor.get("G", Double.valueOf(0));
////					neighbor.set("F", g + Double.valueOf(Edge.calculateDistance(startVertex, endVertex)));
////					neighbor.F=neighbor.G+this.calculateDistance(neighbor,airports.get(destination));
//					
//					Edge tempEdge = graph.findEdge(neighbor, endVertex);
//					if(tempEdge != null)
//						neighbor.f = neighbor.g + tempEdge.getWeight();
//					
//					//Temp code
//					if(neighbor.getCode().equals("LHR"))
//					{
//						System.out.println("ReachedLHR");
//					}
//					//End Temp
//
//					if(searchInList(OpenList, neighbor)==null)
//					{
//						OpenList.add(neighbor);
//					}
//					else
//					{
//						Vertex openNeighbour=searchInList(OpenList, neighbor);
//						if(openNeighbour != null && openNeighbour.g != null && neighbor.g < openNeighbour.g)
//						{
//							openNeighbour.g = neighbor.g;
//							openNeighbour.previousParent = neighbor.previousParent;
//						}
//					}
//				}
//			}
//        }
//        
//        System.out.println("No Path Exists");
//        
//        return null;
//	}
	
	public Route GetLowestFFromHeap(List<Route> lstRoute)
    {
    	double FValue=Double.POSITIVE_INFINITY;
    	Route returnRoute=null;
    	for(Route neighbor : lstRoute) 
    	{
			if(neighbor.f < FValue)
			{
				FValue=neighbor.f;
				returnRoute=neighbor;
			}
    	}
    	return returnRoute;
    }
	
//	public Route AStardijkstra(String source, String destination)
	@Override
	public Route findShortestPath(String source, String destination)
	{
        Route resultRoute = null;
//        MinHeap minHeap = new MinHeap();
        List<Route> minHeap = new ArrayList<Route>();
        Set<Vertex> visitedNodes = new HashSet<Vertex>();
//        Set<Route> travelPath = new HashSet<Route>();
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
            	minHeap.add(new Route(e.getWeight(), e.getOrigin().getCode(), e.getDestination().getCode(), (nextEdge.getWeight()*2)));
            	
            }
            else
            {
            	minHeap.add(new Route(distance, e.getOrigin().getCode(), e.getDestination().getCode(), (distance*2)));
            }
        }
        visitedNodes.add(startVertex); // Add starting (source node) to the visited set
        System.out.println(minHeap.toString());
        
        
        while(!minHeap.isEmpty())
        {
        	System.out.println("MinHeap Size:" + minHeap.size());
           // Route shortestRoute = minHeap.poll();
        	Route shortestRoute = GetLowestFFromHeap(minHeap);
        	System.out.println("ShortestRoute: " + shortestRoute);
        	Vertex neighbor = graph.getVertex(shortestRoute.getLastItem());
        	System.out.println("neighbor "+neighbor);
           //  minHeap.remove(shortestRoute);
            minHeap.clear();
            System.out.println("Visited: " + visitedNodes);
            if(visitedNodes.contains(neighbor))
            {
            	if(minHeap.isEmpty())
                {
                	shortestRoute.getNodes().remove(shortestRoute.getNodes().size()-1);

                	Vertex removed = graph.getVertex(shortestRoute.getNodes().get(shortestRoute.size()-1));
                	System.out.println("RemovedA: "+removed);
                	visitedNodes.remove(removed);
                	minHeap.add(shortestRoute);
                }
            	continue;
            }
            System.out.println("Neighbor: " + neighbor);
            if(neighbor.equals(endVertex))
            { // destination has been reached
                resultRoute = shortestRoute;
                break;
            }
            List<Edge> further_edges = graph.getOutboundConnectedEdges(neighbor);
            for(Edge further_edge : further_edges) {
            	Vertex further_neighbor = further_edge.getDestination();
                if(!visitedNodes.contains(further_neighbor)){

                    long new_distance = shortestRoute.getDistance() + further_edge.getWeight();
                    Edge finalEdge = graph.findEdge(further_neighbor, endVertex);
                    if(finalEdge != null)
                    {
                    	Route newRoute = new Route();
                    	newRoute.f = new_distance + finalEdge.getWeight(); 
	                    newRoute.setDistance(new_distance);
	                    for(int i=0; i< shortestRoute.size();i++){
	                        newRoute.addNode(shortestRoute.getNodes().get(i)); // keep adding the airports in the route e..g SFO -> AUS -> DFW
	                    }
	                    newRoute.addNode(further_neighbor.getCode());
	                    
	                    minHeap.add(newRoute);
                    }
                }
            }
            visitedNodes.add(neighbor); // Add to visited node to the set
            if(minHeap.isEmpty())
            {
            	shortestRoute.getNodes().remove(shortestRoute.size()-1);
//            	visitedNodes.add(graph.getVertex(removed));
            	visitedNodes.remove( graph.getVertex(shortestRoute.getNodes().get(shortestRoute.size()-1)));
            	minHeap.add(shortestRoute);
            }
        }
        
        return resultRoute;
              
//        if(resultRoute!=null)
//        {
//        	 System.out.println("Distance between " + source + " and " + destination + " = " + resultRoute.getDistance() + " miles");
//        System.out.println("Shortest route :");
//        for(String code : resultRoute.getNodes()){
//            System.out.println(graph.getVertex(code).toString());
//        }
//        }
//        else
//        {
//        	System.out.println("No Path in A Star");
//        }
       
	}
}
