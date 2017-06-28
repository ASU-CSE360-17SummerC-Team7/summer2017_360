package CSE360;

/*
 * Team 5

 * @author Austin McCleary
 * @author Zelin Bao
 * @author Yuxue Zhou
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

import org.json.JSONException;
import org.json.JSONObject;

public class Team5 extends JPanel implements Observer {

	boolean ghostAppear = true;
	String lnglat = "33.424564,-111.928001";
	Team5ghost Ghost;

	int baseheight = 150;
	int basewidth = 150;

	int zoom = 12;

	JPanel main;
	JPanel extra, extra1;
	JPanel buttonPanel;
	// JButton button;
	JButton info, ghostAppearing, button2, setting;
	JPanel temp;
	String temperature;
	String degrees;

	public Team5() {
		// Creates main panel to put two panels into
		main = new JPanel();

		// Creates extra panel to put all data and button into
		extra = new JPanel();
		extra1 = new JPanel();

		extra.add(Google());
		extra.setBounds(0, 0, basewidth, baseheight);
		extra.setOpaque(true);

		extra1.add(Weather());
		extra1.setBounds(0, 0, 50, 50);
		extra1.setOpaque(false);
		// Adds Weather and Google panels to the main panel
		main.add(extra);
		extra.setBounds(0, 0, basewidth, baseheight);

		// adding the 2 new buttons here
		info = new JButton();
		info.setPreferredSize(new Dimension(90, 50));
		info.setOpaque(false);
		info.setBorderPainted(false);
		info.setContentAreaFilled(false);
		info.setIcon(new ImageIcon("src/Team5Images/information.png"));

		ghostAppearing = new JButton();
		ghostAppearing.setPreferredSize(new Dimension(90, 50));
		ghostAppearing.setOpaque(false);
		ghostAppearing.setBorderPainted(false);
		ghostAppearing.setContentAreaFilled(false);
		ghostAppearing.setIcon(new ImageIcon("src/Team5Images/setting.png"));

		/*
		 * extra.add(button); extra.add(ghostAppearing);
		 * 
		 */
		// buttonPanel.setLayout(null);

		buttonPanel.setBounds(70, 150, 180, 100);
		buttonPanel.add(info);
		buttonPanel.add(ghostAppearing);
		buttonPanel.setVisible(true);
		buttonPanel.setOpaque(false);

		// This is the button turns on and off the ghost
		ghostAppearing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ghostAppear == true) {
					Ghost.setVisible(false);
					ghostAppear = false;
				} else {
					Ghost.setVisible(true);
					ghostAppear = true;
				}
			}
		});

		// when click this button, it brings up the initial panel with the
		// button and image. when you click
		// the button on the initial panel, dialog box pops up and choose a city

		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == info) {

					removeAll();
					add(new Team5cover());
					repaint();
					revalidate();
				}
			}
		});

		main.setBorder(BorderFactory.createLineBorder(Color.red, 1));

		main.setOpaque(false);
		main.setVisible(true);

		// add ghost
		Ghost = new Team5ghost();
		Ghost.setBounds(0, 0, basewidth, baseheight);
		// main.add(Ghost);
		Ghost.setVisible(true);
		// Ghost.setOpaque(true);

		JLayeredPane layers = new JLayeredPane();
		layers.setLayout(new BorderLayout());

		layers.add(Ghost, 1);
		layers.add(buttonPanel, 2);
		layers.add(extra1, 3);
		layers.add(main, 4);

		add(layers);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				basewidth = getWidth();
				baseheight = getHeight();
				revalidate();
			}
		});

	}

	public JPanel Weather() {
		// New Panel weather
		JPanel weather = new JPanel();

		try {

			JSONObject json = readJsonFromUrl(
					"https://api.darksky.net/forecast/f657e7aed849b4520c258bb7bd2f093c/" + lnglat);

			System.out.println(json.getJSONObject("currently").getString("summary"));
			JLabel j1 = new JLabel("visibility:");

			String n3 = Double.toString(json.getJSONObject("currently").getDouble("apparentTemperature"));

			String strMsg = n3;
			j1.setText(strMsg);
			weather.add(j1);

			String degrees = Double.toString(json.getJSONObject("currently").getDouble("apparentTemperature"));

			System.out.println(degrees);

		} catch (IOException e) {
			System.exit(1);
		} catch (JSONException e) {
			System.exit(2);
		}
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

		// Used if you want to have user input
		Scanner reader = new Scanner(System.in);
		try {

			// New project6
			String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?" + "center=" + lnglat + "&zoom=" + zoom
					+ "&size=" + basewidth + "x" + baseheight + "&maptype=road";

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
		map.setPreferredSize(new Dimension(basewidth, baseheight));
		map.setBounds(0, 0, basewidth, baseheight);
		// Adds map to map Panel

		JLabel labelMap = new JLabel(new ImageIcon((new ImageIcon("image.jpg")).getImage().getScaledInstance(basewidth,
				baseheight, java.awt.Image.SCALE_SMOOTH)));

		labelMap.setOpaque(false);
		// map.add(new JLabel(temperature));
		map.add(labelMap);
		// Returns map panel

		map.setOpaque(false);
		return map;
	}

	private void rebuildWM() {
		extra.removeAll();

		// extra.add(Weather());
		extra.add(Google());

		main.revalidate();
		main.repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (lnglat != ControlCenter.getInstance().getGPS()) {

			lnglat = ControlCenter.getInstance().getGPS();
			rebuildWM();

		} else if (ghostAppear != ControlCenter.getInstance().getShowGhost()) {

			if (ghostAppear == false) {

				Ghost.setVisible(true);
				ghostAppear = true;
			} else {

				ghostAppear = false;
				Ghost.setVisible(false);
			}

		}
	}
}