package CSE360;

import javax.swing.*;

import java.io.*;
import java.util.Random;

public class Team1Ghost extends JLabel implements Runnable {
	private int x = 0;
	private int y = 0;
	private int xIncreament = 1;
	private int yIncreament = 1;
	private final static int GHOST_WIDTH = 29;
	private final static int GHOST_HEIGHT = 34;
	private static int G_PANEL_WIDTH = Team1.PANEL_WIDTH;
	private static int G_PANEL_HEIGHT = Team1.PANEL_HEIGHT;
	private final static Object mLock = new Object();
	private boolean ghostOn = true;

	public Team1Ghost() {
		ImageIcon icon = new ImageIcon(Team1.GHOST_FILE_NAME);
		setIcon(new ImageIcon(icon.getImage().getScaledInstance(29, 34, 0)));

	}

	@Override
	public void run() {
		while (true) {
			synchronized (mLock) {
				setVisible(ghostOn);
			}
			setBounds(x, y, GHOST_WIDTH, GHOST_HEIGHT);
			if (x < 0)
				xIncreament = 1;
			if (x > G_PANEL_WIDTH - GHOST_WIDTH) {
				xIncreament = -1;
			}
			if (y < 0) {
				yIncreament = 1;
			}
			if (y > G_PANEL_HEIGHT - GHOST_HEIGHT) {
				yIncreament = -1;
			}
			x += xIncreament;
			y += yIncreament;
			try {
				Thread.sleep(30);
			} catch (InterruptedException i) {
				System.exit(1);
			}
		}
	}

	public void setGhostOn(boolean value) {
		synchronized (mLock) {
			ghostOn = value;
		}
	}

	public void setSize(int x, int y) {
		G_PANEL_WIDTH = x;
		G_PANEL_HEIGHT = y;
	}
}