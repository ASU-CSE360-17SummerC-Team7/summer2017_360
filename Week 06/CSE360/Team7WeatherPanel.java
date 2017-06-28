package CSE360;

import java.awt.*;
import javax.swing.*;

public class Team7WeatherPanel extends JPanel
{
    private Team7WeatherInfo geoLocation;
    private JLabel temp;
    private int xbound;
    private int ybound;
    // End of variables declaration 
    /**
     * Creates new form WeatherPanel
     */
    public Team7WeatherPanel(double latitude, double longitude,int xb, int yb)  {

        setLayout(new BorderLayout());
        xbound=xb;ybound=yb;
        geoLocation = new Team7WeatherInfo(latitude,longitude);
        temp = new JLabel(geoLocation.getWeatherFieldString("currently", "temperature")+"\u00b0"+"F");//,SwingConstants.LEFT);
        System.out.println("Initial temperature: "+geoLocation.getWeatherFieldString("currently", "temperature")+"\u00b0"+"F");
        temp.setHorizontalAlignment(JLabel.LEFT);
        add(temp,BorderLayout.SOUTH);
        setOpaque(false);
        setVisible(true);
        setSize(xb,yb);
    }

    public void updateWeatherPanel(double latitude,double longitude) { 
        geoLocation.UpdateGeoLocation(latitude, longitude);
        temp.setText(geoLocation.getWeatherFieldString("currently", "temperature")+"\u00b0"+"F");
        System.out.println("Updated temperature: "+geoLocation.getWeatherFieldString("currently", "temperature")+"\u00b0"+"F");
    }
    public void updateBounds(int xb,int yb){
        xbound=xb;ybound=yb;
        this.setSize(xbound,ybound);
        
    }
}


