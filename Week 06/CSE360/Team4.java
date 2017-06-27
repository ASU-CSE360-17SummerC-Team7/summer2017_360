
package CSE360;



import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

public class Team4 extends JPanel implements Observer{
    private static JPanel testPanel;
    private String gps_coords = "33.416952,-111.939817";
    private MapPanel mp;
    
    public Team4() throws IOException{        
        System.out.println("New Team4 object created");
        testPanel = new JPanel();       
        mp = new MapPanel();
        BackgroundPanel.runGhost();
        testPanel = BackgroundPanel.setGhost(mp.createMapPlate(gps_coords));
        this.add(testPanel);
    }
    
    public static JPanel getTestPanel() {
    	return testPanel;
    }

    @Override
    public void update(Observable o, Object arg) {
        MapPanel thisMapPanel = new MapPanel();
        try {
            Team4 thisTeam = new Team4();
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
        JPanel test = null;
        System.out.println("Update called");
        String coordinates = (String) arg;
        System.out.println("The coordinates are: " + coordinates);
        try {
             this.removeAll();
             this.revalidate();

            testPanel = BackgroundPanel.setGhost(mp.createMapPlate(coordinates));
            this.add(testPanel);
            this.repaint();
        } catch (IOException ex) {
            System.out.println("IOException");
        }

    }
    
    
    
}
