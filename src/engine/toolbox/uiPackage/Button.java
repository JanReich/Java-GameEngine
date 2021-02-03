package engine.toolbox.uiPackage;

import engine.graphics.interfaces.AdvancedMouseInterface;
import engine.toolbox.math.Point2f;
import engine.toolbox.resourceHelper.DrawHelper;
import engine.toolbox.resourceHelper.Image;

import java.awt.event.MouseEvent;

public class Button implements AdvancedMouseInterface {

  private final int width;
  private final int height;
  private boolean hover;
  private boolean clicked;

  private final Image button;
  private final Image hoverButton;
  private final Point2f position;

  public Button(final String image, final boolean hover, final Point2f position, final int width, final int height) {
    this(image, hover, (int) position.getX(), (int) position.getY(), width, height);
  }

  public Button(final String image, final boolean hover, final int x, final int y, final int width, final int height) {
    this.width = width;
    this.height = height;
    this.position = new Point2f(x, y);
    this.button = new Image(image);
    if (hover) {
      this.hoverButton = new Image(image.replace(".png", "-hover.png"));
    } else {
      hoverButton = button;
    }
  }

  @Override
  public void draw(final DrawHelper draw) {
    if (hover) {
      draw.drawImage(hoverButton, (int) position.getX(), (int) position.getY(), width, height);
    } else {
      draw.drawImage(button, (int) position.getX(), (int) position.getY(), width, height);
    }
  }

  @Override
  public void mouseMoved(final MouseEvent e) {
    if (e.getX() > position.getX() && e.getX() < position.getX() + width && e.getY() > position.getY() && e.getY() < position.getY() + height) {
      hover = true;
    } else {
      hover = false;
    }
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    if (e.getX() > position.getX() && e.getX() < position.getX() + width && e.getY() > position.getY() && e.getY() < position.getY() + height) {
      clicked = true;
    } else {
      clicked = false;
    }
  }

  @Override
  public void mouseDragged(final MouseEvent e) {
  }

  //---------- GETTER AND SETTER ----------
  public boolean isClicked() {
    return clicked;
  }
}
