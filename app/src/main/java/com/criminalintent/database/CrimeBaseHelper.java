package com.criminalintent.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.criminalintent.R;
import com.criminalintent.TypeUser;

import java.util.UUID;

//Clase que nos facilita la apertura y tratamiento de la base de datos
public class CrimeBaseHelper extends SQLiteOpenHelper {

    //Declaro la version de la DB
    private static final int VERSION = 1;

    //Instancio el nombre de la DB
    private static final String DATABASE_NAME = "crimeBase.db";

    //Definimos el constructor
    public CrimeBaseHelper(Context context){super(context,DATABASE_NAME,null,VERSION);}

    //Codigo dedicado para crear la base de datos inicial
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Sentencia de la DB para crear una tabla de crimes
        db.execSQL("create table " + CrimeDbSchema.CrimeTable.NAME + "(" +
            "_id integer primary key autoincrement, " +
            CrimeDbSchema.CrimeTable.Cols.UUID + ", "+
            CrimeDbSchema.CrimeTable.Cols.TITLE + ", "+
            CrimeDbSchema.CrimeTable.Cols.DATE + ", "+
            CrimeDbSchema.CrimeTable.Cols.SOLVED + ","+
            CrimeDbSchema.CrimeTable.Cols.SUSPECT + ")"

        );

        //Sentencia de la DB para crear una tabla de users
        db.execSQL("create table " + CrimeDbSchema.UserTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                CrimeDbSchema.UserTable.Cols.UUID + ", " +
                CrimeDbSchema.UserTable.Cols.TYPEUSER + ", " +
                CrimeDbSchema.UserTable.Cols.NAME + ", " +
                CrimeDbSchema.UserTable.Cols.EMAIL + ", " +
                CrimeDbSchema.UserTable.Cols.PASSWORD + ", " +
                CrimeDbSchema.UserTable.Cols.PHOTO + ")"

        );

        //Inserto un administrador para crear los primeros contactos
        db.execSQL("insert into " + CrimeDbSchema.UserTable.NAME + "(" +
                        CrimeDbSchema.UserTable.Cols.UUID + ", " +
                        CrimeDbSchema.UserTable.Cols.TYPEUSER + ", " +
                        CrimeDbSchema.UserTable.Cols.NAME + ", " +
                        CrimeDbSchema.UserTable.Cols.EMAIL + ", " +
                        CrimeDbSchema.UserTable.Cols.PASSWORD + ", " +
                        CrimeDbSchema.UserTable.Cols.PHOTO + ")" +

                    "values('" + UUID.randomUUID().toString() + "','" +
                                 TypeUser.TYPE_ADMIN.name() + "','" +
                                 "admin" + "','" +
                                 "admin" + "','" +
                                 "root" + "','" +
                                 R.drawable.eventolandia + "')");

    }

    //Código destinado a gestionar cualquier actualización
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
