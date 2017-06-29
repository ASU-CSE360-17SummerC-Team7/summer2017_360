/*
 * 	CSE 360 - Summer 2017
 * 	Team Members:
 * 		+ Amit Ranjan
 * 		+ Bahar Shahrokhian Ghahfarokhi
 * 		+ Michael Ostaszewski
 * 		+ Yaqoub Alyakoob
 */

package CSE360;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Team8 extends JPanel implements Observer {

    //Concrete Observable Subject
    private ControlCenter cc = ControlCenter.getInstance();

    //Contains all data for the requested city.
    private Team8LocationData selectedCity = new Team8LocationData();

    //Panel Size
    private int panelH = 140;
    private int panelW = 140;

    //Swing Components
    private JPanel coverPanel, gearPanel, plusPanel;
    private JLayeredPane layeredPane;
    private Font font;

    //Panels
    private Team8Ghost ghostPanel;

    //Used for event listeners...
    private boolean coverShown = false;

    public Team8() {
        //Local Swing Components
        ImageIcon gearIcon, plusIcon;
        JButton gearButton, plusButton;

        //Swing Component Properties
        int defaultIconWidth = 15, defaultIconHeight = 15;
        int fontSize = panelW/12;

        //Instantiate the cover panel.
        coverPanel = new Team8Cover(panelH, panelW);

        //Set the properties for the layered pane.
        ghostPanel = new Team8Ghost(panelW, panelH);
        ghostPanel.setOpaque(false);
        ghostPanel.setSize(panelW, panelH);

        //Set the properties for the layered pane.
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(panelW, panelH));
        layeredPane.setOpaque(false);

        //Fetch icons for various purposes...
        gearIcon = GetImageFromUrl("https://cdn2.iconfinder.com/data/icons/flat-ui-icons-24-px/24/settings-24-512.png", defaultIconWidth, defaultIconHeight);
        plusIcon = GetImageFromUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Plus_font_awesome.svg/512px-Plus_font_awesome.svg.png", defaultIconWidth, defaultIconHeight);

        //Set the Font Properties
        font = new Font("Calibri", Font.BOLD | Font.ITALIC, fontSize);

        //Set the Button Properties
        gearButton = new JButton();
        gearButton.setOpaque(false);
        gearButton.setBorder(null);
        gearButton.setContentAreaFilled(false);
        gearButton.setFocusPainted(false);
        gearButton.setIcon(gearIcon);
        gearButton.setToolTipText("Show Ghost");
        gearButton.addActionListener((ActionEvent e) -> {
            if (ghostPanel.showGhost) {
                ghostPanel.showGhost = false;
                layeredPane.remove(ghostPanel);
                this.revalidate();
                this.repaint();
            } else {
                ghostPanel.showGhost = true;
                Thread ghost = new Thread(ghostPanel);
                ghost.start();
                layeredPane.add(ghostPanel, new Integer(8));
            }
        });

        //Set the Button Properties
        plusButton = new JButton();
        plusButton.setOpaque(false);
        plusButton.setBorder(null);
        plusButton.setContentAreaFilled(false);
        plusButton.setFocusPainted(false);
        plusButton.setIcon(plusIcon);
        plusButton.setToolTipText("Show Panel Cover");
        plusButton.addActionListener((ActionEvent e) -> {
            if (coverShown) {
                coverShown = false;
                //remove cover
                layeredPane.remove(coverPanel);
                this.revalidate();
                this.repaint();
            } else {
                coverShown = true;
                //add cover
                layeredPane.add(coverPanel, new Integer(9));
            }
        });

        //Set the properties for the options panel.
        gearPanel = new JPanel();
        gearPanel.setOpaque(false);
        gearPanel.setSize(panelW/6, panelH/6);
        gearPanel.setLocation((panelW*4)/5, (panelH*4)/5);
        gearPanel.add(gearButton);

        //Set the properties for the gear panel.
        plusPanel = new JPanel();
        plusPanel.setOpaque(false);
        plusPanel.setSize(panelW/6, panelH/6);
        plusPanel.setLocation((panelW*3)/5, (panelH*4)/5);
        plusPanel.add(plusButton);

        //Set the properties for the cover panel.
        coverPanel = new Team8Cover(panelW, panelH);

        //Set the properties for the Team8 panel.
        this.setLayout(new BorderLayout());
        this.setSize(panelW, panelH);

        //Show the cover panel first.
        layeredPane.add(coverPanel, new Integer(9));
        this.add(layeredPane);
    }

    //Display a city and its relevant data to Team8's panel.
    private void showNewInfo() {
        //Remove all components.
        this.removeAll();
        layeredPane.removeAll();

        //Add and display the new components.
        layeredPane.add(newPanel(), JLayeredPane.DEFAULT_LAYER);
        this.add(layeredPane);
        this.revalidate();
        this.repaint();
    }

    //Gather new city information and place the data into a new panel to be added to the pane.
    private JPanel newPanel() {
        JLabel mapLabel;
        JPanel newPanel;
        JLabel temperatureLabel;

        //Update the Team8LocationData members
        String parsedCoordinates[] = cc.getGPS().split(",");
        selectedCity.setCityName(cc.getCity());
        selectedCity.setLongitude(Double.parseDouble(parsedCoordinates[1]));
        selectedCity.setLatitude(Double.parseDouble(parsedCoordinates[0]));

        //Retrieve the city map.
        Team8GoogleMaps googleMap = new Team8GoogleMaps(selectedCity.getLatitude(), selectedCity.getLongitude(), panelW, panelH);

        //Gather the weather information of the selected city.
        Team8DarkSky.updateWeatherInfo(selectedCity);

        //Set the label properties.
        temperatureLabel = new JLabel(String.valueOf(selectedCity.getTemperature()) + "°F");
        temperatureLabel.setForeground(Color.BLACK);
        temperatureLabel.setFont(font);

        //Set the newPanel properties.
        newPanel = new JPanel(new BorderLayout());
        newPanel.setSize(panelW, panelH);
        mapLabel = new JLabel(googleMap.getImage());
        newPanel.add(mapLabel, BorderLayout.CENTER);
        newPanel.add(temperatureLabel, BorderLayout.NORTH);

        return newPanel;
    }

    //Display an image of a moving ghost.
    private void showGhost() {
        //Show the ghost.
        if (!ghostPanel.showGhost) {
            ghostPanel.showGhost = true;
            Thread ghost = new Thread(ghostPanel);
            ghost.start();
            layeredPane.add(ghostPanel,new Integer(8));
        }
        //Remove the ghost.
        else {
            ghostPanel.showGhost = false;
            layeredPane.remove(ghostPanel);
            this.revalidate();
            this.repaint();
        }
    }

    //Display the gear icon with functionality.
    private void showGearButton() {
        layeredPane.add(gearPanel, new Integer(10));
        this.add(layeredPane);
        this.revalidate();
        this.repaint();
    }

    //Display the plus icon with functionality.
    private void showPlusButton() {
        layeredPane.add(plusPanel, new Integer(10));
        this.add(layeredPane);
        this.revalidate();
        this.repaint();
    }

    @Override
    //Update the state of the panel.
    public void update(Observable o, Object arg) {
        //Update only if the city has changed.
        if (!selectedCity.getCityName().equals(cc.getCity()) && (cc.getCity() != null)) {
            showNewInfo();
            showGearButton();
            showPlusButton();
            ghostPanel.showGhost = false;
        }
        else {
            showGhost();
        }
    }

    //Return an ImageIcon object from a URL path.
    private static ImageIcon GetImageFromUrl(String urlString, int width, int height) {
        ImageIcon img = null;
        try {
            URL url = new URL(urlString);
            BufferedImage bufferedImage = ImageIO.read(url);
            img = new ImageIcon(bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            System.exit(1);
        }
        return img;
    }
}
