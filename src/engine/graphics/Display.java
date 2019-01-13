package engine.graphics;

import engine.configSystem.DisplayConfig;
import engine.graphics.interfaces.WindowEventListener;
import engine.graphics.panels.DrawingPanel;
import engine.graphics.panels.Panel;
import engine.graphics.panels.CustomTheme;
import engine.logger.MyLogger;
import engine.toolbox.resourceHelper.FileHelper;
import engine.toolbox.resourceHelper.ImageHelper;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;

public class Display extends JFrame implements WindowListener, ComponentListener {

            //Attribute

            //Referenzen
        private Panel activePanel;
        private DisplayConfig config;
        private Dimension windowDimension;

        private ArrayList<Panel> panels;
        private ArrayList<WindowEventListener> events;

    /**
     * Im Konstruktor werden die verschiedenen Arraylists initalisiert, damit keine Nullpointer-Exeptions während der Run-Time auftreten.
     */
    public Display(DisplayConfig config) {

        this.config = config;
        this.panels = new ArrayList<>();
        this.events = new ArrayList<>();

        init();
    }

    /**
     * In der Init-Methode wird die Konfiguration für das Display erstellt.
     */
    private void init() {

        MyLogger.engineInformation("[engine] Load Display-Settings from Config...");
        addWindowListener(this);
        addComponentListener(this);

            //Attribute des JFrames festlegen
        setTitle(config.getTitle());
        setAlwaysOnTop(config.isAlwaysOnTop());

        if(config.isQuitConfirmation()) setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        else setDefaultCloseOperation(EXIT_ON_CLOSE);

        setIconImage(ImageHelper.getImage("Engine/Images/EngineIcon.png"));
        windowDimension = new Dimension(config.getWidth(), config.getHeight());

        if(config.isResizable()) {

            setPreferredSize(windowDimension);
        } else {

            setSize(windowDimension);
            setMinimumSize(windowDimension);
            setMaximumSize(windowDimension);
            setPreferredSize(windowDimension);
        }

            //Position des Displays
        if(config.isCentered()) setLocationRelativeTo(null);
        else setLocation(config.getWindowX(), config.getWindowY());

        if(config.useCustomLayout()) {

            try {

                if(config.useCustomTheme()) {

                    MetalTheme theme = new CustomTheme(FileHelper.getFile("Engine/JFrameThemes/" + config.getCustomTheme()));
                    MetalLookAndFeel.setCurrentTheme(theme);
                }
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);

                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception e) {}
        }

        if(config.useCustomCursor()) setCursor(getToolkit().createCustomCursor(ImageHelper.getImage("Engine/Images/Cursor.png"), new Point(0, 0), "Jans Cursor"));
        addDrawingPanel();



        setVisible(true);
    }

    /**
     * Erstellt ein neues Drawingpanel und setzt dieses als aktives Panel, wenn es kein aktives Panel gibt
     */
    public DrawingPanel addDrawingPanel() {

        if(config != null) {

            DrawingPanel panel = new DrawingPanel(config);
            panels.add(panel);
            if(activePanel == null) setActivePanel(panel);
            return panel;
        } else throw new NullPointerException("Die Config konnte nicht geladen werden... Um den Fehler zu beheben starte das Programm neu, damit eine neue Config erstellt werden kann!");
    }

    /**
     * In dieser Methode wird ein Panel zum aktiven Panel gesetzt. Nur das aktive
     * Panel wird auf dem Display dargestellt.
     */
    public void setActivePanel(Panel panel) {

        if(panel != null) {

            if(panel instanceof DrawingPanel) {

                if(activePanel != null) {

                    remove(activePanel);

                    activePanel = panel;
                    add(activePanel);
                    revalidate();
                } else {

                    add(panel);
                    activePanel = panel;
                }
            } else {

                //TODO: Andere panels
            }
        }
    }

    /**
     * In dieser Methode wird ein bereits existierendes Panel vom Display entfernt
     */
    public void removePanel(Panel panel) {

        if(panels.contains(panel)) {

            if(panel.equals(activePanel)) {

                activePanel = null;
                remove(panel);
                panels.remove(panel);
            } else {

                panels.remove(activePanel);
            }
        } else throw new IllegalArgumentException("Du kannst nur panels entfernen, die du vorher hinzugefügt hast!");
    }

    /**
     * Diese Methode returnt das active Panel, dass aktuell auf dem Display gezeichnet wird.
     */
    public DrawingPanel getActivePanel() {

        if(activePanel instanceof DrawingPanel)
            return (DrawingPanel) activePanel;
        else System.err.println("[ERROR] Das Active Panel ist kein Drawing Panel");
        return null;
    }

    public void addWindowListener(WindowEventListener eventListener) {

        SwingUtilities.invokeLater(() -> events.add(eventListener));
    }

    public void removeWindowListener(WindowEventListener eventListener) {

        SwingUtilities.invokeLater(() -> events.add(eventListener));
    }

    @Override
    public void windowOpened(WindowEvent e) {

            //Event wird beim öffenen des Windows aufgeführt
        Iterator<WindowEventListener> iterator = events.iterator();
        while (iterator.hasNext()) {

            WindowEventListener tempObject = iterator.next();
            tempObject.windowOpened(e);
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {

            //Event wird beim schließen des Windows aufgeführt
        Iterator<WindowEventListener> iterator = events.iterator();
        while (iterator.hasNext()) {

            WindowEventListener tempObject = iterator.next();
            tempObject.windowClosing(e);
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {

        //Event wird ausgeführt, wenn das Window aktiv ist
        Iterator<WindowEventListener> iterator = events.iterator();
        while (iterator.hasNext()) {

            WindowEventListener tempObject = iterator.next();
            tempObject.windowActivated(e);
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

        //Event wird ausgeführt, wenn das Window inaktiv wird
        Iterator<WindowEventListener> iterator = events.iterator();
        while (iterator.hasNext()) {

            WindowEventListener tempObject = iterator.next();
            tempObject.windowDeactivated(e);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {

        if(!config.isResizable())
            setSize(windowDimension);
    }

    /**
     * Event wird in dem Framework nicht benutzt
     */
    @Override
    public void componentMoved(ComponentEvent e) {

    }

    /**
     * Event wird in dem Framework nicht benutzt
     */
    @Override
    public void componentShown(ComponentEvent e) {

    }

    /**
     * Event wird in dem Framework nicht benutzt
     */
    @Override
    public void componentHidden(ComponentEvent e) {

    }

    /**
     * Event wird in dem Framework nicht benutzt
     */
    @Override
    public void windowClosed(WindowEvent e) {

    }

    /**
     * Event wird in dem Framework nicht benutzt
     */
    @Override
    public void windowIconified(WindowEvent e) {

    }

    /**
     * Event wird in dem Framework nicht benutzt
     */
    @Override
    public void windowDeiconified(WindowEvent e) {

    }
}