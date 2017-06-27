
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
