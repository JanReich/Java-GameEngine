package Engine.Graphics.Interfaces;

import java.awt.event.KeyEvent;

public interface KeyInterface extends GraphicalObject {

    void keyPressed(KeyEvent e);

    void keyReleased(KeyEvent e);
}
