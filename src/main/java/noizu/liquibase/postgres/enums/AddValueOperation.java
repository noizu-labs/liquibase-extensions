package noizu.liquibase.postgres.enums;

import liquibase.database.Database;
        import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.RollbackImpossibleException;
        import noizu.liquibase.postgres.EnumChange;

import java.sql.Statement;

public class AddValueOperation extends EnumChange {
    private String value;

    public void setValue(String value) {
        value = value.trim();
        if (!value.matches("[a-zA-Z0-9_]+")) {
            throw new IllegalArgumentException("Invalid enum value: " + value);
        }
        this.value = "'" + value + "'";
    }

    @Override
    public void execute(Database database) throws CustomChangeException {
        JdbcConnection conn = (JdbcConnection) database.getConnection();

        String sql = "ALTER TYPE " + this.enumName + " ADD VALUE '" + this.value + "';";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            throw new CustomChangeException("Failed to add value to enum type", e);
        }
    }

    @Override
    public void rollback(Database database) throws CustomChangeException, RollbackImpossibleException {
        JdbcConnection conn = (JdbcConnection) database.getConnection();

        String sql = "ALTER TYPE " + this.enumName + " DROP VALUE '" + this.value + "';";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            throw new CustomChangeException("Failed to remove value from enum type", e);
        }
    }
}
