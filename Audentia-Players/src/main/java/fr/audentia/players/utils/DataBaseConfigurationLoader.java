package fr.audentia.players.utils;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.players.infrastructure.database.DatabaseConnection;

import java.util.Optional;

import static fr.audentia.players.infrastructure.database.DatabaseConnectionBuilder.aDatabaseConnection;

public class DataBaseConfigurationLoader {

    public static DatabaseConnection loadConnection(String filePath) {

        FileConfig fileConfig = CommentedFileConfig.builder(filePath)
                .defaultResource("configuration.toml")
                .autosave()
                .build();

        fileConfig.load();

        DatabaseConnection connection = aDatabaseConnection()
                .withUser(fileConfig.getOrElse("database.user", ""))
                .withPassword(fileConfig.getOrElse("database.password", ""))
                .withHost(fileConfig.getOrElse("database.host", ""))
                .withDatabase(fileConfig.getOrElse("database.database", ""))
                .build();

        fileConfig.close();

        return connection;
    }

}
