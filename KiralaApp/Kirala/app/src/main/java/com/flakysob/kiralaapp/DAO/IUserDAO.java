package com.flakysob.kiralaapp.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.flakysob.kiralaapp.Entity.UserEntity;

import java.util.List;

@Dao
public interface IUserDAO {
    @Insert
    void insertUser(UserEntity user);

    @Update
    void updateUser(UserEntity user);

    @Query("SELECT * FROM usertbl")
    List<UserEntity> loadAllUsers();

    @Query("SELECT * FROM usertbl WHERE id=:id")
    UserEntity loadUserById(long id);

    @Query("SELECT * FROM usertbl WHERE user_eposta=:eposta")
    UserEntity loadUserByEposta(String eposta);
}
