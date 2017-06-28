
package CSE360;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.lang.*;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.border.Border;

public class MapPanel extends ComponentAdapter implements ActionListener{
    private BufferedImage buffMapImage = null;
    private BufferedImage buffScaledImage = null;
    private BufferedImage buffGearImage = null;
    private BufferedImage buffInfoImage = null;
    
    private Image gearImage = null;
    private Image infoImage = null;
    private Image mapImage = null;
    
    
    private JPanel mapPanel;
    private JPanel iconPanel;
    private JLabel jLabel;
    private JButton gearButton;
    private JButton infoButton;
    private JLabel tempLabel;
    private JPanel basePanel;
    private JPanel clearPanel;
    
    private BackgroundPanel backgroundPanel;
    
    private int infoButtonCounter = 0;
    private int gearButtonCounter = 0;

    
    public MapPanel(){
        super();
    }
        
    
    public BackgroundPanel createMapPlate(String coords) throws IOException{
        try{
            WeatherData w = new WeatherData();
            tempLabel = new JLabel(w.getWeatherData(coords));
            tempLabel.setFont(new Font("Serif", Font.BOLD, 16));
            String latlong = coords;
            mapPanel = new JPanel();
            iconPanel = new JPanel();
            basePanel = new JPanel();
            clearPanel = new JPanel();

            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?" 
                        +"center=" + latlong + "&zoom=9&size=250x125&scale=1&maptype=roadmap";

            String destinationFile = "mapImage.jpg"; 
            String str = destinationFile;
            URL url = new URL(imageUrl);
            OutputStream os;
            System.out.println("File path to map: " + this.getClass().getResource("mapImage.jpg"));
            System.out.println("File path to gearImage.png  " + this.getClass().getResource("Team4Images/gearImage.png"));
            gearImage = ImageIO.read(this.getClass().getResource("Team4Images/gearImage.png"));
            System.out.println("File path to informationIcon.jpg  " + this.getClass().getResource("Team4Images/informationIcon.jpg"));
            infoImage = ImageIO.read(this.getClass().getResource("Team4Images/informationIcon.jpg"));
            
            
            Border emptyBorder = BorderFactory.createEmptyBorder();
            
            gearButton = new JButton();
            //gearButton.setSize(30, 30);
            gearButton.setIcon(new ImageIcon(gearImage));
            gearButton.setContentAreaFilled(false);
            gearButton.setBorder(emptyBorder);
            infoButton = new JButton();
            infoButton.setContentAreaFilled(false);
            infoButton.setBorder(emptyBorder);
            infoButton.setSize(30, 30);
            infoButton.setIcon(new ImageIcon(infoImage));
            
            
                            
            try (InputStream is = url.openStream()) {
                os = new FileOutputStream(destinationFile);
                byte[] b = new byte[2048];
                int length;
                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }
            }
            os.close();
            buffMapImage = null;
            buffMapImage = ImageIO.read(new File(destinationFile));
        } 
        catch (IOException e) {
            System.out.println("IOException, file not found!");
        }

        backgroundPanel = new BackgroundPanel(buffMapImage, BackgroundPanel.ACTUAL);
        iconPanel.add(tempLabel);        
        iconPanel.add(infoButton);
        iconPanel.add(gearButton);
        iconPanel.setOpaque(false);
        backgroundPanel.add(iconPanel, BorderLayout.SOUTH);                
        gearButton.addActionListener(this);
        infoButton.addActionListener(this);
        return backgroundPanel;
    }
    
    public JPanel getBackGround() {
    	return backgroundPanel;
    }
   
        
    private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(gearButton)){          // gear button clicked
            gearButtonCounter ++;               
            System.out.println("Gear button clicked");
            
            if((gearButtonCounter % 2) == 1){   // gear button clicked odd number of times
                System.out.println("Put ghost pane on bottom");
            	BackgroundPanel.removeGhost(Team4.getTestPanel());
            }
            else {                              // gear button clicked even number of times
                System.out.println("Put ghost pane on top");
                BackgroundPanel.showGhost(Team4.getTestPanel());

            }
                
            }
            else if (e.getSource().equals(infoButton)){     // info button clicked
               // System.out.println("Info Button Clicked");
                infoButtonCounter ++;
                if ((infoButtonCounter % 2) == 1){  // button clicked odd number of times
                    InitialPanel ip = new InitialPanel();
                    System.out.println("Put Week1 panel on top");
                    backgroundPanel.removeAll();
                    backgroundPanel.revalidate();
                   // System.out.println("File path to gearImage.png  " + this.getClass().getResource("Team4Images/White.png"));
                    try {
                        BufferedImage whiteImage = ImageIO.read(this.getClass().getResource("Team4Images/White.png"));
                        backgroundPanel.setImage(whiteImage);
                        backgroundPanel.setStyle(0);
                        
                    } catch (IOException ex) {
                        System.out.println("IOExeption !!!!!");
                    }
                    

                    backgroundPanel.add(ip);
                    backgroundPanel.add(iconPanel, BorderLayout.SOUTH);
                    backgroundPanel.repaint();
                    }
                else{
                    System.out.println("Put Week1 panel on bottom");
                    backgroundPanel.removeAll();
                    backgroundPanel.revalidate();
                    backgroundPanel.setImage(buffMapImage);
                    backgroundPanel.add(iconPanel, BorderLayout.SOUTH);
                    backgroundPanel.repaint();
                    
                }
               
            }
            }
    }





