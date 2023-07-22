package noizu.liquibase.postgres.enums;

import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.RollbackImpossibleException;
import noizu.liquibase.postgres.EnumChange;

import java.sql.Statement;
import java.util.Arrays;

public class CreateOperation extends EnumChange {
    private String values;

    public void setValues(String values) {
        this.values = values;
    }

    @Override
    public void execute(Database database) throws CustomChangeException {
        JdbcConnection conn = (JdbcConnection) database.getConnection();

        String[] enumValues = this.values.split(",");
        for (int i = 0; i < enumValues.length; i++) {
            String value = enumValues[i].trim();
            if (!value.matches("[a-zA-Z0-9_]+")) {
                throw new CustomChangeException("Invalid enum value: " + value);
            }
            enumValues[i] = "'" + value + "'";
        }

        String enumValuesStr = String.join(",", Arrays.asList(enumValues));
        String sql = "DO $$ BEGIN\n" +
                "CREATE TYPE " + this.enumName + " as ENUM (" + enumValuesStr + ");\n" +
                "EXCEPTION\n" +
                "WHEN duplicate_object THEN null;\n" +
                "END $$;";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            throw new CustomChangeException("Failed to create enum type: [" + sql + "]", e);
        }
    }

    @Override
    public void rollback(Database database) throws CustomChangeException, RollbackImpossibleException  {
        JdbcConnection conn = (JdbcConnection) database.getConnection();
        String sql = "DROP TYPE " + this.enumName + ";";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            throw new CustomChangeException("Failed to rollback enum type creation", e);
        }
    }
}
