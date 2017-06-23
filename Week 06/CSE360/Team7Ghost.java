/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSE360;
//testing

import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author pdreiter
 * @modified ghost moving to make sure it is always in the panel (Chen)
 */

// original test class for Team7Ghost
//public class Team7Ghost implements Team7Ghost {
//    Team7Ghost y;
//    /**
//     * @param args the command line arguments
//     */
//    public Team7Ghost() {
//        y = new GhostAnimationLabel();
//        this.add(y);
//    }
//    public GhostAnimationLabel getGhostAnimationLabel(){ return y;}
//    
//}
//

//about the moving, the origin is the center point of the top height
class Team7Ghost extends JPanel implements Runnable
{
    private JLabel animation;
    private String dir;
    private int xg;
    private int yg;
    public int xbound, ybound;
    private static final int step = 10;    // how many pixels stepped in whatever direction
    private static String iconPath;
    private static final int ghostScale = 50;
    private boolean isVisible;
    Thread gt;
    
    public Team7Ghost(int xbound,int ybound,String imp) 
    {
        isVisible=true;
        iconPath=imp+"/ghost_";
        xg=0;yg=0;
        this.xbound = xbound;
        this.ybound = ybound;
        dir="right";
        animation = new JLabel ("",new ImageIcon((new ImageIcon(getFullIconPath()).getImage().getScaledInstance(ghostScale, ghostScale,
                java.awt.Image.SCALE_SMOOTH)),"Blinky"),JLabel.CENTER);
        this.add(animation);
        this.setBounds(xg,yg,xbound,ybound);
        setVisible(false);
        setOpaque(false);

    }

    public void setDirection(int a){ 
        switch(a) { 
            case 0: 
                dir = "up";
                break;
            case 1: 
                dir = "down";
                break;
            case 2: 
                dir = "left";
                break;
            default: 
                dir = "right";
                break;
        }
    }
    public boolean moveGhost() {
        switch(dir) { 
            case "up": 
                return moveGhostUp();
            case "down":
                return moveGhostDown();
            case "left":
                return moveGhostLeft();
            case "right":
                return moveGhostRight();
            default: 
                return moveGhostRight();
        }
    }
    public boolean moveGhostRight() { 
        if((xg+step+animation.getIcon().getIconWidth()/3)<= xbound/2){ updateGhostCoordinates(xg+step,yg); return true;}
        else return false;
    }
    public boolean moveGhostLeft() { 
        if((xg-step-animation.getIcon().getIconWidth()/3)>=(-xbound/2)){ updateGhostCoordinates(xg-step,yg); return true;}
        else return false;
    }
    public boolean moveGhostUp() { 
    	if((yg-step)>=0){ updateGhostCoordinates(xg,yg-step); return true;}
        else return false;
    }
    public boolean moveGhostDown() { 
        if((yg+step+animation.getIcon().getIconHeight())<=ybound){ updateGhostCoordinates(xg,yg+step); return true;}
        else return false;
    }
    public void updateGhostAnimation() throws IOException{
       // animation.setIcon(new ImageIcon(ImageIO.read(new File(getFullIconPath()))));
       animation.setIcon(new ImageIcon((new ImageIcon(getFullIconPath()).getImage().getScaledInstance(ghostScale, ghostScale,
                java.awt.Image.SCALE_SMOOTH)),"Blinky"));
    }
    public String getFullIconPath(){ return iconPath+dir+".png";}

    public void updateGhostCoordinates(int x,int y) {
        xg=x;yg=y;
        setLocation(xg,yg);
    }
    public void updateDirection(int x, int y) {
        if(xg>x){dir="left";}
        else if(yg<y){dir="down";}
        else if(yg>y) { dir="up"; }
        else { dir="right";}
    }
    public String getDirection(){ return dir; }
    public int getXloc() { return xg; }
    public int getYloc() { return yg; }
    // handling ghost status within class
    public void toggleGhostMovement(){
        isVisible=!isVisible;
        if(isVisible==false){ stopGhostMovement();}
        else { startGhostMovement(); }
    }
    // moving GhostAnimationLoop thread stuff into the Ghost class
    public void startGhostMovement() {
        setVisible(true);
        gt = new Thread(this);
        gt.start();
        
    }
    public void stopGhostMovement() {
        setVisible(false);
        gt.interrupt();
        while(gt.isInterrupted()==true){} // wait until thread has completely been interrupted
        gt=null; System.gc(); // then delete thread and clean up
    }

    @Override
    public void run(){
        int i=0;
        int moveCtr=0;

        while (true) { 
            try { 
                if(gt.interrupted()) { return; }
                if(moveCtr<=0) { 
                    moveCtr = 30; //(int) (Math.random()%40); 
                    setDirection((int) (Math.floor(Math.random()*101))%4);
                }
                //pause for 0.1 seconds
                gt.sleep(100);
                //System.out.println(gal.getFullIconPath());
                try {
                    // if it's not a valid move within boundaries of the frame, then don't move, but regenerate random direction on next loop
                    if(moveGhost()==false){ 
                        moveCtr=0; 
                        //System.out.println("("+Integer.toString(gal.getXloc())+","+Integer.toString(gal.getYloc())+")\n => MAX: "+Integer.toString(gal.getMaxX())+","+Integer.toString(gal.getMaxY())+")");
                    } 

                    updateGhostAnimation();
                } catch (IOException ex) {
                    Logger.getLogger(Team7Ghost.class.getName()).log(Level.SEVERE, null, ex);
                }
                i=(++i)%100;moveCtr--;
            } catch (InterruptedException ex) {
                return; // we've been interrupted, no more thread execution
            }
        }
    }



}


