/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSE360;

import javax.swing.*;
import java.awt.*;

public class InitialPanel extends JPanel {
    


//    private JFrame f;
    private JPanel p;
    private JButton b1;
    private JLabel lab;
    
    public InitialPanel(){
        this.setBackground(Color.YELLOW);
        
        b1 = new JButton("TEST");
        
        lab = new JLabel(" Ross, Sean");
        
        this.add(b1);
        this.add(lab);
    }      
    
}
