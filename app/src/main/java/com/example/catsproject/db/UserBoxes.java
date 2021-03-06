package com.example.catsproject.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "user_boxes",
        primaryKeys = { "userId", "boxId"},
        indices = {@Index("userId")},
        foreignKeys = {
                @ForeignKey(entity = Player.class,
                        parentColumns = "uid",
                        childColumns = "userId")

        })
public class UserBoxes {

    public UserBoxes(){}

    public UserBoxes(int user, long savedTime, int boxId){
        userId=user;
        this.savedTime=savedTime;
        this.boxId=boxId;
    }

    @ColumnInfo(name = "userId")
    public int userId;

    @ColumnInfo(name = "boxId")
    public int boxId;

    @ColumnInfo(name = "elapsedTime")
    public int elapsedTime;

    @ColumnInfo(name = "currentTime")
    public int currentTime;

    @ColumnInfo(name = "savedTime")
    public long savedTime;
}
