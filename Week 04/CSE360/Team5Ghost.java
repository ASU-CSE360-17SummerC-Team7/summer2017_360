package CSE360;

/*
 * Team 5
 * @author Melissa Day
 * @author Austin McCleary
 * @author Zelin Bao
 * @author Yuxue Zhou
 */

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Team5Ghost extends JPanel implements Runnable {
	
	public Thread movingGhost;
	JPanel ghostPanel = new JPanel();
	int dataPanelWidth = 50;
	int dataPanelHeight = 50;
	int x = 5;
	int y = 3;
	
	public Team5Ghost() {
		
		JPanel Team5GhostPanel = new JPanel();
		Team5GhostPanel.add(setGhostPanel());
		
		Team5GhostPanel.setOpaque(false);

		Team5City city = new Team5City();
		int height = city.getHeight();
		int width = city.getWidth();
		
		movingGhost = new Thread (this);
        movingGhost.start();
        
		add(Team5GhostPanel);
	}
	
	
	/*
	 * Sets JPanel with ghost image
	 */
	public JPanel setGhostPanel() {
        ghostPanel.setSize(200,300);
        
       JLabel ghost = new JLabel(new ImageIcon(
                (new ImageIcon(this.getClass().getResource("/Team5Images/Ghost.png")).getImage())));
       ghostPanel.add(ghost);
        
        //ghostPanel.add(ghostLbl);
        ghostPanel.setOpaque(false);
        ghostPanel.setBounds(x, y, 100, 100);
 
        return ghostPanel;
    }
	
    /*
     * Updates ghost's position
     */
 	public void run() {
 		int xChange = 1;
 		int yChange = 1;
 		int xvalueChange = xChange;
 		int yvalueChange = yChange;
 		
 		ghostPanel.revalidate();
 		ghostPanel.repaint();
 		try {
 			for (int i = 1; i < 100000; i++) {
 				
 				x = x + xvalueChange;	// updates ghost's x position
 				y = y + yvalueChange; // updates ghost's y position
 				
 				if (x > dataPanelWidth) { // update ghost's location so it stays on panel
 					
 					xvalueChange = -xChange;
 					//x = 5;
 				}
 				if (y > dataPanelHeight) { // update ghost's location so it stays on panel
 					
 					yvalueChange = -yChange;
 					//y = 5;
 				}
 				
 				if (x < 0) {
 					xvalueChange = xChange;
 				}
 				
 				if (y < 0) {
 					yvalueChange = yChange;
 				}
 				ghostPanel.setBounds(x, y, 100, 100);
 				
 				Thread.sleep(100); // making num in parameter seems to make image move faster
 			}
 		} catch (InterruptedException e) {
 		}
 	}

}
