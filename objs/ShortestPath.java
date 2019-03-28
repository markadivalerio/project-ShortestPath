package objs;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.BaseData;
import util.Coordinate;
import util.Haversine;

public class ShortestPath implements SPAlgorithm
{
	protected String src, dest;
	protected Graph graphObj;
	protected Graph graph;
	protected BaseData dataObj;
	protected Map<String, Vertex> airports;
	protected Set<Flight> flights;
	
	protected long time_ms;
    
    public ShortestPath(String source, String destination, Graph graph)
    {
    	src = source;
    	dest = destination;
    	this.graph = graph;
    	if(graph == null)
    	{
	    	graphObj = new Graph(false);
	        dataObj = BaseData.getInstance();
	        if(this.init())
	        {
	        	buildGraph();
	        }
    	}
    	time_ms = System.currentTimeMillis();
    }

    public boolean init() {
        boolean bRetVal = false;
        if (dataObj.initialize()) {
            this.airports = dataObj.getAirports();
            this.flights = dataObj.getFlights();
            bRetVal = true;
        } else {
            bRetVal = false;
        }
        return bRetVal;
    }

    public boolean buildGraph() {

        boolean bRetVal = false;

        if (this.init()) {
            Iterator<Flight> i = flights.iterator();
            while (i.hasNext()) {
                Flight currentFlight = i.next();
                try {
                    Vertex origin = airports.get(currentFlight.getOrigin());
                    Vertex destination = airports.get(currentFlight.getDestination());
                    if (origin != null && destination != null) {
                        graphObj.addConnectedNodes(origin, destination, true); //undirected=true
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    continue;
                }
            }
            bRetVal = true;
        }
        return bRetVal;
    }

    public long calculateDistance(Vertex s, Vertex d){

        Coordinate source = new Coordinate(s.getLongitude(), s.getLatitude());
        Coordinate destination = new Coordinate(d.getLongitude(), d.getLatitude());
        return Math.round(Haversine.haversine(source, destination));
    }
    
    public Route findShortestPath()
    {
    	return this.findShortestPath(this.src, this.dest);
    }
    
    public Route findShortestPath(String source, String destination)
    {
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
        for(String code : shortestRoute.getAirports()){
        	try
        	{
        		str += airports.get(code).toString();
        		if(prev != null)
            	{
            		totalDist += Edge.calculateDistance(airports.get(code), prev);
            		str += "\n"+prev.getCode() + " -> " + code + " = " + Edge.calculateDistance(airports.get(code), prev)+". Total Dist="+totalDist;
            	}
        		prev = airports.get(code);
        	}
        	catch(NullPointerException e)
        	{
        		str += graph.getVertex(code).toString();
        		if(prev != null)
            	{
            		totalDist += Edge.calculateDistance(graph.getVertex(code), prev);
            		str += "\n"+prev.getCode() + " -> " + code + " = " + Edge.calculateDistance(graph.getVertex(code), prev)+". Total Dist="+totalDist;
            	}
        		prev = graph.getVertex(code);
        	}

        	str += "\n";
        }
        str += "\n";
        return str;
    }
}
