/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSE360;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author rsatchel
 */
 
 
public class Team4Cover extends JPanel {
    private JTextArea ta1, ta2, ta3, ta4, ta5, ta6, ta7; // text areas to hold weather data
    
    JLayeredPane layeredPane; //Used to Layer Ghost on top of map
    Team4Ghost ghost; //New Runnable object
    //JPanel layeredPanel = new JPanel();
    JPanel mapPanel = new JPanel();             // panel to hold the map
    JPanel weatherPanel = new JPanel();         // panel to hold the weather data
    JPanel buttonPanel = new JPanel();          // panel to hold the button
    JPanel basePanel = new JPanel();            // base panel to hold both weather and map
    
    JPanel comboPanel = new JPanel();           // comboPanel holds a comboBox for selecting cities
    JPanel comboSubPanel = new JPanel();        // this is used to make the comboBox smaller and look nicer 
    JPanel comboSelectionPanel = new JPanel();
    JPanel comboImagePanel = new JPanel();
    
    JButton b1 = new JButton();
    JLabel lab = new JLabel();

   
    JLabel selectCity = new JLabel("Select City");  // label to go beside the combo panel "Select City"
    JButton button1 = new JButton("Change City");   // button to reset and change the city selected

    
    private String time = "";                      // time of measurement
    private Double tempF = 0.0; 
    private Double tempC = 0.0; 
    private Double nearestStorm = 0.0;
    private Double probPrecip = 0.0;                // variables used in extracting 
    private Double windVelocityKph = 0.0;           // weather data from JSON
    private Double windDirectionDegrees = 0.0;
    private String windDir = "";
    private String atmosPressure;
    private String humidity = "";
    private String conditions = "";
    private Double vis = 0.0;
    private int uv_rad = 0;
    private String url_icon = "";
    private String icon_descrip = "";
    
    
    
    private Double _lat = 0.0;
    private Double _long = 0.0;
    private String lat_long = "";                   // used to pass coordinates to map and weather panels
    private String city = "";                       // used to pass name of city to weather panel

    private String cities[] = {"Phoenix", "Bisbee", "Los Angeles" , "Boise", "Miami", "Houston",     // list of cities
                            "New York", "Chicago", "Cleveland", "Nashville", "Seattle"};    // to select from
    private JComboBox<String> cityBox = new JComboBox<>(cities);    // comboBox to hold city names


    
    
    public Team4Cover()  {    // constructor	
    	ghost = new Team4Ghost();
    	Thread newThread = new Thread(ghost); //Runs ghost movement algorithm
    	newThread.start(); //Runs Ghost movement algorithm
	    gui();
		b1.addActionListener(b1Listener);	 
		layeredPane = new JLayeredPane();
	   	layeredPane.setPreferredSize(new Dimension(250,150));
	   	this.setPreferredSize(new Dimension(250, 150));
	   
	    JLabel myImage = new JLabel(new ImageIcon("/CSE360/Pac-Man-Ghost-PNG-Transparent-Image.png"));
	  	layeredPane.add(myImage, JLayeredPane.PALETTE_LAYER);
	    layeredPane.add(ghost, JLayeredPane.PALETTE_LAYER);
	    
    	this.setBounds(1,1,250,150);
 
    	layeredPane.setBounds(0,0,250,150);
    	layeredPane.setOpaque(false);
    	
        this.setVisible(true);
    }
    
    ActionListener b1Listener = new ActionListener(){           // buttonListener for initial screen
        @Override
        public void actionPerformed(ActionEvent e) {
            cityBox.addActionListener(cbActionListener);	    // add comboBox ActionListener
            comboSubPanelPlate();                               // start with comboPanel of cities
            basePlateComboBox();                                //      which is placed on comboSubPanel
            comboPlate();  	                                    // add the cityBox to the comboPanel             
        }
        
    };

	public void gui(){
	    //this.setBackground(Color.YELLOW);
	        
	    b1 = new JButton("TEST");
	        
	    lab = new JLabel("Sagarika, Ross, Sean");
	        
	    this.add(b1);
	    this.add(lab);        
    }

