package noizu.liquibase.postgres;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

public abstract class EnumChange implements CustomTaskChange {
    protected String enumName;

    public void setEnum(String enumName) {
        this.enumName = enumName;
    }

    public abstract void execute(Database database);

    public String getConfirmationMessage() {
        return "Enum change completed.";
    }

    public void setUp() throws SetupException {
    }

    public void setFileOpener(ResourceAccessor resourceAccessor) {
    }

    public ValidationErrors validate(Database database) {
        return new ValidationErrors();
    }
}
