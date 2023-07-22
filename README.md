
# Noizu Liquibase PostgreSQL Enum Extension

This extension for Liquibase provides additional changes for managing PostgreSQL enum types. It allows you to create, delete, and modify enum types directly from your Liquibase change sets.

## Changes

This extension adds the following changes:

* `noizu.liquibase.postgres.enum.CreateOperation`: Creates a new enum type with specified values.
* `noizu.liquibase.postgres.enum.DeleteOperation`: Deletes an existing enum type.
* `noizu.liquibase.postgres.enum.AddValueOperation`: Adds a new value to an existing enum type.
* `noizu.liquibase.postgres.enum.RemoveValueOperation`: Removes a value from an existing enum type.

## Usage

To use these changes, add a `customChange` element to your change set in your Liquibase change log file. Set the `class` attribute to one of the classes listed above.

Here are some examples:

**Create Enum**

```xml
<changeSet author="you" id="create_enum">
  <customChange class="noizu.liquibase.postgres.enum.CreateOperation">
    <param name="enum" value="user_enum_name"/>
    <param name="values" value="value1,value2,value3"/>
  </customChange>
</changeSet>
```

**Delete Enum**

```xml
<changeSet author="you" id="delete_enum">
  <customChange class="noizu.liquibase.postgres.enum.DeleteOperation">
    <param name="enum" value="user_enum_name"/>
  </customChange>
</changeSet>
```

**Add Value to Enum**

```xml
<changeSet author="you" id="add_enum_value">
  <customChange class="noizu.liquibase.postgres.enum.AddValueOperation">
    <param name="enum" value="user_enum_name"/>
    <param name="value" value="additional_value"/>
  </customChange>
</changeSet>
```

**Remove Value from Enum**

```xml
<changeSet author="you" id="remove_enum_value">
  <customChange class="noizu.liquibase.postgres.enum.RemoveValueOperation">
    <param name="enum" value="user_enum_name"/>
    <param name="value" value="additional_value"/>
  </customChange>
</changeSet>
```

## Building

This project uses Gradle for building. To build the project, navigate to the project directory in your terminal and run the `gradle build` command. The build output will be placed in the `build/libs` directory.
