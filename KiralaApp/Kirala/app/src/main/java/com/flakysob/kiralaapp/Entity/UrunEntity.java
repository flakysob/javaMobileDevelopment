package com.flakysob.kiralaapp.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "uruntbl",foreignKeys = {
        @ForeignKey(entity = UserEntity.class,
        parentColumns = "id",
        childColumns = "user_id",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = KategoriEntity.class,
        parentColumns = "id",
        childColumns = "kategori_id",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE)}
)
public class UrunEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name="urun_baslik")
    private String urunBaslik;
    @ColumnInfo(name = "urun_resim")
    private String urunResim;
    @ColumnInfo(name = "kategori_id")
    private long kategoriId;
    @ColumnInfo(name = "user_id")
    private long userId;
    @ColumnInfo(name = "kiralandi_mi", defaultValue = "0")
    private int kiralandiMi;
    @ColumnInfo(name="urun_fiyat")
    private int Fiyat;
    @ColumnInfo(name="urun_aciklama")
    private String Aciklama;

    public String getUrunResim() {
        return urunResim;
    }

    public void setUrunResim(String urunResim) {
        this.urunResim = urunResim;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrunBaslik() {
        return urunBaslik;
    }

    public void setUrunBaslik(String urunBaslik) {
        this.urunBaslik = urunBaslik;
    }

    public long getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(long kategoriId) {
        this.kategoriId = kategoriId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getKiralandiMi() {
        return kiralandiMi;
    }

    public void setKiralandiMi(int kiralandiMi) {
        this.kiralandiMi = kiralandiMi;
    }

    public int getFiyat() {
        return Fiyat;
    }

    public void setFiyat(int fiyat) {
        Fiyat = fiyat;
    }

    public String getAciklama() {
        return Aciklama;
    }

    public void setAciklama(String aciklama) {
        Aciklama = aciklama;
    }
}
