package CSE360;

import javax.swing.*;

/**
 * Created by User on 6/21/2017.
 */
public class Team3Cover extends JPanel {
    private JRadioButton b1, b2, b3;
    private JLabel label;
    public Team3Cover() {

        label = new JLabel("CSE360 Team 3");
        b1 = new JRadioButton("CSE 360");
        b2 = new JRadioButton("Team 3");
        b3 = new JRadioButton("Awesome!");

        add(b1);
        add(b2);
        //add(b3);
    }

    private JLabel JLabel(String csE360_Team_3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}