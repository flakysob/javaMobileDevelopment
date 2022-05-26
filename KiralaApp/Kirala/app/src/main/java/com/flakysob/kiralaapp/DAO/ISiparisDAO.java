package com.flakysob.kiralaapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.flakysob.kiralaapp.Entity.SiparisEntity;
import com.flakysob.kiralaapp.Entity.UrunEntity;

import java.util.List;

@Dao
public interface ISiparisDAO {
    @Insert
    void insertSiparis(SiparisEntity siparis);

    @Update
    void updateSiparis(SiparisEntity siparis);

    @Delete
    void deleteSiparis(SiparisEntity siparis);

    @Query("SELECT * FROM siparistbl WHERE musteri_id=:musteriId")
    List<SiparisEntity> loadMusteriSiparis(long musteriId);

    @Query("SELECT * FROM siparistbl WHERE urun_id=:urunID")
    SiparisEntity loadByIlanID(long urunID);

    @Query("SELECT * FROM siparistbl WHERE satici_id=:saticiId")
    List<SiparisEntity> loadSaticiSiparis(long saticiId);

    @Query("SELECT urun_id FROM siparistbl WHERE onaylandi_mi=:onay AND musteri_id=:musteriId")
    List<Long> loadSiparisOnay(int onay,long musteriId);

    @Query("SELECT urun_id FROM siparistbl WHERE onaylandi_mi=:onay AND satici_id=:saticiID")
    List<Long> loadSiparisOnayKullanici(int onay,long saticiID);

    @Query("SELECT * FROM siparistbl")
    List<SiparisEntity> loadAllSiparis();

    @Query("SELECT * FROM siparistbl WHERE id=:id")
    SiparisEntity loadSiparisById(long id);
}
