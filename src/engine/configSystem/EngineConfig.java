package engine.configSystem;

import engine.toolbox.resourceHelper.FileHelper;

public class EngineConfig extends Config {

  private boolean useMySQL;
  private boolean openHtmlFile;

  public EngineConfig() {
    super(FileHelper.getFile("Engine/Configs/EngineConfig.properties"));
  }

  @Override
  public void save() {
  }

  @Override
  public void readConfig() {
    useMySQL = Boolean.parseBoolean(FileHelper.getProperty(file, "useMySQL"));
    openHtmlFile = Boolean.parseBoolean(FileHelper.getProperty(file, "openHtmlFileOnError"));
  }

  @Override
  public void setStandards() {
  }

  //---------- GETTER AND SETTER ----------
  public boolean isUseMySQL() {
    return useMySQL;
  }

  public boolean isOpenHtmlFile() {
    return openHtmlFile;
  }
}