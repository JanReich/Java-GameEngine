package engine.physik;

import engine.graphics.Display;
import engine.graphics.interfaces.TimeBasedObject;
import engine.logger.MyLogger;
import engine.physik.PhysicalObjects.*;

import javax.swing.*;
import java.util.ArrayList;

public abstract class PhysicalWorld implements TimeBasedObject {

  private final Display display;

  private final ArrayList<PhysicalObject> staticObjects;
  private final ArrayList<PhysicalObject> physicalObjects;

  private final ArrayList<PhysicalObject> disappearedStaticObjects;
  private final ArrayList<PhysicalObject> disappearedPhysicalObjects;

  public PhysicalWorld(final Display display) {
    MyLogger.engineInformation("[engine] Physikalische Umgebung wurde gestartet...");
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
  public abstract void onCollisionPhysicalStatic(final PhysicalObject physicalObject, final PhysicalObject staticObject);

  /**
   * Diese Methode wird aufgerufen, wenn eine Kollision zwischen Physikalischen Objekten stattfindet
   */
  public abstract void onCollisionBetweenPhysicalObjects(final PhysicalObject pObject1, final PhysicalObject pObject2);

  /**
   * Diese Methode wird aufgerufen, wenn ein Objekt in den sichtbaren
   * Bereich zurückkehrt.
   */
  public abstract void onAppearOnDisplay(final PhysicalObject object);

  /**
   * Diese Methode wird aufgerufen, wenn ein Objekt den sichtbaren
   * Bereich verlässt.
   */
  public abstract void onDisappearFromDisplay(final PhysicalObject object);

  /**
   * In dieser Methode werden die verschiedenen ArrayListen überprüft und
   * gegebenenfalls Elemente verschoben.
   * <p>
   * 1: Überprüfen, ob sich die Elemente noch auf dem Display befinden
   * 2: Überprüfen, ob einige Elemente wieder Sichtbar geworden sind
   * <p>
   * 3: Kollision zwischen physikalischen Objekten und statischen Objekten
   * überprüfen.
   * 4: Kollision zwischen zwei physikalischen Objekten überprüfen
   */
  @Override
  public void update(final double dt) {
    //1: Überprüfen, ob Elemente den sichtbaren Bereich des Displays verlassen
    checkActiveObjects(physicalObjects, disappearedPhysicalObjects);
    checkActiveObjects(staticObjects, disappearedStaticObjects);
    //2: Überprüfen, ob Elemente wieder im sichtbaren Bereich des Displays sind
    checkInactiveObjects(disappearedPhysicalObjects, physicalObjects);
    checkInactiveObjects(disappearedStaticObjects, staticObjects);
    //3: Kollision zwischen physikalischen und statischen Objekten überprüfen
    for (PhysicalObject pObject : physicalObjects) {
      for (PhysicalObject sObject : staticObjects) {
        if (pObject instanceof Rectangle && sObject instanceof Rectangle) {
          if (pObject.getPosition().getX() < sObject.getPosition().getX() + ((Rectangle) sObject).getWidth() &&
              sObject.getPosition().getX() < pObject.getPosition().getX() + ((Rectangle) pObject).getWidth() &&
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
        if (pObject instanceof Rectangle && pObject2 instanceof Rectangle) {
          if (pObject.getPosition().getX() < pObject2.getPosition().getX() + ((Rectangle) pObject2).getWidth() &&
              pObject2.getPosition().getX() < pObject.getPosition().getX() + ((Rectangle) pObject).getWidth() &&
              pObject.getPosition().getY() < pObject2.getPosition().getY() + ((Rectangle) pObject2).getHeight() &&
              pObject2.getPosition().getY() < pObject.getPosition().getY() + ((Rectangle) pObject).getHeight()) {
            onCollisionBetweenPhysicalObjects(pObject, pObject2);
          } else if (pObject instanceof Triangle && pObject2 instanceof Triangle) {

          } else if ((pObject instanceof Triangle && pObject2 instanceof Rectangle) || (pObject instanceof Rectangle && pObject2 instanceof Triangle)) {

          } else if (pObject instanceof Circle && pObject2 instanceof Circle) {

          } else if ((pObject instanceof Circle && pObject2 instanceof Rectangle) || (pObject instanceof Rectangle && pObject2 instanceof Circle)) {

          } else if ((pObject instanceof Circle && pObject2 instanceof Triangle) || (pObject instanceof Triangle && pObject2 instanceof Circle)) {

          } else if ((pObject instanceof Circle && pObject2 instanceof Ellipse) || (pObject instanceof Ellipse && pObject2 instanceof Circle)) {

          } else if (pObject instanceof Ellipse && pObject instanceof Ellipse) {

          }
        }
      }
    }
  }

  public void removeStaticObject(final PhysicalObject object) {
      if (object != null && staticObjects != null) {
          SwingUtilities.invokeLater(() -> staticObjects.remove(object));
      }
  }

  public void registerStaticObject(final PhysicalObject object) {
      if (object != null && staticObjects != null) {
          SwingUtilities.invokeLater(() -> staticObjects.add(object));
      }
  }

  public void removePhysicalObject(final PhysicalObject object) {
    MyLogger.info("[engine] Ein neues statisches Objekt wurde in der Welt registiert und wird nun von der PhysicalWorld beeinflusst");
      if (object != null && physicalObjects != null) {
          SwingUtilities.invokeLater(() -> physicalObjects.remove(object));
      }
  }

  public void registerPhysicalObject(final PhysicalObject object) {
    MyLogger.info("[engine] Ein neues physikalisches Objekt wurde in der Welt registiert und wird nun von der PhysicalWorld beeinflusst");
      if (object != null && physicalObjects != null) {
          SwingUtilities.invokeLater(() -> physicalObjects.add(object));
      }
  }

  /**
   * In dieser Methode wird eine ArrayList mit Objekten überprüft.
   * Falls diese Objekte im Sichtfeld des Displays liegen, passiert
   * mit diesen nichts. Ansonsten werden diese in eine andere ArrayListe
   * verschoben.
   */
  private void checkActiveObjects(final ArrayList<PhysicalObject> physicalObjects, final ArrayList<PhysicalObject> addObjects) {
    for (PhysicalObject object : physicalObjects) {
      if (object instanceof Rectangle) {
        if (object.getPosition().getX() + ((Rectangle) object).getWidth() < 0 || object.getPosition().getY() + ((Rectangle) object).getHeight() < 0 || object.getPosition().getX() > display.getWidth() || object.getPosition().getY() > display.getHeight()) {
          SwingUtilities.invokeLater(() -> physicalObjects.remove(object));
          SwingUtilities.invokeLater(() -> addObjects.add(object));
          onDisappearFromDisplay(object);
          MyLogger.engineInformation("[engine] Ein Objekt vom Typ Rechteck ist nun inaktiv: Es ist nicht mehr sichtbar!");
        }
      } else if (object instanceof Triangle) {

      } else if (object instanceof Circle) {

      } else if (object instanceof Ellipse) {

      }
    }
  }

  /**
   * In dieser Methode wird eine ArrayList mit Objekten überprüft.
   * Falls diese Objekte im Sichtfeld des Displays liegen, werden
   * sie in eine andere ArrayListe verschoben.
   */
  private void checkInactiveObjects(final ArrayList<PhysicalObject> physicalObjects, final ArrayList<PhysicalObject> addObjects) {
    for (PhysicalObject object : physicalObjects) {
      if (object instanceof Rectangle) {
        if (object.getPosition().getX() + ((Rectangle) object).getWidth() > 0 && object.getPosition().getY() + ((Rectangle) object).getHeight() > 0 && object.getPosition().getX() < display.getWidth() && object.getPosition().getY() < display.getHeight()) {
          SwingUtilities.invokeLater(() -> physicalObjects.remove(object));
          SwingUtilities.invokeLater(() -> addObjects.add(object));
          onAppearOnDisplay(object);
          MyLogger.engineInformation("[engine] Ein Objekt vom Typ Rechteck ist nun aktiv: Es ist nun wieder sichtbar!");
        }
      } else if (object instanceof Triangle) {

      } else if (object instanceof Circle) {

      } else if (object instanceof Ellipse) {

      }

    }
  }
}
