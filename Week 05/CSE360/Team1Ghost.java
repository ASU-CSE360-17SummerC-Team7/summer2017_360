package CSE360;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Team1Ghost extends JLabel implements Runnable {

	public Team1Ghost() {
		ImageIcon icon = new ImageIcon(Team1.GHOST_FILE_NAME);
		setIcon(new ImageIcon(icon.getImage().getScaledInstance(29, 34, 0)));

	}

	@Override
	public void run() {
		int x = 0;
		int y = 0;
		int xIncreament = 5;
		int yIncreament = 5;
		int GHOST_WIDTH = 29;
		int GHOST_HEIGHT = 34;
		while (true) {
			setBounds(x, y, GHOST_WIDTH, GHOST_HEIGHT);
			if (x < 0)
				xIncreament = 5;
			if (x > Team1.PANEL_WIDTH - GHOST_WIDTH) {
				xIncreament = -5;
			}
			if (y < 0) {
				yIncreament = 5;
			}
			if (y > Team1.PANEL_HEIGHT - GHOST_HEIGHT) {
				yIncreament = -5;
			}
			x += xIncreament;
			y += yIncreament;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.out.println("There was an Interruption");
			}
		}
	}
}