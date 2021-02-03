package engine.configSystem;

import engine.toolbox.resourceHelper.FileHelper;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class EngineConfig extends Config {

  @Getter(AccessLevel.NONE)
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

  public boolean isUsingMySQL() {
    return useMySQL;
  }
}