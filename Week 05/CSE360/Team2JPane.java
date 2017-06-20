
package CSE360;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JLabel;

/*
CSE360 Summer 2017
Kyle Sun
Jingyi Li
Lin Sun
*/
// Nested class for that performs similar to JLabel save for the addition of a gradient background.
public class Team2JPane extends JLabel {
    public Team2JPane() {
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        LinearGradientPaint lgp = new LinearGradientPaint(
        new Point(0, 0),
        new Point(0, getHeight()),
        new float[]{0.1f, 0.3f, 0.9f},
        new Color[]{new Color(220, 220, 255), new Color(150, 150, 210), new Color(40, 40, 100)});
        g2d.setPaint(lgp);
        g2d.fill(new Rectangle(0, 0, getWidth(), getHeight()));
        g2d.dispose();
        super.paintComponent(g);
    }
}