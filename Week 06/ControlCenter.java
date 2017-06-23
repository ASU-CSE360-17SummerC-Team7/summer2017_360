/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week06;

import java.util.Observable;

/**
 *
 * @author rsatchel
 */
public class ControlCenter extends Observable{
    private boolean showGhost;
    private String city;
    private static ControlCenter instance;
    
    private ControlCenter() {}
    
//    public static Singleton getInstance(){
//        if (instance == null){
//            instance = new Singleton();
//        }
//        return instance;
//    }
    
    public String getCity(){
        
        return city;
    }
    
    public boolean getShowGhost() {
        return showGhost;
    }
}