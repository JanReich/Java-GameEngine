package Engine.Toolbox.uiPackage;

import Engine.Graphics.Interfaces.AdvancedMouseInterface;
import Engine.Toolbox.Math.Point2f;
import Engine.Toolbox.ResourceHelper.DrawHelper;
import Engine.Toolbox.ResourceHelper.Image;

import java.awt.event.MouseEvent;

public class Button implements AdvancedMouseInterface {

            //Attribute
        private int width;
        private int height;

        private boolean hover;
        private boolean clicked;

            //Referenzen
        private Image button;
        private Image hoverButton;

        private Point2f position;

    public Button(String image, boolean hover, Point2f position, int width, int height) {

        this(image, hover, (int) position.getX(), (int) position.getY(), width, height);
    }

    public Button(String image, boolean hover, int x, int y, int width, int height) {

        this.width = width;
        this.height = height;
        this.position = new Point2f(x, y);

        this.button = new Image(image);
        if(hover) this.hoverButton = new Image(image.replace(".png", "-hover.png"));
        else hoverButton = button;
    }

    @Override
    public void draw(DrawHelper draw) {

        if(hover) draw.drawImage(hoverButton, (int) position.getX(), (int) position.getY(), width, height);
        else draw.drawImage(button, (int) position.getX(), (int) position.getY(), width, height);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if(e.getX() > position.getX() && e.getX() < position.getX() + width && e.getY() > position.getY() && e.getY() < position.getY() + height) hover = true;
        else hover = false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if(e.getX() > position.getX() && e.getX() < position.getX() + width && e.getY() > position.getY() && e.getY() < position.getY() + height) clicked = true;
        else clicked = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

        //---------- GETTER AND SETTER ----------
    public boolean isClicked() {

        return clicked;
    }
}
