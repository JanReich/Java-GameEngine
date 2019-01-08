package engine.configSystem;

import engine.logger.MyLogger;
import engine.toolbox.resourceHelper.FileHelper;

import java.util.HashMap;

public class DisplayConfig extends Config {

            //Config-Werte
        private int width;
        private int height;

        private boolean resizable;
        private boolean alwaysOnTop;

        private String title;

            //Display - Position
        private int windowX;
        private int windowY;

        private boolean showFPS;
        private boolean isCentered;
        private boolean useCustomCursor;

        private String customTheme;
        private boolean useCustomTheme;
        private boolean useCustomLayout;

        private String quitMessage;
        private String programmTitle;
        private boolean quitConfirmation;


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
        programmTitle = FileHelper.getProperty(file, "programmTitle");
        quitConfirmation = Boolean.parseBoolean(FileHelper.getProperty(file, "quitConfirmation"));
    }

    @Override
    public void setStandards() {

        if(!FileHelper.isFileExisting(file)) {

            if(necessary)
                System.err.println("Program kann ohne ConfigDatei nicht gestartet werden! Config wird mit Standardwerten erstellt...");

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

            if(necessary)
                System.exit(-1);
        }
    }

        //GETTER AND SETTER
    public int getWidth() {

        return width;
    }

    public int getHeight() {

        return height;
    }

    public boolean isResizable() {

        return resizable;
    }

    public boolean isAlwaysOnTop() {

        return alwaysOnTop;
    }

    public String getTitle() {

        return title;
    }

    public int getWindowX() {

        return windowX;
    }

    public int getWindowY() {

        return windowY;
    }

    public boolean isShowFPS() {

        return showFPS;
    }

    public boolean isCentered() {

        return isCentered;
    }

    public boolean useCustomCursor() {

        return useCustomCursor;
    }

    public boolean useCustomLayout() {

        return useCustomLayout;
    }

    public String getCustomTheme() {

        return customTheme;
    }

    public boolean useCustomTheme() {

        return useCustomTheme;
    }

    public String getQuitMessage() {

        return quitMessage;
    }

    public String getProgrammTitle() {

        return programmTitle;
    }

    public boolean isQuitConfirmation() {

        return quitConfirmation;
    }
}
