package fr.audentia.players.infrastructure.database;

public class DatabaseConnectionBuilder {

    private String host;
    private String user;
    private String password;
    private String database;
    private int port;

    private DatabaseConnectionBuilder() {
    }

    public static DatabaseConnectionBuilder aDatabaseConnection() {
        return new DatabaseConnectionBuilder();
    }

    public DatabaseConnectionBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    public DatabaseConnectionBuilder withUser(String user) {
        this.user = user;
        return this;
    }

    public DatabaseConnectionBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public DatabaseConnectionBuilder withDatabase(String database) {
        this.database = database;
        return this;
    }

    public DatabaseConnectionBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    public DatabaseConnection build() {
        return new DatabaseConnection(this);
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public int getPort() {
        return port;
    }

}