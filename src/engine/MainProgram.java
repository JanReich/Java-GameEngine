package engine;

import engine.abitur.database.DatabaseConnector;
import engine.configSystem.DisplayConfig;
import engine.configSystem.GameConfig;
import engine.configSystem.MySQLConfig;
import engine.graphics.Display;
import engine.graphics.EventListener;
import engine.logger.MyLogger;
import gamePackage.GameManager;

import javax.swing.*;

/**
 * GitHub-Link zur engine:
 * Bug-Reports oder gewünschte Features per Mail an: Jan.reich@mail.de
 * Entwicklungsstatus der engine (Überblick über kommende Features): https://trello.com/b/I2jmqmZQ/engine
 *
 * @Author: Jan-Philipp Reich (Jan Reich) aus Dortmund wohnhaft in NRW
 * @Version: 0.71 Beta
 * @since 20.04.2017
 *
 * Alle Rechte vorbehalten (All rights reserved). Für Bildungszwecke zur freien Verwendung (mit der Angabe des Autoren der engine)
 */
public class MainProgram {

            //Attribute

            //Referenzen
        private Display display;
        private GameConfig gameConfig;
        private MySQLConfig mySQLConfig;
        private DisplayConfig displayConfig;

    public MainProgram() {

        MyLogger.setup();
        MyLogger.engineInformation("[engine] engine by Jan - All rights reserved!");
        MyLogger.engineInformation("[engine] engine wird geladen...");
        this.gameConfig = new GameConfig();
        this.displayConfig = new DisplayConfig();

        if(displayConfig.useCustomLayout()) {

            try {

                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
            } catch(Exception e) {}
        }

        display = new Display(displayConfig);
        new EventListener(display, displayConfig);

        if(gameConfig.isUseMySQL()) {

            MyLogger.engineInformation("[<b>MySQL</b>] Config wird geladen...");
            mySQLConfig = new MySQLConfig();
            MyLogger.engineInformation("[<b>MySQL</b>] Verbindung zur Datenbank wird hergestellt...");
            DatabaseConnector connector = new DatabaseConnector(mySQLConfig.getHost(), mySQLConfig.getPort(), mySQLConfig.getDatabase(), mySQLConfig.getUsername(), mySQLConfig.getPassword());

            MyLogger.engineInformation("[engine] Laden der engine abgeschlossen!");
            GameManager gameManager = new GameManager(display, connector);
            display.getActivePanel().drawObjectOnPanel(gameManager);
        } else {

            MyLogger.engineInformation("[engine] Laden der engine abgeschlossen!");
            new GameManager(display);
        }
    }

    public static void main(String[] args) {

        new MainProgram();
    }
}
