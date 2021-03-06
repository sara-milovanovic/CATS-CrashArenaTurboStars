package com.example.catsproject.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "user_added_components",
        primaryKeys = { "userId", "compId" },
        indices = {@Index("userId"),@Index("compId")},
        foreignKeys = {
                @ForeignKey(entity = Player.class,
                        parentColumns = "uid",
                        childColumns = "userId"),
                @ForeignKey(entity = Components.class,
                        parentColumns = "cid",
                        childColumns = "compId")
        })
public class UserAddedComponents {

    public UserAddedComponents(){}

    public UserAddedComponents(int user, int component){
        userId=user;
        compId=component;
    }

    @ColumnInfo(name = "userId")
    public int userId;

    @ColumnInfo(name = "compId")
    public int compId;
}
