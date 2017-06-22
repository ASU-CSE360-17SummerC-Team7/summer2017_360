package CSE360;
/*
 * Team8Ghost class inheriting from JPanel,
 * Contain the moving ghost in ghostLabel
 */
//package CSE360;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;

public class Team8Ghost extends JPanel implements Runnable {
    
    String path = "Team8Image/Ghost.png";
    Integer gs = 30;
    ImageIcon ghostImage;
    JLabel ghostLabel;
    Integer xStart = 0,   yStart = 30,
            xLimit = 250, yLimit = 100;
    boolean bounced=false;    //determined the direction of movement
    public Team8Ghost()
    {
        Initialize();
    }
    private void Initialize()
    {
        this.setSize(super.getWidth(), super.getHeight());
        this.setLayout(null);
        URL url = getClass().getResource(path);
        Image image = null;
        try {
            image = ImageIO.read(url);
        } catch (IOException ex){}
        
        ghostImage = new ImageIcon(resizeImage(image,gs,gs));
        
        ghostLabel = new JLabel();
        ghostLabel.setIcon(ghostImage);
        ghostLabel.setSize(gs,gs);
    }
    @Override
    public void run()
    {
        moveGhost(xStart,yStart,xLimit,yLimit);
    }
    //moving ghost
    private void moveGhost(int x, int y, int xL, int yL)
    {
        while(true)
        {
            try{Thread.sleep(10);}
          catch(InterruptedException e){System.exit(1);}
          
          
          if(!bounced) //did not hit the wall yet
          {
              x++;
              if(x >= xL - gs)
                  bounced = true;  
          }
          else if(bounced)  //hit the wall yet
          {
              x--;
              if(x == 0)
                  bounced = false;
          }     
          upadateGhostPosition(x,y);
        } 
    }
    
    //update ghost position
    private void upadateGhostPosition(int x, int y)
    {
        this.removeAll();
        ghostLabel.setLocation(x,y);
        this.add(ghostLabel);
        super.revalidate();
        super.repaint();
    }
    private Image resizeImage(Image Img, int width, int height){
        BufferedImage newImg = new BufferedImage(width, height, 
                                                BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = newImg.createGraphics();

    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(Img, 0, 0, width, height, null);
    g2.dispose();

    return newImg;
    }
}