	ActionListener cbActionListener = new ActionListener(){     // ActionListener for the comboBox
	      @Override
	      public void actionPerformed(ActionEvent e){
	        String s =  (String) cityBox.getSelectedItem();     // get city name from comboBox
	
	        switch(s) {                                                    
	          case "Phoenix":                                   // depending on city selected, set the coordinates
	            _lat = 33.448376 - 0.1;
	            _long = -112.074036;
	            city = "Phoenix AZ";
	            break;
	          case "Bisbee":
	            _lat = 31.448155;
	            _long = -109.928408;
	            city = "Bisbee AZ";     
	            break;       
	          case "Los Angeles":
	            _lat = 34.052235 - 0.1;
	            _long = -118.243683;
	            city = "Los Angeles CA";
	            break;
	          case "Boise":
	            _lat = 43.618881 - 0.1;
	            _long = -116.215019;
	            city = "Boise ID";
	            break;
	          case "Miami":
	            _lat = 25.774271;// - 0.1;
	            _long = -80.193661;
	            city = "Miami FL";
	            break;
	          case "Houston":
	            _lat = 29.780472 - 0.1;
	            _long = -95.386342;
	            city = "Houston TX";
	            break;
	          case "New York":
	            _lat = 40.664274;// - 0.1;
	            _long = -74.005970;
	            city = "New York NY";
	            break;
	          case "Chicago":
	            _lat = 41.837551;// - 0.1;
	            _long = -87.681844;
	            city = "Chicago IL";
	            break;
	          case "Cleveland":
	            _lat = 41.478138 - 0.1;
	            _long = -81.679486;
	            city = "Cleveland OH";
	            break;
	          case "Nashville":
	            _lat = 36.165890;// - 0.1;
	            _long = 86.784443;
	            city = "Nashville";
	            break;
	          case "Seattle":
	            _lat = 47.620499 - 0.1;
	            _long = -122.350876;
	            city = "Seattle WA";
	            break;
	        }
	        lat_long = String.valueOf(_lat) + "," + String.valueOf(_long);
	        //System.out.println("Lat_Long = " + lat_long);         // used for testing
	        
	        mapPlate(lat_long);                                     // get map of the chosen coordinates
	        weatherPlate(lat_long, city);                           // get weather data for the coordinates and include city name
	        basePlateData();                                        // add panels for the weather and map display
      }
    };

	ActionListener buttonListener = new ActionListener(){
	    @Override
	    public void actionPerformed(ActionEvent e){
	            repaint();
	            basePlateComboBox();
	    }
	};
	
	
    private JPanel basePlateComboBox(){
        removeAll();
        repaint();
        revalidate();

        setLayout(new GridLayout(1,1));
        this.setBorder(BorderFactory.createRaisedBevelBorder());
        this.add(comboSubPanel);
        return this;
    }


    private JPanel comboSubPanelPlate(){
        comboSubPanel.removeAll();
        comboSubPanel.revalidate();
        comboSubPanel.repaint();
        //setLayout(new GridLayout(1,1));
        setLayout(new FlowLayout());
        comboSubPanel.add(comboPanel);
        return comboSubPanel;	
    }
		

	
    private JPanel comboPlate(){
        comboImagePanel.setLayout(new FlowLayout());
        comboSelectionPanel.setLayout(new FlowLayout());
        comboPanel.setLayout(new BorderLayout());
        comboImagePanel.add(new JLabel(new ImageIcon((new ImageIcon("wunderground_logo.jpg")).getImage().
        getScaledInstance(220, 70, java.awt.Image.SCALE_SMOOTH))));
        comboPanel.add(comboImagePanel,BorderLayout.NORTH);
        comboSelectionPanel.add(selectCity);
        comboSelectionPanel.add(cityBox);
        comboPanel.add(comboSelectionPanel, BorderLayout.SOUTH);
        return comboPanel;
    }

        
    private JPanel basePlateData(){
        this.removeAll();
        this.revalidate();
        this.repaint();

        this.setLayout(new BorderLayout());

        basePanel.setLayout(new GridLayout(1,2));
        

       

        basePanel.add(weatherPanel);
        basePanel.add(mapPanel);
        basePanel.setBorder(BorderFactory.createRaisedBevelBorder());

        button1.setSize(10, 10);
        buttonPanel.add(button1);
    	
        //this.setSize(125,125);
       // JPanel lowerBasePanel = new JPanel();
        
	   	this.add(layeredPane, BorderLayout.CENTER);
        this.setSize(10, 10);

        button1.addActionListener(buttonListener); 
        
        basePanel.setSize(layeredPane.getPreferredSize());
    	basePanel.setLocation(0,0); 
    	layeredPane.add(basePanel, JLayeredPane.DEFAULT_LAYER);
        this.add(basePanel, BorderLayout.CENTER); //Adds base Panel to the Layered Panel
    	
    	buttonPanel.setSize(layeredPane.getPreferredSize());
    	buttonPanel.setLocation(0,0); 
    	layeredPane.add(buttonPanel, JLayeredPane.DEFAULT_LAYER);
        this.add(buttonPanel, BorderLayout.SOUTH); //Adds the Button to the Layered Panel
    	
        return this;

    }

    private void basePlate(){

        basePanel.add(weatherPanel);
        basePanel.add(mapPanel);                
    }
    
