package com.example.catsproject.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "components")
public class Components {
    @PrimaryKey(autoGenerate = true)
    public int cid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "power")
    public int power;

    @ColumnInfo(name = "health")
    public int health;

    @ColumnInfo(name = "energy")
    public int energy;

    public Components(int power, int health, int energy,int cid) {
        this.power = power;
        this.health = health;
        this.energy = energy;
        this.cid=cid;
    }
    public Components() {
    }

}
