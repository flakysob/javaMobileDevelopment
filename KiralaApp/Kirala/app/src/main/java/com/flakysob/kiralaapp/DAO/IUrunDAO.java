package com.flakysob.kiralaapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.flakysob.kiralaapp.Entity.UrunEntity;
import com.flakysob.kiralaapp.Entity.UserEntity;

import java.util.List;

@Dao
public interface IUrunDAO {
    @Insert
    void insertUrun(UrunEntity urun);

    @Update
    void updateUrun(UrunEntity urun);

    @Delete
    void deleteUrun(UrunEntity urun);

    @Query("SELECT * FROM uruntbl WHERE kiralandi_mi=0")
    List<UrunEntity> loadNotKiralaUruns();

    @Query("SELECT * FROM uruntbl WHERE kategori_id=:kategoriID AND kiralandi_mi=0")
    List<UrunEntity> loadUrunByKategori(long kategoriID);

    @Query("SELECT * FROM uruntbl WHERE kategori_id=:kategoriID AND kiralandi_mi=0 AND NOT user_id=:userID")
    List<UrunEntity> loadUrunByKategoriAndUserID(long kategoriID,long userID);

    @Query("SELECT * FROM uruntbl")
    List<UrunEntity> loadAllUruns();

    @Query("SELECT * FROM uruntbl WHERE id=:id")
    UrunEntity loadUrunById(long id);

    @Query("SELECT * FROM uruntbl WHERE user_id=:id")
    List<UrunEntity> loadAllUrunByUserID(long id);

    @Query("SELECT user_id FROM uruntbl WHERE id=:id")
    long userIDbyUrun(long id);
}