    private JPanel mapPlate(String coords){
        try{
        	mapPanel.setLayout(new BorderLayout());
            String latlong = coords;
            JPanel mapPanel = new JPanel();
            
            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" 
                + latlong + "&zoom=9&size=250x250&scale=1&maptype=roadmap";

            String destinationFile = "mapImage.jpg";
            String str = destinationFile;
            URL url = new URL(imageUrl);
            OutputStream os;
                
            try (InputStream is = url.openStream()) {
                os = new FileOutputStream(destinationFile);
                byte[] b = new byte[2048];
                int length;
                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }
            }
            os.close();
        } 
        catch (IOException e) {
            System.exit(1);
        }
        mapPanel.removeAll();
        mapPanel.repaint();
        mapPanel.add(new JLabel(new ImageIcon((new ImageIcon("mapImage.jpg")).getImage().getScaledInstance(125,125,
                java.awt.Image.SCALE_SMOOTH))));
        mapPanel.setVisible(true);
        return mapPanel;
    }
        



    
	private JPanel weatherPlate(String coords, String location) {
	    try{
	        String latlong = coords;
	        String cityName = location;        
	        
	        weatherPanel.setLayout(new GridLayout(7,1));
		    
	       
	        String yourKey = "2ebb44a45e67c7f1";
	        JSONObject json = readJsonFromUrl("http://api.wunderground.com/api/" + yourKey + 
	            "/conditions/q/" + latlong + ".json");        
	            
	        ta1 = new JTextArea(1,20);
	        ta2 = new JTextArea(1,20);
	        ta3 = new JTextArea(1,20);
	        ta4 = new JTextArea(1,20);
	        ta5 = new JTextArea(1,20);
	        ta6 = new JTextArea(1,20);
	        ta7 = new JTextArea(1,20);
	            
	        ta1.setEditable(false);
	        ta2.setEditable(false);
	        ta3.setEditable(false);
	        ta4.setEditable(false);
	        ta5.setEditable(false);
		    ta6.setEditable(false);
		    ta7.setEditable(false);
	        
		    DecimalFormat oneDigit = new DecimalFormat("#,##0.0");  //format to 1 decimal place    
	        
	        Object curr_obs = json.get("current_observation");      // JSON object from which all our weather data comes from
	        
	        time = json.getJSONObject("current_observation").getString("observation_time");
	        tempF = json.getJSONObject("current_observation").getDouble("temp_f");
	        tempC = json.getJSONObject("current_observation").getDouble("temp_c");
	        humidity = json.getJSONObject("current_observation").getString("relative_humidity");
	        uv_rad = json.getJSONObject("current_observation").getInt("UV");
	        url_icon = json.getJSONObject("current_observation").getString("icon_url");
	        icon_descrip = json.getJSONObject("current_observation").getString("icon");
	        windVelocityKph = json.getJSONObject("current_observation").getDouble("wind_kph");
	        windDir = json.getJSONObject("current_observation").getString("wind_dir");
	        atmosPressure = json.getJSONObject("current_observation").getString("pressure_mb");
	        //System.out.println(atmosPressure);
	        
	        
	        String iconUrl;
	        iconUrl = "http://icons.wxug.com/i/c/k/" + icon_descrip + ".gif";
	        String clearIconFile = "icon.gif";
	        String str = clearIconFile;
	        URL clearIconUrl = new URL(iconUrl);
	        OutputStream os;
	        
	        try (InputStream is = clearIconUrl.openStream()) {
	            os = new FileOutputStream(clearIconFile);
	            byte[] b = new byte[2048];
	            int length;
	            while ((length = is.read(b)) != -1) {
	                os.write(b, 0, length);
	            }
	        }
	        os.close();
	
	        Font labelFont = new Font("Serif", Font.PLAIN, 6);
	        Font titleFont = new Font("Serif", Font.BOLD + Font.ITALIC, 5);
	        
	        ta1.setText(cityName);
	        ta2.setText("Temp: " + String.valueOf(tempF) + "F" + " / " + String.valueOf(tempC) + "C");
	        ta3.setText("Humidity: " + humidity);
	        ta4.setText("UV Index: " + String.valueOf(uv_rad));
	        ta5.setText("Wind: " + String.valueOf(windVelocityKph) + "kph, " + windDir);
	        ta6.setText("Pressure: " + atmosPressure + " mb");
	            
	        ta1.setFont(titleFont);
	        ta2.setFont(labelFont);
	        ta3.setFont(labelFont);             // set fonts for city name 
	        ta4.setFont(labelFont);             // and weather conditions
	        ta5.setFont(labelFont);
		    ta6.setFont(labelFont);
	        
	        weatherPanel.removeAll();           // clear contents of last repaint
	        
	       
	        
	        weatherPanel.add(ta1);              // add city name
	        
	        weatherPanel.add(new JLabel(new ImageIcon((new ImageIcon("icon.gif")).getImage().   // add current conditions
	                    getScaledInstance(12, 12, java.awt.Image.SCALE_SMOOTH))));              // icon
	                    
	        weatherPanel.add(ta2);              // add temp
	        weatherPanel.add(ta3);              // add humidity
	        weatherPanel.add(ta4);              // add UV Index
	        weatherPanel.add(ta5);              // add Wind speed and direction
	        weatherPanel.add(ta6);              // add atmospheric pressure
		    
		    weatherPanel.repaint();             // repaint the panel
	            //weatherPanel.setSize(50,50);
		    
		    
		    
	
	        return weatherPanel;
	    }
		catch(IOException e){
		    System.out.println("IOException !!");
			return null;
		}
		catch(JSONException e){
		    System.out.println("JSONException !!");
			return null;
		}
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
}
