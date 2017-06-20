package CSE360;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Team5ghost extends JPanel implements Runnable{

	public Thread Ghost;
	
	public Team5ghost(){
		
	;
	  JLabel ghost = new JLabel(new ImageIcon(
           (new ImageIcon(this.getClass().getResource("/Team5Images/Ghost.png")).getImage())));
      
	  ghost.setSize(49, 49);
	  add(ghost, BorderLayout.CENTER);
	  Ghost = new Thread (this);
	  Ghost.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int xChange = 1;
 		int yChange = 1;
 		int xvalueChange = xChange;
 		int yvalueChange = yChange;
		int x = 0,y = 0;
		
			
			try {
	 			for (int i = 1; i < 100000; i++) {
	 				
	 				x = x + xvalueChange;	// updates ghost's x position
	 				y = y + yvalueChange; // updates ghost's y position
	 				
	 				if (x > 250) { // update ghost's location so it stays on panel
	 					
	 					xvalueChange = -xChange;
	 					//x = 5;
	 				}
	 				if (y > 155) { // update ghost's location so it stays on panel
	 					
	 					yvalueChange =  -yChange;
	 					//y = 5;
	 				}
	 				
	 				if (x <= -250) {
	 					xvalueChange = xChange;
	 				}
	 				
	 				if (y < 0) {
	 					yvalueChange = yChange;
	 				}
	 				
	 				setBounds(x, y, 250, 155);
	 				Thread.sleep(100);
		}
			} catch (InterruptedException e) {
			}
			}
   
	
	
}
