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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Team5Layers extends JPanel {

	
	public Team5Layers() {
		JPanel complete = new JPanel();
		//JPanel ghost = new JPanel();
		layer layer1 = new layer();
		
		layer1.setVisible(true);
		layer1.setOpaque(false);
		//ghost.add(new Team5Ghost());
		JPanel weather = new JPanel();
		weather.add(new Team5City());
		
		// create layered pane
    	JLayeredPane layers = new JLayeredPane();
    	layers.setLayout(new BorderLayout());
    	layers.add(layer1, 1);
    	layers.add(weather, 0);
    	//ghost.setOpaque(false);
    	
    	Insets insets = layers.getInsets();
    	//Dimension size = ghost.getPreferredSize();
    	weather.setSize(250, 155);
    	Dimension size = weather.getPreferredSize();
    	
    	//ghost.setBounds(0 + insets.left, 0 + insets.top,
    	            // size.width, size.height);
    	size = weather.getPreferredSize();
    	weather.setBounds(0 + insets.left, 0 + insets.top,
    	             size.width, size.height);
		
    	layers.revalidate();
    	layers.repaint();
    	complete.add(layers);
    	
    
		
		add(complete);
	}
	
	public class layer extends JPanel{
		
			JPanel layer;
			JLabel label;
			
			public layer(){

				JLabel label = new JLabel(new ImageIcon(
				           (new ImageIcon(this.getClass().getResource("/Team5Images/Ghost.png")).getImage())));
				label.setVisible(true);
				
				add(label);
		}
		
	}
	
}
