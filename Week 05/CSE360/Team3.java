//Authors:  Devyn Hedin
//          Jonathan Proctor
//          Thunpisit Amnuaikiatloet
//          Melissa Day

package CSE360;

import java.awt.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import javax.swing.JOptionPane;
import java.net.URL;

import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;

public class Team3 extends JPanel implements ActionListener {
    private JPanel mainPanel, mapPanel, weatherPanel; //super panel with two sub panels for the map and for the weather
    private JLabel weatherStatus, humidity, temperature; //Three pieces of weather information
    private String c1, c2; //Coordinates
    private JLayeredPane layeredPane;
    private JButton chooseLocation;
    private Team3Cover cover;
    private Team3Ghost ghost;

    // Team3 constructor
    public Team3() throws JSONException {
        mapPanel = new JPanel();
        mainPanel = new JPanel();
        weatherPanel = new JPanel();

        //NEW CODE
        layeredPane = new JLayeredPane();
        chooseLocation = new JButton("Cities");
        chooseLocation.addActionListener(this);
        cover = new Team3Cover();
        ghost = new Team3Ghost();
        c1 = "39.203922";
        c2 = "30.203492";

        weatherStatus = new JLabel();
        humidity = new JLabel();
        temperature = new JLabel();

        //Add weather info to display
        weatherPanel.add(weatherStatus);
        weatherPanel.add(humidity);
        weatherPanel.add(temperature);

        //Generate team3.jpg file for use in display
        renderMap(c1, c2);

        //Sets appropriate sizes for JPanels
        mapPanel.setPreferredSize(new Dimension(100, 100));
        weatherPanel.setPreferredSize(new Dimension(100,100));

        //Adapted from the ExampleWeather class to deliver team3.jpg at size of 100 by 100
        mapPanel.add(new JLabel(new ImageIcon((new ImageIcon("team3.jpg")).getImage().getScaledInstance(100, 100,
                java.awt.Image.SCALE_SMOOTH))));

        mainPanel.setLayout(new GridLayout(1, 2));

        mainPanel.add(mapPanel);
        mainPanel.add(weatherPanel);

        layeredPane.setPreferredSize(new Dimension(200,100));
        mainPanel.setBounds(0,0,200,100);
        layeredPane.add(mainPanel, new Integer(0));
        chooseLocation.setBounds(100,75,75, 25);
        layeredPane.add(chooseLocation, new Integer(2));
        cover.setBounds(0,0,200,100);
        layeredPane.add(cover, new Integer(1));
        ghost.setBounds(0,0,200,100);
        layeredPane.add(ghost, new Integer(4));
        layeredPane.setVisible(true);

        this.add(layeredPane);
        this.setVisible(true);
    }

    //Adapted from the main method of the ExampleGoogleMaps class
    private void  renderMap(String c1, String c2){
        try {
            // c1 and c2 are used in URL to pull a specific map from Google
            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + c1 + "," + c2 + "&zoom=15&size=912x912&scale=2&maptype=roadmap";
            System.out.println(c1 + " , " + c2);
            //Image is saved as image.jpg in local folder
            String destinationFile = "team3.jpg";
            String str = destinationFile;
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);

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
    //Taken from the ExampleWeather.java file
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    //Taken from the ExampleWeather.java file
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

    public void actionPerformed(ActionEvent e) {
        //Redraw layeredPane
        layeredPane.removeAll();

        System.out.print("Action Performed"); // FOR DEBUGGING

        //City list for dialog
        Team3City[] cities = {new Team3City("Tempe", "33.424564", "-111.928001"), new Team3City("Dubai", "25.276987", "55.296249")
                , new Team3City("New York City", "40.730610", "-73.935242"), new Team3City("Mexico City", "19.432608", "-99.133209"), new Team3City("Tokyo", "35.652832", "139.839478"), new Team3City("Paris", "48.864716", "2.349014"), new Team3City("Singapore", "1.290270", "103.851959"), new Team3City("San Francisco", "37.733795", "-122.446747"), new Team3City("London", "51.501476", "-0.140634"), new Team3City("New Delhi", "28.644800", "77.216721")};
        //Dialog to choose city
        String[] choices = {"Tempe", "Dubai", "New York City", "Mexico City", "Tokyo", "Paris" , "Singapore", "San Francisco" , "London", "New Delhi"};
        String s = (String) JOptionPane.showInputDialog(null, "Select a city:", "Dialog Box", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
        for (int i = 0; i < cities.length; i++)
        {
            if (cities[i].getName().equals(s)) {
                c1 = cities[i].getLatitude();
                c2 = cities[i].getLongitude();
            }
        }

        //Redraw mapPanel
        renderMap(c1, c2);
        mapPanel.removeAll();
        mapPanel.add(new JLabel(new ImageIcon((new ImageIcon("team3.jpg")).getImage().getScaledInstance(100, 100,
                java.awt.Image.SCALE_SMOOTH))));
        mapPanel.revalidate();

        //Redraw weatherPanel
        weatherPanel.removeAll();

        //Adapted from the main method of the ExampleWeather class
        try {
            JSONObject json = readJsonFromUrl("https://api.darksky.net/forecast/"
                    +"58402e9dc3a740efde7142594af11c6d/" + c1 + "," + c2);
            //Prints image to debugging console. Need to create a graphical representation.
            System.out.println(json.toString());
            System.out.println(json.getJSONObject("currently").getString("summary"));
            //Display Fonts
            weatherStatus.setFont(new Font("Arial", Font.PLAIN, 10));
            humidity.setFont(new Font("Arial", Font.PLAIN, 10));
            temperature.setFont(new Font("Arial", Font.PLAIN, 10));
            //Alignments
            weatherStatus.setHorizontalAlignment(SwingConstants.RIGHT);
            humidity.setHorizontalAlignment(SwingConstants.RIGHT);
            temperature.setHorizontalAlignment(SwingConstants.RIGHT);
            //Setting Text
            weatherStatus.setText("" + json.getJSONObject("currently").getString("summary"));
            humidity.setText("Humidity: " + Double.toString(json.getJSONObject("currently").getDouble("humidity")));
            temperature.setText("Temperature: " + Double.toString(json.getJSONObject("currently").getDouble("apparentTemperature")) + Character.toString((char) 176));
        } catch(IOException ex) {
            System.exit(1);
        }

        //Add weather info to display
        weatherPanel.add(weatherStatus);
        weatherPanel.add(humidity);
        weatherPanel.add(temperature);

        weatherPanel.revalidate();

        mainPanel.setBounds(0,0,200,100);
        layeredPane.add(mainPanel, new Integer(0));
        chooseLocation.setBounds(100,75,75, 25);
        layeredPane.add(chooseLocation, new Integer(2));
        ghost.setBounds(0,0,200,100);
        layeredPane.add(ghost, new Integer(4));
        layeredPane.revalidate();
    }
}