package CSE360;



import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;

import java.io.FileReader;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.net.URL;

import java.lang.Object;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Date;


import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.util.Date;

import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.JFrame;
import org.json.JSONException;

import java.awt.Font;
import java.awt.font.*;


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
        setOpaque(true);
        setVisible(true);
        setSize(xb,yb);
    }

    public void updateWeatherPanel(double latitude,double longitude) { 
        geoLocation.UpdateGeoLocation(latitude, longitude);
        temp.setText(geoLocation.getWeatherFieldString("currently", "temperature")+"\u00b0"+"F");
        System.out.println("Updated temperature: "+geoLocation.getWeatherFieldString("currently", "temperature")+"\u00b0"+"F");
    }
}


