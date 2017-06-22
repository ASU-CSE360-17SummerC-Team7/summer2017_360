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
    private ImageIcon weatherIcon;
    private JLabel weatherLabel;
    // Variables declaration - do not modify                     
    private javax.swing.JPanel IconImage;
    private javax.swing.JTextArea TextSummary;
    private javax.swing.JTextArea TextCloudCover;
    private javax.swing.JTextArea TextPrecipitationProbability;
    private javax.swing.JTextArea TextTemperature;
    private javax.swing.JTextArea TextHumidity;
    private static String iconPath;
    private int xbound;
    private int ybound;
    // End of variables declaration 
    /**
     * Creates new form WeatherPanel
     */
    public Team7WeatherPanel(double latitude, double longitude,String iP, int xb, int yb)  {
        xbound=xb;ybound=yb;
        geoLocation = new Team7WeatherInfo(latitude,longitude);
        iconPath=iP;
        initComponents();
        setOpaque(false);
    }

    @SuppressWarnings("unchecked")
    private void initComponents()  {
        setBackground(Color.white);
        Font uniformFont = new Font("Courier",Font.TRUETYPE_FONT,8);
        TextSummary = new javax.swing.JTextArea(2,4);
        TextSummary.setFont(uniformFont);
        TextHumidity = new javax.swing.JTextArea(2,10);
        TextHumidity.setFont(uniformFont);
        TextTemperature = new javax.swing.JTextArea(2,10);
        TextTemperature.setFont(uniformFont);
        TextPrecipitationProbability = new javax.swing.JTextArea(2,10);
        TextPrecipitationProbability.setFont(uniformFont);
        TextCloudCover = new javax.swing.JTextArea(2,10);
        TextCloudCover.setFont(uniformFont);
        weatherIcon = new ImageIcon((new ImageIcon(iconPath+"/"+geoLocation.getWeatherFieldString("currently","icon")+".png").getImage().getScaledInstance(xbound, xbound,
                java.awt.Image.SCALE_SMOOTH)),"Weather");
        
        weatherLabel = new JLabel("", weatherIcon, JLabel.CENTER);
        weatherLabel.setIcon(weatherIcon);
        
        setLayout(new java.awt.GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx=0;c.gridy=0;
        c.gridheight=1;
        c.gridwidth=1;
        c.weighty = 0.333;
        c.weightx = 1;
        weatherLabel.setOpaque(true);
        weatherLabel.setBackground(Color.white);
        add(weatherLabel,c);
        c.gridx=0;c.gridy=1;
        TextSummary.setText(geoLocation.getWeatherFieldString("currently", "summary"));
        
        add(TextSummary,c);
        TextHumidity.setText(geoLocation.getWeatherFieldString("currently", "humidity")+"[Humidity]");
        c.gridx=0;c.gridy=2;
        add(TextHumidity,c);

        TextTemperature.setText(geoLocation.getWeatherFieldString("currently", "temperature")+"\u00b0"+" F[Temp]");
        c.gridx=0;c.gridy=3;
        add(TextTemperature,c);

        TextPrecipitationProbability.setText(geoLocation.getWeatherFieldString("currently", "precipProbability")+"[PrecipProb]");
        c.gridx=0;c.gridy=4;
        add(TextPrecipitationProbability,c);

        TextCloudCover.setText(geoLocation.getWeatherFieldString("currently", "cloudCover")+"[CloudCover]");
        c.gridx=0;c.gridy=5;
        add(TextCloudCover,c);
    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
    public void updateWeatherPanel(double latitude,double longitude) { 
        geoLocation.UpdateGeoLocation(latitude, longitude);
        weatherLabel.setIcon(new ImageIcon((new ImageIcon(iconPath+"/"+geoLocation.getWeatherFieldString("currently","icon")+".png").getImage().getScaledInstance(xbound, xbound,
                java.awt.Image.SCALE_SMOOTH)),"Weather"));
        TextSummary.setText(geoLocation.getWeatherFieldString("currently", "summary"));
        TextHumidity.setText(geoLocation.getWeatherFieldString("currently", "humidity")+"\n[Humidity]");
        TextTemperature.setText(geoLocation.getWeatherFieldString("currently", "temperature")+"\u00b0"+" F[Temp]");
        TextPrecipitationProbability.setText(geoLocation.getWeatherFieldString("currently", "precipProbability")+"[PrecipProb]");
        TextCloudCover.setText(geoLocation.getWeatherFieldString("currently", "cloudCover")+"[CloudCover]");
    }
}


