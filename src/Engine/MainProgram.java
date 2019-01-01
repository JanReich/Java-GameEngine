package Engine;

import Engine.Abitur.Database.DatabaseConnector;
import Engine.ConfigSystem.DisplayConfig;
import Engine.ConfigSystem.GameConfig;
import Engine.ConfigSystem.MySQLConfig;
import Engine.Graphics.Display;
import Engine.Graphics.EventListener;
import Engine.Logger.MyLogger;
import GamePackage.GameManager;

import javax.swing.*;

/**
 * GitHub-Link zur Engine:
 * Bug-Reports oder gewünschte Features per Mail an: Jan.reich@mail.de
 * Entwicklungsstatus der Engine (Überblick über kommende Features): https://trello.com/b/I2jmqmZQ/engine
 *
 * @Author: Jan-Philipp Reich (Jan Reich) aus Dortmund wohnhaft in NRW
 * @Version: 0.68 Beta
 * @since 20.04.2017
 *
 * Alle Rechte vorbehalten (All rights reserved). Für Bildungszwecke zur freien Verwendung (mit der Angabe des Autoren der Engine)
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
        MyLogger.engineInformation("[Engine] Engine by Jan - All rights reserved!");
        MyLogger.engineInformation("[Engine] Engine wird geladen...");
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

            MyLogger.engineInformation("[Engine] Laden der Engine abgeschlossen!");
            new GameManager(display, connector);
        } else {

            MyLogger.engineInformation("[Engine] Laden der Engine abgeschlossen!");
            new GameManager(display);
        }
    }

    public static void main(String[] args) {

        new MainProgram();
    }
}
