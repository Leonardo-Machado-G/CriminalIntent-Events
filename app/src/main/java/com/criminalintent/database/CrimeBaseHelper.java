package com.criminalintent.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

        //Sentencia de la DB para crear una tabla
        db.execSQL("create table " + CrimeDbSchema.CrimeTable.NAME + "(" +
            "_id integer primary key autoincrement, " +
            CrimeDbSchema.CrimeTable.Cols.UUID + ", "+
            CrimeDbSchema.CrimeTable.Cols.TITLE + ", "+
            CrimeDbSchema.CrimeTable.Cols.DATE + ", "+
            CrimeDbSchema.CrimeTable.Cols.SOLVED + ","+
            //añadimos la nueva columna en la tabla
            CrimeDbSchema.CrimeTable.Cols.SUSPECT +
        ")"

        );

        //Sentencia de la DB para crear una tabla
        db.execSQL("create table " + CrimeDbSchema.CrimeTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                CrimeDbSchema.CrimeTable.Cols.UUID + ", "+
                CrimeDbSchema.CrimeTable.Cols.TITLE + ", "+
                CrimeDbSchema.CrimeTable.Cols.DATE + ", "+
                CrimeDbSchema.CrimeTable.Cols.SOLVED + ","+
                CrimeDbSchema.CrimeTable.Cols.SUSPECT +
                ")"

        );

    }

    //Código destinado a gestionar cualquier actualización
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
