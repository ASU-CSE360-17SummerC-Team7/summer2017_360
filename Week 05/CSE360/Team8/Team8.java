/*
 * 	CSE 360 - Summer 2017
 * 	Team Members:
 * 		+ Amit Ranjan
 * 		+ Bahar Shahrokhian Ghahfarokhi
 * 		+ Michael Ostaszewski
 * 		+ Yaqoub Alyakoob
 */

//package CSE360;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.*;

import Team8.CityDetail;
import Team8.GoogleMaps;
import Team8.Weather;

public class Team8 extends JPanel {

    private HashMap<String, CityDetail> hash;
    private String[] cityNames;
    private CityDetail selectedCity = null;
    private JPanel weatherPanel, map_weather_panel, coverPanel, optionsPanel;
    private JLayeredPane layeredPane;
    private ImageIcon WeatherIcon, VisibilityIcon, HumidityIcon,
            CloudCoverIcon, WindSpeedIcon;
    private Font font;
    private int panelH;
    private int panelW;

    public Team8(int widthFrame, int heightFrame, GridLayout grid) {

        double height = Math.ceil(heightFrame/grid.getRows());
        double width = Math.ceil((widthFrame/grid.getColumns()));

        panelH = (int) height;
        panelW = (int) width;

        Initialize();
        ShowCover();
    }

    private void Initialize() {
        int fontSize = panelW/20;
        JButton optionsButton;
        ImageIcon OptionsIcon;

        //Construct an array of cities
        hash = new HashMap<>();
        hash.put("New York", new CityDetail("New York", 40.7128, -74.0059));
        hash.put("Las Vegas", new CityDetail("Las Vegas", 36.1699, -115.1398));
        hash.put("New Orleans", new CityDetail("New Orleans", 29.9511, -90.0715));
        hash.put("Phoenix", new CityDetail("Phoenix", 33.4484, -112.0740));
        hash.put("Los Angeles", new CityDetail("Los Angeles", 34.0522, -118.2437));
        hash.put("San Francisco", new CityDetail("San Francisco", 37.7749, -122.4194));
        hash.put("Chicago", new CityDetail("Chicago", 41.8781, -87.6298));
        hash.put("Seattle", new CityDetail("Seattle", 47.6062, -122.3321));
        hash.put("Miami", new CityDetail("Miami", 25.7617, -80.1918));
        hash.put("San Diego", new CityDetail("San Diego", 32.7157, -117.1611));

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
        optionsPanel.setSize(panelW/6, panelH/6);
        optionsPanel.setLocation((panelW*4)/5, (panelH)/50);
        optionsPanel.add(optionsButton);

        coverPanel = new Team8Cover(panelW, panelH);

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

        ghostPanel = new Team8Ghost(panelW, panelH);
        ghostPanel.setOpaque(false);
        ghostPanel.setSize(panelW, panelH);
        Thread ghost = new Thread(ghostPanel);
        ghost.start();
        layeredPane.add(ghostPanel, new Integer(8));
    }

