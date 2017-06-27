package CSE360;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Team0 extends JPanel implements Observer {
    
  JLabel label = new JLabel("");  

  public Team0 () {
    this.setBackground(Color.GRAY);
    this.add(label);
  }
    
  @Override
  public void update(Observable o, Object arg) {
    String s = "<html>" + ((ControlCenter)o).getCity() + ", <br>";
    s = s + ((ControlCenter)o).getGPS() + ", <br>";
    s = s + ((ControlCenter)o).getShowGhost() + "</html>";
    label.setText(s);
  }

}
