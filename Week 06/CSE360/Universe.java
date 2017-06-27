/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
