/*
CSE360 Summer 2017
Assignment4
Kyle Sun
Jingyi Li
Lin Sun
*/
package CSE360;
import java.awt.*;
import java.io.*;

import javax.swing.*;
import org.json.*;
import java.net.*;
import java.awt.event.*;

public class Team2 extends JPanel implements ActionListener {
    private static JLabel all;        
    private static String city_name;
    private static Team2Ghost blinky;
    private static Team2Cover cover;
    private static JLayeredPane layeredPane;
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 250;
        
    public Team2() {        
        all = new JLabel();
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));        
        try {
            blinky = new Team2Ghost("src/CSE360/Team2Images/Blinky2.gif", "src/CSE360/Team2Images/Blinky.gif", FRAME_WIDTH, FRAME_HEIGHT);
        } catch (IOException b) {
            System.out.println(b);
            System.exit(1);            
        }        
        //start a new thread for Blinky to move on Team2 Panel
        Thread tRace = new Thread(blinky);
        tRace.start();
        all.setBounds(0, 0, FRAME_WIDTH , FRAME_HEIGHT);        
        JLabel gear = new JLabel(new ImageIcon((new ImageIcon("src/CSE360/Team2Images/geer.png")).getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH)));
        gear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DisplayDialog();                  
            }
        });
        gear.setBounds(FRAME_WIDTH - 40, FRAME_HEIGHT - 40, 32, 32);        
        cover = new Team2Cover(FRAME_WIDTH , FRAME_HEIGHT);    
        layeredPane.setOpaque(false);               
        layeredPane.add(all, new Integer(1));           
        layeredPane.add(blinky, new Integer(2));
        layeredPane.add(cover, new Integer(3));  
        layeredPane.add(gear, new Integer(4));        
        this.add(layeredPane);
    }
    
    // Initialize the weather panel.
    private void init(double lat, double lon) {
        try {            
            all.removeAll();            
            // Set map image as label
            JLabel map = setMap("AIzaSyCz92cudhuiKxqRG-AEFPHbEXk-6H_R9eM", "roadmap", FRAME_WIDTH, FRAME_HEIGHT, 2, "result.jpg", lat, lon);     
            // Get and set weather info
            Double temp = getWeather("29fd0065da58c853121182640d464df8", lat, lon).getJSONObject("currently").getDouble("temperature");            
            int color_red = temp.intValue() * 2;
            if (color_red > 255) {
                color_red = 255;
            } else if (color_red < 0) {
                color_red = 0;
            }
            int color_blue = 255 - color_red;
            JLabel  l_temp = new JLabel (String.valueOf(temp)+'\u2109');
            l_temp.setForeground(new Color(color_red, 50, color_blue));
            l_temp.setBackground(Color.LIGHT_GRAY);
            l_temp.setFont(new Font("Sans Serif", Font.BOLD, 25));
            SpringLayout layout = new SpringLayout();
            layout.putConstraint(SpringLayout.WEST, l_temp, 2, SpringLayout.WEST, all);
            layout.putConstraint(SpringLayout.SOUTH, l_temp, 2, SpringLayout.SOUTH, all);
            // Add components to whole panel
            all.setLayout(layout);
            all.add(l_temp);
            all.add(map);            
            all.revalidate();
            all.repaint();
            layeredPane.remove(cover);
            layeredPane.revalidate();
            layeredPane.repaint();
        } catch (IOException | JSONException e) {
            System.out.println(e);
            System.exit(1);
        }
    }
    
    // Returns a JLabel displaying the map of the location based on the given parameters.
    private JLabel setMap(String gKey, String mapType, int mapWidth, int mapHeight, int scale, String fileName, double lat, double lon) throws IOException{
        /*-------------------------------GoogleMap--------------------------------*/
        // Concatenate URL
        String mapUrl = "https://maps.googleapis.com/maps/api/staticmap?"
        + "center=" + lat +"," + lon
        + "&zoom=11&size=" + mapWidth + "x" + mapHeight
        + "&scale=" + scale + "&maptype=" + mapType + "&key="
        + gKey;
        URL url = new URL(mapUrl);
        // Open IO Streams
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(fileName);

        byte[] b = new byte[2048];
        int length;

        // Read one byte of image data and write it to output
        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }
        is.close();
        os.close();
        return new JLabel(new ImageIcon((new ImageIcon(fileName)).getImage().getScaledInstance(mapWidth, mapHeight, java.awt.Image.SCALE_SMOOTH)));
    }
    
    // Returns a JSON object containing the weather data of the location stored in the latitude and longitude coordinates.
    private JSONObject getWeather(String wKey , double lat, double lon) throws IOException, JSONException{
        /*---------------------------DarkskyWeather-------------------------------*/
        // Connect to darksky.net
        String weaUrl = "https://api.darksky.net/forecast/" + wKey + "/" + lat + "," + lon;
        URL url = new URL(weaUrl);
        URLConnection wc = url.openConnection();
        BufferedReader in = new BufferedReader(
                            new InputStreamReader(wc.getInputStream()));
        String inputLine;
        String json = "";
        // Read response and add to string
        while ((inputLine = in.readLine()) != null) {
            json = json + inputLine;
        }
        in.close();
        return new JSONObject(json);   
    }
    
   void DisplayDialog(){
        Object[] cities = {"Tempe", "London", "Rome", "Nashville", "San Francisco", "Austin", "Tokyo", "Shanghai", "Los Angeles", "New York"};
       String result = (String) JOptionPane.showInputDialog(
       Team2.this,
       "Select your city:",
       "City Select",
       JOptionPane.PLAIN_MESSAGE,
       null,
       cities,
       city_name);
       if ((result != null) && (result.length() > 0)) {
           result = result.replace(' ', '%');
           city_name = result;
           try {
           	//find the lat and lon by chosen city
               String mapURL = "https://maps.googleapis.com/maps/api/geocode/json?address=" + result;
               URL url = new URL(mapURL);
               URLConnection wc = url.openConnection();
               BufferedReader in = new BufferedReader(
               new InputStreamReader(
               wc.getInputStream()));
               String inputLine;
               String json = "";
               // Read response and add to string
               while ((inputLine = in.readLine()) != null) {
                   json = json + inputLine;
               }
               // Parse JSON
               JSONObject obj = new JSONObject(json);
               in.close();
               
               JSONArray resultArr = obj.getJSONArray("results");
               double lat = resultArr.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
               double lon = resultArr.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
               //reinitialize 
               init(lat, lon);
           } catch (IOException| JSONException i) {
               System.out.println(i);
               System.exit(1);
           }
       }
	   
   }
    @Override
    public void actionPerformed(ActionEvent e) {        
        //Input Dialog
        if ("pressed".equals(e.getActionCommand())) {
           DisplayDialog();
        }
    }
    
}
