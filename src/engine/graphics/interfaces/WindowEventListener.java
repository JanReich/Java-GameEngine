package engine.graphics.interfaces;

import java.awt.event.WindowEvent;

public interface WindowEventListener {

  void windowOpened(final WindowEvent e);

  void windowClosing(final WindowEvent e);

  void windowActivated(final WindowEvent e);

  void windowDeactivated(final WindowEvent e);
}
