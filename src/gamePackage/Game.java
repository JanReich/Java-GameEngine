package gamePackage;

import engine.abitur.database.DatabaseConnector;
import engine.graphics.Display;

public abstract class Game {

  private final double VERSION = 1.0;

  protected Display display;
  protected DatabaseConnector connector;

  public Game(final Display display) {
    this.display = display;
  }

  public Game(final Display display, final DatabaseConnector connector) {
    this.display = display;
    this.connector = connector;
  }
}
