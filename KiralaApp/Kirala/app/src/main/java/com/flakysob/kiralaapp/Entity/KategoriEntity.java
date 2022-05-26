package com.flakysob.kiralaapp.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kategoritbl")
public class KategoriEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name ="kategori_adi")
    private String kategoriAdi;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }
}
