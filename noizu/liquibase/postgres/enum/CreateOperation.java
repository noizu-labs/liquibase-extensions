package noizu.liquibase.postgres.enum;

import liquibase.database.Database;
        import liquibase.database.jvm.JdbcConnection;
        import liquibase.exception.RollbackImpossibleException;
        import noizu.liquibase.postgres.EnumChange;

        import java.sql.SQLException;
        import java.sql.Statement;
        import java.util.Arrays;

public class CreateOperation extends EnumChange {
    private String values;

    public void setValues(String values) {
        this.values = values;
    }

    @Override
    public void execute(Database database) {
        JdbcConnection conn = (JdbcConnection) database.getConnection();

        String[] enumValues = this.values.split(",");
        for (String value : enumValues) {
            if (!value.matches("[a-zA-Z0-9_]+")) {
                throw new IllegalArgumentException("Invalid enum value: " + value);
            }
        }

        String enumValuesStr = String.join(",", Arrays.asList(enumValues));
        String sql = "DO $$ BEGIN\n" +
                "CREATE TYPE " + this.enumName + " as ENUM (" + enumValuesStr + ");\n" +
                "EXCEPTION\n" +
                "WHEN duplicate_object THEN null;\n" +
                "END $$;";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create enum type", e);
        }
    }

    @Override
    public void rollback(Database database) throws RollbackImpossibleException {
        JdbcConnection conn = (JdbcConnection) database.getConnection();
        String sql = "DROP TYPE " + this.enumName + ";";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to rollback enum type creation", e);
        }
    }
}
