
package week06;



import javax.swing.JPanel;

public class Team4 extends JPanel{
    private JPanel testPanel;
    
    public Team4(){        
        System.out.println("New Team4 object created");
        testPanel = new JPanel();       
        MapPanel mp = new MapPanel();
        testPanel = mp.createMapPlate();
        this.add(testPanel);
    }
    
}
