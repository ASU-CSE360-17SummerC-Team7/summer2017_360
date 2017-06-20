// specify which package the file is in
package CSE360;

// Team Member Names:
// Devyn Hedin
// Thunpisit Amnuaikiatloet
// Jonathan Proctor
// Melissa Day

// need to import for JPanel and JRadioButton
import javax.swing.*;
// Team3 Class inherits from JPanel
public class Team3Cover extends JPanel {

	// JRadioButtons defined
	private JRadioButton b1, b2, b3;

	// Team3 Constructor
	public Team3Cover() {
	
	// set label of JRadioButtons
	b1 = new JRadioButton("CSE 360");
	b2 = new JRadioButton("is");
	b3 = new JRadioButton("fun!");
	
	// add JRadioButtons to Team3 panel
	add(b1);
	add(b2);
	add(b3);
	}
}
