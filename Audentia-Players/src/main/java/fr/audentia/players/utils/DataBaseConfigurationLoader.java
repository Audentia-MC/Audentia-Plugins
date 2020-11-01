package fr.audentia.players.utils;

import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.players.infrastructure.database.DatabaseConnection;

import java.io.File;

import static fr.audentia.players.infrastructure.database.DatabaseConnectionBuilder.aDatabaseConnection;

public class DataBaseConfigurationLoader {

    public static DatabaseConnection loadConnection(String filePath) {

        FileConfig fileConfig = FileConfig.of(filePath + File.separator + "configuration.toml");

        fileConfig.load();

        DatabaseConnection connection = aDatabaseConnection()
                .withUser(fileConfig.getOrElse("database.user", ""))
                .withPassword(fileConfig.getOrElse("database.password", ""))
                .withHost(fileConfig.getOrElse("database.host", ""))
                .withPort(fileConfig.getOrElse("database.port", 3306))
                .withDatabase(fileConfig.getOrElse("database.database", ""))
                .build();

        fileConfig.close();

        return connection;
    }

}
