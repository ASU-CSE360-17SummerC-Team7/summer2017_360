/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Team4Week2;

//package CSE360;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.awt.*;
import javax.swing.*;

import org.json.JSONException;
import org.json.JSONObject;

public class ExampleWeather extends JFrame{
    
    private JFrame f;
    private JPanel p;
    //private JButton b1;
    //private JLabel label;
    private JTextArea ta1, ta2, ta3, ta4, ta5, ta6, ta7, ta8, ta9;
    private GridLayout grid;
    
    
    public ExampleWeather(){
        
        gui();
        
    }
    
    public void gui(){
        f = new JFrame();
        f.setVisible(true);
        f.setSize(600,800);
        f.setTitle("Darksky Weather");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        p = new JPanel();
        p.setBackground(Color.YELLOW);
        
        //b1 = new JButton("Get Weather");
        //tf1 = new JTextField("TEst1");
        ta1 = new JTextArea(1,20);
        ta2 = new JTextArea(1,20);
        ta3 = new JTextArea(1,20);
        ta4 = new JTextArea(1,50);
        ta5 = new JTextArea(1,20);
        ta6 = new JTextArea(1,20);
        ta7 = new JTextArea(1,20);
        ta8 = new JTextArea(1,20);
        ta9 = new JTextArea(1,20);
        
        ta1.setEditable(false);
        ta2.setEditable(false);
        ta3.setEditable(false);
        ta4.setEditable(false);
        ta5.setEditable(false);
        ta6.setEditable(false);
        ta7.setEditable(false);
        ta8.setEditable(false);
        ta9.setEditable(false);
        //tf2 = new JTextField("Test2",20);
        //label = new JLabel("Weather Pane");
        p.setLayout(new GridLayout(0,1));
        //p.add(b1);
        p.add(ta1);
        p.add(ta2);
        p.add(ta3);
        p.add(ta4);
        p.add(ta5);
        p.add(ta6);
        p.add(ta7);
        p.add(ta8);
        //p.add(label);
        
        //p.add(tf1,BorderLayout.SOUTH);
        //p.add(tf2,BorderLayout.SOUTH);
        f.add(p);
        //f.add(p,BorderLayout.CENTER);
        ta1.setText("Text Area 1");
        ta2.setText("Text Area 2");
        ta3.setText("Text Area 3");
        ta4.setText("Text Area 4");
        ta5.setText("Text Area 5");
        ta6.setText("Text Area 6");
        ta7.setText("Text Area 7");
        ta8.setText("Text Area 8");
        
        
        
    }

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
        
      is.close();
    }
  }
  
      
  

  public static void main(String[] args) throws IOException, JSONException {
    ExampleWeather e = new ExampleWeather();
        
    String yourKey = "cab82799b5b1e817dbccab51d6d7ec40"; 
    JSONObject json = readJsonFromUrl("https://api.darksky.net/forecast/"
            + yourKey +"/37.8267,-122.4233");
    //System.out.println(json.toString());
    System.out.println(json.getJSONObject("currently").getString("summary"));        
    //System.out.println(json.getJSONObject("currently").getString("pressure"));
    
    Double time = 0.0;
    Double nearestStorm = 0.0;
    Double probPrecip = 0.0;      
    Double windVelocity = 0.0;
    Double windDirection = 0.0;
    Double atmosPressure = 0.0;
    Double vis = 0.0;
    
    nearestStorm = json.getJSONObject("currently").getDouble("nearestStormDistance");
    time = json.getJSONObject("currently").getDouble("time");
    probPrecip = json.getJSONObject("currently").getDouble("precipProbability");
    windVelocity = json.getJSONObject("currently").getDouble("windSpeed");
    windDirection = json.getJSONObject("currently").getDouble("windBearing");
    atmosPressure = json.getJSONObject("currently").getDouble("pressure");
    vis = json.getJSONObject("currently").getDouble("visibility");
    
    System.out.println("Nearest Storm: " + nearestStorm + " miles");
    System.out.println("Probability of Precipitation :" + probPrecip + "%");
    


    //e.ta1.setText(json.getJSONObject("currently").getString("time"));        
    //e.ta1.setText("Icon: " + json.getJSONObject("currently").getString("icon"));
    e.ta1.setText("Forecast: " + json.getJSONObject("daily").getString("summary"));
    e.ta2.setText("Currently: " + json.getJSONObject("currently").getString("summary"));
    e.ta3.setText("Nearest Storm: " + String.valueOf(nearestStorm) + " miles");
    e.ta4.setText("Prob of Precip: " + String.valueOf(probPrecip) + "%");
    e.ta5.setText("Wind Speed: " + String.valueOf(windVelocity) + " mph");
    e.ta6.setText("Wind Direction: " + String.valueOf(windDirection) + "°");
    e.ta7.setText("Pressure: " + String.valueOf(atmosPressure) + " hPa");
    e.ta8.setText("Visibility: " + String.valueOf(vis) + " miles");
   
    
    //e.ta5.setText(String.valueOf(time));
    
    

    //System.out.println(json.getJSONObject("currently").get("time"));
    
    
  
  }
}