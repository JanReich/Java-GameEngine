package Engine.ConfigSystem;

import Engine.Toolbox.ResourceHelper.FileHelper;

public class GameConfig extends Config {

            //Attribute
        private boolean useMySQL;

            //Referenzen

    public GameConfig() {

        super(FileHelper.getFile("Engine/Configs/GameConfig.properties"));
    }

    @Override
    public void save() {

    }

    @Override
    public void readConfig() {

        useMySQL = Boolean.parseBoolean(FileHelper.getProperty(file, "useMySQL"));
    }

    @Override
    public void setStandards() {

    }

        //---------- GETTER AND SETTER ----------
    public boolean isUseMySQL() {

        return useMySQL;
    }
}