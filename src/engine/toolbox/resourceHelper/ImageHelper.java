package engine.toolbox.resourceHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHelper {

  private static final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("out/production/GameEngine/", "res/");

  public static BufferedImage getImage(final String path) {
    try {
      return ImageIO.read(new File(rootPath + path));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
