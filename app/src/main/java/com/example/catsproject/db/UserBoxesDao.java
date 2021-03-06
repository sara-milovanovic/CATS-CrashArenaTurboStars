package com.example.catsproject.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserBoxesDao {
    @Insert
    void insertAll(UserBoxes... users);

    @Query("SELECT * FROM user_boxes " +
            "WHERE user_boxes.userId=:userId")
    List<UserBoxes> getComponentsForPlayer(final int userId);

    @Query("SELECT max(boxId) FROM user_boxes " +
            "WHERE user_boxes.userId=:userId")
    int getMaxId(final int userId);

    @Query("DELETE FROM user_boxes " +
            "WHERE user_boxes.userId=:userId")
    void removeForUser(final int userId);

    @Delete
    void deleteAll(UserBoxes... u);

}
