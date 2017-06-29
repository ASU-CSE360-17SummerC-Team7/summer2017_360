package CSE360;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/*
    This is the cover panel.
    To be displayed initially and then toggled on or off.
*/

class Team8Cover extends JPanel {

    //Configure the Cover Panel
    public Team8Cover(int height, int width) {

        int fontSize = 22;
        String fontType = "Arial";
        String[] names = {"Michael", "Yaqoub", "Amit", "Bahar"};

        //Set font properties.
        Font font = new Font(fontType, Font.BOLD | Font.ITALIC, fontSize);

        //Set text component properties.
        JTextArea text = new JTextArea(names[0] + "\n" + names[1] + "\n" + names[2] + "\n" + names[3]);
        text.setFont(font);
        text.setBackground(Color.CYAN);
        text.setEditable(false);

        //Set panel properties.
        this.setBackground(Color.CYAN);
        this.setSize(width, height);

        //Add panel components.
        this.add(text);
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentResized(ComponentEvent e) {
                text.setFont(text.getFont().deriveFont((e.getComponent().getWidth() + e.getComponent().getHeight()) / 15f));
            }
            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });
    }
}
