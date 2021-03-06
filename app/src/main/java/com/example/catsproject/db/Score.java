package com.example.catsproject.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "score",
        primaryKeys = { "userId" },
        indices = {@Index("userId")},
        foreignKeys = {
                @ForeignKey(entity = Player.class,
                        parentColumns = "uid",
                        childColumns = "userId")

        })
public class Score {

    public Score(){}

    public Score(int user){
        userId=user;
    }

    @ColumnInfo(name = "userId")
    public int userId;

    @ColumnInfo(name = "win")
    public int win;

    @ColumnInfo(name = "lose")
    public int lose;
}
