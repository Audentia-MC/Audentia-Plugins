package fr.audentia.players.infrastructure.database;


import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private final HikariDataSource dataSource;

    public DatabaseConnection(DatabaseConnectionBuilder builder) {
        dataSource = new HikariDataSource();
        setUpDataSource(builder);
    }

    private void setUpDataSource(DatabaseConnectionBuilder builder) {
        dataSource.setJdbcUrl("jdbc:mysql://" + builder.getHost() + ":3306/" + builder.getDatabase());
        dataSource.setUsername(builder.getUser());
        dataSource.setPassword(builder.getPassword());
        dataSource.addDataSourceProperty("autoReconnect", true);
        dataSource.addDataSourceProperty("tcpKeepAlive", true);
        dataSource.addDataSourceProperty("serverTimezone", "Europe/Paris");
        dataSource.addDataSourceProperty("characterEncoding", "utf8");
        dataSource.addDataSourceProperty("useUnicode", "true");
        dataSource.setMaximumPoolSize(15);
        dataSource.setMinimumIdle(0);
    }

    public DSLContext getDatabaseContext() {

        try (Connection connection = dataSource.getConnection()) {

            return DSL.using(dataSource.getConnection(), SQLDialect.MARIADB);

        } catch (SQLException exception) {

            exception.printStackTrace();
            return null;

        }

    }

}