package com.example.catsproject.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserAddedComponentsDao {
    @Insert
    void insertAll(UserAddedComponents... users);

    @Query("SELECT * FROM components " +
            "INNER JOIN user_added_components " +
            "ON components.cid=user_added_components.compId " +
            "WHERE user_added_components.userId=:userId")
    List<Components> getComponentsForPlayer(final int userId);

    @Delete
    void deleteAll(UserAddedComponents... components);

}
