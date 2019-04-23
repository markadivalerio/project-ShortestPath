package objs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Route {

    private long distance;
    private ArrayList<String> nodes;
    public double f;
    public double h;

    public Route(){
    	nodes = new ArrayList<String>();
        this.distance = 0;
    }
    
    public Route(Vertex s, Vertex d)
    {
    	nodes = new ArrayList<String>();
    	nodes.add(s.getName());
    	nodes.add(d.getName());
//    	this.distance = Edge.calculateDistance(s, d);
    }

    public Route(long distance, String s, String d){
        this.distance = distance;
        nodes = new ArrayList<String>();
        nodes.add(s);
        nodes.add(d);
    }
    
    public Route(long distance, String s, String d, double f){
        this.distance = distance;
        nodes = new ArrayList<String>();
        nodes.add(s);
        nodes.add(d);
        this.f = f;
    }
    
    public void addNode(String code){
    	nodes.add(code);
    }

    public long getDistance() {
        return this.distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public List<String> getNodes(){
        return this.nodes;
    }
    
    public int size()
    {
    	return this.nodes.size();
    }
    
    public void setAirports(ArrayList<String> ports)
    {
    	this.nodes = ports;
    }

    public String getLastItem()
    {
        return this.nodes.get(nodes.size()-1); // return the last item in the list which is not yet visited
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Route{ ");
        sb.append("distance = " + distance);
        sb.append(", nodes = [");
        for(String s : nodes)
        {
            sb.append(s);
            sb.append(",");
        }
        sb.setLength(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return (nodes.equals(route.nodes) && this.distance ==route.distance);
    }

    @Override
    public int hashCode() {
        //return airports.hashCode();
        return Objects.hash(this.distance, nodes.hashCode());
    }
}
