package engine.graphics;

import engine.configSystem.DisplayConfig;
import engine.graphics.interfaces.WindowEventListener;
import engine.logger.MyLogger;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class EventListener implements WindowEventListener {

            //Attribute

            //Referenzen
        private Display display;
        private DisplayConfig config;

    public EventListener(Display display, DisplayConfig config) {

        MyLogger.engineInformation("[engine] EventListener wird geladen...");
        this.config = config;
        this.display = display;
        display.addWindowListener(this);
        MyLogger.engineInformation("[engine] EventLister: Waiting for events...");
    }

    @Override
    public void windowOpened(WindowEvent e) {

        //Event wird beim öffenen des Windows aufgeführt
    }

    @Override
    public void windowClosing(WindowEvent e) {

        if(config.isQuitConfirmation()) {

            int n = JOptionPane.showConfirmDialog(display, config.getQuitMessage(), config.getProgrammTitle(), JOptionPane.YES_NO_OPTION);

            if (n == 0) {

                MyLogger.engineInformation("[engine] Das Programm wurde beendet.");
                System.exit(0);
            }
        } else System.exit(0);
    }

    @Override
    public void windowActivated(WindowEvent e) {

        //Event wird ausgeführt, wenn das Window aktiv ist
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

        //Event wird ausgeführt, wenn das Window inaktiv wird
    }
}
