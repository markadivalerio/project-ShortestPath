package objs;

import java.util.Objects;

import util.Coordinate;
import util.Haversine;

//import util.Coordinate;
//import util.Haversine;

public class Edge
{
	private Vertex origin, destination;
	private String index;
	private int weight;
	private Long distance;
	
	public Edge()
	{
		this.index = null;
		this.origin = null;
        this.destination = null;
        this.weight = 0;
//        this.distance = 0L;
	}

    public Edge(Vertex origin, Vertex destination, int weight)
    {
        this.origin = origin;
        this.destination = destination;
        this.index = Edge.generateEIndex(origin, destination);
        this.weight = weight;
        if(origin.latitude > 0 && origin.longitude != 0 && destination.latitude != 0 && destination.longitude != 0)
        {
        	this.distance = Edge.calculateDistance(origin, destination);
        }
        else
        {
        	this.distance = 0L;
        }
    }
    
    public static String generateEIndex(Vertex u, Vertex v)
    {
    	return Edge.generateEIndex(u.getCode(), v.getCode());
    }
    
    public static String generateEIndex(String u, String v)
    {
    	return u + "-" + v;
    }
    
    public Vertex getOrigin()
    {
        return origin;
    }

    public Vertex getDestination()
    {
        return destination;
    }
    
    public String getIndex()
    {
    	return index;
    }

    public int getWeight()
    {
        return weight;
    }
    
    public Long getDistance()
    {
    	return distance;
    }
    
    public static long calculateDistance(Vertex src, Vertex dest)
    {
    	Coordinate s = new Coordinate(src.getLongitude(), src.getLatitude());
        Coordinate d = new Coordinate(dest.getLongitude(), dest.getLatitude());
        return Math.round(Haversine.haversine(s, d));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (this == o) return true;
        Edge e = (Edge) o;
        return (this.getOrigin().equals(e.getOrigin()) && this.getDestination().equals(e.getDestination()) && this.getWeight() == e.getWeight());
//        return (weight == flight.weight && origin.equals(flight.origin) && destination.equals(flight.destination));
    }

    @Override
    public String toString() {
        return "origin=" + origin +
                ", destination=" + destination +
                ", weight=" + weight;
//                ", distance=" + distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination, weight);
    }
}
