package CSE360;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Team7Proj2Panel extends JPanel{
    String result;
    Object[] cityList = {"Tempe", "New York", "LA", "Chicago", "Houston", "Dallas", "Austin", "Detroit", "Boston", "Seattle"};
    double latitude = 33.4255, longitude = -111.9400;
    Team7WeatherPanel weather;
    Team7GoogleMap map;
    
    Team7Proj2Panel(int xbound, int ybound, String iP)
    {
        this.setSize(xbound,ybound);
        setLayout(new java.awt.GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx=0;c.gridy=0;
        c.gridheight=1;
        c.gridwidth=1;
        c.weightx = 0.6666;
        c.weighty = 1;
        setVisible(true);
        setBackground(Color.white);
//        JButton choosingCity = new JButton("Choose City");
//        choosingCity.addActionListener(new cityChoosingListener());
//        add(choosingCity);
        
        
        map = new Team7GoogleMap(latitude, longitude,((xbound*2)/3),ybound);
        add(map);
        c.gridx=1;c.weightx = 0.333;
        weather = new Team7WeatherPanel(latitude, longitude,iP,(xbound/3),ybound);
        add(weather);       
    }
    public void DisplayGeoMenu() {
            result = (String)JOptionPane.showInputDialog(
                    null,
                    "Select a city:",
                    "",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    cityList,
                    "Tempe");
            
            if(result.equals("Tempe"))
            {
                latitude = 33.4255;
                longitude = -111.9400;
            }
            else if(result.equals("New York"))
            {
                latitude = 40.7128;
                longitude = -74.0059;
            }
            else if(result.equals("LA"))
            {
                latitude = 34.0522;
                longitude = -118.2437;
            }
            else if(result.equals("Chicago"))
            {
                latitude = 41.8781;
                longitude = -87.6298;
            }
            else if(result.equals("Houston"))
            {
                latitude = 29.7604;
                longitude = -95.3698;
            }
            else if(result.equals("Dallas"))
            {
                latitude = 32.7767;
                longitude = -96.7970;
            }
            else if(result.equals("Austin"))
            {
                latitude = 30.2672;
                longitude = -97.7431;
            }
            else if(result.equals("Detroit"))
            {
                latitude = 42.3314;
                longitude = -83.0458;
            }
            else if(result.equals("Boston"))
            {
                latitude = 42.3601;
                longitude = -71.0589;
            }
            else
            {
                latitude = 47.6062;
                longitude = -122.3321;
            }
            
            
            map.updateMap(latitude, longitude);
            weather.updateWeatherPanel(latitude, longitude);
            //remove(weather);
            //weather = new Team7WeatherPanel(latitude, longitude);
            //add(weather);
            
            revalidate();
            repaint();
        }
    private class cityChoosingListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            DisplayGeoMenu();
        }
    }


    
}
