package engine.configSystem;

import engine.toolbox.resourceHelper.FileHelper;

import java.io.File;

public abstract class Config {

  protected final File file;
  protected boolean necessary;

  public Config(final File file) {
    this.file = file;
    this.necessary = true;
    if (!FileHelper.isFileExisting(file)) {
      setStandards();
    }
    readConfig();
  }

  /**
   * In dieser Methode werden die Config-Werte gespeichert
   */
  public abstract void save();

  /**
   * In dieser Methode werden die Config-Werte ausgelesen
   */
  public abstract void readConfig();

  /**
   * Falls die Config nicht existiert werden in dieser Methode die Standarts festgelegt
   */
  public abstract void setStandards();
}
