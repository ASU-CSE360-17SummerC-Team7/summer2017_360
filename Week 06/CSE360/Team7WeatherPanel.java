package CSE360;

import java.awt.*;
import javax.swing.*;

public class Team7WeatherPanel extends JPanel
{
    private Team7WeatherInfo geoLocation;
    private JLabel temp;
    private int xbound;
    private int ybound;
    private int xsize;
    private int ysize;
    // End of variables declaration 
    /**
     * Creates new form WeatherPanel
     */
    public Team7WeatherPanel(double latitude, double longitude, int xoffset, int yoffset, int xb, int yb)  {

        setLayout(new BorderLayout());
        xbound=xb;ybound=yb;
        xsize=xbound-xoffset; ysize=ybound-yoffset;
        geoLocation = new Team7WeatherInfo(latitude,longitude);
        temp = new JLabel(geoLocation.getWeatherFieldString("currently", "temperature")+"\u00b0");//,SwingConstants.LEFT);
        //System.out.println("Initial temperature: "+geoLocation.getWeatherFieldString("currently", "temperature")+"\u00b0"+"F");
        temp.setFont(new Font("Courier",Font.BOLD,ysize/2));
        //System.out.println("xsize: "+Integer.toString(xsize)+ " xbound: "+Integer.toString(xb)+ " xoffset: "+Integer.toString(xoffset)+" String: "+temp.getText());
        this.setSize(xbound,ybound);
        temp.setBounds(xoffset,yoffset,xbound,ybound);
	temp.setSize(xbound,ybound);
	add(temp,BorderLayout.SOUTH);
        setOpaque(false);
        setVisible(true);
    }

    // method: updateWeatherPanel
    // independently updates weather information with new latitude,longitude
    public void updateWeatherPanel(double latitude,double longitude) { 
        geoLocation.UpdateGeoLocation(latitude, longitude);
        temp.setText(geoLocation.getWeatherFieldString("currently", "temperature")+"\u00b0");
        temp.setFont(new Font(temp.getFont().getName(),temp.getFont().getStyle(),xsize/2));
        //System.out.println("Updated temperature: "+geoLocation.getWeatherFieldString("currently", "temperature")+"\u00b0"+"F");
    }
    // method: updateBounds
    // helper function to abstract gui resizing issues
    public void updateBounds(int xoffset, int yoffset, int xb,int yb){
        xbound=xb;ybound=yb;xsize=xbound-xoffset; ysize=ybound-yoffset;
        this.setSize(xbound,ybound); temp.setSize(xbound,ybound);
        //System.out.println("xsize: "+Integer.toString(xsize)+ " xbound: "+Integer.toString(xb)+ " xoffset: "+Integer.toString(xoffset)+" String: "+temp.getText());
        //System.out.println("ysize: "+Integer.toString(ysize)+ " ybound: "+Integer.toString(yb)+ " yoffset: "+Integer.toString(yoffset)+" String: "+temp.getText());
        temp.setFont(new Font("Courier",Font.BOLD,ysize/2));
        temp.setBounds(xoffset,yoffset,xbound,ybound);
        //System.out.println("Temperature Located at : ("+Integer.toString(xoffset)+","+Integer.toString(yoffset)+") of (0:"+Integer.toString(xbound)+",0:"+Integer.toString(ybound)+")");
    }
}


