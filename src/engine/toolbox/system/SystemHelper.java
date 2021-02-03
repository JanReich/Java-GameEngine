package engine.toolbox.system;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemHelper {

  /**
   * Diese Methode gibt als Ergebnis das Datum zum aktuellen Zeitpunkt
   * als String zurück. In folgendem Format: dd.MMM.yyyy HH:mm
   */
  public static String calculateDate() {
    SimpleDateFormat date_format = new SimpleDateFormat("dd.MMM.yyyy HH:mm");
    Date resultDate = new Date();
    return date_format.format(resultDate);
  }

  /**
   * Diese Methode gibt den Tag wieder zu dem Zeitpunkt zudem diese Methode aufgerufen wird.
   */
  public static String getDate() {
    SimpleDateFormat date_format = new SimpleDateFormat("dd.MMM.yyyy");
    Date resultDate = new Date();
    return date_format.format(resultDate);
  }

  /**
   * Diese Methode gibt die aktuelle Uhrzeit wieder
   */
  public static String getTime() {
    SimpleDateFormat date_format = new SimpleDateFormat("HH:mm");
    Date resultDate = new Date();
    return date_format.format(resultDate);
  }

  /**
   * Diese Methode gibt die aktuelle Uhrzeit wieder
   */
  public static String getTimeAsFilename() {
    SimpleDateFormat date_format = new SimpleDateFormat("HH,mm");
    Date resultDate = new Date();
    return date_format.format(resultDate);
  }
}

