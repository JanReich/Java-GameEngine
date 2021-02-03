package engine.configSystem;

import engine.toolbox.resourceHelper.FileHelper;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class ServerConfig extends Config {

  private int serverPort;
  private int maxPlayers;
  private int minPlayers;
  private boolean logging;
  private boolean autoRegister;
  private boolean keepConnectionAlive;

  public ServerConfig() {
    super(FileHelper.getFile(""));
  }

  @Override
  public void save() {
  }

  @Override
  public void readConfig() {
    serverPort = Integer.parseInt(FileHelper.getProperty(file, "port"));
    minPlayers = Integer.parseInt(FileHelper.getProperty(file, "Min-Player"));
    maxPlayers = Integer.parseInt(FileHelper.getProperty(file, "Max-Player"));
    logging = Boolean.parseBoolean(FileHelper.getProperty(file, "logging"));
    autoRegister = Boolean.parseBoolean(FileHelper.getProperty(file, "autoRegister"));
    keepConnectionAlive = Boolean.parseBoolean(FileHelper.getProperty(file, "keepConnectionAlive"));
  }

  @Override
  public void setStandards() {
    if (!FileHelper.isFileExisting(file)) {
      FileHelper.createNewFile(file);
      HashMap<String, String> config = new HashMap<>();

      config.put("port", "666");
      config.put("logging", "true");
      config.put("autoRegister", "true");
      config.put("keepConnectionAlive", "true");
      config.put("MinPort", "2");
      config.put("MaxPort", "2");
      FileHelper.setProperty(file, config);
    }
  }
}
