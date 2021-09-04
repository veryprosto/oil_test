package ru.veryprosto.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "equipment")
public class Equipment {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(unique = true, canBeNull = false)
    private String name;

    @DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true, columnName = "well_id")
    private Well well;

    public Equipment() {
    }

    public Equipment(String name, Well well) {
        this.name = name;
        this.well = well;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Well getWell() {
        return well;
    }

    public void setWell(Well well) {
        this.well = well;
    }
}
