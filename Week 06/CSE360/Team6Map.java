//Authors:  Joel Menja
//          Manuel Ucles
//          Michael Warnick
package CSE360;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.charset.Charset;
import java.security.Security;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.*;
import org.json.*;
import javax.swing.ImageIcon;



public class Team6Map  extends JPanel{
    
      ControlCenter cc = ControlCenter.getInstance(); 
      static String city;
      static JPanel panel = new JPanel();
      static JPanel panel3 = new JPanel();
      static JPanel panel2 = new JPanel();
      static JPanel panelG = new JPanel();
      
      
     String cf = "";
     String sa = "";
     String weather = "";
     JSONObject jon;
     double dewPoint = 0.00;
     double visibility = 0.00;
     double humidity = 0.00;
     double time = 0.00;
     int temperature = 0;
     JLabel label, label2,label3, label4, map, mainMap, myImage, layeredPanel;
     JLayeredPane layeredPane;
     Team6Ghost ghost;
     Thread thread;
     int i= 0;
      
      
 /* readThis reads the input from the JSON information*/
    public static String readThis(Reader rd) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		int cp;
		
		while((cp = rd.read()) != -1){
			sb.append((char) cp);
			
		}
		
		return sb.toString();
	}
	
	
 /* readURL takes in a string wich is a URL and reads the JSON information from the URL*/
 public static JSONObject readURL(String url) throws IOException, JSONException{
	
		 InputStream in =  new URL(url).openStream();
		 try{
			 BufferedReader read = new BufferedReader(
                            new InputStreamReader(in, Charset.forName("UTF-8")));
			 String jsonText = readThis(read);
			 JSONObject jsn = new JSONObject(jsonText);
			 
			 return jsn;
			 
		 } finally {
			 
			 in.close();
		 }
    }
     

   
    /* setURL sets the city URL and renturns a String of a complete URL*/
   public String setURL(String ci){

       double lon = 0.00; 
       double lat = 0.00;
     //System.out.println(ci);
      // String en = "https://api.darksky.net/forecast/4f02d91363f259f5ca95263c5c032dfc/";
      
       switch(ci){
           
           case "Tempe":
               lat = 33.424564;
               lon = -111.928001;
               break;
           case "NY":
               lat = 40.730610;
               lon = -73.935242;
               break;
               
             case "Bangalore":
               lat = 12.972442;
               lon = 77.580643;
               break;
            case "Venice":
               lat = 45.444958;
               lon = 77.580643;
               break;
            case "Dublin":
               lat = 53.350140;
               lon = -6.266155;
               break;
           case "SFO":
               lat = 37.733795;
               lon = -122.446747;
               break;
           case "Berlin":
               lat = 52.518623;
               lon = 13.376198;
               break;
           case "London":
               lat = 51.501476;
               lon = -0.140634;
               break;
           case "Mexico":
               lat = 19.451054;
               lon = -99.125519;
               break;
            case "Delhi":
               lat = 28.644800;
               lon = 77.216721;
               break;
               
           default:
                 lat = 32.776664;
                 lon = -96.796988;
               break;
                 
       }
      
       String en = "https://api.darksky.net/forecast/4f02d91363f259f5ca95263c5c032dfc/" + Double.toString(lat) + "," + Double.toString(lon);
      // System.out.println(en);
       return en;
    }
   
  
   
    public JLabel setMAP(){
       return mainMap;
   }
   /********************** HomeWork 3 ******************************************/ 

    public Team6Map(String ct) {

        
        setPreferredSize(new Dimension(160,160));
        JLabel label1 = new JLabel(" Weather: ");
        JLabel label2 = new JLabel("\n Temperature: ");
        JLabel label3 = new JLabel("\n Humidity: ");
        JLabel label4 = new JLabel("\n Visibility: ");
        JLabel label5 = new JLabel("\n dewPoint: ");
        JLabel map = new JLabel();
        
        Team6Ghost ghost = new Team6Ghost();; 
        JPanel layeredPanel = new JPanel();
        Thread thread = new Thread(ghost);
        
 
      //static JLabel map = new JLabel();
        JLabel myImage = new JLabel(new ImageIcon("src/CSE360/Team6Images/pacman.png"));
        




	
            
            /************************** Start try and catch **************************************/
           Double atl, lon;
           
                        try {
				cf = setURL(ct);
                                System.out.println(ct);
				jon = readURL(cf);

				String degree = "\u00b0";
				Font myFont = new Font("Serif", Font.BOLD, 100);   
				weather = jon.getJSONObject("currently").getString("summary");
				temperature = jon.getJSONObject("currently").getInt("temperature");
                                
                                 //panel3.setPreferredSize(new Dimension(150, 150));
                                 //panel3.setVisible(true);
                                 
                                 
                              
                                 
                                 
                                 
                                 //panel2.setPreferredSize(new Dimension(160,160));
                                 //panel2.setVisible(true);
                                 //panel2.setOpaque(true);
                        
				label1.setText(" Weather: " + weather);
				label2.setText("\n Temperature: " + Double.toString(temperature) + degree  + "F");
                        
				
                                
				//panel3.add(map);
                                
                                //layeredPane.setPreferredSize(new Dimension(250,250));
                                //panel3.add(layeredPane, BorderLayout.CENTER);


                                //map.setSize(layeredPane.getPreferredSize());
                                
                              
                                
                        
                      
                        } catch (IOException ex) {
                            Logger.getLogger(Team6Cover.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JSONException ex) {
                            Logger.getLogger(Team6Cover.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
                

               //this.add(panel);
                 //add(panel2);

        }
     public int getTemp(){
       return temperature;
   }
    
}
