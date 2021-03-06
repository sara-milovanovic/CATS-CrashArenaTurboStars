package com.example.catsproject.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserComponentsDao {
    @Insert
    void insertAll(UserComponents... users);

    @Query("SELECT * FROM components " +
            "INNER JOIN user_components " +
            "ON components.cid=user_components.compId " +
            "WHERE user_components.userId=:userId")
    List<Components> getComponentsForPlayer(final int userId);

    @Delete
    void deleteAll(UserComponents... u);

}
