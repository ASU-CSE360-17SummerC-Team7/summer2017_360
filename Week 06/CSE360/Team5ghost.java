package CSE360;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Team5ghost extends JPanel implements Runnable {

	public Thread Ghost;
	static int baseheight = 150;
	static int basewidth = 200;

	public Team5ghost() {

		ImageIcon image = new ImageIcon("src/Team5Images/Ghost.png");
		JLabel ghost = new JLabel();
		ghost.setIcon(image);

		ghost.setSize(49, 49);
		// add(ghost, BorderLayout.CENTER);

		ghost.setBounds(0, 0, 49, 49);
		add(ghost);
		Ghost = new Thread(this);
		Ghost.start();

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				basewidth = getWidth();
				baseheight = getHeight();
				revalidate();
			}
		});
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int xChange = 1;
		int yChange = 1;
		int xvalueChange = xChange;
		int yvalueChange = yChange;
		int x = 0, y = 0;

		try {
			for (int i = 1; i < 100000; i++) {

				x = x + xvalueChange; // updates ghost's x position
				y = y + yvalueChange; // updates ghost's y position

				if (x > 100) { // update ghost's location so it stays on panel
								// right

					xvalueChange = -xChange;
					// x = 5;
				}
				if (y > 150) { // update ghost's location so it stays on panel
								// bottom

					yvalueChange = -yChange;
					// y = 5;
				}

				if (x < -60) {
					xvalueChange = xChange;
				}

				if (y < 0) {
					yvalueChange = yChange; // top
				}

				setBounds(x, y, 166, 206);
				// this.setBounds(x, y, width, height);
				setOpaque(false);
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
		}
	}

}