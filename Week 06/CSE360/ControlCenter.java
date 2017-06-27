

package CSE360;
 
 import java.util.Hashtable;
 import java.util.Observable;
 
 public class ControlCenter extends Observable {
 
   private boolean showGhost;
   private String city;
   private String gps; 
   private static ControlCenter instance;
   
   private Hashtable<String, String> data = new Hashtable<String, String>();
   
   public final static String[] CITIES = { "Tempe", "NY", "Bangalore",
     "Venice", "Dublin", "SFO", "Berlin", "London",
     "Mexico", "Delhi" };
   
   private ControlCenter() {
     data.put(CITIES[0], "33.424564,-111.928001");    
     data.put(CITIES[1], "40.730610,-73.935242");
     data.put(CITIES[2], "12.972442,77.580643");
     data.put(CITIES[3], "45.444958,12.328463");
     data.put(CITIES[4], "53.350140,-6.266155" );
     data.put(CITIES[5], "37.733795,-122.446747");
     data.put(CITIES[6], "52.518623,13.376198" );
     data.put(CITIES[7], "51.501476,-0.140634");
     data.put(CITIES[8], "19.451054,-99.125519"); 
     data.put(CITIES[9], "28.644800,77.216721");
   }
   
   public static ControlCenter getInstance() {
     if (instance == null) {
       instance = new ControlCenter();
     }
     return instance;
   }
 
   public String getCity() {
     return city;
   }
 
   public boolean getShowGhost() {
     return showGhost;
   }
 
   public String getGPS() {
     return gps;
   }
 
   void setShowGhost(boolean b) {
     showGhost = b;
     setChanged();
   }
 
   public void setCity(String city) {
     this.city = city;
     setGPS(city);
     setChanged();
   }
   
   private void setGPS(String city) {
     this.gps = data.get(city);
   }  
   
 } 

/*
package CSE360;

import java.util.Observable;

public class ControlCenter extends Observable{
    private boolean showGhost;
    private String city;
    private static ControlCenter instance;
    private double _lat;
    private double _long;
    
    private ControlCenter() {}
    
    public static ControlCenter getInstance(){
        if (instance == null){
            instance = new ControlCenter();
        }
        return instance;
    }
    
    public String getCity(){
        
        return city;
    }
    
    public boolean getShowGhost() {
        return showGhost;
    }
    
    public void setCoordinates(double _lat, double _long){
        this._lat = _lat;
        this._long = _long;
        String coords = String.valueOf(_lat) + "," + String.valueOf(_long);
        setChanged();
        notifyObservers(coords);
        System.out.println("In setCords");
    }
}

*/
