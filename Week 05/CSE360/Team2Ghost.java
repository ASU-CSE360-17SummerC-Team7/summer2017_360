/*
CSE360 Summer 2017
Assignment4
Kyle Sun
Jingyi Li
Lin Sun
*/
package CSE360;
import javax.swing.*;
import java.io.*;
import java.util.Random;

// The ghost moves and bounces around the screen.
public class Team2Ghost extends JLabel implements Runnable {
    public int x;
    public int y;
    private int hspeed;
    private int vspeed;
    private final int FRAME_HEIGHT;
    private final int FRAME_WIDTH;
    private String imgRight;
    private String imgLeft;
    Random r;
    
    // Create a Ghost with the provided file paths for the ghost image moving 
    // right and left.
    public Team2Ghost(String imgRight, String imgLeft, int width, int height) throws IOException {
        super(new ImageIcon(imgRight));
        this.imgRight = imgRight;
        this.imgLeft = imgLeft;
        FRAME_WIDTH = width;
        FRAME_HEIGHT = height;        
        r = new Random();
        x = r.nextInt(FRAME_WIDTH - 64) + 32;
        y = r.nextInt(FRAME_HEIGHT - 64) + 32;        
        hspeed = r.nextInt(3) + 1;
        vspeed = r.nextInt(3) + 1;
                
    }
    
    // Move the ghost around the screen and bounces it off the frame boundaries.
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(25);
            } catch (InterruptedException i) {
                System.exit(1);
            }
            setBounds(x, y, 32, 32);
            x += hspeed;
            y += vspeed; 
            if (x < 0 || x + 32 > FRAME_WIDTH) {
                hspeed *= -1;
                if (hspeed < 0) {
                    setIcon(new ImageIcon(imgLeft));
                } else {
                    setIcon(new ImageIcon(imgRight));
                }
            }
            if (y < 0 || y + 32 > FRAME_HEIGHT) {
                vspeed *= -1;
            }     
        }
    }
}
