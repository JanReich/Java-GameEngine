package Engine.Physik;

import Engine.Graphics.Display;
import Engine.Graphics.Interfaces.TimeBasedObject;
import Engine.Logger.MyLogger;
import Engine.Physik.PhysicalObjects.PhysicalObject;
import Engine.Physik.PhysicalObjects.Rectangle;

import javax.swing.*;
import java.util.ArrayList;

public abstract class PhysicalWorld implements TimeBasedObject {

            //Attribute

            //Referenzen
        private Display display;

        private ArrayList<PhysicalObject> staticObjects;
        private ArrayList<PhysicalObject> physicalObjects;

        private ArrayList<PhysicalObject> disappearedStaticObjects;
        private ArrayList<PhysicalObject> disappearedPhysicalObjects;

    public PhysicalWorld(Display display) {

        MyLogger.engineInformation("[Engine] Physikalische Umgebung wurde gestartet...");

        this.display = display;

        staticObjects = new ArrayList<>();
        physicalObjects = new ArrayList<>();

        disappearedStaticObjects = new ArrayList<>();
        disappearedPhysicalObjects = new ArrayList<>();
    }

    /**
     * Diese Methode wird aufgerufen, wenn eine Kollision zwischen einem StaticObject und einem
     * PhysicalObject auftritt.
     */
    public abstract void onCollisionPhysicalStatic(PhysicalObject physicalObject, PhysicalObject staticObject);

    /**
     * Diese Methode wird aufgerufen, wenn eine Kollision zwischen Physikalischen Objekten stattfindet
     */
    public abstract void onCollisionBetweenPhysicalObjects(PhysicalObject pObject1, PhysicalObject pObject2);

    /**
     * Diese Methode wird aufgerufen, wenn ein Objekt in den sichtbaren
     * Bereich zurückkehrt.
     */
    public abstract void onAppearOnDisplay(PhysicalObject object);

    /**
     * Diese Methode wird aufgerufen, wenn ein Objekt den sichtbaren
     * Bereich verlässt.
     */
    public abstract void onDisappearFromDisplay(PhysicalObject object);

    /**
     * In dieser Methode werden die verschiedenen ArrayListen überprüft und
     * gegebenenfalls Elemente verschoben.
     *
     * 1: Überprüfen, ob sich die Elemente noch auf dem Display befinden
     * 2: Überprüfen, ob einige Elemente wieder Sichtbar geworden sind
     *
     * 3: Kollision zwischen physikalischen Objekten und statischen Objekten
     * überprüfen.
     * 4: Kollision zwischen zwei physikalischen Objekten überprüfen
     */
    @Override
    public void update(double dt) {

            //1: Überprüfen, ob Elemente den sichtbaren Bereich des Displays verlassen
        checkActiveObjects(physicalObjects, disappearedPhysicalObjects);
        checkActiveObjects(staticObjects, disappearedStaticObjects);

            //2: Überprüfen, ob Elemente wieder im sichtbaren Bereich des Displays sind
        checkInactiveObjects(disappearedPhysicalObjects, physicalObjects);
        checkInactiveObjects(disappearedStaticObjects, staticObjects);

            //3: Kollision zwischen physikalischen und statischen Objekten überprüfen
        for (PhysicalObject pObject : physicalObjects) {
            for(PhysicalObject sObject : staticObjects) {

                if(pObject instanceof Rectangle && sObject instanceof  Rectangle) {

                    if(pObject.getPosition().getX() < sObject.getPosition().getX() + ((Rectangle) sObject).getWidth()  &&
                        sObject.getPosition().getX() < pObject.getPosition().getX() + ((Rectangle) pObject).getWidth()  &&
                        pObject.getPosition().getY() < sObject.getPosition().getY() + ((Rectangle) sObject).getHeight() &&
                        sObject.getPosition().getY() < pObject.getPosition().getY() + ((Rectangle) pObject).getHeight()) {

                        onCollisionPhysicalStatic(pObject, sObject);
                    }


                }
            }
        }

            //4: Kollision zwischen physikalischen Objekten überprüfen
        for (PhysicalObject pObject : physicalObjects) {
            for (PhysicalObject pObject2 : physicalObjects) {

                if(pObject instanceof Rectangle && pObject2 instanceof  Rectangle) {

                    if(pObject.getPosition().getX() < pObject2.getPosition().getX() + ((Rectangle) pObject2).getWidth()  &&
                            pObject2.getPosition().getX() < pObject.getPosition().getX() + ((Rectangle) pObject).getWidth()  &&
                            pObject.getPosition().getY() < pObject2.getPosition().getY() + ((Rectangle) pObject2).getHeight() &&
                            pObject2.getPosition().getY() < pObject.getPosition().getY() + ((Rectangle) pObject).getHeight()) {

                        onCollisionBetweenPhysicalObjects(pObject, pObject2);
                    }


                }
            }
        }
    }

