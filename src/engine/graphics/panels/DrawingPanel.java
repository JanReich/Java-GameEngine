package engine.graphics.panels;

import engine.configSystem.DisplayConfig;
import engine.graphics.interfaces.*;
import engine.logger.MyLogger;
import engine.toolbox.resourceHelper.DrawHelper;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DrawingPanel extends Panel implements KeyListener, MouseListener, MouseMotionListener {

            //Attribute

            //Referenzen
        private Timer timer;
        private DisplayConfig config;
        private DrawHelper drawHelper;

        private LinkedHashMap<GraphicalObject, Integer> graphicalObjects;
        private ArrayList<TimeBasedObject> timeBasedObjects;

    public DrawingPanel(DisplayConfig config) {

        super();
        this.config = config;

        this.timeBasedObjects = new ArrayList<>();
        this.graphicalObjects = new LinkedHashMap<>();

        setDoubleBuffered(true);
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {

            //Listener werden zum Start einmalig dem Panel hinzugefügt.
        if(!requested) {

            addKeyListener(this);
            addMouseListener(this);
            addMouseMotionListener(this);

            requestFocusInWindow();
            requested = !requested;
        }

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

            //graphics setzen oder updaten
        if(!(drawHelper != null)) drawHelper = new DrawHelper(g2d);
        else drawHelper.updateGraphics(g2d);

        drawHelper.setScreenWidth(getWidth());
        drawHelper.setScreenHeight(getHeight());

            //ANTIALIASING einschalten
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //Updating Frame
        if(graphicalObjects != null && timeBasedObjects != null) {

            for(Map.Entry<GraphicalObject, Integer> entrySet : graphicalObjects.entrySet()) {

                if(entrySet.getValue() != null) {

                    entrySet.getKey().draw(drawHelper);
                } else System.err.println("[Error] Es wird versucht ein Objekt == null zuzeichnen");
            }

            graphicalObjects = sortHashMap(graphicalObjects);

            for(TimeBasedObject obj : timeBasedObjects) {

                obj.update(delta / 1000);
            }
        }


        //FPS-Settings
        frames++;
        currentFrame = System.currentTimeMillis();
        if(currentFrame > firstFrame + 1000) {

            firstFrame = currentFrame;
            fps = frames;
            frames = 0;
        }

        if(config.isShowFPS()) {

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("", Font.BOLD, 24));
            g2d.drawString("FPS: " + fps, 5, 24);
        }
    }

    /**
     * Diese Methode sotiert alle Objekte die aktuell gezeichnet werden nach ihrem Z-Index.
     */
    public LinkedHashMap<GraphicalObject, Integer> sortHashMap(Map<GraphicalObject, Integer> unsorted) {

        Object[] objects = unsorted.entrySet().toArray();
        Arrays.sort(objects, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<GraphicalObject, Integer>) o1).getValue().compareTo(((Map.Entry<GraphicalObject, Integer>) o2).getValue());
            }
        });

        LinkedHashMap<GraphicalObject, Integer> map = new LinkedHashMap<>();
        for (Object e : objects)
            map.put(((Map.Entry<GraphicalObject, Integer>) e).getKey(), ((Map.Entry<GraphicalObject, Integer>) e).getValue());

        return map;
    }

        //GraphicalObject
    public void drawObjectOnPanel(GraphicalObject object) {

        if(object != null)
            this.drawObjectOnPanel(object, 1);
    }

    public void drawObjectOnPanel(GraphicalObject object, int zIndex) {

        if(object != null)
            SwingUtilities.invokeLater(() -> graphicalObjects.put(object, zIndex));
    }

        //Graphical and Timebased Object
    public void drawTimebasedObject(Object object) {

        drawTimebasedObject(object, 1);
    }

    public void drawTimebasedObject(Object object, int index) {

        if(object != null) {
            if (object instanceof GraphicalObject && object instanceof TimeBasedObject) {

                SwingUtilities.invokeLater(() -> timeBasedObjects.add((TimeBasedObject) object));
                SwingUtilities.invokeLater(() -> graphicalObjects.put((GraphicalObject) object, index));
            } else MyLogger.warn("[engine] Das Objekt ist nicht gleichzeitig GraphicalObject und TimebasedObject - Fehler beim hinzufügen zum Drawingpanel");
        } else MyLogger.warn("[engine] Es wird versucht ein Object == null zum Drawingpannel hinzuzufügen.");
    }

        //TimebasedObject
    public void addTimebasedObject(TimeBasedObject object) {

        if(object != null)
            SwingUtilities.invokeLater(() -> timeBasedObjects.add(object));
    }

    public void removeObjectFromPanel(Object object) {

        if(object != null) {

            if(object instanceof RemovableObject)
                ((RemovableObject) object).remove();

            if(object instanceof GraphicalObject)
                SwingUtilities.invokeLater(() -> graphicalObjects.remove(object));

            if(object instanceof TimeBasedObject)
                SwingUtilities.invokeLater(() -> timeBasedObjects.remove(object));
        }
    }


    public boolean containsObject(Object object) {

        if(object instanceof GraphicalObject)
            return graphicalObjects.containsKey(object);

        else if(object instanceof TimeBasedObject)
            return timeBasedObjects.contains(object);

        else return false;
    }

    public void removeAllObjectsFromPanel() {

        if(graphicalObjects != null)
            graphicalObjects.clear();

        if(timeBasedObjects != null)
            timeBasedObjects.clear();
    }

    public int getZIndex(GraphicalObject object) {

        if(object != null)
            return graphicalObjects.get(object);
        else return -1;
    }



    @Override
    public void keyPressed(KeyEvent e) {

        for(Map.Entry<GraphicalObject, Integer> entry : graphicalObjects.entrySet()) {

            GraphicalObject obj = entry.getKey();
            if(obj instanceof KeyInterface)
                ((KeyInterface) obj).keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        for(Map.Entry<GraphicalObject, Integer> entry : graphicalObjects.entrySet()) {

            GraphicalObject obj = entry.getKey();
            if(obj instanceof KeyInterface)
                ((KeyInterface) obj).keyReleased(e);
        }
    }




    @Override
    public void mouseReleased(MouseEvent e) {

        for(Map.Entry<GraphicalObject, Integer> entry : graphicalObjects.entrySet()) {

            GraphicalObject obj = entry.getKey();
            if(obj instanceof MouseInterface)
                ((MouseInterface) obj).mouseReleased(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        for(Map.Entry<GraphicalObject, Integer> entry : graphicalObjects.entrySet()) {

            GraphicalObject obj = entry.getKey();
            if(obj instanceof AdvancedMouseInterface)
                ((AdvancedMouseInterface) obj).mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        for(Map.Entry<GraphicalObject, Integer> entry : graphicalObjects.entrySet()) {

            GraphicalObject obj = entry.getKey();
            if(obj instanceof AdvancedMouseInterface)
                ((AdvancedMouseInterface) obj).mouseMoved(e);
        }
    }





    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
