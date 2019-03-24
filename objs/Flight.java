package objs;

import java.util.Objects;

import util.Coordinate;
import util.Haversine;

public class Flight {
    private String origin;
    private String destination;
//	private Vertex origin;
//	private Vertex destination;
    private int nstops;
    private long distance;

    public Flight() {
        this.origin = null;
        this.destination = null;
        this.nstops = 0;
        this.distance = 0;
    }

    public Flight(String origin, String destination, int nstops) {
        this.origin = origin;
        this.destination = destination;
        this.nstops = nstops;
        this.distance = 0;
//        this.distance = Flight.calculateDistance(origin, destination);
    }
    
//    public Flight(Vertex origin, Vertex destination, int nstops) {
//        this.origin = origin;
//        this.destination = destination;
//        this.nstops = nstops;
//        this.distance = 0;
////        this.distance = Flight.calculateDistance(origin, destination);
//    }
    
    public static long calculateDistance(Vertex src, Vertex dest)
    {
    	Coordinate s = new Coordinate(src.getLongitude(), src.getLatitude());
        Coordinate d = new Coordinate(dest.getLongitude(), dest.getLatitude());
        return Math.round(Haversine.haversine(s, d));
    }
    
    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getNstops() {
        return nstops;
    }
    
    public long getDistance()
    {
    	return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        //return nstops == flight.nstops &&
          //      Objects.equals(origin, flight.origin) &&
            //    Objects.equals(destination, flight.destination);
        return (nstops == flight.nstops && origin.equals(flight.origin) && destination.equals(flight.destination));
    }

    @Override
    public String toString() {
        return "Flight{" +
                "origin=" + origin +
                ", destination=" + destination +
                ", nstops=" + nstops +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination, nstops);
    }
}
