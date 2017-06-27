package CSE360;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Image.*;
//Devyn Hedin
//Melissa Day
//Jonathon Proctor
//Thunpisit Amnuaikiatloet
/**
 * Created by User on 6/21/2017.
 */
public class Team3Map extends JPanel{

    public Team3Map(String coord1, String coord2) {
        renderMap(coord1, coord2);
    }
    //Adapted from the main method of the ExampleGoogleMaps class
    private void  renderMap(String coord1, String coord2){
        try {
            // c1 and c2 are used in URL to pull a specific map from Google
            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + coord1 + "," + coord2 + "&zoom=15&size=912x912&scale=2&maptype=roadmap";
            System.out.println(coord1 + " , " + coord2);
            //Image is saved as image.jpg in local folder
            String destinationFile = "team3.jpg";
            String str = destinationFile;
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();

            add(new JLabel(new ImageIcon((new ImageIcon("team3.jpg")).getImage().getScaledInstance(250, 250,
                    java.awt.Image.SCALE_SMOOTH))));
        } catch (IOException e) {
            System.exit(2);
        }
    }
}
