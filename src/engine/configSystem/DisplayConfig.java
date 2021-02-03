package engine.configSystem;

import engine.logger.MyLogger;
import engine.toolbox.resourceHelper.FileHelper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class DisplayConfig extends Config {

  private int width;
  private int height;
  private boolean resizable;
  private boolean alwaysOnTop;
  private int windowX;
  private int windowY;
  private boolean showFPS;
  private boolean isCentered;
  @Getter(AccessLevel.NONE)
  private boolean useCustomCursor;
  @Getter(AccessLevel.NONE)
  private boolean useCustomTheme;
  @Getter(AccessLevel.NONE)
  private boolean useCustomLayout;
  private boolean quitConfirmation;

  private String title;
  private String customTheme;
  private String quitMessage;
  private String programTitle;

  public DisplayConfig() {
    super(FileHelper.getFile("Engine/Configs/DisplayConfig.properties"));
    MyLogger.engineInformation("[engine] DisplayConfig wird geladen...");
  }

  @Override
  public void save() {
    //TODO:
  }

  @Override
  public void readConfig() {
    //todo: Check if the width, height, windowX ord windowY is null (Check null or)
    width = Integer.parseInt(FileHelper.getProperty(file, "width"));
    height = Integer.parseInt(FileHelper.getProperty(file, "height"));

    resizable = Boolean.parseBoolean(FileHelper.getProperty(file, "resizable"));
    alwaysOnTop = Boolean.parseBoolean(FileHelper.getProperty(file, "alwaysOnTop"));

    title = FileHelper.getProperty(file, "title");
    windowX = Integer.parseInt(FileHelper.getProperty(file, "windowX"));
    windowY = Integer.parseInt(FileHelper.getProperty(file, "windowY"));

    showFPS = Boolean.parseBoolean(FileHelper.getProperty(file, "showFPS"));
    isCentered = Boolean.parseBoolean(FileHelper.getProperty(file, "isCentered"));
    useCustomCursor = Boolean.parseBoolean(FileHelper.getProperty(file, "useCustomCursor"));

    customTheme = FileHelper.getProperty(file, "customTheme");
    useCustomTheme = Boolean.parseBoolean(FileHelper.getProperty(file, "useCustomTheme"));
    useCustomLayout = Boolean.parseBoolean(FileHelper.getProperty(file, "useCustomLayout"));

    quitMessage = FileHelper.getProperty(file, "quitMessage");
    programTitle = FileHelper.getProperty(file, "programmTitle");
    quitConfirmation = Boolean.parseBoolean(FileHelper.getProperty(file, "quitConfirmation"));
  }

  @Override
  public void setStandards() {
    if (!FileHelper.isFileExisting(file)) {
      if (necessary) {
        System.err.println("Program kann ohne ConfigDatei nicht gestartet werden! Config wird mit Standardwerten erstellt...");
      }

      FileHelper.createNewFile(file);
      HashMap<String, String> config = new HashMap<>();
      config.put("width", "1920");
      config.put("height", "1080");
      config.put("resizable", "false");
      config.put("alwaysOnTop", "true");
      config.put("title", "engine.Test-Window");
      config.put("scale", "1");
      config.put("windowX", "0");
      config.put("windowY", "0");

      config.put("showFPS", "true");
      config.put("isCentered", "true");
      config.put("useCustomCursor", "true");
      config.put("customTheme", "gray.theme");
      config.put("useCustomTheme", "true");
      config.put("useCustomLayout", "true");

      config.put("quitConfirmation", "true");
      config.put("programmTitle", "Programm schließen");
      config.put("quitMessage", "Möchten Sie das Programm wirklich schließen?");
      FileHelper.setProperty(file, config);
      if (necessary) {
        System.exit(-1);
      }
    }
  }

  public boolean isUsingCustomTheme() {
    return useCustomTheme;
  }

  public boolean isUsingCustomLayout() {
    return useCustomLayout;
  }

  public boolean isUsingCustomCursor() {
    return useCustomCursor;
  }
}
