package databases;

import databases.mysql.MySQLDatabaseFactory;

public abstract class DatabaseFactory {
    public static DatabaseFactory factory(DatabaseType databaseType) {
        switch (databaseType) {
            case MYSQL:
                return new MySQLDatabaseFactory();
            default:
                throw new IllegalArgumentException("unknown " + databaseType.toString());
        }
    }

    public abstract Database getDatabase();
}
