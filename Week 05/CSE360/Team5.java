package CSE360;

/*
 * Team 5
 * @author Melissa Day
 * @author Austin McCleary
 * @author Zelin Bao
 * @author Yuxue Zhou
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Team5 extends JPanel implements ActionListener {
	JButton button;

	public Team5() {

		// Add border
		setBorder(BorderFactory.createLineBorder(Color.green, 5));
		setSize(250,155);
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
		

		button = new JButton();
		button.setIcon(new ImageIcon(this.getClass().getResource("/Team5Images/setting.jpg")));
		button.setPreferredSize(new Dimension(90, 50));
		button.addActionListener(this);
		button.setVisible(true);
		button.setBounds(150, 70, 100, 100);

		this.setLayout(new FlowLayout());
		this.setBounds(125, 80, 250, 155);
		this.setVisible(true);

		add(button);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == button) {

			this.removeAll();
			this.setBorder(null);
			add(new Team5Layers());

		}
	}

	 public static void main(String[] args) {
		 JFrame j = new JFrame();
		 Team5 t = new Team5();
		 j.add(t);
		 j.setVisible(true);
	 }
}
