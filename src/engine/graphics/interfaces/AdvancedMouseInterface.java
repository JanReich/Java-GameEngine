package engine.graphics.interfaces;

import java.awt.event.MouseEvent;

public interface AdvancedMouseInterface extends MouseInterface {

  void mouseMoved(final MouseEvent e);

  void mouseDragged(final MouseEvent e);
}
