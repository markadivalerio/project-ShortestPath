package objs;

import java.util.Objects;

import util.Coordinate;
import util.Haversine;

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
        this.distance = 0L;
	}

    public Edge(Vertex origin, Vertex destination, int nstops)
    {
        this.origin = origin;
        this.destination = destination;
        this.index = Edge.indexOf(origin, destination);
        this.weight = nstops;
        this.distance = Edge.calculateDistance(origin, destination);
    }
    
    public static String indexOf(Vertex u, Vertex v)
    {
    	return Edge.indexOf(u.getCode(), v.getCode());
    }
    
    public static String indexOf(String u, String v)
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
                ", nstops(weight)=" + weight +
                ", distance=" + distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination, weight);
    }
}
