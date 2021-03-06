package com.example.catsproject.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreDao {
    @Insert
    void insertAll(Score... score);

    @Query("select * from score where userId=:userId")
    Score getScore(final int userId);

    @Query("update score set win=win+1 where userId=:userId")
    void incWins(final int userId);

    @Query("update score set lose=lose+1 where userId=:userId")
    void incLose(final int userId);

    @Delete
    void deleteAll(Score... s);

}
