
package week06;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;


public class MapPanel extends ComponentAdapter{
    private BufferedImage buffImage = null;
    private BufferedImage buffScaledImage = null;
    //private ImageIcon imageIcon;
    //private ImageIcon scaledImageIcon;
    private JPanel mapPanel;
    private JLabel jLabel;
    
    public MapPanel(){
        super();
    }
    
    public JPanel createMapPlate(){
        try{
            mapPanel = new JPanel();
            
            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?" 
                        +"center=33.416952,-111.939817&zoom=9&size=250x250&scale=1&maptype=roadmap";

            String destinationFile = "mapImage.jpg";
            String str = destinationFile;
            URL url = new URL(imageUrl);
            OutputStream os;
            
            buffImage = ImageIO.read(new File(destinationFile));
            
            //imageIcon = new ImageIcon(destinationFile);
                            
            try (InputStream is = url.openStream()) {
                os = new FileOutputStream(destinationFile);
                byte[] b = new byte[2048];
                int length;
                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }
            }
            os.close();
        } 
        catch (IOException e) {
            System.out.println("IOException, file not found!");
        }
        
        
        jLabel = new JLabel(new ImageIcon(buffImage));
                
        mapPanel.setLayout(new BorderLayout());
        mapPanel.add(jLabel, BorderLayout.CENTER);        
        mapPanel.setVisible(true);
        
        
        mapPanel.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent ce){
                System.out.println("Window changed size!!!");
                Component c = ce.getComponent();
                System.out.println("Component size: " + ce.getComponent().toString());
                System.out.println("width: " + c.getWidth());
                System.out.println("Height :" + c.getHeight());
                //scaledImage = image.getScaledInstance(c.getWidth(), c.getHeight(), Image.SCALE_SMOOTH);               
                buffScaledImage = (BufferedImage) buffImage.getScaledInstance(c.getWidth(), c.getHeight(), BufferedImage.SCALE_SMOOTH);
                //scaledImageIcon = imageIcon.
            }
        });
        return mapPanel;
    }
   
    
    
    private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

//    @Override
//    public void update(Observable o, Object arg) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
}


