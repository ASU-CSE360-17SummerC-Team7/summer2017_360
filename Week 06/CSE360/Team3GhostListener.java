package CSE360;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Devyn Hedin
//Melissa Day
//Jonathon Proctor
//Thunpisit Amnuaikiatloet
/**
 * Created by User on 6/22/2017.
 */
public class Team3GhostListener extends Team3Ghost implements ActionListener{
    public void actionPerformed(ActionEvent e) {
        if(Team3.ghost.thread != null) {
            Team3.ghost.thread = null;
            
            Team3.ghost.setVisible(false);
            
            
        } else {
            
        	Team3.ghost.setVisible(true);
        	
        	Team3.ghost.thread = new Thread(Team3.ghost);
            Team3.ghost.thread.start();
        }
    }
}
