package CSE360;

/*
 * Team 5
 * @author Austin McCleary
 * @author Zelin Bao
 * @author Yuxue Zhou
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

public class Team5cover extends JPanel implements ActionListener {
	JButton button;
	int baseheight = getWidth();
	int basewidth = getHeight();

	public Team5cover() {

		// Add border
		setBorder(BorderFactory.createLineBorder(Color.green, 5));
		// Add names
		JLabel label1 = new JLabel("Team 5:");
		JLabel label2 = new JLabel("Austin McCleary,");
		JLabel label3 = new JLabel("Yuxue Zhou,");
		JLabel label4 = new JLabel("Zelin Bao");

		// set font & size
		label1.setFont(new Font("Rockwell", 1, 20));

		add(label1);
		add(label2);
		add(label3);
		add(label4);
		this.setSize(new Dimension(basewidth, baseheight));

		button = new JButton();

		button.setIcon(new ImageIcon("src/Team5Images/setting.png"));
		button.setPreferredSize(new Dimension(90, 50));
		button.addActionListener(this);
		button.setVisible(true);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);

		this.setLayout(new FlowLayout());
		this.setBounds(0, 0, basewidth, baseheight);
		this.setVisible(true);

		add(button);

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				basewidth = getWidth();
				baseheight = getHeight();
				setBounds(0, 0, basewidth, baseheight);
				revalidate();
				repaint();
			}
		});
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == button) {

			this.removeAll();
			this.setBorder(null);
			add(new Team5());

		}
	}
}