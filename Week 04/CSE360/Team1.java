/*CSE 360
 *Team1
 *Manuel Bravo
 *Daniel Ene Neagu
 *Rozhin Azima
 *Shrinivas Bhat
 */
package CSE360;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.JSONException;
import org.json.JSONObject;

public class Team1 extends JPanel implements MouseListener {

	JLayeredPane layeredPane = new JLayeredPane();
	JPanel imagePanel = new JPanel();
	JPanel weatherPanel = new JPanel();
	private JPanel basePanel = new JPanel();
	private final static String FILE_PATH = "Team1Images/";
	public final static String GHOST_FILE_NAME = FILE_PATH + "red_ghost.jpg";
	// LOCATIONS
	HashMap<String, String> cityLocMap = new HashMap<String, String>();
	private String[] loc = { "33.424564,-111.928001", "40.730610,-73.935242",
			"12.972442,77.580643", "45.444958,12.328463",
			"53.350140,-6.266155", "37.733795,-122.446747",
			"52.518623,13.376198", "51.501476,-0.140634",
			"19.451054,-99.125519", "28.644800,77.216721" };
	private String[] cities = { "TEMPE", "NEW_YORK_CITY", "BANGALORE",
			"VENICE", "DUBLIN", "SAN_FRANCISCO", "BERLIN", "LONDON",
			"MEXICO_CITY", "NEW_DELHI" };

	// WEATHER DATA
	private final String W_HOST_URL = "https://api.darksky.net/";
	private final String W_PATH = "forecast/";
	private final String W_KEY = "b829783e844371d18cd29c0ffd19e42b/";

	// MAP Data
	private final String M_HOST_URL = "https://maps.googleapis.com/";
	private final String M_PATH = "maps/api/staticmap";
	private final String M_QUERY1 = "?center=";
	private final String M_QUERY2 = "&zoom=10&size=640x400&scale=2&maptype=roadmap";
	private final static String FILE_NAME = FILE_PATH + "Team1Map.jpg";

	// Change Panel
	public final static String SETTINGS_FILE_NAME = FILE_PATH
			+ "setting_button.jpg";
	String input = null;

	public static int PANEL_WIDTH = 250;
	public static int PANEL_HEIGHT = 150;

	public Team1() {
		setDimension(basePanel, 1, 1);
		setDimension(layeredPane, 0, 0);
		// add(basePanel);
		Team1Ghost ghost = new Team1Ghost();
		// layeredPane.add(ghost);
		layeredPane.setOpaque(false);
		layeredPane.add(basePanel, JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(ghost, JLayeredPane.PALETTE_LAYER);
		layeredPane.add(new JLabel(new ImageIcon(SETTINGS_FILE_NAME)),
				JLayeredPane.PALETTE_LAYER);
		add(layeredPane);
		Thread ghostThread = new Thread(ghost);
		ghostThread.start();
		setCityDetails();
		setFirstPanel();
		// initialize();
	}

	private void setDimension(JComponent component, int x, int y) {
		component.setBounds(x, y, PANEL_WIDTH, PANEL_HEIGHT);
		// component.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		component.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	private void setFirstPanel() {
		Team1Cover firstPanel = new Team1Cover(layeredPane, this);
		basePanel.add(firstPanel);
		setSettingMenu(basePanel);
	}

	private void initialize() {
		basePanel.removeAll();
		setDimension(basePanel, 0, 0);
		setPanel();
		if (cityLocMap.get(input) != null) {
			setImage(cityLocMap.get(input));
			setReport(cityLocMap.get(input));
		}
		basePanel.revalidate();
		basePanel.repaint();
	}

	private void setSettingMenu(JPanel panel) {
		ImageIcon image = new ImageIcon(new ImageIcon(SETTINGS_FILE_NAME)
				.getImage().getScaledInstance(35, 35, 1));
		JLabel imageButton = new JLabel(image);
		panel.add(imageButton);
		imageButton.addMouseListener(this);
	}

	private String setOptionPane() {
		String message = "Select a location to get information";
		String title = "Location Details";
		input = (String) JOptionPane.showInputDialog(this, message, title,
				JOptionPane.QUESTION_MESSAGE, null, cities, cities[0]);
		return input;

	}

	private void setCityDetails() {
		for (int i = 0; i < 10; i++) {
			cityLocMap.put(cities[i], loc[i]);
		}
	}

	private void setPanel() {
		setDimension(basePanel, 1, 1);
		GridLayout layout = new GridLayout(1, 2);
		basePanel.setLayout(layout);
		basePanel.add(imagePanel);
		basePanel.add(weatherPanel);

	}

	private void setReport(String Location) {
		weatherPanel.removeAll();
		GridLayout wPanelLayout = new GridLayout(2, 1);
		// setDimension(weatherPanel, 1, 1);
		weatherPanel.setLayout(wPanelLayout);
		try {
			JSONObject jsonObj = getWeatherInfoFromURL(W_HOST_URL + W_PATH
					+ W_KEY + Location);
			JSONObject currentlyObject = (JSONObject) jsonObj.get("currently");
			JLabel reportLabel = new JLabel("<html><p>"
					+ currentlyObject.get("summary") + " </p><p> "
					+ currentlyObject.get("temperature") + " </p><p> "
					+ currentlyObject.get("humidity") + " </p><p>"
					+ jsonObj.get("timezone") + "</p>");
			weatherPanel.add(reportLabel);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		setSettingMenu(weatherPanel);
	}

	private void setImage(String Location) {
		imagePanel.removeAll();
		getMapFromURL(M_HOST_URL + M_PATH + M_QUERY1 + Location + M_QUERY2,
				FILE_NAME);
		JLabel imageLabel = new JLabel(new ImageIcon((new ImageIcon(FILE_NAME))
				.getImage().getScaledInstance(PANEL_WIDTH * 2, PANEL_HEIGHT,
						Image.SCALE_SMOOTH)));
		imagePanel.add(imageLabel);
	}

	private JSONObject getWeatherInfoFromURL(String string) {
		try {
			URL url = new URL(string);
			URLConnection connection = url.openConnection();
			connection.connect();
			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), Charset.forName("UTF-8")));
			StringBuilder sBuilder = new StringBuilder();
			String temp;
			while ((temp = bReader.readLine()) != null) {
				sBuilder.append(temp);
			}
			JSONObject jsonResult;
			jsonResult = new JSONObject(sBuilder.toString());
			bReader.close();
			return jsonResult;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void getMapFromURL(String string, String fileName) {
		try {
			URL url = new URL(string);
			URLConnection connection = url.openConnection();
			connection.connect();
			InputStream inStream = connection.getInputStream();
			OutputStream outStream = new FileOutputStream(fileName);
			byte[] temp = new byte[1024];
			int len;
			while ((len = inStream.read(temp)) != -1) {
				outStream.write(temp, 0, len);
			}
			inStream.close();
			outStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		String result = setOptionPane();
		if (result != null) {
			initialize();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
