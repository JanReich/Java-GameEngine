package engine.toolbox.uiPackage;

import engine.graphics.Display;
import engine.graphics.interfaces.AdvancedMouseInterface;
import engine.graphics.interfaces.RemovableObject;
import engine.toolbox.math.Point2f;
import engine.toolbox.resourceHelper.DrawHelper;
import engine.toolbox.system.Animation;

import java.awt.event.MouseEvent;

public class AnimatedButton implements AdvancedMouseInterface, RemovableObject {

            //Attribute
        private int width;
        private int height;

        private boolean clicked;
        private Point2f position;

            //Referenzen
        private Display display;
        private Animation animation;

    public AnimatedButton(Display display, String path, int tilesWidth, int tilesHeight, int x, int y, int width, int height) {

        this.width = width;
        this.height = height;
        this.display = display;
        position = new Point2f(x, y);

        animation = new Animation(path, tilesWidth,tilesHeight, x, y, width, height);
        display.getActivePanel().drawTimebasedObject(animation);
    }

    @Override
    public void remove() {

        display.getActivePanel().removeObjectFromPanel(animation);
        animation = null;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if(e.getX() > position.getX() && e.getX() < position.getX() + width && e.getY() > position.getY() && e.getY() < position.getY() + height) animation.play();
        else animation.stop();
    }

    @Override
    public void draw(DrawHelper draw) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getX() > position.getX() && e.getX() < position.getX() + width && e.getY() > position.getY() && e.getY() < position.getY() + height)
            clicked = true;
        else clicked = false;
    }

        //---------- GETTER AND SETTER ----------
    public boolean isClicked() {

        return clicked;
    }
}
