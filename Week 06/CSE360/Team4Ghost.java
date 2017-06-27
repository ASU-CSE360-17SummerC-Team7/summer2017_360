///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.'
// * Author: Sean Casaus
// */

 package CSE360;

 import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

 public class Team4Ghost extends JLabel implements Runnable {
   

 	public Team4Ghost() { //Constructor
        ImageIcon newImage = new ImageIcon(this.getClass().getResource("Team4Images/Pac-Man-Ghost-PNG-Transparent-Image.png"));
        setIcon(new ImageIcon(newImage.getImage().getScaledInstance(29, 34, 0))); //scales the image
 	}
 	
    public static void loadIMG() {
    	BufferedImage ghost = null;

    	try {
    		ghost = ImageIO.read(new File("Pac-Man-Ghost-PNG-Transparent-Image.png"));
    	} catch (IOException e) {
    		System.out.println("Ghost not Loaded");
    	}
    }

 	//Basic Algorithm that moves the ghost along the x axis at the bottom of a panel
 	@Override
 	public void run() {
 		int x = 0;
 		int y = -29; //default y coordinate
 		int Speed = 3; //Change Speed of Ghost
 		int xSpeed = Speed;
 		int ySpeed = Speed;
 		int width = 221; //Difference between width of panel and width of ghost
 		int height = 91; //The height the ghost can travel
 		
 		while (true) {
 			setBounds(x, y, width, height);
 			if (x < 0) {
 				xSpeed = Speed;
 			}
 			if (x > width) {
 				xSpeed = -Speed;
 			}
 			if (y < -29) {
 				ySpeed = Speed;
 			}
 			if (y > height) {
 				ySpeed = -Speed;
 			}
 			x += xSpeed;
 			y += ySpeed;
 			try {
 				Thread.sleep(50);
 			} catch (InterruptedException e) {
 				System.out.println("Error");
 			}
 		}
 	}
 }
