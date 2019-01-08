package Engine.Graphics.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Panel extends JPanel implements ActionListener {

            //Attribute
        protected int fps;
        protected int frames;
        protected double delta;

        protected long lastLoop;
        protected long firstFrame;
        protected long elapsedTime;
        protected long currentFrame;

        protected boolean requested;

            //Referenzen

    public Panel() {

        super();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        elapsedTime = System.nanoTime() - lastLoop;
        lastLoop = System.nanoTime();
        delta = (int) ((elapsedTime / 1000000L) + 0.5);
        if ( delta == 0 ) delta = 1;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
    }
}
