package com.flakysob.kiralaapp.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usertbl")
public class UserEntity {
    @PrimaryKey(autoGenerate =true)
    private long id;
    @ColumnInfo(name = "user_adisoyadi")
    private String isimsoyisim;
    @ColumnInfo(name ="user_eposta")
    private String eposta;
    @ColumnInfo(name ="user_telefon")
    private String telefon;

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsimsoyisim() {
        return isimsoyisim;
    }

    public void setIsimsoyisim(String isimsoyisim) {
        this.isimsoyisim = isimsoyisim;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }
}
