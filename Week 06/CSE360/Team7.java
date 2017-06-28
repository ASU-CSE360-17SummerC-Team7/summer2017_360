package CSE360;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.Observer;
import java.util.Observable;

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

public class Team7 extends JPanel implements Observer
{
    JLayeredPane layer;
    Team7Cover p1;
    Team7WeatherPanel weather;
    Team7GoogleMap map;
    Team7OverlayObject gear;
    Team7OverlayObject info;
    Team7Ghost ghost;
    boolean initialState;
    boolean showGhost;
    private int xbound=200;
    private int ybound=150;
    double lat,lon;
    boolean showinfo;
    String whichCity;
    private ControlCenter cc;
    // creating a root imagePath directory string to be reused
    private String imagePath = "CSE360/Team7Images"; // default image path that works for default projects [no setup, no src path, no additional include dirs]

    private String iconPath, infoPath;
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
        iconPath = imagePath+"/gear";
        infoPath = imagePath+"/info";
        setLayout(new BorderLayout());
        lat=33.4255;
        lon=-111.9400;
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
        //layer.add(p1, new Integer(0));
        
        
        map = new Team7GoogleMap(lat,lon,xbound,ybound);
        weather = new Team7WeatherPanel(lat,lon,xbound,ybound);
        weather.setPreferredSize(new Dimension(xbound,ybound));

        gear = new Team7OverlayObject(xbound, ybound,iconPath);
        gear.setPreferredSize(new Dimension(xbound, ybound));
        info = new Team7OverlayObject(xbound, ybound,infoPath);
        info.setPreferredSize(new Dimension(xbound, ybound));
        ghost = new Team7Ghost(xbound, ybound, imagePath);
        showGhost=true;
        showinfo=true;
        gear.setVisible(true);
        info.setVisible(true);
        ghost.startGhostMovement();

        addHierarchyBoundsListener(new HierarchyBoundsListener(){
            @Override
            public void ancestorMoved(HierarchyEvent e) {updateBounds();}
            @Override
            public void ancestorResized(HierarchyEvent e) {updateBounds();}
        });
        gear.addMouseListener(new MouseAdapter(){
          @Override
          public void mouseClicked(MouseEvent e) { 
            if(showGhost==true){ 
                gear.setVisible(true);
                ghost.startGhostMovement();
                showGhost = false;
            }
            else {
                ghost.toggleGhostMovement();
            }
            layer.revalidate();
            layer.repaint();
          }      
        });
        
       info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (showinfo) {
                	layer.remove(map);
                	layer.remove(weather);
                	layer.add(p1, new Integer(0));
                	showinfo = false;
                }
                else
                {
                	layer.remove(p1);
                	layer.add(map, new Integer(1));
                	layer.add(weather, new Integer(2));
                	showinfo = true;
                }
                layer.revalidate();
                layer.repaint();
            }
        }); 
        
        layer.add(gear, new Integer(5));
        layer.add(info, new Integer(4));
        layer.add(ghost, new Integer(3));
        layer.add(weather, new Integer(2));        
        layer.add(map, new Integer(1)); 
        add(layer);
    }


 
    
    private void updateBounds(){
        
        int w = this.getWidth();
        int h = this.getHeight();
        if(w<=0){w=500/3;}
        if(h<=0){h=500/3;}
        p1.setSize(w,h);
        layer.setBounds(0, 0, w, h);
        map.updateBounds(w,h);
        ghost.updateBounds(w,h);
        weather.updateBounds(w,h);
        
    }
    public void refresh()
    {
    	layer.remove(map);
    	layer.remove(weather);
    	map = new Team7GoogleMap(lat,lon,xbound,ybound);
        weather = new Team7WeatherPanel(lat,lon,xbound,ybound); 
        weather.setPreferredSize(new Dimension(xbound,ybound));
        layer.add(weather, new Integer(2));
        layer.add(map, new Integer(1));
        layer.revalidate();
        layer.repaint();     
    }
    @Override
	public void update(Observable o, Object arg1) {
		cc = (ControlCenter) o;
		if (whichCity != cc.getCity())
		{
			whichCity = cc.getCity();
			String gps = cc.getGPS();
			String[] parts = gps.split(",");
			lat =  Double.parseDouble(parts[0]);
			lon =  Double.parseDouble(parts[1]);
			System.out.println(lat);
			System.out.println(lon);
			refresh();
	    }
		else
		{
		    if (!cc.getShowGhost()) {
		    	ghost.toggleGhostMovement();
		    	showGhost = !showGhost;
		           }
		    else {
		    	ghost.startGhostMovement();
		    	showGhost = !showGhost;
		    		}
		}
		     layer.revalidate();
	         layer.repaint();
	    }
		
	}




    
       

