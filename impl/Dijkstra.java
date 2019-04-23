package impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import objs.*;

public class Dijkstra extends ShortestPath
{
    public Dijkstra(String source, String destination, Graph g)
    {
    	super(source, destination, g);
    }
    
	@Override
	public Route findShortestPath(String source, String destination) {
        Route resultRoute = null;
        MinHeap minHeap = new MinHeap();
        Set<Vertex> visitedNodes = new HashSet<Vertex>();
//        Set<Route> travelPath = new HashSet<Route>();
        
        Vertex startVertex = graph.getVertex(source);
        Vertex endVertex = graph.getVertex(destination);

        //System.out.println("start vertex = " + startVertex);
        //System.out.println("start vertex = " + endVertex);


        ArrayList<Vertex> neighbors = graph.getConnectedVertices(startVertex);

        //System.out.println("neighbors count = " + neighbors.size());

        for (Vertex neighbor : neighbors)
        {
//        	long dist = Edge.calculateDistance(startVertex, neighbor);
        	Edge e = graph.findEdge(startVertex, neighbor);
        	long dist = e.getWeight();
            minHeap.add(new Route(dist, source, neighbor.getCode()));
            //System.out.println("Edge = " + e);
        }
        visitedNodes.add(startVertex); // Add starting (source node) to the visited set

        while(!minHeap.isEmpty()){
            Route shortestRoute = minHeap.poll();
            //System.out.println(shortestRoute);
            Vertex neighbor = graph.getVertex(shortestRoute.getLastItem());

            if (visitedNodes.contains(neighbor)){
                continue;
            }
            if (neighbor.getCode().equals(endVertex.getCode())) { // destination has been reached
                resultRoute = shortestRoute;
                break;
            }
            List<Vertex> further_neighbors = graph.getConnectedVertices(neighbor);
            for(Vertex further_neighbor : further_neighbors) {
                //System.out.println("further_neighbor = " + further_neighbor);
                if(!visitedNodes.contains(further_neighbor))
                {
                	//
                    Edge e = graph.findEdge(neighbor, further_neighbor);
                	if(e == null)
                	{
                		continue;
                	}
                	long new_distance = (long)(shortestRoute.getDistance() + e.getWeight());
//                    long new_distance = shortestRoute.getDistance() + Edge.calculateDistance(neighbor, further_neighbor);
                    Route newRoute = new Route();
                    newRoute.setDistance(new_distance);
                    for(int i=0; i< shortestRoute.getNodes().size();i++){
                        newRoute.addNode(shortestRoute.getNodes().get(i)); // keep adding the airports in the route e..g SFO -> AUS -> DFW
                    }
                    newRoute.addNode(further_neighbor.getCode());

                    minHeap.add(newRoute);
                }
            }
            visitedNodes.add(neighbor); // Add to visited node to the set
        }
        return resultRoute;
	}

	// Using Fibonacci Heap
    public Route findShortestPathUsingFibonacciHeap(String source, String destination) {
        Route resultRoute = null;
        FibonacciHeap<Route> fiboHeap = new FibonacciHeap<>();
        Set<Vertex> visitedNodes = new HashSet<Vertex>();

        Vertex startVertex = graph.getVertex(source);
        Vertex endVertex = graph.getVertex(destination);

        //System.out.println("start vertex = " + startVertex);
        //System.out.println("start vertex = " + endVertex);


        ArrayList<Vertex> neighbors = graph.getConnectedVertices(startVertex);

        //System.out.println("neighbors count = " + neighbors.size());

        for (Vertex neighbor : neighbors)
        {
            Edge e = graph.findEdge(startVertex, neighbor);
            long dist = e.getWeight();
            //minHeap.add(new Route(dist, source, neighbor.getCode()));
            fiboHeap.enqueue(new Route(dist, source, neighbor.getCode()),Double.valueOf(dist)); // use weight as priority
            //System.out.println("Edge = " + e);
        }
        visitedNodes.add(startVertex); // Add starting (source node) to the visited set

        while(!fiboHeap.isEmpty()){
            Route shortestRoute = fiboHeap.dequeueMin().getValue();
            //System.out.println(shortestRoute);
            Vertex neighbor = graph.getVertex(shortestRoute.getLastItem());

            if (visitedNodes.contains(neighbor)){
                continue;
            }
            if (neighbor.getCode().equals(endVertex.getCode())) { // destination has been reached
                resultRoute = shortestRoute;
                break;
            }
            List<Vertex> further_neighbors = graph.getConnectedVertices(neighbor);
            for(Vertex further_neighbor : further_neighbors) {
                //System.out.println("further_neighbor = " + further_neighbor);
                if(!visitedNodes.contains(further_neighbor))
                {
                    //
                    Edge e = graph.findEdge(neighbor, further_neighbor);
                    if(e == null)
                    {
                        continue;
                    }
                    long new_distance = (long)(shortestRoute.getDistance() + e.getWeight());
//                    long new_distance = shortestRoute.getDistance() + Edge.calculateDistance(neighbor, further_neighbor);
                    Route newRoute = new Route();
                    newRoute.setDistance(new_distance);
                    for(int i=0; i< shortestRoute.getNodes().size();i++){
                        newRoute.addNode(shortestRoute.getNodes().get(i)); // keep adding the airports in the route e..g SFO -> AUS -> DFW
                    }
                    newRoute.addNode(further_neighbor.getCode());

                    //minHeap.add(newRoute);
                    fiboHeap.enqueue(newRoute,Double.valueOf(new_distance));
                }
            }
            visitedNodes.add(neighbor); // Add to visited node to the set
        }
        return resultRoute;
    }
}
