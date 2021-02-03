package engine.configSystem;

import engine.toolbox.resourceHelper.FileHelper;

import java.util.HashMap;

public class MySQLConfig extends Config {

  private boolean debugging;
  private int port;
  private String host;
  private String database;
  private String username;
  private String password;

  public MySQLConfig() {
    super(FileHelper.getFile("Engine/Configs/MySQLConfig.properties"));
  }

  // --------------- MySQL - Configreader --------------- //
  @Override
  public void save() {

  }

  @Override
  public void readConfig() {
    debugging = Boolean.parseBoolean(FileHelper.getProperty(file, "debugInformation"));
    host = FileHelper.getProperty(file, "host");
    port = Integer.parseInt(FileHelper.getProperty(file, "port"));
    database = FileHelper.getProperty(file, "database");
    username = FileHelper.getProperty(file, "username");
    password = FileHelper.getProperty(file, "password");
  }

  @Override
  public void setStandards() {
    if (!FileHelper.isFileExisting(file)) {
      FileHelper.createNewFile(file);
      HashMap<String, String> config = new HashMap<>();

      config.put("debugInformation", "false");
      config.put("port", "3306");
      config.put("database", "db85565x2810214");
      config.put("host", "mysql.webhosting24.1blu.de");
      config.put("username", "s85565_2810214");
      config.put("password", "kkgbeste");
      FileHelper.setProperty(file, config);
    }
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  public String getDatabase() {
    return database;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public boolean isDebugging() {
    return debugging;
  }
}
