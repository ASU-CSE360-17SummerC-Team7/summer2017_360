package CSE360;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by User on 6/21/2017.
 */
public class Team3Ghost extends JPanel implements Runnable {

    public volatile Thread thread;
    private Image ghost;
    private int x, y, dx;
    
    public static boolean ghostStatus;

    public Team3Ghost() {
        super();
        
        ghostStatus = true;
        
        setSize(200,100);
        thread = new Thread(this);
        x = 100;
        dx = 1;
        y = 50;
        try {
            ghost = ImageIO.read(new URL("http://i.imgur.com/Ewi8y70.png"));
        } catch(IOException exc) {
            System.exit(3);
        }
        this.setOpaque(false);
        thread.start();
    }
    public void run() {
        while(Thread.currentThread() == thread) {
            repaint();
            try {
                Thread.sleep(10);
            } catch(InterruptedException e) {
                System.exit(3);
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        x += dx;
        if(x < 0) {
            dx = -dx;
        }

        if(x >= 150) {
            dx = -dx;
        }
        g.drawImage(ghost, x, y, 50, 50, null);
    }
    public void update(Graphics g) {
        paintComponent(g);
    }
}
