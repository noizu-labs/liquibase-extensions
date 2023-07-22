package noizu.liquibase.postgres.enums;

import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.RollbackImpossibleException;
import noizu.liquibase.postgres.EnumChange;

import java.sql.Statement;

public class DeleteOperation extends EnumChange {
    @Override
    public void execute(Database database) throws CustomChangeException {
        JdbcConnection conn = (JdbcConnection) database.getConnection();

        String sql = "DROP TYPE " + this.enumName + ";";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            throw new CustomChangeException("Failed to delete enum type", e);
        }
    }

    @Override
    public void rollback(Database database) throws CustomChangeException, RollbackImpossibleException {
        // Your implementation for rollback.
        // Perhaps recreating the enum type, but you would need to know the original values.
    }
}