    private void UpdateWeatherPanels() {

        JLabel mapLabel;
        JPanel mapPanel, footerPanel;

        GoogleMaps googleMap = new GoogleMaps(selectedCity.getLatitude(), selectedCity.getLongitude(), panelW, (panelH*11)/15);
        mapPanel = new JPanel(new BorderLayout());
        mapPanel.setSize(panelW, (panelH*12)/15);

        mapLabel = new JLabel(googleMap.getImage());

        mapPanel.add(mapLabel, BorderLayout.CENTER);

        footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setSize(panelW, (panelH*4)/15);
        footerPanel.add(new JLabel(WeatherIcon), BorderLayout.WEST);

        weatherPanel = new JPanel();
        weatherPanel.setLayout(new GridLayout(2, 5));
        weatherPanel.setBackground(Color.WHITE);

        Weather weather = new Weather(selectedCity);
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

        footerPanel.add(weatherPanel, BorderLayout.CENTER);

        map_weather_panel = new JPanel(new BorderLayout());
        map_weather_panel.setSize(panelW, panelH);
        map_weather_panel.add(mapPanel, BorderLayout.NORTH);
        map_weather_panel.add(footerPanel, BorderLayout.CENTER);
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
///////////////////////////////////////////////////////////

/*	public class CityDetail {
        private String cityName;
		private double latitude;
		private double longitude;

		public CityDetail(String cityName, double latitude, double longitude) {
			this.cityName = cityName;
			this.latitude = latitude;
			this.longitude = longitude;
		}

		public String getCityName() {
			return cityName;
		}
		public double getLatitude() {
			return latitude;
		}
		public double getLongitude() {
			return longitude;
		}
		public String toString() {
			return cityName;
		}
	}
	
	///////////////////////////////////////////////////////////
	
	public class GoogleMaps {

		private String api_url = "https://maps.googleapis.com/maps/api/staticmap?";
		private String api_key = "AIzaSyCPBkB4FfvXzIYRgbRBmAMxqXLhnaGJVXU";
		private String zoom = "zoom=11&";
		private String size = "size=612x612&";
		private String scale = "scale=1&";
		private String mapType = "maptype=roadmap&";
		ImageIcon img;

		public GoogleMaps(double latitude, double longitude) {

			String center = "center=" + latitude +"," + longitude + "&";

			api_url = api_url + center + zoom + size + scale + mapType + "key=" + api_key;

			try {
				URL url = new URL(api_url);
				BufferedImage bufimg = ImageIO.read(url);
				img = new ImageIcon(bufimg);
			} catch (IOException e) {
				System.exit(1);
			}
		}

		public ImageIcon getImage() {
			return img;
		}
	}
}
	///////////////////////////////////////////////////////////
	
	public class Weather{
		
		WeatherData weatherData;

		Weather(CityDetail city)
		{
			this._run(city);
		}

		private void _run(CityDetail city)
		{
			try 
			{
				String yourKey = "fdd8a0537b1af5efefd850be6d1e3488"; 
				JSONObject json = readJsonFromUrl("https://api.darksky.net/forecast/"
						+yourKey+"/" + city.getLatitude() + "," + city.getLongitude());
				String summary = json.getJSONObject("currently").getString("summary");
				double windSpeed = json.getJSONObject("currently").getDouble("windSpeed");
				double temperature = json.getJSONObject("currently").getDouble("temperature");
				double humidity = json.getJSONObject("currently").getDouble("humidity");
				double visibility = json.getJSONObject("currently").getDouble("visibility");
				double cloudCover = json.getJSONObject("currently").getDouble("cloudCover");
				weatherData = new WeatherData(summary, windSpeed, temperature, humidity, visibility, cloudCover); 
			} 
			catch (IOException | JSONException ex) 
			{
				Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, ex);
				System.exit(1);
			}
		}
		
		private String readAll(Reader rd) throws IOException
		{
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1){
				sb.append((char) cp);
			}
			return sb.toString();
		}

		private JSONObject readJsonFromUrl(String url) throws IOException, JSONException 
		{
			try (InputStream is = new URL(url).openStream()){
				BufferedReader rd = new BufferedReader(
						new InputStreamReader(is, Charset.forName("UTF-8")));
				String jsonText = readAll(rd);
				JSONObject json = new JSONObject(jsonText);
				return json;
			}
		}
	}
	
	//////////////////////////////////////////////////////////
	
	public class WeatherData {
		private String summary;
		private double windSpeed;
		private double temperature;
		private double humidity;
		private double visibility;
		private double cloudCover;

		public WeatherData(String summary, double windSpeed, double temperature, double humidity, double visibility, double cloudCover) {
			this.setSummary(summary);
			this.setWindSpeed(windSpeed);
			this.setTemperature(temperature);
			this.setHumidity(humidity);
			this.setVisibility(visibility);
			this.setCloudCover(cloudCover);
		}

		public String getSummary() {
			return summary;
		}
		public void setSummary(String summary) {
			this.summary = summary;
		}
		public double getWindSpeed() {
			return windSpeed;
		}
		public void setWindSpeed(double windSpeed) {
			this.windSpeed = windSpeed;
		}
		public double getTemperature() {
			return temperature;
		}
		public void setTemperature(double temperature) {
			this.temperature = temperature;
		}
		public double getHumidity() {
			return humidity;
		}
		public void setHumidity(double humidity) {
			this.humidity = humidity;
		}
		public double getVisibility() {
			return visibility;
		}
		public void setVisibility(double visibility) {
			this.visibility = visibility;
		}
		public double getCloudCover() {
			return cloudCover;
		}
		public void setCloudCover(double cloudCover) {
			this.cloudCover = cloudCover;
		}

	}
}*/