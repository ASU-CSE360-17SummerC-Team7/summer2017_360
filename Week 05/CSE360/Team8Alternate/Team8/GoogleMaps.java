package CSE360;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class GoogleMaps {

    private ImageIcon img;

    public GoogleMaps(double latitude, double longitude, int width, int height) {

        String api_url = "https://maps.googleapis.com/maps/api/staticmap?";
        String api_key = "AIzaSyCPBkB4FfvXzIYRgbRBmAMxqXLhnaGJVXU";
        String zoom = "zoom=9&";
        String size = "size=" + width + "x" + height + "&";
        String scale = "scale=1&";
        String mapType = "maptype=roadmap&";

        String center = "center=" + latitude + "," + longitude + "&";

        api_url = api_url + center + zoom + size + scale + mapType + "key=" + api_key;

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
