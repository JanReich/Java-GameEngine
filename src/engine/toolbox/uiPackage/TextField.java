package engine.toolbox.uiPackage;

import engine.graphics.Display;
import engine.graphics.interfaces.GraphicalObject;
import engine.graphics.interfaces.RemovableObject;
import engine.toolbox.resourceHelper.DrawHelper;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public class TextField implements GraphicalObject, RemovableObject {

  private final int width;

  private Font font;
  private KeyInput input;
  private final Display display;

  public TextField(final Display display, final int width) {
    this.width = width;
    this.display = display;
    this.input = new KeyInput();
    display.getActivePanel().drawObjectOnPanel(input);
  }

  @Override
  public void draw(final DrawHelper draw) {
    draw.setFont(font);
    if (width < draw.getFontWidth(input.getInputQuerry())) {
      input.removeLetter();
    }
  }

  @Override
  public void remove() {
    display.getActivePanel().removeObjectFromPanel(input);
    input = null;
  }

  //---------- GETTER AND SETTER ----------\\
  public KeyInput getInput() {
    return input;
  }

  public void setFont(Font font) {
    this.font = font;
  }

  public void setTyping(boolean typing) {
    input.setTyping(typing);
  }

  public void setInputQuerry(String input) {
    this.input.setInputQuerry(input);
  }
}
