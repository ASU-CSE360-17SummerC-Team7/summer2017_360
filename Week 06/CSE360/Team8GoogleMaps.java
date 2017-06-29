package CSE360;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/*
   The GoogleMaps class is responsible for retrieving data from a GoogleMaps API call.
   The image returned is stored within an ImageIcon object.
*/

public class Team8GoogleMaps {

    private ImageIcon img;
    private String key = "AIzaSyCPBkB4FfvXzIYRgbRBmAMxqXLhnaGJVXU";

    //Creating an object of this class will also initialize its ImageIcon member.
    public Team8GoogleMaps(double latitude, double longitude, int width, int height) {

        //These variables will determine the properties of the image requested.
        String api_url = "https://maps.googleapis.com/maps/api/staticmap?";
        String api_key = this.key;
        String zoom = "zoom=9&";
        String size = "size=" + width + "x" + height + "&";
        String scale = "scale=1&";
        String mapType = "maptype=roadmap&";
        String center = "center=" + latitude + "," + longitude + "&";

        api_url = api_url + center + zoom + size + scale + mapType + "key=" + api_key;

        //Retrieve the image and store it.
        try {
            URL url = new URL(api_url);
            BufferedImage bufferedImage = ImageIO.read(url);
            img = new ImageIcon(bufferedImage);
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public ImageIcon getImage() {
        return img;
    }
}
