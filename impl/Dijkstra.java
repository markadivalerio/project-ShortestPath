package impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import objs.Edge;
import objs.Graph;
import objs.MinHeap;
import objs.Route;
import objs.Vertex;

public class Dijkstra extends ShortestPath
{
	public Dijkstra()
	{
		super();
	}
	
	public Dijkstra(Graph g)
	{
		super(g);
	}

    public Dijkstra(String source, String destination)
    {
    	super(source, destination);
    }
    
    public Dijkstra(String source, String destination, Graph g)
    {
    	super(source, destination, g);
    }
    
	@Override
	public Route findShortestPath(String source, String destination) {
//	public Route findShortestPath2(String source, String destination) {
        Route resultRoute = null;
        MinHeap minHeap = new MinHeap();
        Set<Vertex> visitedNodes = new HashSet<Vertex>();
//        Set<Route> travelPath = new HashSet<Route>();
        
        Vertex startVertex = graph.getVertex(source);
        Vertex endVertex = graph.getVertex(destination);

        ArrayList<Vertex> neighbors = graph.getConnectedVertices(startVertex);
//        System.out.println(graphObj.getEdges(airports.get(source)));
        System.out.println(neighbors);
        for (Vertex neighbor : neighbors) {
        	long dist = Edge.calculateDistance(startVertex, neighbor);
            minHeap.add(new Route(dist, source, neighbor.getCode()));
        }
        visitedNodes.add(startVertex); // Add starting (source node) to the visited set

        while(!minHeap.isEmpty()){
            Route shortestRoute = minHeap.poll();
//            Vertex neighbor = airports.get(shortestRoute.getYetToVisitAirport());
            Vertex neighbor = graph.getVertex(shortestRoute.getYetToVisitAirport());

            if (visitedNodes.contains(neighbor)){
                continue;
            }
            if (neighbor.getCode().equals(endVertex.getCode())) { // destination has been reached
                resultRoute = shortestRoute;
                break;
            }
            List<Vertex> further_neighbors = graph.getConnectedVertices(neighbor);
            for(Vertex further_neighbor : further_neighbors) {
                if(!visitedNodes.contains(further_neighbor)){
                    long new_distance = shortestRoute.getDistance() + Edge.calculateDistance(neighbor, further_neighbor);
                    Route newRoute = new Route();
                    newRoute.setDistance(new_distance);
                    for(int i=0; i< shortestRoute.getAirports().size();i++){
                        newRoute.addAirport(shortestRoute.getAirports().get(i)); // keep adding the airports in the route e..g SFO -> AUS -> DFW
                    }
                    newRoute.addAirport(further_neighbor.getCode());

                    minHeap.add(newRoute);
                }
            }
            visitedNodes.add(neighbor); // Add to visited node to the set
        }
        return resultRoute;
	}

//	@Override
//	public Route findShortestPath(String source, String destination) {
	public Route findShortestPath2(String source, String destination) {
        Route resultRoute = null;
        MinHeap minHeap = new MinHeap();
        Set<Vertex> visitedNodes = new HashSet<Vertex>();
//        Set<Route> travelPath = new HashSet<Route>();
        Vertex startVertex = airports.get(source);
        Vertex endVertex = airports.get(destination);
        long distance = 0;

        List<Vertex> edges = graphObj.getEdges(airports.get(source));
        for (Vertex neighbor : edges) {
            distance = this.calculateDistance(airports.get(source),neighbor);
             minHeap.add(new Route(distance, source, neighbor.getCode()));
        }
        visitedNodes.add(startVertex); // Add starting (source node) to the visited set

        while(!minHeap.isEmpty()){
            Route shortestRoute = minHeap.poll();
            Vertex neighbor = airports.get(shortestRoute.getYetToVisitAirport());

            if (visitedNodes.contains(neighbor)){
                continue;
            }
            if (neighbor.equals(endVertex)) { // destination has been reached
                resultRoute = shortestRoute;
                break;
            }
            List<Vertex> further_edges = graphObj.getEdges(neighbor);
            for(Vertex further_neighbor : further_edges) {
                if(!visitedNodes.contains(further_neighbor)){

                    long new_distance = shortestRoute.getDistance() + this.calculateDistance(neighbor,further_neighbor);
                    Route newRoute = new Route();
                    newRoute.setDistance(new_distance);
                    for(int i=0; i< shortestRoute.getAirports().size();i++){
                        newRoute.addAirport(shortestRoute.getAirports().get(i)); // keep adding the airports in the route e..g SFO -> AUS -> DFW
                    }
                    newRoute.addAirport(further_neighbor.getCode());

                    minHeap.add(newRoute);
                }
            }
            visitedNodes.add(neighbor); // Add to visited node to the set
        }
        return resultRoute;
	}
}
