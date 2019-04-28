package objs;

import java.util.Comparator;


public class DistanceComparatorAStar implements Comparator<Route> {
    // Implemented as per - https://docs.oracle.com/javase/7/docs/api/java/util/Comparator.html
    @Override
    public int compare(Route o1, Route o2) {
        int iVal = 0;
        if (o1 != null || o2 !=null){
        	 if(o1.f == o2.f) {
                 iVal = 0;
                 

//             	if(o1.getAirports().size() == o2.getAirports().size())
//             	{
//             		iVal = 0;
//             	}
//             	else if(o1.getAirports().size() < o2.getAirports().size())
//             	{
//             		iVal = -1;
//             	}
//             	else
//             	{
//             		iVal = 1;
//             	}
             }
             else if (o1.f < o2.f) {
                 iVal = -1;
             }
             else {
                 iVal = 1;
             }
         }
 
//            if(o1.getDistance() == o2.getDistance()) {
//                iVal = 0;
//                
//
////            	if(o1.getAirports().size() == o2.getAirports().size())
////            	{
////            		iVal = 0;
////            	}
////            	else if(o1.getAirports().size() < o2.getAirports().size())
////            	{
////            		iVal = -1;
////            	}
////            	else
////            	{
////            		iVal = 1;
////            	}
//            }
//            else if (o1.getDistance() < o2.getDistance()) {
//                iVal = -1;
//            }
//            else {
//                iVal = 1;
//            }
//        }
        return iVal;
    }
}