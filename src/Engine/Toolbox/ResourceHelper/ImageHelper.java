package Engine.Toolbox.ResourceHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHelper {

            //Attribute

            //Referenzen
        private static String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("out/production/GameEngine/", "res/");


    public static BufferedImage getImage(String path) {

        try {

            return ImageIO.read(new File(rootPath + path));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }
}
