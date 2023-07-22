package noizu.liquibase.postgres;

import liquibase.change.custom.CustomTaskChange;
import liquibase.change.custom.CustomTaskRollback;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.RollbackImpossibleException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

public abstract class EnumChange implements CustomTaskChange, CustomTaskRollback  {
    protected String enumName;

    public void setEnum(String enumName) {
        this.enumName = enumName;
    }

    public abstract void execute(Database database)  throws CustomChangeException;
    public abstract void rollback(Database database) throws CustomChangeException, RollbackImpossibleException;
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
