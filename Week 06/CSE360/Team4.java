

package CSE360;



import java.awt.Color;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Team4 extends JPanel implements Observer{
    private static JPanel testPanel = new JPanel();
    private String gps_coords = "33.416952,-111.939817";
    private MapPanel mp = new MapPanel();
    String currentCity = "";
    
    String newCity = "";
   
    JLabel label = new JLabel("");  

  public Team4 () {
    InitialPanel ip = new InitialPanel();

    this.add(ip);
    BackgroundPanel.runGhost();
    
  }
    
    public static JPanel getTestPanel() {
    	return testPanel;
    }


    
    @Override
  public void update(Observable o, Object arg) {
    
    String s = "<html>" + ((ControlCenter)o).getCity() + ", <br>";
    s = s + ((ControlCenter)o).getGPS() + ", <br>";
    newCity = ((ControlCenter)o).getCity();
    String coords = ((ControlCenter)o).getGPS();
    
    s = s + ((ControlCenter)o).getShowGhost() + "</html>";
    boolean ghostState = ((ControlCenter)o).getShowGhost();
        System.out.println("ghostState : " + ghostState);


        
        if (newCity != currentCity){
            testPanel.removeAll();
            testPanel.revalidate();
            testPanel.repaint();
        }
        
        BackgroundPanel.runGhost();
        try {
            testPanel = BackgroundPanel.setGhost(mp.createMapPlate(coords));
        } catch (IOException ex) {
            System.out.println("IOException");
        }
        this.removeAll();
        this.revalidate();
        this.repaint();
        this.add(testPanel);
        
        currentCity = newCity;

        if (ghostState == true) {
            BackgroundPanel.removeGhost(Team4.getTestPanel());
        }

        else if(ghostState == false) {
            BackgroundPanel.showGhost(Team4.getTestPanel());
        }
  }
}



