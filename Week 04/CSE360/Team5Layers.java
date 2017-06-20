package CSE360;

/*
 * Team 5
 * @author Melissa Day
 * @author Austin McCleary
 * @author Zelin Bao
 * @author Yuxue Zhou
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Team5Layers extends JPanel {

	
	public Team5Layers() {
		JPanel complete = new JPanel();
		JPanel ghost = new JPanel();
		ghost.add(new Team5Ghost());
		JPanel weather = new JPanel();
		weather.add(new Team5City());
		
		// create layered pane
    	JLayeredPane layers = new JLayeredPane();
    	layers.setLayout(new BorderLayout());
    	layers.add(ghost, 1);
    	layers.add(weather, 0);
    	Insets insets = layers.getInsets();
    	Dimension size = ghost.getPreferredSize();
    	ghost.setBounds(0 + insets.left, 0 + insets.top,
    	             size.width, size.height);
    	size = weather.getPreferredSize();
    	weather.setBounds(0 + insets.left, 0 + insets.top,
    	             size.width, size.height);
		
    	layers.revalidate();
    	layers.repaint();
    	complete.add(layers);
		
		add(complete);
	}
}
