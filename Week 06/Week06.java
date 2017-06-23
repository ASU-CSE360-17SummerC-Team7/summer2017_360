/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week06;


import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.*;
import javax.swing.JFrame;
import org.json.JSONException;

/**
 *
 * @author rsatchel
 */
public class Week06 extends JFrame {

    
     public Week06 () throws IOException, JSONException {
     GridLayout grid = new GridLayout (4,2);   
     setLayout(grid);
     this.add(new Team4());
     this.add(new Team4());
     this.add(new Team4());
     this.add(new Team4());
     this.add(new Team4());
     this.add(new Team4());
     this.add(new Team4());
     this.add(new Team4());     
    }
    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) throws IOException, JSONException {
        Week06 u = new Week06();
        u.setTitle("Universe Frame");
        u.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        u.setSize(500, 500);
        u.setVisible(true);
	u.setResizable(true);
        
    }
    
}




