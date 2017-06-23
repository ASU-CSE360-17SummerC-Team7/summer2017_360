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
import java.util.*;
import javax.swing.*;
import org.json.*;
import java.net.*;
import java.awt.event.*;

public class Team2 extends JPanel implements Observer{
    private static JLabel all;
    private static JLabel map;
    private static JLabel temp;
    private static JLabel gear;
    private static JLabel info;
    private static String fileName;
    private static Team2Ghost blinky;
    private static boolean ghostFlag;
    private static boolean coverFlag;
    private static Team2Cover cover;
    private static JLayeredPane layeredPane;
    private static int FRAME_WIDTH = 500;
    private static int FRAME_HEIGHT = 250;
    //private static ControlCenter c;
    
    public Team2() {
        //ControlCenter c
        //this.c = c.getInstance();
        all = new JLabel();
        ghostFlag = false;
        coverFlag = false;
        layeredPane = new JLayeredPane();
        cover = new Team2Cover();
        cover.setCoverSize(FRAME_WIDTH , FRAME_HEIGHT);
        fileName = "result.jpg";
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
        gear = new JLabel(new ImageIcon((new ImageIcon("src/CSE360/Team2Images/gear.png")).getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH)));
        info = new JLabel(new ImageIcon((new ImageIcon("src/CSE360/Team2Images/info.png")).getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH)));
        gear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ghostFlag) {
                    layeredPane.remove(blinky);
                    ghostFlag = false;          
                } else {
                    layeredPane.add(blinky, new Integer(2));
                    ghostFlag = true;
                }
                layeredPane.revalidate();
                layeredPane.repaint();
            }
        });
        
        info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (coverFlag) {
                    layeredPane.remove(cover);
                    coverFlag = false;          
                } else {
                    layeredPane.add(cover, new Integer(3));  
                    coverFlag = true;
                }
                layeredPane.revalidate();
                layeredPane.repaint();
            }
        });
        gear.setBounds(FRAME_WIDTH - 40, FRAME_HEIGHT - 40, 32, 32);
        info.setBounds(FRAME_WIDTH - 80, FRAME_HEIGHT - 40, 32, 32);
        init(0, 0);
        layeredPane.setOpaque(false);               
        layeredPane.add(all, new Integer(1));           
        layeredPane.add(blinky, new Integer(2));                
        layeredPane.add(gear, new Integer(4));
        layeredPane.add(info, new Integer(4));
        ghostFlag = true;
        this.add(layeredPane);
        this.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e) {
                FRAME_WIDTH = getWidth(); 
                FRAME_HEIGHT = getHeight();
                fullValidate();
            }
        });
    }
    
    private void fullValidate() {
        if (map != null) {
            all.remove(map);
            map = new JLabel(new ImageIcon((new ImageIcon(fileName)).getImage().getScaledInstance(FRAME_WIDTH, FRAME_HEIGHT, java.awt.Image.SCALE_SMOOTH))); 
            all.add(map);
        }
        all.setBounds(0, 0, FRAME_WIDTH , FRAME_HEIGHT); 
        all.revalidate();
        all.repaint();
        cover.setCoverSize(FRAME_WIDTH, FRAME_HEIGHT);
        blinky.setFrame(FRAME_WIDTH, FRAME_HEIGHT);
        layeredPane.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT)); 
        gear.setBounds(FRAME_WIDTH - 40, FRAME_HEIGHT - 40, 32, 32);
        info.setBounds(FRAME_WIDTH - 80, FRAME_HEIGHT - 40, 32, 32);
        layeredPane.revalidate();
        layeredPane.repaint();
    }
    
    // Initialize the weather panel.
    private void init(double lat, double lon) {
        try {            
            all.removeAll();            
            // Set map image as label
            map = setMap("AIzaSyCz92cudhuiKxqRG-AEFPHbEXk-6H_R9eM", "roadmap", FRAME_WIDTH, FRAME_HEIGHT, 2, lat, lon);     
            // Get and set weather info
            Team2GetWeather weatherInfo = new Team2GetWeather("29fd0065da58c853121182640d464df8", lat, lon);
            Double tempValue = weatherInfo.getDoubleInfo("temperature");
            int color_red = tempValue .intValue() * 2;
            if (color_red > 255) {
                color_red = 255;
            } else if (color_red < 0) {
                color_red = 0;
            }
            int color_blue = 255 - color_red;
            temp = new JLabel(String.valueOf(tempValue)+'\u2109');
            temp.setForeground(new Color(color_red, 50, color_blue));
            temp.setBackground(Color.LIGHT_GRAY);
            temp.setFont(new Font("Sans Serif", Font.BOLD, 25));
            temp.setBounds(20, FRAME_HEIGHT, 60, 60);
            SpringLayout layout = new SpringLayout();
            layout.putConstraint(SpringLayout.WEST, temp, 10, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.SOUTH, temp, -10, SpringLayout.SOUTH, this);
            // Add components to whole panel
            all.setLayout(layout);
            all.add(temp);
            all.add(map);
            fullValidate();            
        } catch (IOException | JSONException e) {
            System.out.println(e);
            System.exit(1);
        }
    }
    
    // Returns a JLabel displaying the map of the location based on the given parameters.
    private JLabel setMap(String gKey, String mapType, int mapWidth, int mapHeight, int scale, double lat, double lon) throws IOException{
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

   @Override
   public void update(Observable o, Object arg) {
       /*String result = c.getCity();
       Team2LatLon coord = new Team2LatLon(result);
       init(coord.getLat(), coord.getLon());
       ghostFlag = c.getShowGhost();
       if (ghostFlag) {
            layeredPane.remove(blinky);
            ghostFlag = false;          
       } else {
           layeredPane.add(blinky, new Integer(2));
           ghostFlag = true;
       }
        layeredPane.revalidate();
        layeredPane.repaint();   */
    }
}
