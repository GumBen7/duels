package databases.mysql;

import databases.Database;
import databases.DatabaseFactory;

public class MySQLDatabaseFactory extends DatabaseFactory {
    @Override
    public Database getDatabase() {
        return MySQLDatabase.getInstance();
    }
}
