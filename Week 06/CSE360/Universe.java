

package CSE360;
  
 import java.awt.BorderLayout;
 
 import java.awt.GridLayout;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.JFrame;
 import javax.swing.JButton;
 import javax.swing.JOptionPane;
 import javax.swing.JPanel;


public class Universe extends JFrame implements ActionListener {
  
     ControlCenter cc = ControlCenter.getInstance(); 
     Team0 t1 = new Team0();
     Team0 t2 = new Team0();
     Team0 t3 = new Team0();
     Team0 t4 = new Team0();
     Team0 t5 = new Team0();
     Team0 t6 = new Team0();
     Team0 t7 = new Team0();
     Team0 t8 = new Team0();
     Team0 t9 = new Team0();
     JButton b1 = new JButton("select a city");
     JButton b2 = new JButton("enable/disable ghost");
 
 public Universe ()  {     
      // observer-observable
      cc.addObserver(t1); 
      cc.addObserver(t2); 
      cc.addObserver(t3); 
      cc.addObserver(t4); 
      cc.addObserver(t5); 
      cc.addObserver(t6); 
      cc.addObserver(t7); 
      cc.addObserver(t8); 
      cc.addObserver(t9);      
      // center
      JPanel center = new JPanel();       
      center.setLayout(new GridLayout (3,3));
      center.add(t1);
      center.add(t2);
      center.add(t3);
      center.add(t4);
      center.add(t5);
      center.add(t6);
      center.add(t7);
      center.add(t8);
      center.add(t9);     
      // status bar
      b1.addActionListener(this);
      b2.addActionListener(this);
      JPanel bar = new JPanel();       
      bar.setLayout(new GridLayout (1,2));
      bar.add(b1);
      bar.add(b2);
      // assembly
      setLayout(new BorderLayout());   
      add(center, BorderLayout.CENTER);
      add(bar, BorderLayout.SOUTH);     
 }
 
     @Override
     public void actionPerformed(ActionEvent e) {
        if (e.getSource()==b1) {
         String message = "Select a location: ";
         String title = "Location";
         String input = (String)JOptionPane.showInputDialog(this, message, title, 
                 JOptionPane.QUESTION_MESSAGE, null, ControlCenter.CITIES, ControlCenter.CITIES[0]);
         cc.setCity(input);
         cc.notifyObservers();
        } else {
          cc.setShowGhost(!cc.getShowGhost());
          cc.notifyObservers();
        }
    }
 
     public static void main(String[] args) {
       Universe u = new Universe();
       u.setTitle("Week 06");
       u.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       u.setSize(500, 500);
       u.setVisible(true);
    }
  
 } 


/*
package CSE360;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.io.IOException;
import javax.swing.*;
import javax.swing.JFrame;
import org.json.JSONException;
import java.util.Observable;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Vector;
//import javafx.beans.InvalidationListener;
//import javafx.beans.Observable;


public class Universe extends JFrame implements ComponentListener{
    private JComboBox cityBox;
    private String[] cityNames;
    private int windowWidth;
    private int windowHeight;
    private double _lat;
    private double _long;
    private String coords = "";
    private ControlCenter cc;
    private Team4 team4;

    
     public Universe () throws IOException, JSONException {
        team4 = new Team4();
        cc = ControlCenter.getInstance();
        cc.addObserver(team4);
        this.cityNames = new String[]{"Phoenix", "Los Angeles"};
        this.addObserver();
        cityBox = new JComboBox();
        //Vector cityBoxItems = new Vector();
        cityBox.addItem("Phoenix");
        cityBox.addItem("Los Angeles");
        this.add(cityBox);
        cityBox.addActionListener(cityBoxListener);
        
                 
        GridLayout grid = new GridLayout (1,1);   
        setLayout(grid);
        this.add(team4);
//    this.add(new Team4());
//     this.add(new Team4());
//     this.add(new Team4());
//     this.add(new Team4());
//     this.add(new Team4());
//     this.add(new Team4());
//     this.add(new Team4());     
    }
     
     ActionListener cityBoxListener = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = (String) cityBox.getSelectedItem();
            
            switch(s){
                case "Phoenix":
                    _lat = 33.448376;
                    _long = -112.074036;
                    break;
                case "Los Angeles":
                    _lat = 34.052235;
                    _long = -118.243683;
                    break;
                    
            }
            coords = String.valueOf(_lat) + "," + String.valueOf(_long);
            System.out.println("Coordinates: " + coords);
            cc.setCoordinates(_lat, _long);

        }
            
     };
             
  
   public static void main(String[] args) throws IOException, JSONException {
        Universe u = new Universe();
        u.setTitle("Universe Frame");
        u.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        u.setSize(500, 500);
        u.pack();
        u.addComponentListener(u);
        u.setVisible(true);
	u.setResizable(true);
        
    }

    @Override
    public void componentResized(ComponentEvent e) {
        windowWidth = this.getWidth();
        windowHeight = this.getHeight();
        System.out.println("Width : " + windowWidth + "    height : " + windowHeight);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentShown(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void addObserver(){        
    }

}



*/
