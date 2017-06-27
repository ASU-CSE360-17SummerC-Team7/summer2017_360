
package CSE360;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by User on 6/22/2017.
 */
public class Team3Listener implements ActionListener {
    
    
    private int i = 0;
    public void actionPerformed(ActionEvent e) {
        
        
        if (i % 2 == 0)
        {
        Team3.cover.setVisible(false);
        Team3.cover.revalidate();
        }
        
        else
        {
            Team3.cover.setVisible(true);
            Team3.cover.revalidate();
        }
        
        i++;
    }
}
