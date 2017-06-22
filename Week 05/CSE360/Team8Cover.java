package CSE360;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.*;

class Team8Cover extends JPanel {

    private String[] names = {"Michael", "Yaqoub", "Amit", "Bahar"};
    private JTextArea text;

    public Team8Cover() {
        init();
        addComponents();
    }

    private void init() {

        int panelW = 250, panelH = 125;
        Font font;
        int fontSize = 22;
        String fontKind = "Arial";

        this.setBackground(Color.CYAN);
        //this.setLayout(new BorderLayout());
        this.setSize(panelW, panelH);
        //this.setSize(250,125);
        text = new JTextArea(names[0] + "\n" + names[1] + "\n" + names[2] + "\n" + names[3]);
        //text.setFont(text.getFont().deriveFont((this.getWidth()+this.getHeight())/15f));
        font = new Font(fontKind, Font.BOLD | Font.ITALIC, fontSize);
        text.setFont(font);
        text.setBackground(Color.CYAN);
        text.setEditable(false);
    }

    private void addComponents() {
        this.add(text);
        this.addComponentListener(new ComponentListener() {

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentResized(ComponentEvent e) {
                text.setFont(text.getFont().deriveFont((e.getComponent().getWidth() + e.getComponent().getHeight()) / 15f));
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
    }
}
