/*
CSE360 Summer 2017
Assignment4
Kyle Sun
Jingyi Li
Lin Sun
*/
package CSE360;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Team2Cover extends JPanel implements ActionListener{
	
        // Time between changing block colors
	Timer t = new Timer(100, this);
	
        // Array representing grid of blocks.
	Team2Block[][] blocks = new Team2Block[20][20];
	Random r = new Random();
	
        // Create a Team2 object, populating the Blocks array and starting the timer.
	public Team2Cover() {
            this.setBounds(0, 0, 250, 125); 
		t.start();		
		for (int i = 0; i < blocks.length; i++){
			for (int j = 0; j < blocks[i].length; j++){
				blocks[i][j] = new Team2Block(i, j, r.nextInt(225), r.nextInt(255), r.nextInt(255));
			}
		}
	}
        
        public Team2Cover(int width, int height) {
            this.setBounds(0, 0, width, height); 
		t.start();		
		for (int i = 0; i < blocks.length; i++){
			for (int j = 0; j < blocks[i].length; j++){
				blocks[i][j] = new Team2Block(i, j, r.nextInt(225), r.nextInt(255), r.nextInt(255));
			}
		}
	}
	
        // Update the colors in each block
	public void updateColors() {
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[0].length; j++){
				blocks[i][j].setColor(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			}
		}
	}
	
        // Calls to update color and redraw block on update
	public void actionPerformed (ActionEvent arg0) {
		updateColors();
		repaint();	
	}
	
        // Redraw each block with new the new color
	public void paint(Graphics g){
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j<blocks[0].length; j++){
				blocks[i][j].draw(g);
			}
		}
	}

}
