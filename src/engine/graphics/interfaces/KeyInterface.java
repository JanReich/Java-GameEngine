package engine.graphics.interfaces;

import java.awt.event.KeyEvent;

public interface KeyInterface extends GraphicalObject {

    void keyPressed(KeyEvent e);

    void keyReleased(KeyEvent e);
}
