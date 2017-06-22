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
import java.util.HashMap;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Team8 extends JPanel {

    private HashMap<String, Team8CityDetail> hash;
    private String[] cityNames;
    private Team8CityDetail selectedCity = null;
    private JPanel weatherPanel, map_weather_panel, coverPanel, optionsPanel;
    private JLayeredPane layeredPane;
    private ImageIcon WeatherIcon, VisibilityIcon, HumidityIcon,
            CloudCoverIcon, WindSpeedIcon;
    private Font font;

    public Team8() {
        Initialize();
        ShowCover();
    }

    private void Initialize() {

        int panelH = 125, panelW = 250,  //height and width of Team8 panel
                fontSize = 10;
        JButton optionsButton;
        ImageIcon OptionsIcon;

        //Construct an array of cities
        hash = new HashMap<>();
        hash.put("New York", new Team8CityDetail("New York", 40.7128, -74.0059));
        hash.put("Las Vegas", new Team8CityDetail("Las Vegas", 36.1699, -115.1398));
        hash.put("New Orleans", new Team8CityDetail("New Orleans", 29.9511, -90.0715));
        hash.put("Phoenix", new Team8CityDetail("Phoenix", 33.4484, -112.0740));
        hash.put("Los Angeles", new Team8CityDetail("Los Angeles", 34.0522, -118.2437));
        hash.put("San Francisco", new Team8CityDetail("San Francisco", 37.7749, -122.4194));
        hash.put("Chicago", new Team8CityDetail("Chicago", 41.8781, -87.6298));
        hash.put("Seattle", new Team8CityDetail("Seattle", 47.6062, -122.3321));
        hash.put("Miami", new Team8CityDetail("Miami", 25.7617, -80.1918));
        hash.put("San Diego", new Team8CityDetail("San Diego", 32.7157, -117.1611));

        Set<String> keySet = hash.keySet();
        cityNames = keySet.toArray(new String[keySet.size()]);

        //Fetch icons for various purposes...
        int defaultIconWidth = 15, defaultIconHeight = 15;
        OptionsIcon = GetImageFromUrl("https://cdn2.iconfinder.com/data/icons/flat-ui-icons-24-px/24/settings-24-512.png", defaultIconWidth, defaultIconWidth);
        WeatherIcon = GetImageFromUrl("http://www.concordiaks.org/images/weather_20icon.png", defaultIconWidth * 2, defaultIconHeight * 2);
        VisibilityIcon = GetImageFromUrl("https://d30y9cdsu7xlg0.cloudfront.net/png/118188-200.png", defaultIconWidth, defaultIconHeight);
        HumidityIcon = GetImageFromUrl("https://d30y9cdsu7xlg0.cloudfront.net/png/1001987-200.png", defaultIconWidth, defaultIconHeight);
        CloudCoverIcon = GetImageFromUrl("https://cdn1.iconfinder.com/data/icons/simple-icons/4096/skydrive-4096-black.png", defaultIconWidth, defaultIconHeight);
        WindSpeedIcon = GetImageFromUrl("https://cdn4.iconfinder.com/data/icons/weather-conditions/512/wind-512.png", defaultIconWidth, defaultIconHeight);

        font = new Font("Calibri", Font.BOLD | Font.ITALIC, fontSize);

        optionsButton = new JButton();
        optionsButton.setOpaque(false);
        //optionsButton.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, rightMargin));
        optionsButton.setBorder(null);
        optionsButton.setContentAreaFilled(false);
        optionsButton.setFocusPainted(false);
        optionsButton.setIcon(OptionsIcon);
        optionsButton.setToolTipText("Options");
        optionsButton.addActionListener((ActionEvent e) -> {
            ShowDialog();
        });
        optionsPanel = new JPanel();
        optionsPanel.setOpaque(false);
        optionsPanel.setSize(250, 125);
        optionsPanel.setLocation(110, 100);
        optionsPanel.add(optionsButton);

        coverPanel = new Team8Cover();

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(panelW, panelH));
        layeredPane.setOpaque(false);

        this.setLayout(new BorderLayout());
        this.setSize(panelW, panelH);
    }

    /*
     * Adds the button to the layered panel,
     * repaint components of layered panel
     */
    private void ShowLayeredPane() {
        layeredPane.add(optionsPanel, new Integer(8));
        this.add(layeredPane);
        this.revalidate();
        this.repaint();
    }

    /*
     * Adds the cover to the layered panel
     */
    private void ShowCover() {
        this.removeAll();
        layeredPane.removeAll();
        layeredPane.add(coverPanel, new Integer(5));
        ShowLayeredPane();
    }

    /*
     * Adds the weather to the layered panel,
     * Add the moving ghost the layered panel
     */
    private void ShowWeather() {
        this.removeAll();
        layeredPane.removeAll();
        UpdateWeatherPanels();
        layeredPane.add(map_weather_panel, new Integer(6));
        ShowGhost();
        ShowLayeredPane();
    }

    /*
     * Start the thread of the ghost movement,
     * add ghost to layered Panel
     */
    private void ShowGhost() {
        Team8Ghost ghostPanel;

        ghostPanel = new Team8Ghost();
        ghostPanel.setOpaque(false);
        ghostPanel.setSize(250, 125);
        Thread ghost = new Thread(ghostPanel);
        ghost.start();
        layeredPane.add(ghostPanel, new Integer(8));
    }

    private void UpdateWeatherPanels() {

        JLabel mapLabel;
        JPanel mapPanel, footerPanel;

        Team8GoogleMaps googleMap = new Team8GoogleMaps(selectedCity.getLatitude(), selectedCity.getLongitude());
        mapPanel = new JPanel(new BorderLayout());
        mapPanel.setSize(250, 100);

        if (this.getWidth() > 0)
            mapLabel = new JLabel(new ImageIcon((googleMap.getImage()).getImage().getScaledInstance(mapPanel.getWidth(), mapPanel.getHeight(), Image.SCALE_SMOOTH)));
        else
            mapLabel = new JLabel(googleMap.getImage());

        mapPanel.add(mapLabel, BorderLayout.CENTER);

        footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setSize(250, 25);
        footerPanel.add(new JLabel(WeatherIcon), BorderLayout.WEST);

        weatherPanel = new JPanel();
        weatherPanel.setLayout(new GridLayout(2, 5));
        weatherPanel.setBackground(Color.WHITE);

        Team8Weather weather = new Team8Weather(selectedCity);
        JLabel temperatureLabel = new JLabel("" + weather.weatherData.getTemperature() + "°F");
        temperatureLabel.setForeground(Color.BLUE);
        temperatureLabel.setFont(font);
        temperatureLabel.setToolTipText("" + weather.weatherData.getTemperature() + "°F");

        JLabel summaryLabel = new JLabel(weather.weatherData.getSummary());
        summaryLabel.setFont(font);
        summaryLabel.setToolTipText(weather.weatherData.getSummary());
        weatherPanel.add(temperatureLabel);

        AddWeatherComponent(VisibilityIcon, weather.weatherData.getVisibility(), "Visibility");
        AddWeatherComponent(HumidityIcon, weather.weatherData.getHumidity(), "Humidity");
        weatherPanel.add(summaryLabel);
        AddWeatherComponent(CloudCoverIcon, weather.weatherData.getCloudCover(), "Cloud Cover");
        AddWeatherComponent(WindSpeedIcon, weather.weatherData.getWindSpeed(), "Wind Speed");

        JPanel whiteSpace = new JPanel();
        whiteSpace.setBackground(Color.WHITE);

        footerPanel.add(weatherPanel, BorderLayout.CENTER);

        map_weather_panel = new JPanel(new BorderLayout());
        map_weather_panel.setSize(250, 125);
        map_weather_panel.add(mapPanel, BorderLayout.CENTER);
        map_weather_panel.add(footerPanel, BorderLayout.SOUTH);
    }

    private void ShowDialog() {
        String newCity = (String) JOptionPane.showInputDialog(this, "Select a city:", "City Options", JOptionPane.OK_CANCEL_OPTION, null, cityNames, selectedCity);
        if (newCity == null) //cancel
        {
        } else if (selectedCity == null || !newCity.equals(selectedCity.getCityName())) {
            selectedCity = hash.get(newCity);
            ShowWeather();
        }
    }

    private void AddWeatherComponent(ImageIcon imgIcon, double weatherDataValue, String componentTooltip) {

        int rightMargin = 20;

        JLabel imgLabel = new JLabel(imgIcon);
        imgLabel.setToolTipText(componentTooltip);
        weatherPanel.add(imgLabel);
        JLabel weatherComponentLabel = new JLabel("" + weatherDataValue);
        weatherComponentLabel.setToolTipText("" + weatherDataValue);
        weatherComponentLabel.setFont(font);
        weatherComponentLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, rightMargin));
        weatherPanel.add(weatherComponentLabel);
    }

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
