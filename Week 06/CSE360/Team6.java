//Authors:  Joel Menja
//          Manuel Ucles
//          Michael Warnick

package CSE360;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.logging.Logger;
import static javafx.scene.paint.Color.color;
import javax.swing.ImageIcon;


public class Team6 extends JPanel implements Observer{
    
    private static int FRAME_WIDTH = 300;
    private static int FRAME_HEIGHT = 150;
    Team6Map temp;
    boolean team6View = false;
    ControlCenter cc = ControlCenter.getInstance(); 
    private Team6Ghost ghost = new Team6Ghost();
    private Thread thread = new Thread(ghost);
    private static JButton info, star;
    JLayeredPane pane;
   // pane.setPreferredSize(new Dimension(160,160));
    boolean coverflag = true;
    boolean ghostflag = false;
    JLabel label = new JLabel("");
    Team6Cover cover;
    JLabel map = new JLabel();
    JLabel map2 = new JLabel();
    JLabel tempLabel = new JLabel();
    JLabel tempLabel2 = new JLabel();
    JPanel names;
    JPanel options, dialog;
    //Team6Map map, map2;
    boolean setv = false;
    int i = 0;
    public Team6(){
        
                
            
        info = new JButton(new ImageIcon((new ImageIcon("src/CSE360/Team6Images/info.png")).getImage().getScaledInstance(22, 22, java.awt.Image.SCALE_SMOOTH)));
        star = new JButton(new ImageIcon((new ImageIcon("src/CSE360/Team6Images/settings.png")).getImage().getScaledInstance(22, 22, java.awt.Image.SCALE_SMOOTH)));
        pane = new JLayeredPane();
        pane.setPreferredSize(new Dimension(300, 150));
        cover = new Team6Cover();
        names = new JPanel();
        options = new JPanel();
       
        names.add(cover);
        //pane.setOpaque(false);
        options.add(info);
        options.add(star);
        star.setBounds(FRAME_WIDTH - 40, FRAME_HEIGHT - 42, 32, 32);
        info.setBounds(FRAME_WIDTH - 80, FRAME_HEIGHT - 42, 32, 32);
        //info.setBounds(62, 110, 40, 40);
        //star.setBounds(150, 110, 40, 40);
        
        names.setBounds(0, 0, 300, 150);
        //options.setBounds(50, 110, 250, 250);
       
         pane.add(names, new Integer(0));
         pane.add(info, new Integer(1));
         pane.add(star, new Integer(2));
         
         thread.start();
                
        //setLayout(new BorderLayout());
          
        //add(names, BorderLayout.CENTER);
        //add(options, BorderLayout.SOUTH);
        add(pane);
    
        
    info.addActionListener(new ActionListener(){
        
            @Override
        public void actionPerformed(ActionEvent e) {
            
            String message = "Select a location: ";
            String title = "Location";
            String input = (String)JOptionPane.showInputDialog(dialog, message, title, 
            JOptionPane.QUESTION_MESSAGE, null, ControlCenter.CITIES, ControlCenter.CITIES[0]);
            String degree = "\u00b0";
            Font myFont = new Font("Serif", Font.BOLD, 20);
            //cc.setCity(input);
            team6View = true;
            
            map = new JLabel();
            tempLabel = new JLabel();
            
            
                try{
                    Team6Map temp = new Team6Map(input);
                    int tempFinal = temp.getTemp();
                    //System.out.println(tempFinal);
                    
                    tempLabel.setText(Integer.toString(tempFinal) + degree  + "F");
                    tempLabel.setFont(myFont);
                    
                    input = input.toLowerCase();
                    String imageURL = "https://maps.googleapis.com/maps/api/staticmap?center=" + input + "&zoom=12&size=300x150&key=AIzaSyDFPRe6kHjdwp-UD5hY0-hYRZpx4L-czGs";
                    System.out.println(imageURL);
                    String destinationFile = "image.jpg";
                    URL url = new URL(imageURL);

                    java.io.InputStream in = url.openStream();
                    OutputStream out = new FileOutputStream(destinationFile);

                    byte b[] = new byte[2048];
                    int lenght;

                    while((lenght = in.read(b)) != -1){
                            out.write(b,0,lenght); //change
                    }
                    URL urlmap = new URL(imageURL);
                    BufferedImage img = ImageIO.read(urlmap);
                    ImageIcon icon = new ImageIcon(img);

                    in.close();
                    out.close();

                    map.setIcon(icon);
                    map.setBounds(0,0,300,150);
                    tempLabel.setBounds(FRAME_WIDTH -140, FRAME_HEIGHT - 42, 50, 32);
                    //pane.add(tempLabel,new Integer(0) );
              
                    //pane.add(map,new Integer(0) );            

               
               if(i == 0){
                    pane.remove(2);
                    pane.add(map,new Integer(0) );
                    pane.add(tempLabel, new Integer(4));
                    pane.add(ghost, JLayeredPane.PALETTE_LAYER);
                    //add(mapThread);
                    //pane.setVisible(true);
                    //names.setVisible(false);
                   // pane.revalidate();
                    add(pane, BorderLayout.CENTER);
                    i = i+1;
                    
               }
                else{
                   map2 = map;
                   tempLabel2 = tempLabel;
                   pane.remove(4);
                   pane.remove(1);
                   //pane.remove(4);
                   pane.add(map2, new Integer(0) );
                   pane.add(tempLabel2, new Integer(4));
                  // pane.add(tempLabel2, new Integer(1) );
                   
               }
            System.out.println(input);
           // revalidate();
            cc.notifyObservers();
                
                } catch (IOException ex) {
                            Logger.getLogger(Team6Cover.class.getName()).log(Level.SEVERE, null, ex);
                        } 

           }
    });
        
        
      star.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(ghostflag == false){
                    
                    //pane.remove(ghost);
                    ghost.setVisible(false);
                    ghostflag = true;
                    
                } 
                else {
                    
                    //pane.add(ghost, JLayeredPane.PALETTE_LAYER);
                    ghost.setVisible(true);
                    ghostflag = false;
                }
            }
        
        });
  }
    

    @Override
    public void update(Observable o, Object arg) {
       String cityFocus = ((ControlCenter)o).getCity();
       boolean ghostState = ((ControlCenter)o).getShowGhost();
       String updateCity = cityFocus.toLowerCase();
       String degree = "\u00b0";
       Font myFont = new Font("Serif", Font.BOLD, 20);
       
       
       try{
           if(team6View != true){
           Team6Map temp = new Team6Map(cityFocus);
           int tempFinal = temp.getTemp();
           tempLabel.setText(Integer.toString(tempFinal) + degree  + "F");
           tempLabel.setFont(myFont);
            String globImageUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + updateCity + "&zoom=12&size=300x150&key=AIzaSyDFPRe6kHjdwp-UD5hY0-hYRZpx4L-czGs"; 
            System.out.println(globImageUrl);
            String destinationFile = "image.jpg";
            URL url = new URL(globImageUrl);

            java.io.InputStream in = url.openStream();
            OutputStream out = new FileOutputStream(destinationFile);

            byte b[] = new byte[2048];
            int lenght;

            while((lenght = in.read(b)) != -1){
                    out.write(b,0,lenght); //change
            }
            URL urlmap = new URL(globImageUrl);
            BufferedImage img = ImageIO.read(urlmap);
            ImageIcon icon = new ImageIcon(img);

            in.close();
            out.close();

            map.setIcon(icon);
            map.setBounds(0,0,300,150);
            tempLabel.setBounds(FRAME_WIDTH -140, FRAME_HEIGHT - 42, 50, 32);

              //  map = new Team6Map(input);

               if(i == 0){
                    pane.remove(2);
                    pane.add(map,new Integer(0) );
                    pane.add(tempLabel, new Integer(4));
                    pane.add(ghost, JLayeredPane.PALETTE_LAYER);
                    //add(mapThread);
                    //pane.setVisible(true);
                    //names.setVisible(false);
                   // pane.revalidate();
                    add(pane, BorderLayout.CENTER);
                    i = i+1;
                    
               }
                else{
                   map2 = map;
                   tempLabel2 = tempLabel;
                   pane.remove(4);
                   pane.remove(1);
                   //pane.remove(4);
                   pane.add(map2, new Integer(0) );
                   pane.add(tempLabel2, new Integer(4));
                  // pane.add(tempLabel2, new Integer(1) );
                   
               }
           }
           team6View = false;
           
               if(ghostState == false){
                   ghost.setVisible(false);
               }
               if(ghostState == true){
                   ghost.setVisible(true);
               }
               //AIzaSyDFPRe6kHjdwp-UD5hY0-hYRZpx4L-czGs


            //System.out.println(input);
           // revalidate();
            cc.notifyObservers();
                
                } catch (IOException ex) {
                            Logger.getLogger(Team6Cover.class.getName()).log(Level.SEVERE, null, ex);
                        } 
       //System.out.println(globImageUrl);
       //  (ControlCenter)o).setCity();
       //  ((ControlCenter)o).getGPS()
       //ghostflag = ((ControlCenter)o).getShowGhost();
       
         //System.out.println(ghostflag);
    

    }
    /*
          star.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(cc.getShowGhost()){
                    
                    cc.setShowGhost(false);
                    
                } else if(!cc.getShowGhost()) {
                    
                    cc.setShowGhost(true);
                }
            }
        
        });
    */
}
