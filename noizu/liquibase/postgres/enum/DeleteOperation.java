package noizu.liquibase.postgres.enums;

import liquibase.database.Database;
        import liquibase.database.jvm.JdbcConnection;
        import liquibase.exception.RollbackImpossibleException;
        import noizu.liquibase.postgres.EnumChange;

        import java.sql.SQLException;
        import java.sql.Statement;

public class DeleteOperation extends EnumChange {
    @Override
    public void execute(Database database) {
        JdbcConnection conn = (JdbcConnection) database.getConnection();

        String sql = "DROP TYPE " + this.enumName + ";";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete enum type", e);
        }
    }

    @Override
    public void rollback(Database database) throws RollbackImpossibleException {
        // Your implementation for rollback.
        // Perhaps recreating the enum type, but you would need to know the original values.
    }
}
