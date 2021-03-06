package com.example.catsproject.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ComponentsDao {
    @Query("SELECT * FROM components")
    List<Components> getAll();

    @Query("SELECT * FROM components WHERE cid IN (:compIds)")
    List<Components> loadAllByIds(int[] compIds);

    @Query("SELECT * FROM components WHERE name LIKE :n")
    Components findByName(String n);

    @Insert
    void insertAll(Components... components);

    @Delete
    void delete(Components components);

    @Query("DELETE from components")
    public void deleteAll();

    @Query("UPDATE components set name=:n, power=:p,health=:h, energy=:e WHERE cid=:id")
    public void updateEntireComponent(int id,String n, int p, int h, int e);

    @Query("UPDATE components set name=:n WHERE cid=:id")
    public void updateName(int id, String n);

    @Query("DELETE from components where cid>:i")
    public void deleteGreaterThan(int i);
}
