package CSE360;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pdreiter
 * @modified Updated to be reused for gear or info icon [chen]
 * @modified further updated to abstract more (moved placement into xoffset/yoffset instead of relying on string name) [pemma]
 */
public class Team7OverlayObject extends JLabel{
    private String iconPath;
    private int xscale;
    private int yscale;
    public Team7OverlayObject(int xoffset, int yoffset, int xs, int ys, String iP) {
        iconPath=iP;
        xscale=xs;yscale=ys;
        System.out.println("Getting image from "+iconPath);

        this.setIcon(new ImageIcon((new ImageIcon(iconPath).getImage().getScaledInstance(xscale, yscale,
                    java.awt.Image.SCALE_SMOOTH)),"icon"));
        this.setBounds(xoffset,yoffset,xscale,yscale); //upperFrame.getSize().width/2,upperFrame.getSize().height,512,512);
        
        setVisible(true);
        setOpaque(false);
    }
    public void updateBounds(int xoffset, int yoffset, int xs, int ys) {
    	xscale=xs;yscale=ys;
        this.setIcon(new ImageIcon((new ImageIcon(iconPath).getImage().getScaledInstance(xscale, yscale,
                java.awt.Image.SCALE_SMOOTH)),"icon"));
    	this.setBounds(xoffset,yoffset,xscale,yscale);
    }
        
}
