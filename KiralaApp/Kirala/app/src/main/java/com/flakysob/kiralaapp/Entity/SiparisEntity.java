package com.flakysob.kiralaapp.Entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "siparistbl", foreignKeys = {
        @ForeignKey( entity = UrunEntity.class,
        parentColumns = "id",
        childColumns = "urun_id",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE),
        @ForeignKey( entity = UserEntity.class,
                parentColumns = "id",
                childColumns = "musteri_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE),
        @ForeignKey( entity = UserEntity.class,
                parentColumns = "id",
                childColumns = "satici_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)
})
public class SiparisEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name="onaylandi_mi", defaultValue = "0")
    private int onaylandi_mi;
    @ColumnInfo(name="urun_id")
    private long urunId;
    @ColumnInfo(name = "musteri_id")
    private long musteriId;

    @ColumnInfo(name = "satici_id")
    private long saticiId;

    public long getMusteriId() {
        return musteriId;
    }

    public void setMusteriId(long musteriId) {
        this.musteriId = musteriId;
    }

    public long getSaticiId() {
        return saticiId;
    }

    public void setSaticiId(long saticiId) {
        this.saticiId = saticiId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOnaylandi_mi() {
        return onaylandi_mi;
    }

    public void setOnaylandi_mi(int onaylandi_mi) {
        this.onaylandi_mi = onaylandi_mi;
    }

    public long getUrunId() {
        return urunId;
    }

    public void setUrunId(long urunId) {
        this.urunId = urunId;
    }
}
