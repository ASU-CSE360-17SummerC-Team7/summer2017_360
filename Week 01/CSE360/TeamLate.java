package CSE360;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TeamLate extends JPanel implements ActionListener {
	private static final String name = "Bahar Shahrokhian";
	private static final String email = "bshahrok@asu.edu";
	private static final String ID = "1207869360";
	private static final String explanationText = "(Assignment 1 late submission)";
	private boolean isClicked = false;
	
	private JLabel nameLabel, myEmail, ASUID, explanation ;
	private JButton showInfo;
	
	public TeamLate() {
		addNameLable();
		addInfoButton();
		this.setBackground(Color.ORANGE);
		

	}

	public void addNameLable () {
		nameLabel = new JLabel(name);
		nameLabel.setFont(new Font("TimesRoman", Font.TYPE1_FONT, 15));
		this.add(nameLabel);
	}

	public void addInfoButton() {
		ASUID = new JLabel(ID);
		myEmail = new JLabel(email);
		showInfo = new JButton("SHOW INFO");
		explanation = new JLabel(explanationText);
		explanation.setFont(new Font("TimesRoman", Font.ITALIC ,10));
		
		showInfo.addActionListener(this);
		this.add(showInfo);
	}

	public void actionPerformed(ActionEvent e)
	  {
		if (e.getSource() == showInfo) {
			if (!this.isClicked) {
				showInfo.setText("HIDE INFO");
				setClicked(true);
				
				add(myEmail);
				add(ASUID);
				add(explanation);
				ASUID.setVisible(true);
				myEmail.setVisible(true);
				explanation.setVisible(true);
				validate();
				
			} else {
				setClicked(false);
				showInfo.setText("SHOW INFO");

				ASUID.setVisible(false);
				myEmail.setVisible(false);
				explanation.setVisible(false);
			}
			
		}
	  }
	public boolean isClicked() {
		return isClicked;
	}
	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}
}
