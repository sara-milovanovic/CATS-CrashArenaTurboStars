package com.example.catsproject.db;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "player")
public class Player {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "heart")
    public int heart;

    @ColumnInfo(name = "sword")
    public int sword;

    @ColumnInfo(name = "thunder")
    public int thunder;

    @ColumnInfo(name = "boxes")
    public int boxes;

    @ColumnInfo(name = "generateBox")
    public int generateBox;
}
