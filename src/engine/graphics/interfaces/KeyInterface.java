package engine.graphics.interfaces;

import java.awt.event.KeyEvent;

public interface KeyInterface extends GraphicalObject {

  void keyPressed(final KeyEvent e);

  void keyReleased(final KeyEvent e);
}
