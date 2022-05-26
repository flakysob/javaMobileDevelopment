package com.flakysob.kiralaapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.flakysob.kiralaapp.Entity.KategoriEntity;
import com.flakysob.kiralaapp.Entity.UrunEntity;
import com.flakysob.kiralaapp.Entity.UserEntity;

import java.util.List;

@Dao
public interface IKategoriDAO {
    @Insert
    void insertKategori(KategoriEntity kategori);

    @Update
    void updateKategori(KategoriEntity kategori);

    @Delete
    void deleteKategori(KategoriEntity kategori);

    @Query("SELECT * FROM kategoritbl")
    List<KategoriEntity> loadAllKategori();

    @Query("SELECT * FROM kategoritbl WHERE id=:id")
    KategoriEntity loadKategoriById(long id);
}
