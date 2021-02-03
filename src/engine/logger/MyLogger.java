package engine.logger;

import engine.configSystem.EngineConfig;
import engine.toolbox.resourceHelper.FileHelper;
import engine.toolbox.system.SystemHelper;

import java.awt.*;
import java.io.IOException;
import java.util.logging.*;

public class MyLogger {

  private static boolean opened;
  private static String loggingFile;
  private static FileHandler fileHTML;
  private static Formatter formatterHTML;

  private static EngineConfig engineConfig;
  private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  /**
   * Die SetUp-Methode initalisiert die benötigten Formatter und Files
   */
  public static void setup(final EngineConfig config) {
    engineConfig = config;
    try {
      // get the global logger to configure it
      Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
      Logger rootLogger = Logger.getLogger("");
      Handler[] handlers = rootLogger.getHandlers();
      if (handlers[0] instanceof ConsoleHandler) {
        rootLogger.removeHandler(handlers[0]);
      }
      logger.setLevel(Level.ALL);
      if (!FileHelper.isFileExisting(FileHelper.getFile("Logging"))) {
        FileHelper.createDir(FileHelper.getFile("Logging"));
      }
      if (!FileHelper.isFileExisting(FileHelper.getFile("Logging/" + SystemHelper.getDate()))) {
        FileHelper.createDir(FileHelper.getFile("Logging/" + SystemHelper.getDate()));
      }
      fileHTML = new FileHandler("res/Logging/" + SystemHelper.getDate() + "/" + "log_" + SystemHelper.getTimeAsFilename() + ".html");
      loggingFile = "Logging/" + SystemHelper.getDate() + "/" + "log_" + SystemHelper.getTimeAsFilename() + ".html";
      // create an HTML formatter
      formatterHTML = new HtmlFormatter();
      fileHTML.setFormatter(formatterHTML);
      logger.addHandler(fileHTML);
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("[Error] Fehler beim erstellen des Loggers");
    }
  }

  public static void openHtmlFile() {
    if (!opened) {
      try {
        opened = true;
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(FileHelper.getFile(loggingFile).toURI());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Zum debuggen können die Logs benutzt werden, dazu dient die Methode, damit die
   * Debug-Informationen in den Logs sichtbar sind.
   */
  public static void debug(final String message) {
    LOGGER.setLevel(Level.ALL);
    LOGGER.finer(message);
  }

  /**
   * Um Informationen in den Logs zu speichern kan die Info-Methode benutzt werden.
   */
  public static void info(final String message) {
    LOGGER.setLevel(Level.ALL);
    LOGGER.info(message);
  }

  /**
   * Um Warnungen in den Logs Hervorzuheben, kann diese Methode verwendet werden
   */
  public static void warn(final String message) {
    LOGGER.setLevel(Level.ALL);
    LOGGER.warning(message);
  }

  /**
   * Um Fehler in die Logs zu schreiben dient diese Methode. Diese werden rot hervorgehoben.
   */
  public static void error(final String message) {
    LOGGER.setLevel(Level.ALL);
    LOGGER.severe(message);
    if (engineConfig.isOpenHtmlFile()) {
      openHtmlFile();
    }
  }

  /**
   * Um Fehler in die Logs zu schreiben dient diese Methode. Diese werden rot hervorgehoben.
   */
  public static void fatalError(final String message) {
    LOGGER.setLevel(Level.ALL);
    LOGGER.severe(message);
    if (engineConfig.isOpenHtmlFile()) {
      openHtmlFile();
    }
  }

  /**
   * Informationen aus der engine werden mit dieser Methode in den Logs gespeichert
   */
  public static void engineInformation(final String message) {
    LOGGER.setLevel(Level.ALL);
    LOGGER.fine(message);
  }
}
