package CSE360;

/*
 * Team 5
 * @author Melissa Day
 * @author Austin McCleary
 * @author Zelin Bao
 * @author Yuxue Zhou
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Scanner;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

import org.json.JSONException;
import org.json.JSONObject;

public class Team5City extends JPanel {

	double lng = 0.0;
	double lat = 0.0;
	int zoom = 0;

	String[] cities = { "Austin, TX", "New York, NY", "Arlington, TX", "Phoenix, AZ", "San Diego, CA", "Bangor, ME",
			"Chandler, AZ", "Los Angeles, CA", "Tampa Bay, FL", "Chicago, IL" };
	String input = "Austin, TX";
	String inputb = input;
	JPanel main;
	JPanel extra;
	JButton button;

	public Team5City() {
		// Creates main panel to put two panels into
		main = new JPanel(new BorderLayout());
	
		// Creates extra panel to put all data and button into
		extra = new JPanel(new BorderLayout());
		// Adds weather and google map panel together
		extra.add(Weather(), BorderLayout.EAST);
		extra.add(Google(), BorderLayout.WEST);
		
		extra.setOpaque(false);
		// Adds Weather and Google panels to the main panel
		main.add(extra, BorderLayout.NORTH);
		
		main.add(button = new JButton("Select City"), BorderLayout.SOUTH);
		button.setOpaque(false);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PromptForInput();
			}
		});
		
	
		main.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		main.setSize(250,125);
		main.setOpaque(false);
		add(main);
		main.setVisible(true);
		
		
		//add ghost
		ghost Ghost = new ghost();
		main.add(Ghost);
		Ghost.setVisible(true);
		//Ghost.setOpaque(true);
		
		
		
	}

	public void PromptForInput() {
		input = (String) JOptionPane.showInputDialog(null, "Choose city: ", "City Data", JOptionPane.QUESTION_MESSAGE,
				null, cities, cities[0]);
		if (input == null) {
			input = inputb;
			main.removeAll();
			extra.removeAll();
			extra.add(Weather(), BorderLayout.EAST);
			extra.add(Google(), BorderLayout.WEST);
			main.add(button, BorderLayout.SOUTH);
			main.add(extra, BorderLayout.NORTH);
			revalidate();
			repaint();
		}
		SetInput();
		main.removeAll();
		extra.removeAll();
		extra.add(Weather(), BorderLayout.EAST);
		extra.add(Google(), BorderLayout.WEST);
		main.add(button, BorderLayout.SOUTH);
		main.add(extra, BorderLayout.NORTH);
		revalidate();
		repaint();
	}

	public void SetInput() {
		if ((input != null) && (input.length() > 0)) {
			if (input == "Austin, TX") {
				lng = 30.2731851; // reader.nextDouble();
				lat = -97.7424588; // reader.nextDouble();
				zoom = 13; // reader.nextInt();
				inputb = input;
			} else if (input == "Arlington, TX") {
				lng = 32.7357;
				lat = -97.1081;
				zoom = 16;
				inputb = input;
			} else if (input == "New York, NY") {
				lng = 40.714728;
				lat = -73.998672;
				zoom = 11;
				inputb = input;
			} else if (input == "Phoenix, AZ") {
				lng = 33.6050991;
				lat = -112.4052444;
				zoom = 14;
				inputb = input;
			} else if (input == "San Diego, CA") {
				lng = 32.7276824;
				lat = -117.1867012;
				zoom = 10;
				inputb = input;
			} else if (input == "Bangor, ME") {
				lng = 44.829648;
				lat = -68.8591154;
				zoom = 10;
				inputb = input;
			} else if (input == "Chandler, AZ") {
				lng = 33.2827979;
				lat = -111.9338934;
				zoom = 14;
				inputb = input;
			} else if (input == "Los Angeles, CA") {
				lng = 34.0531837;
				lat = -118.3431097;
				zoom = 10;
				inputb = input;
			} else if (input == "Tampa Bay, FL") {
				lng = 27.9707307;
				lat = -82.519097;
				zoom = 12;
				inputb = input;
			} else if (input == "Chicago, IL") {
				lng = 41.8333925;
				lat = -88.0123439;
				zoom = 14;
				inputb = input;
			}
		} 
		else {
			System.exit(1);
		}
	}
	
	public JPanel Weather() {
		// New Panel weather
		JPanel weather = new JPanel();
		weather.setPreferredSize(new Dimension(250, 125));
		SetInput();
		try {
			JSONObject json = readJsonFromUrl(
					"https://api.darksky.net/forecast/f657e7aed849b4520c258bb7bd2f093c/" + lng + "," + lat);
			System.out.println(json.getJSONObject("currently").getString("summary"));
			JLabel j1 = new JLabel("visibility:");
			String n0 = json.getJSONObject("currently").getString("summary");
			String n1 = "Visibility: " + json.getJSONObject("currently").getDouble("visibility");
			String n2 = "Humidity: " + json.getJSONObject("currently").getDouble("humidity");
			String n3 = "Temperature: " + json.getJSONObject("currently").getDouble("apparentTemperature");
			String n4 = "WindSpeed: " + json.getJSONObject("currently").getDouble("windSpeed");
			String n5 = "CloudCover: " + json.getJSONObject("currently").getDouble("cloudCover");
			String strMsg = "<html><body>" + input + ": " + n0 + "<br>" + n3 + "<br>" + n2 + "<br>" + n1 + "<br>" + n4
					+ "<br>" + n5 + "<body></html>";
			j1.setText(strMsg);
			weather.add(j1);
		} catch (IOException e) {
			System.exit(1);
		} catch (JSONException e) {
			System.exit(2);
		}
		// Add Border
		weather.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		// Set Background
		weather.setBackground(Color.white);
		// Returns JPanel
		weather.setOpaque(false);
		return weather;
	}

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

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public JPanel Google() {
		// New panel map
		JPanel map = new JPanel();
		SetInput();
		// Used if you want to have user input
		Scanner reader = new Scanner(System.in);
		try {
			// Image url
			String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?" + "center=" + lng + "," + lat + "&zoom="
					+ zoom + "&size=640x640&maptype=road";

			String destinationFile = "image.jpg";
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
			reader.close();
		} catch (IOException e) {
			System.exit(1);
		}
		map.setPreferredSize(new Dimension(250, 125));
		// Adds map to map Panel
		/*
		map.add(new JLabel(new ImageIcon(
				(new ImageIcon("image.jpg")).getImage().getScaledInstance(175, 175, java.awt.Image.SCALE_SMOOTH))));
		
		*/
		JLabel labelMap = new JLabel(new ImageIcon(
				(new ImageIcon("image.jpg")).getImage().getScaledInstance(175, 175, java.awt.Image.SCALE_SMOOTH))); 
		
		labelMap.setOpaque(false);
		
		map.add(labelMap);
		// Returns map panel
		
		map.setOpaque(false);
		return map;
	}
}