package com.example.catsproject.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlayerDao {
    @Query("SELECT * FROM player")
    List<Player> getAll();

    @Query("SELECT * FROM player WHERE uid IN (:userIds)")
    List<Player> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM player WHERE username LIKE :u AND " +
            "password LIKE :p LIMIT 1")
    Player findByName(String u, String p);

    @Query("SELECT player.uid FROM player WHERE username LIKE :u")
    int findByNameOnly(String u);

    @Query("SELECT player.username FROM player WHERE uid LIKE :id")
    String findById(int id);

    @Query("SELECT player.heart FROM player WHERE uid LIKE :u")
    int findHeart(int u);

    @Query("SELECT player.sword FROM player WHERE uid LIKE :u")
    int findSword(int u);

    @Query("SELECT player.thunder FROM player WHERE uid LIKE :u")
    int findThunder(int u);

    @Query("SELECT player.boxes FROM player WHERE uid LIKE :u")
    int findBoxes(int u);

    @Query("SELECT player.generateBox FROM player WHERE uid LIKE :u")
    int toGenerate(int u);

    @Query("SELECT player.password FROM player WHERE uid LIKE :u")
    String findPasswordById(Integer u);

    @Query("UPDATE player set heart=:h WHERE uid LIKE :u")
    void updateHeart(Integer h, Integer u);

    @Query("UPDATE player set sword=:s WHERE uid LIKE :u")
    void updateSword(Integer s, Integer u);

    @Query("UPDATE player set thunder=:t WHERE uid LIKE :u")
    void updateThunder(Integer t, Integer u);

    @Query("UPDATE player set boxes=boxes+1 WHERE uid LIKE :u")
    void incBoxes(Integer u);

    @Query("UPDATE player set generateBox=0 WHERE uid LIKE :u")
    void falseGenerate(Integer u);

    @Query("UPDATE player set generateBox=1 WHERE uid LIKE :u")
    void trueGenerate(Integer u);

    @Insert
    void insertAll(Player... users);

    @Delete
    void delete(Player user);

    @Query("DELETE from player")
    public void deleteAll();

    @Query("UPDATE player set heart=:h, sword=:s, thunder=:t WHERE uid LIKE :id")
    void updateAtributesHSTId(Integer h, Integer s, Integer t, Integer id);
}