    public void removeStaticObject(PhysicalObject object) {

        if(object != null && staticObjects != null)
            SwingUtilities.invokeLater(() -> staticObjects.remove(object));
    }

    public void registerStaticObject(PhysicalObject object) {


        if(object != null && staticObjects != null)
            SwingUtilities.invokeLater(() -> staticObjects.add(object));
    }

    public void removePhysicalObject(PhysicalObject object) {

        MyLogger.info("[Engine] Ein neues statisches Objekt wurde in der Welt registiert und wird nun von der PhysicalWorld beeinflusst");
        if(object != null && physicalObjects != null)
            SwingUtilities.invokeLater(() -> physicalObjects.remove(object));
    }

    public void registerPhysicalObject(PhysicalObject object) {

        MyLogger.info("[Engine] Ein neues physikalisches Objekt wurde in der Welt registiert und wird nun von der PhysicalWorld beeinflusst");
        if(object != null && physicalObjects != null)
            SwingUtilities.invokeLater(() -> physicalObjects.add(object));
    }

    /**
     * In dieser Methode wird eine ArrayList mit Objekten überprüft.
     * Falls diese Objekte im Sichtfeld des Displays liegen, passiert
     * mit diesen nichts. Ansonsten werden diese in eine andere ArrayListe
     * verschoben.
     */
    private void checkActiveObjects(ArrayList<PhysicalObject> physicalObjects, ArrayList<PhysicalObject> addObjects) {

        for(PhysicalObject object : physicalObjects) {

            if(object instanceof Rectangle) {

                if(object.getPosition().getX() + ((Rectangle) object).getWidth() < 0 || object.getPosition().getY() + ((Rectangle) object).getHeight() < 0 || object.getPosition().getX() > display.getWidth() || object.getPosition().getY() > display.getHeight()) {

                    SwingUtilities.invokeLater(() -> physicalObjects.remove(object));
                    SwingUtilities.invokeLater(() -> addObjects.add(object));

                    onDisappearFromDisplay(object);
                    MyLogger.engineInformation("[Engine] Ein Objekt vom Typ Rechteck ist nun inaktiv: Es ist nicht mehr sichtbar!");
                }
            }


        }
    }

    /**
     * In dieser Methode wird eine ArrayList mit Objekten überprüft.
     * Falls diese Objekte im Sichtfeld des Displays liegen, werden
     * sie in eine andere ArrayListe verschoben.
     */
    private void checkInactiveObjects(ArrayList<PhysicalObject> physicalObjects, ArrayList<PhysicalObject> addObjects) {

        for(PhysicalObject object : physicalObjects) {

            if(object instanceof Rectangle) {

                if(object.getPosition().getX() + ((Rectangle) object).getWidth() > 0 && object.getPosition().getY() + ((Rectangle) object).getHeight() > 0 && object.getPosition().getX() < display.getWidth() && object.getPosition().getY() < display.getHeight()) {

                    SwingUtilities.invokeLater(() -> physicalObjects.remove(object));
                    SwingUtilities.invokeLater(() -> addObjects .add(object));

                    onAppearOnDisplay(object);
                    MyLogger.engineInformation("[Engine] Ein Objekt vom Typ Rechteck ist nun aktiv: Es ist nun wieder sichtbar!");
                }
            }
        }
    }
}
