package CSE360;

import java.awt.*;
import javax.swing.*;

import java.lang.String;
import java.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

class Team7GoogleMap extends JPanel{
    double latitude,longitude;
    private JLabel map;
    private int xbound;
    private int ybound;
    //constructor
    public Team7GoogleMap(double latitude, double longitude, int xb, int yb)     {
        setLayout(new BorderLayout());
        xbound=xb;ybound=yb;
        this.setSize(xbound,ybound);
        updateGeoLoc(latitude,longitude);
        map =new JLabel(new ImageIcon((new ImageIcon("mycity.jpg")).getImage().getScaledInstance(xbound, ybound,
                java.awt.Image.SCALE_SMOOTH))); 
        add(map);
        setVisible(true);
        setOpaque(false);
    }
    public void updateMap(double latitude, double longitude) { 
        updateGeoLoc(latitude,longitude);
        map.setIcon(new ImageIcon((new ImageIcon("mycity.jpg")).getImage().getScaledInstance(xbound, ybound,
                java.awt.Image.SCALE_SMOOTH)));
        this.revalidate(); this.repaint();
    }
    public void updateBounds(int xb,int yb) {
        xbound=xb;ybound=yb; 
        this.setBounds(0,0,xbound,ybound);
        map.setIcon(new ImageIcon((new ImageIcon("mycity.jpg")).getImage().getScaledInstance(xbound, ybound,java.awt.Image.SCALE_SMOOTH)));
        this.revalidate(); this.repaint();
    }
    private void updateGeoLoc(double latitude, double longitude)     {
    	this.latitude = latitude;
    	this.longitude = longitude;
    	
   
    	int zoom = 11;
    	
        try {
            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?" + "center=" + latitude + "," + longitude + 
            		"&zoom=" + zoom + "&size=640x640&maptype=road";
            String outputImageFile = "mycity.jpg";
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(outputImageFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }
}
