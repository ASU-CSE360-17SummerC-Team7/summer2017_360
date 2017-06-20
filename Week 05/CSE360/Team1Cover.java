package CSE360;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.util.*;

import javax.swing.*;

public class Team1Cover extends JPanel {
	private static final long serialVersionUID = 1L;
	private MouseListener parent;
	private static final int TEAM_SIZE = 4;
	private static String[] memberNames = { "Daniel Ene Neagu", "Manuel Bravo",
			"Rozhin Azima", "Shrinivas Bhat" };
	private static String[] memberEmailIds = { "deneneag@asu.edu",
			"mbravo6@asu.edu", "Razima@asu.edu", "sbhat10@asu.edu" };

	private JLabel teamName;
	private JLabel settings;
	private ArrayList<JLabel> name = new ArrayList<JLabel>();

	public Team1Cover(JLayeredPane layeredPane, MouseListener parent) {
		this.setBounds(-5, 0, 250, 200);
		initComponents(layeredPane);
		this.parent = parent;
	}

	private void initComponents(JLayeredPane layeredPane) {

		initTeamTags();
		initTeamDetails();
		setSettingMenu(layeredPane);
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGroup(
								layout.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addComponent(name.get(3))
										.addComponent(name.get(2))
										.addComponent(name.get(1))
										.addComponent(name.get(0))
										.addComponent(teamName))
						.addContainerGap(0, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(teamName)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(name.get(0))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(name.get(1))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(name.get(2))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(name.get(3))
								.addGap(0, 0, Short.MAX_VALUE)));
	}

	private void initTeamDetails() {
		for (int i = 0; i < TEAM_SIZE; i++) {
			name.add(new JLabel());
			name.get(i).setText(memberNames[i] + "(" + memberEmailIds[i] + ")");
		}
	}

	private void initTeamTags() {
		teamName = new JLabel();
		teamName.setFont(new Font("Tahoma", 3, 11));
		teamName.setForeground(new Color(153, 0, 0));
		teamName.setText("Team1");
	}

	private void setSettingMenu(JComponent panel) {
		ImageIcon image = new ImageIcon(new ImageIcon(Team1.SETTINGS_FILE_NAME)
				.getImage().getScaledInstance(15, 15, 1));
		settings = new JLabel(image);
		panel.add(settings);
		settings.addMouseListener(parent);
	}

}