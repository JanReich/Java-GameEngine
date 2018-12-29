package Engine.Logger;

import Engine.Toolbox.ResourceHelper.FileHelper;
import Engine.Toolbox.System.SystemHelper;

import java.io.IOException;
import java.util.logging.*;

public class MyLogger {

            //Attribute

            //Referenzen
        private static FileHandler fileHTML;
        private static Formatter formatterHTML;

        private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Die SetUp-Methode initalisiert die benötigten Formatter und Files
     */
    public static void setup() {

        try {
            // get the global logger to configure it
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            if (handlers[0] instanceof ConsoleHandler)
                rootLogger.removeHandler(handlers[0]);

            logger.setLevel(Level.ALL);

            if(!FileHelper.isFileExisting(FileHelper.getFile("Logging/" + SystemHelper.getDate())))
                FileHelper.createDir(FileHelper.getFile("Logging/" + SystemHelper.getDate()));
            fileHTML = new FileHandler("res/Logging/" + SystemHelper.getDate() + "/" + "log_" + SystemHelper.getTimeAsFilename() + ".html");

                // create an HTML formatter
            formatterHTML = new HtmlFormatter();
            fileHTML.setFormatter(formatterHTML);
            logger.addHandler(fileHTML);
        } catch (IOException e) {

            e.printStackTrace();
            System.err.println("[Error] Fehler beim erstellen des Loggers");
        }
    }

    /**
     * Zum debuggen können die Logs benutzt werden, dazu dient die Methode, damit die
     * Debug-Informationen in den Logs sichtbar sind.
     */
    public static void debug(String message) {

        LOGGER.setLevel(Level.ALL);
        LOGGER.finer(message);
    }

    /**
     * Um Informationen in den Logs zu speichern kan die Info-Methode benutzt werden.
     */
    public static void info(String message) {

        LOGGER.setLevel(Level.ALL);
        LOGGER.info(message);
    }

    /**
     * Um Warnungen in den Logs Hervorzuheben, kann diese Methode verwendet werden
     */
    public static void warn(String message) {

        LOGGER.setLevel(Level.ALL);
        LOGGER.warning(message);
    }

    /**
     * Um Fehler in die Logs zu schreiben dient diese Methode. Diese werden rot hervorgehoben.
     */
    public static void error(String message) {

        LOGGER.setLevel(Level.ALL);
        LOGGER.severe(message);
    }

    /**
     * Um Fehler in die Logs zu schreiben dient diese Methode. Diese werden rot hervorgehoben.
     */
    public static void fatalError(String message) {

        LOGGER.setLevel(Level.ALL);
        LOGGER.severe(message);

    }

    /**
     * Informationen aus der Engine werden mit dieser Methode in den Logs gespeichert
     */
    public static void engineInformation(String message) {

        LOGGER.setLevel(Level.ALL);
        LOGGER.fine(message);
    }
}
