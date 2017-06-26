/*CSE 360
 *Team1
 *Manuel Bravo
 *Daniel Ene Neagu
 *Rozhin Azima
 *Shrinivas Bhat
 */
package CSE360;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import org.json.JSONException;
import org.json.JSONObject;

public class Team1 extends JPanel implements Observer {

	private final static String FILE_PATH = "src/CSE360/Team1Images/";
	public final static String GHOST_FILE_NAME = FILE_PATH + "red_ghost.gif";
	private final static String SETTINGS_FILE_NAME = FILE_PATH
			+ "settings_button.png";
	private final static String MAP_FILE_NAME = FILE_PATH + "Team1Map.jpg";
	private final static String INFO_FILE_NAME = FILE_PATH + "info.png";

	private JPanel basePanel = new JPanel();
	private JPanel imagePanel = new JPanel();
	private String latLong = "33.424564,-111.928001";
	public static int PANEL_WIDTH = 300;
	public static int PANEL_HEIGHT = 150;
	Team1Ghost ghost = new Team1Ghost();
	private boolean isGhostOn;
	private boolean isBasicOn;
	JLayeredPane pane = new JLayeredPane();
	JLabel settingLabel = new JLabel(new ImageIcon(new ImageIcon(
			SETTINGS_FILE_NAME).getImage().getScaledInstance(30, 25, 0)));
	JLabel infoLabel = new JLabel(new ImageIcon(new ImageIcon(INFO_FILE_NAME)
			.getImage().getScaledInstance(30, 25, 0)));
	JLabel temperatureLabel = new JLabel("Temperature");

	public Team1() {
		isGhostOn = ControlCenter.getInstance().getShowGhost();
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				PANEL_HEIGHT = getHeight();
				PANEL_WIDTH = getWidth();
				initialize();
			}
		});
		settingLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				isGhostOn = !isGhostOn;
				ghost.setGhostOn(isGhostOn);
			}
		});
		infoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				isBasicOn = !isBasicOn;
				initialize();
			}
		});
		Thread ghostRunner = new Thread(ghost);
		ghostRunner.start();
		initialize();
	}

	protected void loadBasicPanel() {
		imagePanel.removeAll();
		JLabel teamLabel = new JLabel("<html><b>Team1</b> <br><br>Names:"
				+ "<br> Shrinivas Bhat" + "<br> Daniel Ene Neagu"
				+ "<br> Manuel Bravo" + "<br> Rozhin Azima <br></html>");
		imagePanel.add(teamLabel);
		imagePanel.revalidate();
		imagePanel.repaint();
	}

	private void initialize() {
		pane.removeAll();
		this.setBackground(Color.GRAY);
		setDimension(basePanel, 0, 0, PANEL_WIDTH, PANEL_WIDTH);
		setDimension(pane, 0, 0, PANEL_WIDTH, PANEL_WIDTH);
		setDimension(settingLabel, PANEL_WIDTH - 50, PANEL_HEIGHT - 40, 30, 25);
		setDimension(infoLabel, PANEL_WIDTH - 100, PANEL_HEIGHT - 40, 30, 25);
		setDimension(temperatureLabel, 50, PANEL_HEIGHT - 40, 70, 25);
		ghost.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		pane.setOpaque(false);
		pane.add(basePanel, JLayeredPane.DEFAULT_LAYER);
		pane.add(ghost, JLayeredPane.PALETTE_LAYER);
		pane.add(settingLabel, JLayeredPane.PALETTE_LAYER);
		pane.add(infoLabel, JLayeredPane.PALETTE_LAYER);
		pane.add(temperatureLabel, JLayeredPane.PALETTE_LAYER);
		ghost.setGhostOn(isGhostOn);
		pane.revalidate();
		pane.repaint();
		initializePanel();
		add(pane);
	}

	private void setDimension(JComponent component, int x, int y, int width,
			int height) {
		component.setBounds(x, y, width, height);
		component.setPreferredSize(new Dimension(width, height));
	}

	private void initializePanel() {
		basePanel.removeAll();
		basePanel.add(imagePanel);
		int width = PANEL_WIDTH;
		int height = PANEL_HEIGHT;
		if (width > 0 && height > 0) {
			if (isBasicOn)
				loadBasicPanel();
			else {
				drawMapImage(width, height);
				loadTemperature(latLong);
			}
		}
		basePanel.revalidate();
		basePanel.repaint();
	}

	private void loadTemperature(String latLong) {
		temperatureLabel.removeAll();
		System.out.println(latLong + " " + getTemperature(latLong));
		temperatureLabel = new JLabel("srini" + getTemperature(latLong));
		temperatureLabel.setFont(new Font("Almendra", Font.ROMAN_BASELINE, 20));
		temperatureLabel.setForeground(Color.RED);
		temperatureLabel.revalidate();
		temperatureLabel.repaint();
	}

	private void drawMapImage(int width, int height) {
		imagePanel.removeAll();
		JLabel label;
		if (latLong == null) {
			latLong = "33.424564,-111.928001";
		}
		label = new JLabel(new ImageIcon(new ImageIcon(getMapImage(latLong,
				width, height)).getImage().getScaledInstance(PANEL_WIDTH,
				PANEL_HEIGHT, 0)));
		imagePanel.add(label);
		imagePanel.repaint();
	}

	private String getTemperature(String latLong2) {
		try {
			URL url = new URL(
					"https://api.darksky.net/forecast/b829783e844371d18cd29c0ffd19e42b/"
							+ latLong2);
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
			return ((JSONObject) jsonResult.get("currently"))
					.get("temperature") + "";
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "Temperature";
	}

	private String getMapImage(String latLong, int width, int height) {
		try {
			URL url = new URL(
					"https://maps.googleapis.com/maps/api/staticmap?center="
							+ latLong + "&size=" + width + "x" + height
							+ "&zoom=12&key="
							+ "AIzaSyA0oN14DwtbmnWpT7fnl5sN2P-RwbSMYqE");
			URLConnection connection = url.openConnection();
			connection.connect();
			InputStream inStream = connection.getInputStream();
			OutputStream outStream = new FileOutputStream(MAP_FILE_NAME);
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
		return MAP_FILE_NAME;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (((ControlCenter) o).getGPS() != null
				&& ((ControlCenter) o).getGPS() != latLong) {
			latLong = ControlCenter.getInstance().getGPS();
			initializePanel();
		} else if (isGhostOn != ((ControlCenter) o).getShowGhost()) {
			isGhostOn = ControlCenter.getInstance().getShowGhost();
			ghost.setGhostOn(isGhostOn);
		}
	}
}
