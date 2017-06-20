/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package Team4Week2;
/**
 * Main class
 *
 * @author Javier Gonzalez-Sabchez
 * @author Manohara Rao Penumala
 */

package CSE360;


import java.awt.GridLayout;
import javax.swing.JFrame;
import java.io.*;
import java.util.*;

public class Universe extends JFrame {

    public Universe ()  {
        GridLayout grid = new GridLayout (3,3);
        setLayout(grid);
        this.add(new Team1());
        this.add(new Team2());
        this.add(new Team3());
	this.add(new Team4());
	this.add(new Team5());
	this.add(new Team6());
	this.add(new Team7());
	this.add(new Team8());
	this.add(new Team9());
    }

    public static void main(String[] args) {
        Universe u = new Universe();
        u.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        u.setSize(800, 800);
        u.setVisible(true);
        u.pack();
    }

}
