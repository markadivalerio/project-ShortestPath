package objs;

import java.util.Objects;

public class Vertex{

    private String code;
    private String name;
    private String country;
    public double latitude;
    public double longitude;
//    private HashMap<String, Object> customAttr;
    public Vertex previousParent;
    public Vertex nextChild;
    public Double f;
    public Double g;
    
    public Vertex(String id)
    {
    	code = id;
    	name = id;
    	country = "";
        latitude = 0;
        longitude = 0;
//        customAttr = new HashMap<String, Object>();
        previousParent = null;
        nextChild = null;
    }

    public Vertex(String code, String name, String country, double latitude, double longitude) {
        this.code = code;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
//        customAttr = new HashMap<String, Object>();
        previousParent = null;
        nextChild = null;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    
//    public Object get(String attrKey)
//    {
//    	return customAttr.getOrDefault(attrKey, null);
//    }
//    
//    public Object get(String attrKey, Object defaultValue)
//    {
//    	return customAttr.getOrDefault(attrKey, defaultValue);
//    }
//    
//    public void set(String attrKey, Object attrValue)
//    {
//    	customAttr.put(attrKey, attrValue);
//    }
//    
//    public int attrSize()
//    {
//    	return customAttr.size();
//    }
//    
//    public Set<String> attrKeys()
//    {
//    	return customAttr.keySet();
//    }
    
//    public long getDistanceTo(Vertex x)
//    {
//    	return Edge.calculateDistance(this, x);
//    }

    @Override
    public boolean equals(Object o) {
        //System.out.println("equals called");

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex node = (Vertex) o;
        
//        return node.getCode().equals(this.getCode());

        if (Double.compare(node.latitude, latitude) == 0 &&
                Double.compare(node.longitude, longitude) == 0 &&
                code.equals(node.code) &&
                name.equals(node.name) &&
                country.equals(node.country))
        {
//        	if(!customAttr.keySet().equals(node.attrKeys()))
//        		return false;
//            for(String attrKey: customAttr.keySet())
//            {
//            	if(!customAttr.get(attrKey).equals(node.get(attrKey)))
//            	{
//            		return false;
//            	}
//            }
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
       // System.out.println("hashcode called");

        return Objects.hash(this.code, this.name, this.country, this.latitude, this.longitude);
        //return Objects.hash(this.code, this.name, this.country);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "code=" + code +
                ", name=" + name +
                ", country=" + country +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

