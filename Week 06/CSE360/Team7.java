package CSE360;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

/* 
File: Team7.java
    @author : Haoyu Xu
    @author : Chen Yang
    @author : Pemma Reiter
Description:
    This file is the top level JPanel that will be instantiated in the Universe JFrame (owned by Professor Gonzalez-Sanchez and Rao)
    Team7 contains multiple components:
        JLayeredPane layer : Layer object to keep track of multiple JPanel objects
        Team7Cover : Class that encapsulates Project 1 JPanel for Team7
        Team7Proj2Panel : Class that encapsulates Project 2, Map + Weather Information aggregate JPanel
        Team7OverlayObject : JPanel object displaying a gear that when clicked on, changes the Panel Layers or updates the Map+Weather
        Team7Ghost : ghost png image that moves around in a concurrent process/thread
    simple components:
        boolean initialState : is a variable that is set to false after first mouse click changes JPanel from first view to final view
        int xbound : default max X size for all components, passed to all components so that default look and any later scaling (TBD) is consistent
        int ybound : default max X size for all components, passed to all components so that default look and any later scaling (TBD) is consistent
        String imagePath : Path to all images used in these components, ensures that only one path should be touched

*/

public class Team7 extends JPanel
{
    JLayeredPane layer;
    Team7Cover p1;
    Team7Proj2Panel p2;
    Team7OverlayObject gear;
    Team7Ghost ghost;
    boolean initialState;
    private int xbound=200;
    private int ybound=150;
    private String imagePath = "CSE360/Team7Images"; // default image path that works for default projects [no setup, no src path, no additional include dirs]

    public Team7()
    {
        // pdreiter - some error handling for images - make sure that Team7Images path is correct, despite project include paths
        if((new File(imagePath)).isDirectory()){ System.out.println("Opening up images from "+imagePath+" for Team7\n"); }
        else{
            System.out.println("\n[TEAM7] Cannot find directory: "+imagePath);
            imagePath="Team7Images"; // this is in case the CSE360 subdirectory is in the incdir path
            if((new File(imagePath)).isDirectory()){ System.out.println("Opening up images from "+imagePath+" for Team7\n"); }
            else {
                System.out.println("\n[TEAM7] Cannot find directory: "+imagePath);
                imagePath = "src/CSE360/Team7Images"; // this is a similar root path that Team2 did on Project 3
                if((new File(imagePath)).isDirectory()){ System.out.println("Opening up images from "+imagePath+" for Team7\n"); }
                else { System.out.println("\n[TEAM7] Cannot find directory: "+imagePath+"\nERROR: Program for Team7 will not display properly!!!\n"); }
            }
        }
      //  System.out.println("Height: "+Float.toString(this.getRootPane().getSize().height)+" ; Width: "+Float.toString(this.getRootPane().getSize().width));
        this.setBackground(Color.white);
        // Calling setPreferredSize for all JPanels in case JFrame calls pack(), so that the optimal width and height is reflected
        initialState=true;
        this.setPreferredSize(new Dimension(xbound, ybound));
        setOpaque(false);
        layer = new JLayeredPane();
        layer.setPreferredSize(new Dimension(xbound, ybound));
        
        p1 = new Team7Cover();
        p1.setSize(new Dimension(xbound, ybound));
        p1.setPreferredSize(new Dimension(xbound,ybound));
        
        p2 = new Team7Proj2Panel(xbound,ybound,imagePath);
        p2.setSize(new Dimension(xbound, ybound));
        p2.setPreferredSize(new Dimension(xbound, ybound));
        
        gear = new Team7OverlayObject(xbound, ybound,imagePath);
        gear.setPreferredSize(new Dimension(xbound, ybound));
        ghost = new Team7Ghost(xbound, ybound,imagePath);
        
        gear.addMouseListener(new MouseAdapter(){
          @Override
          public void mouseClicked(MouseEvent e) { 
            if(initialState==true){ 
                layer.remove(p1);
                gear.setVisible(true);
                ghost.setVisible(true);
                startGhostMovement();
                layer.add(p2, new Integer(1));
                layer.revalidate();
                layer.repaint();
                initialState=false;
            }
            else {
                p2.DisplayGeoMenu();
            }
          }      
        });
//        JButton toggle = new JButton("toggle");
//        toggle.setBounds(200, 50, 50, 50);
//        
//        toggleListener t = new toggleListener();
//        toggle.addActionListener(t);
//        layer.add(toggle, new Integer(4));
        
        layer.add(gear, new Integer(4));
        layer.add(p1, new Integer(3));
        layer.add(ghost, new Integer(2));
        
        add(layer);

    }
    
    private class toggleListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(initialState==true){ 
                layer.remove(p1);
                gear.setVisible(true);
                ghost.setVisible(true);
                startGhostMovement();
                layer.add(p2, new Integer(1));
                layer.revalidate();
                layer.repaint();
                initialState=false;
            }
            else {
                p2.DisplayGeoMenu();
            }
            
        }
    }
    
    private void startGhostMovement() {
        ghost.setVisible(true);
        Thread ta = new Thread(new ghostAnimationLoop(ghost));
        ta.start();
    }

}
