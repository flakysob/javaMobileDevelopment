package com.flakysob.kiralaapp.Database;

import android.content.ContentValues;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.flakysob.kiralaapp.DAO.IKategoriDAO;
import com.flakysob.kiralaapp.DAO.ISiparisDAO;
import com.flakysob.kiralaapp.DAO.IUrunDAO;
import com.flakysob.kiralaapp.DAO.IUserDAO;
import com.flakysob.kiralaapp.Entity.KategoriEntity;
import com.flakysob.kiralaapp.Entity.SiparisEntity;
import com.flakysob.kiralaapp.Entity.UrunEntity;
import com.flakysob.kiralaapp.Entity.UserEntity;

@Database(entities = {UrunEntity.class, KategoriEntity.class, UserEntity.class, SiparisEntity.class},version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;
    public abstract IUserDAO getUserDAO();
    public abstract IKategoriDAO getKategoriDAO();
    public abstract IUrunDAO getUrunDAO();
    public abstract ISiparisDAO getSiparisDAO();

    private static  final String databaseName = "mobil101.db";

    public static AppDatabase getAppDatabase(Context context){
        if (appDatabase == null){
            appDatabase = Room.databaseBuilder(context, AppDatabase.class,databaseName)
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("kategori_adi","Konut");
                            db.insert("kategoritbl", OnConflictStrategy.IGNORE,contentValues);
                            contentValues.put("kategori_adi","Araç");
                            db.insert("kategoritbl", OnConflictStrategy.IGNORE,contentValues);
                            contentValues.put("kategori_adi","Teknoloji");
                            db.insert("kategoritbl", OnConflictStrategy.IGNORE,contentValues);
                            contentValues.put("kategori_adi","Moda");
                            db.insert("kategoritbl", OnConflictStrategy.IGNORE,contentValues);
                            contentValues.put("kategori_adi","Outdoor");
                            db.insert("kategoritbl", OnConflictStrategy.IGNORE,contentValues);
                            contentValues.put("kategori_adi","Anne-Bebek");
                            db.insert("kategoritbl", OnConflictStrategy.IGNORE,contentValues);
                            contentValues.put("kategori_adi","Mobilya");
                            db.insert("kategoritbl", OnConflictStrategy.IGNORE,contentValues);
                            contentValues.put("kategori_adi","Beyaz Eşya");
                            db.insert("kategoritbl", OnConflictStrategy.IGNORE,contentValues);
                        }
                        @Override
                        public void onOpen(@NonNull SupportSQLiteDatabase db) {
                            super.onOpen(db);
                            db.disableWriteAheadLogging();
                        }
                    })
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }
    public static void destroyInstance(){
        appDatabase = null;
    }
}
