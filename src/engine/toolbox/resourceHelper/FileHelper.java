package engine.toolbox.resourceHelper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FileHelper {

  private static final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
  private static final String[] dir = Thread.currentThread().getContextClassLoader().getResource("").getPath().split("/");

  /**
   * Diese Methode erstellt mittels eines Strings (@param path) ein neuen
   * File. Dieser erstellte Datei wird dann auch direkt returnt.
   */
  public static File getFile(final String path) {
    int indedx = 0;
    for (int i = 0; i < dir.length; i++) {
      if (dir[i].equalsIgnoreCase("out")) {
        indedx = i + 2;
      }
    }
    return new File(rootPath.replace("out/production/" + dir[indedx] + "/", "res/") + path);
  }

  /**
   * In dieser Methode wird überprüft, ob der File existiert.
   *
   * @param path - Path (will be convertet to File)
   * @return true -> File existiert || false -> file existiert noch nicht!
   */
  public static boolean isFileExisting(final String path) {
    return isFileExisting(getFile(path));
  }

  /**
   * In dieser Methode wird überprüft, ob der File existiert.
   *
   * @param file - File (can be null -> check first)
   * @return true -> File existiert || false -> file existiert noch nicht!
   */
  public static boolean isFileExisting(final File file) {
    if (file != null) {
      if (file.exists()) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * In dieser Methode wird ein neuer File erstellt, aber nur falls noch
   * kein File mit diesem namen existiert und der angegebene file != null.
   *
   * @return true -> File wurde erstellt || false -> File wurde nicht erstellt
   */
  public static boolean createNewFile(final String string) {
    return createNewFile(getFile(string));
  }

  /**
   * In dieser Methode wird ein neuer File erstellt, aber nur falls noch
   * kein File mit diesem namen existiert und der angegebene file != null.
   *
   * @return true -> File wurde erstellt || false -> File wurde nicht erstellt
   */
  public static boolean createNewFile(final File file) {
    if (file != null) {
      //Falls die Datei schon existiert
      if (isFileExisting(file)) {
        return false;
      }
      try {
        file.createNewFile();
        return true;
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
    }
    return false;
  }

  /**
   * Diese Methode ließt einen bestimmten abschnitt aus den Configs aus und returnt diesen als String.
   */
  public static String getProperty(final File file, final String key) {
    try {
      if (file != null && key != null && key != "") {
        Properties properties = new Properties();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));
        properties.load(stream);
        stream.close();
        return properties.getProperty(key);
      }
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Mit dieser Methode können Variabeln in der ConfigDatei gespeichert werden.
   * Dazu müssen nur alle Values mit ihren entsprechenden Werten zuvor in einer HashMap gespeichert werden
   */
  public static void setProperty(final File file, final HashMap<String, String> configs) {
    if (file != null && configs != null) {
      try {
        Properties properties = new Properties();
        for (Map.Entry<String, String> config : configs.entrySet()) {
          properties.setProperty(config.getKey(), config.getValue());
        }
        FileOutputStream writer = new FileOutputStream(file);
        properties.store(writer, "Writing properties to system.out stream");
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Ein neuer Ordner wird erstellt.
   */
  public static void createDir(final File file) {
    if (!FileHelper.isFileExisting(file)) {
      file.mkdir();
    }
  }
}
