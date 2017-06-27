package CSE360;

/*
 * Team 5
 * @author Melissa Day
 * @author Austin McCleary
 * @author Zelin Bao
 * @author Yuxue Zhou
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

import org.json.JSONException;
import org.json.JSONObject;

public class Team5 extends JPanel implements Observer {
	
	boolean ghostAppear = true;
	String lnglat = "33.424564,-111.928001";
	Team5ghost Ghost;
	
	static int baseheight = 150;
	static int basewidth = 200;
	
	static int secondheight = 150;
	static int secondwidth = 200;

	
	
	int zoom = 0;
	
	JPanel main;
	JPanel extra;
	JPanel buttonPanel;
	//JButton button;
	JButton button,ghostAppearing,button2,setting;
	JPanel temp;
	String temperature;
	String degrees;
	JLabel label1 = new JLabel("Team 5:");
	JLabel label2 = new JLabel("Austin McCleary,");
	JLabel label3 = new JLabel("Yuxue Zhou,");
	JLabel label4 = new JLabel("Zelin Bao");
	
	

	public Team5() {
		// Creates main panel to put two panels into
		main = new JPanel();
	   
		
		
		// Creates extra panel to put all data and button into
		extra = new JPanel();
		
		//main.setLayout(null);///////////
		//extra.setLayout(null);///////////
		
		
		//main.setBounds(0, 0, basewidth, baseheight);
		
		//extra.setBounds(0, 0, basewidth, baseheight);
		// Adds weather and google map panel together
		Weather();
	    
		extra.add(Google());
		Google().setBounds(0, 0, basewidth, baseheight);
		
		extra.setOpaque(true);
		// Adds Weather and Google panels to the main panel
		main.add(extra);
		extra.setBounds(0, 0, basewidth, baseheight);
		
		
		
		
		//adding the 2 new buttons here
		button = new JButton();
		button.setPreferredSize(new Dimension(90, 50));
		button.setOpaque(false);
	
		 button.setIcon( new ImageIcon("/Users/zelinbao/Desktop/information.png"));
		
		ghostAppearing = new JButton();
		ghostAppearing.setPreferredSize(new Dimension(90, 50));
		ghostAppearing.setOpaque(false);
	
		ghostAppearing.setIcon(new ImageIcon("/Users/zelinbao/Desktop/setting.png"));
		
		
		/*
		extra.add(button);
		extra.add(ghostAppearing);
         
*/
		buttonPanel = new JPanel();
		//buttonPanel.setLayout(null);
		
		
		
		buttonPanel.setBounds(0, 0, basewidth, baseheight);
		buttonPanel.add(button);
		buttonPanel.add(ghostAppearing);
		buttonPanel.setVisible(true);
		buttonPanel.setOpaque(false);
		
		
		// This is the button turns on and off the ghost
		ghostAppearing.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (ghostAppear==true)
				{
					Ghost.setVisible(false);
					ghostAppear=false;
				}
				else
				{
					Ghost.setVisible(true);
					ghostAppear=true;
				}
			}
		});
		
		// when click this button, it brings up the initial panel with the button and image. when you click
				// the button on the initial panel, dialog box pops up and choose a city
		
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (e.getSource() == button) {

					
					// set font & size
					repaint();
					label1.setFont(new Font("Rockwell", 1, 20));
					add(label1);
					add(label2);
					add(label3);
					add(label4);
					
					setting = new JButton();
					
					setting.setIcon(new ImageIcon("/Users/zelinbao/Desktop/setting.png"));
					
					setting.setPreferredSize(new Dimension(90, 50));
					setting.addActionListener(this);
					setting.setVisible(true);
					add(setting);
					repaint();
					//setting.setBounds(150, 70, 100, 100);

					main.setLayout(new FlowLayout());
					//main.setBounds(125, 80, 166, 206);
					//main.setBounds(0, 0, 500, 400);
					main.setVisible(true);
					main.removeAll();
					extra.removeAll();
					main.setBorder(null);
					//add(new Team5City());	//if the initial panel is showing next to map or on top of map
					//repaint();
					
					
					
					setting.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e) 
						{
							
							main.removeAll();
							extra.removeAll();
							main.setBorder(null);
							extra.add(Google());
							Weather();
							
							extra.add(button);
							extra.add(ghostAppearing);
							main.add(extra);
							main.setVisible(true);
							extra.setVisible(true);
							
							/*
							main.removeAll();
							extra.removeAll();
							main.setBorder(null);
							add(new Team5City());
							*/
							revalidate();
							//main.removeAll();
							repaint();
						}
					});
				}
			}
		});
	
		
		
		
		
		
	
		main.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		
		main.setOpaque(false);
		main.setVisible(true);
		
		// trying to add the degree on the panel as  well as the  ghost
		JLabel temperature = new JLabel(degrees);
		
		//System.out.println(temperature);
		//temp = new JPanel(new BorderLayout());
		temp = new JPanel();
		temp.add(temperature);
		temp.setVisible(true);
		
		
		
		
		
		
		
		
		
		
		//add ghost
		Ghost = new Team5ghost();
		Ghost.setBounds(0, 0, basewidth, baseheight);
		//main.add(Ghost);
		Ghost.setVisible(true);
		//Ghost.setOpaque(true);
		
		JLayeredPane layers = new JLayeredPane();
    	layers.setLayout(new BorderLayout());
    	
    	
    	
    	layers.add(Ghost);
    	layers.add(temp);
    	layers.add(buttonPanel);
    	
    	layers.add(main);
    	
		add(layers);

		
		
	}


	public JPanel Weather() {
		// New Panel weather
		JPanel weather = new JPanel();
		//weather.setPreferredSize(new Dimension(secondwidth/2, secondheight/2));      
		//weather.setBounds(0, 0, secondwidth, secondheight);
		
		//SetInput();  project6
		
		try {
			
			JSONObject json = readJsonFromUrl(
					"https://api.darksky.net/forecast/f657e7aed849b4520c258bb7bd2f093c/" + lnglat);
					
			
			System.out.println(json.getJSONObject("currently").getString("summary"));
			JLabel j1 = new JLabel("visibility:");
			String n0 = json.getJSONObject("currently").getString("summary");
			String n1 = "Visibility: " + json.getJSONObject("currently").getDouble("visibility");
			String n2 = "Humidity: " + json.getJSONObject("currently").getDouble("humidity");
			String n3 = "Temperature: " + json.getJSONObject("currently").getDouble("apparentTemperature");
			String n4 = "WindSpeed: " + json.getJSONObject("currently").getDouble("windSpeed");
			String n5 = "CloudCover: " + json.getJSONObject("currently").getDouble("cloudCover");
			String strMsg = "<html><body>" + "Summary: " + n0 + "<br>" + n3 + "<br>" + n2 + "<br>" + n1 + "<br>" + n4
					+ "<br>" + n5 + "<body></html>";
			j1.setText(strMsg);
			weather.add(j1);
			
String degrees =Double.toString(json.getJSONObject("currently").getDouble("apparentTemperature"));
			
			System.out.println(degrees);
			temperature = degrees;
			
			
			
			
		} catch (IOException e) {
			System.exit(1);
		} catch (JSONException e) {
			System.exit(2);
		}
		// Add Border
		weather.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		// Set Background
		weather.setBackground(Color.white);
		// Returns JPanel
		weather.setOpaque(false);
		return weather;
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public JPanel Google() {
		// New panel map
		JPanel map = new JPanel();
		
		
		// Used if you want to have user input
		Scanner reader = new Scanner(System.in);
		try {
			
			//New project6
			String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?" + "center=" + lnglat + "&zoom="
					+ zoom + "&size=640x640&maptype=road";
			

			String destinationFile = "image.jpg";
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
			reader.close();
		} catch (IOException e) {
			System.exit(1);
		}
		map.setPreferredSize(new Dimension(secondwidth,secondheight));
		map.setBounds(0, 0, secondwidth, secondheight);
		// Adds map to map Panel
		/*
		map.add(new JLabel(new ImageIcon(
				(new ImageIcon("image.jpg")).getImage().getScaledInstance(175, 175, java.awt.Image.SCALE_SMOOTH))));
		
		*/
		JLabel labelMap = new JLabel(new ImageIcon(
				(new ImageIcon("image.jpg")).getImage().getScaledInstance(secondheight, secondwidth, 0))); 
		
		labelMap.setOpaque(false);
		map.add(new JLabel(temperature));
		map.add(labelMap);
		// Returns map panel
		
		map.setOpaque(false);
		return map;
	}
	
	
	private void rebuildWM() {
		extra.removeAll();
		
		extra.add(Weather());
		extra.add(Google());
		

		main.revalidate();
		main.repaint();
	}

	
	
	

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	if(lnglat != ControlCenter.getInstance().getGPS()){
		
	     lnglat  = ControlCenter.getInstance().getGPS();
	     rebuildWM();
	
	}else if(ghostAppear != ControlCenter.getInstance().getShowGhost()){
		
			                if(ghostAppear == false){
			                	
			                	Ghost.setVisible(true);
			                	ghostAppear = true;
			                }else{
			                	
			                	Ghost.setVisible(false);
			                	ghostAppear = false;
			                }
			        
		}
	}
}