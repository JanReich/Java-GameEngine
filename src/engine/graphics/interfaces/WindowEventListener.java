package Engine.Graphics.Interfaces;

import java.awt.event.WindowEvent;

public interface WindowEventListener {

    void windowOpened(WindowEvent e);

    void windowClosing(WindowEvent e);

    void windowActivated(WindowEvent e);

    void windowDeactivated(WindowEvent e);
}
