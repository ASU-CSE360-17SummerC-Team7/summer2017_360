package CSE360;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
    private boolean currentlyVisible;
    Thread gt;
    
    public Team7Ghost(int xbound,int ybound,String imp) 
    {   setOpaque(false);
        setLayout(new BorderLayout());
        currentlyVisible=false;
        iconPath=imp+"/ghost_";
        xg=0;yg=0;
        this.xbound = xbound;
        this.ybound = ybound;
        dir="right";
        animation = new JLabel (new ImageIcon((new ImageIcon(getFullIconPath()).getImage().getScaledInstance(ghostScale, ghostScale,
                java.awt.Image.SCALE_SMOOTH)),"Blinky"));
        animation.setLocation(xg,yg);
        this.add(animation);
        this.setBounds(0,0,ghostScale,ghostScale);
        this.setSize(xbound,ybound);
        setVisible(false);
    }

    // method: updateBounds
    // abstracted method to help with dynamic resizing of panel
    public void updateBounds(int xb,int yb){ 
        xbound=xb;ybound=yb;
        this.setBounds(0,0,ghostScale,ghostScale);   
        this.setSize(xbound,ybound);
        this.setVisible(false);

	// ghost panel's origin (0,0) is in center of frame, so xmin = -xbound/2, xmax = xbound/2
	//  AND ymin = -ybound/2, ymax = ybound/2
	// if current x coordinate (xg) is outside of resized area 
	//    (xbound => xmin<=xg+viewable_image_size<=xmax)
	// then set next x coordinate to limit: xmin or xmax, respective to the boundary overrun
	// if current y coordinate (yg) is outside of resized area 
	//    (ybound => ymin<=yg+viewable_image_size<=ymax)
	// then set next y coordinate to limit: ymin or ymax, respective to the boundary overrun
        this.updateGhostCoordinates(
                (xg>(xbound/2)-animation.getIcon().getIconHeight()/3)?xbound/2-animation.getIcon().getIconHeight()/3
                        :((xg<-(xbound/2)+animation.getIcon().getIconHeight()/3)?-(xbound/2)+animation.getIcon().getIconHeight()/3
                                :xg), 
                (yg>(ybound/2)-animation.getIcon().getIconHeight()/3)?ybound/2-animation.getIcon().getIconHeight()/3
                        :((yg<-(ybound/2)+animation.getIcon().getIconHeight()/3)?-(ybound/2)+animation.getIcon().getIconHeight()/3
                                :yg)
                );

        this.setVisible(currentlyVisible);
        this.revalidate();this.repaint();
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
    // method: moveGhost
    // simple helper method that corresponds current direction with next ghost movement
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
    // -------------------------------------------------------------------------------
    // methods: moveGhostRight, moveGhostLeft, moveGhostUp, moveGhostDown
    // simple helper methods that checks boundary conditions 
    // and if valid, moves ghost in a specific direction
    public boolean moveGhostRight() {
        if((xg+step+animation.getIcon().getIconWidth()/3)<= xbound/2){ updateGhostCoordinates(xg+step,yg); return true;}
        else return false;
    }
    public boolean moveGhostLeft() {
        if((xg-step-animation.getIcon().getIconWidth()/3)>=(-xbound/2)){ updateGhostCoordinates(xg-step,yg); return true;}
        else return false;
    }
    public boolean moveGhostUp() {         
    	if((yg-step-animation.getIcon().getIconHeight()/3)>=(-ybound/2)){ updateGhostCoordinates(xg,yg-step); return true;}
        else return false;
    }
    public boolean moveGhostDown() { 
        if((yg+step+animation.getIcon().getIconHeight()/3)<=(ybound/2)){ updateGhostCoordinates(xg,yg+step); return true;}
        else return false;
    }
    // -------------------------------------------------------------------------------
    // method: updateGhostAnimation
    // simple helper method that helps to rescale and redraw image with new location
    public void updateGhostAnimation() throws IOException{
       // animation.setIcon(new ImageIcon(ImageIO.read(new File(getFullIconPath()))));
       animation.setIcon(new ImageIcon((new ImageIcon(getFullIconPath()).getImage().getScaledInstance(ghostScale, ghostScale,
                java.awt.Image.SCALE_SMOOTH)),"Blinky"));
       
    }
    public String getFullIconPath(){ return iconPath+dir+".png";}

    // method: updateGhostCoordinates
    // simple helper method that sets class variables and updates location
    public void updateGhostCoordinates(int x,int y) {
        xg=x;yg=y;
        setLocation(xg,yg);
        //System.out.println("Ghost location: ("+Integer.toString(xg)+","+Integer.toString(yg)+");\n");
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
    //----------------------------------------------------------------------------------
    // toggleGhostMovement overrides the Ghost movement with the input boolean parameter
    // handling ghost status within class - this just handles all movements for the ghost state 
    // starts or stops the ghost movement (thread) based on the current visibility of the ghost
    public boolean toggleGhostMovement(){
        if(currentlyVisible==true){ stopGhostMovement();}
        else { startGhostMovement(); }
        return currentlyVisible;
    }
    //----------------------------------------------------------------------------------
    // updateGhostMovement overrides the Ghost movement with the input boolean parameter
    // if the ghost is already moving and makeVisible==true, no change
    // if the ghost is not moving and makeVisible == false, no change
    // if the ghost is already moving and makeVisible==false, toggle Ghost movement
    // if the ghost is not moving and makeVisible==true, toggle Ghost movement
    public boolean updateGhostMovement(boolean makeVisible){    
        if(currentlyVisible!=makeVisible){ toggleGhostMovement(); } 
        // if the state is the same, don't do anything
        return currentlyVisible;
    }
    // moving GhostAnimationLoop thread stuff into the Ghost class
    // private helper function: startGhostMovement
    // manages starting the ghost thread to ensure that the thread operates correctly
    private void startGhostMovement() {
    	if(gt == null) { 
	        animation.setLocation(xg,yg); 
	        
	        setVisible(true);
	        gt = new Thread(this);
	        gt.start();
    	}
        currentlyVisible=true;        
    }
    // private helper function: stopGhostMovement
    // manages stopping the ghost thread to ensure that the thread operates correctly
    private void stopGhostMovement() {
    	if(gt != null) { 
	        setVisible(false);
	        gt.interrupt();
	        while(gt.isInterrupted()==true){} // wait until thread has completely been interrupted
	        gt=null; System.gc(); // then delete thread and clean upS
	        //System.out.println("End of Ghost Movement\n");
    	}
        currentlyVisible=false;
    }

    @Override
    public void run(){
        int i=0;
        int moveCtr=0;

        while (true) { 
            try { 
                if(gt.interrupted()) { return; }
                if(moveCtr<=0) { 
                    moveCtr = 100; //(int) (Math.random()%40); 
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


