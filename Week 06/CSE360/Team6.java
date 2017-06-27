//Authors:  Joel Menja
//          Manuel Ucles
//          Michael Warnick

package CSE360;

import java.awt.BorderLayout;
import static java.awt.SystemColor.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.charset.Charset;
import java.security.Security;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.*;
import org.json.*;
import javax.swing.ImageIcon;


public class Team6 extends JPanel implements Observer{
    
    ControlCenter cc = ControlCenter.getInstance(); 
    
    private static JButton info, star;
    JLayeredPane pane;
   // pane.setPreferredSize(new Dimension(160,160));
    boolean coverflag = true;
    boolean ghostflag = false;
    JLabel label = new JLabel("");
    Team6Cover cover;
    JLabel map = new JLabel();
    JPanel names;
    JPanel options, dialog;
    //Team6Map map, map2;
    boolean setv = false;
      
    public Team6(){
        
                
            
        info = new JButton(new ImageIcon((new ImageIcon("src/CSE360/Team6Images/info.png")).getImage().getScaledInstance(22, 22, java.awt.Image.SCALE_SMOOTH)));
        star = new JButton(new ImageIcon((new ImageIcon("src/CSE360/Team6Images/settings.png")).getImage().getScaledInstance(22, 22, java.awt.Image.SCALE_SMOOTH)));
        pane = new JLayeredPane();
        pane.setPreferredSize(new Dimension(250, 250));
        cover = new Team6Cover();
        names = new JPanel();
        options = new JPanel();
       
        names.add(cover);
        //pane.setOpaque(false);
        options.add(info);
        options.add(star);
        
        names.setBounds(0, 0, 250, 250);
        options.setBounds(50, 110, 250, 250);
       
         pane.add(names, new Integer(0));
         pane.add(options, new Integer(1));
                
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
             
            cc.setCity(input);
            map = new JLabel();
            
                try{
            String imageURL = "https://maps.googleapis.com/maps/api/staticmap?center=" + input + "&zoom=12&size=250x250";
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
                                map.setBounds(0,0,250,250);
                                
                                  //  map = new Team6Map(input);
               pane.remove(names);
               
            pane.add(map,new Integer(0) );
            //pane.setVisible(true);
            //names.setVisible(false);
           // pane.revalidate();
            add(pane);
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
                
                if(cc.getShowGhost()){
                    
                    cc.setShowGhost(false);
                    
                } else if(!cc.getShowGhost()) {
                    
                    cc.setShowGhost(true);
                }
            }
        
        });
  }
    

    @Override
    public void update(Observable o, Object arg) {

       //  (ControlCenter)o).setCity();
       //  ((ControlCenter)o).getGPS()
         ((ControlCenter)o).getShowGhost();
    

    }
    
}
