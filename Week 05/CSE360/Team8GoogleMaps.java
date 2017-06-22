package CSE360;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Team8GoogleMaps {
	private String api_url = "https://maps.googleapis.com/maps/api/staticmap?";
	private String api_key = "AIzaSyCPBkB4FfvXzIYRgbRBmAMxqXLhnaGJVXU";
	private String zoom = "zoom=11&";
	private String size = "size=612x612&";
	private String scale = "scale=1&";
	private String mapType = "maptype=roadmap&";
	ImageIcon img;

	Team8GoogleMaps(double latitude, double longitude) {

		String center = "center=" + latitude +"," + longitude + "&";

		api_url = api_url + center + zoom + size + scale + mapType + "key=" + api_key;

		try {
			URL url = new URL(api_url);
			BufferedImage bufimg = ImageIO.read(url);
			img = new ImageIcon(bufimg);
		} catch (IOException e) {
			System.exit(1);
		}
	}

	public ImageIcon getImage() {
		return img;
	}

}
