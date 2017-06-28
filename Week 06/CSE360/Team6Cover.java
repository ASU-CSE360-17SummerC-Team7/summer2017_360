//Authors:  Joel Menja
//          Manuel Ucles
//          Michael Warnick
package CSE360;


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
import javax.swing.JFrame;
import javax.swing.*;
import org.json.*;



public class Team6Cover  extends JPanel{
    
    
    JButton joel = new JButton("Joel Menja");
    JButton Manny = new JButton("Manuel Ucles");
    JButton sam = new JButton("Michael warnick");
  
    Team6Cover(){
         
        setPreferredSize(new Dimension(300,150));
        setBackground(Color.WHITE);
        add(joel);
        add(Manny);
        add(sam);
    }

    
}
