package CSE360;
//Devyn Hedin
//Melissa Day
//Jonathon Proctor
//Thunpisit Amnuaikiatloet
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
//Devyn Hedin
//Melissa Day
//Jonathon Proctor
//Thunpisit Amnuaikiatloet
/**
 * Exit Code List:
 *   0 : Frame was closed, program successfully executed and garbage collected
 *   1 : Issue fetching weather data from DarkSky API
 *   2 : Issue fetching map from URL or storing map in Image object
 *   3 : Issue with Team3Ghost thread
 */

/**
 * Created by User on 6/21/2017.
 */
public class Team3 extends JPanel implements Runnable, Observer {

	public static Team3Cover cover;
	public static Team3Ghost ghost;
	private Team3Map defaultMap, updatedMap;

	private JLayeredPane layeredPane;
	private JScrollPane jsp;
	private JButton openDialog, ghostButton, infoButton;

	private Thread thread;
	private JLabel temperature, updatedTemperature;

	private String cityName;
	private String c1, c2;

	private String coordinates, coordinate1, coordinate2;
	public static boolean ghostControl;
	public boolean ghostControlPrevious;
	
	//private int WIDTH, HEIGHT;

	public Team3() {
		c1 = "33.4255"; // Initialize coordinates
		c2 = "-111.9400";

		cover = new Team3Cover();
		ghost = new Team3Ghost();

		layeredPane = new JLayeredPane();
		// openDialog = new JButton("Cities");
		// ghostButton = new JButton("Ghost");
		openDialog = new JButton("");
		ghostButton = new JButton("");
		infoButton = new JButton("");
		jsp = new JScrollPane();

		thread = new Thread(this);
		temperature = new JLabel(Team3Weather.getTemp(c1, c2));

		openDialog.addActionListener(new Team3Listener());
		ghostButton.addActionListener(new Team3GhostListener());

		temperature.setText(Team3Weather.getTemp(c1, c2));

		// MUST BE ABLE TO DYNAMICALLY CHANGE THESE NUMBERS
		defaultMap = new Team3Map(c1, c2);
		layeredPane.setPreferredSize(new Dimension(250, 250));
		defaultMap.setBounds(0, 0, 250, 250);
		temperature.setBounds(50, 110, 50, 25);
		cover.setBounds(0, 0, 250, 250);
		openDialog.setBounds(100, 110, 50, 25);
		ghostButton.setBounds(150, 110, 50, 25);
		// infoButton.setBounds(50, 110, 50, 25);
		// temperature.setBounds(10, 75, 75, 25);
		System.out.println(Team3Weather.getTemp(c1, c2));
		ghost.setBounds(0, 0, 250, 250);
		//////////////////////////////////////////////////

		try {
			Image img = ImageIO.read(getClass().getResource("Team3Assets/info_button.png"));
			Image newimage = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
			openDialog.setIcon(new ImageIcon(newimage));
			openDialog.setBorderPainted(false);
			openDialog.setBorder(null);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		try {
			Image img = ImageIO.read(getClass().getResource("Team3Assets/setting_button.png"));
			Image newimage = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
			ghostButton.setIcon(new ImageIcon(newimage));
			ghostButton.setBorderPainted(false);
			ghostButton.setBorder(null);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		try {
			Image img = ImageIO.read(getClass().getResource("Team3Assets/info_button.png"));
			Image newimage = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
			infoButton.setIcon(new ImageIcon(newimage));
			infoButton.setBorderPainted(false);
			infoButton.setBorder(null);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		layeredPane.add(defaultMap, new Integer(0));
		layeredPane.add(temperature, new Integer(3));
		layeredPane.add(cover, new Integer(2));
		layeredPane.add(openDialog, new Integer(3));
		layeredPane.add(ghostButton, new Integer(3));
		layeredPane.add(infoButton, new Integer(3));
		layeredPane.add(ghost, new Integer(4));

		add(layeredPane);

		// GroupLayout layout = new GroupLayout(this);
		// this.setLayout(layout);

		// layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		// .addGroup(layout.createSequentialGroup())
		// .addComponent(layeredPane));

		// layout.setVerticalGroup(layout.createSequentialGroup()
		// .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		// .addComponent(layeredPane)));

		// layout.addComponent(layeredPane, 0, GroupLayout.DEFAULT_SIZE,
		// Short.MAX_VALUE);

		// Something like this might work to resize image
		/*
		 * layeredPane.addComponentListener(new ComponentAdapter() { public void
		 * componentResized(ComponentEvent e) { Dimension size =
		 * layeredPane.getSize(); defaultMap.setSize(size);
		 * layeredPane.revalidate(); layeredPane.repaint(); } });
		 */
		//WIDTH = this.getParent().getWidth();
		//HEIGHT = this.getParent().getHeight();

		this.setVisible(true);
	}

	public void run() {

	}

	@Override
	public void update(Observable o, Object arg) {
		coordinates = ((ControlCenter) o).getGPS();
		ghostControl = ((ControlCenter) o).getShowGhost();

		// if the button has been clicked, update ghost visiblity
		if (ghostControlPrevious != ghostControl) {

			this.changeGhostStatus();
		}
		if (coordinates != null) {
			this.changeCityInput();
		}
	}

	/*
	 * Change whether ghost is visible
	 */
	public void changeGhostStatus() {
		// store value of current input from Observer ghost button
		ghostControlPrevious = ghostControl;

		// Set ghost to value of current ghost status
		//Team3Ghost.ghostStatus = !Team3Ghost.ghostStatus;
		Team3Ghost.ghostStatus = ghostControl;

		if (Team3Ghost.ghostStatus == false) {
			Team3.ghost.thread = null;

			Team3.ghost.setVisible(false);
		} else {

			Team3.ghost.setVisible(true);

			Team3.ghost.thread = new Thread(Team3.ghost);
			Team3.ghost.thread.start();
		}
	}

	/*
	 * Update map & temperature to input from dialog box
	 */
	public void changeCityInput() {

		String[] input = coordinates.split(",");

		coordinate1 = input[0];
		coordinate2 = input[1];

		// remove initial map
		layeredPane.remove(defaultMap);
		if (updatedMap != null) {
			layeredPane.remove(updatedMap); // remove current map
		}

		// remove initial temperature
		layeredPane.remove(temperature);
		if (updatedTemperature != null) {
			layeredPane.remove(updatedTemperature); // remove current
													// temperature

		}

		updatedMap = new Team3Map(coordinate1, coordinate2);
		updatedMap.setBounds(0, 0, 250, 250);

		updatedTemperature = new JLabel(Team3Weather.getTemp(coordinate1, coordinate2));
		updatedTemperature.setBounds(50, 110, 50, 25);

		layeredPane.add(updatedMap, new Integer(0));
		layeredPane.add(updatedTemperature, new Integer(3));
	}
}
