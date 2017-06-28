package CSE360;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Universe extends JFrame implements ActionListener {

    ControlCenter cc = ControlCenter.getInstance(); 
    Team5 t1 = new Team5();
    Team5 t2 = new Team5();
    Team5 t3 = new Team5();
    Team5 t4 = new Team5();
    Team5 t5 = new Team5();
    Team5 t6 = new Team5();
    Team5 t7 = new Team5();
    Team5 t8 = new Team5();
    Team5 t9 = new Team5();
    JButton b1 = new JButton("select a city");
    JButton b2 = new JButton("enable/disable ghost");
    
    public Universe ()  {     
     // observer-observable
    cc.addObserver(t1); 
    cc.addObserver(t2); 
    cc.addObserver(t3); 
    cc.addObserver(t4); 
    cc.addObserver(t5); 
    cc.addObserver(t6); 
    cc.addObserver(t7); 
    cc.addObserver(t8); 
     cc.addObserver(t9);      
     // center
     JPanel center = new JPanel();       
     center.setLayout(new GridLayout (3,3));
     center.add(t1);
     center.add(t2);
     center.add(t3);
     center.add(t4);
     center.add(t5);
     center.add(t6);
     center.add(t7);
     center.add(t8);
     center.add(t9);     
     // status bar
     b1.addActionListener(this);
     b2.addActionListener(this);
     JPanel bar = new JPanel();       
     bar.setLayout(new GridLayout (1,2));
     bar.add(b1);
     bar.add(b2);
     // assembly
     setLayout(new BorderLayout());   
     add(center, BorderLayout.CENTER);
     add(bar, BorderLayout.SOUTH);     
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource()==b1) {
        String message = "Select a location: ";
        String title = "Location";
        String input = (String)JOptionPane.showInputDialog(this, message, title, 
                JOptionPane.QUESTION_MESSAGE, null, ControlCenter.CITIES, ControlCenter.CITIES[0]);
        cc.setCity(input);
        cc.notifyObservers();
       } else {
         cc.setShowGhost(!cc.getShowGhost());
         cc.notifyObservers();
       }
         
    }

    public static void main(String[] args) {
      Universe u = new Universe();
      u.setTitle("Week 06");
      u.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      u.setSize(500, 500);
      u.setVisible(true);
    }

}