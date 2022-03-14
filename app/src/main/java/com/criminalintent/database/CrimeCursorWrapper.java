package com.criminalintent.database;
import android.database.Cursor;
import android.database.CursorWrapper;
import com.criminalintent.Crime;
import java.util.Date;
import java.util.UUID;

//Clase creada para empaquetar los métodos de tratamiento del cursor
//CursorWrapper nos permite envolver la clase Cursor y añadirle nuevos metodos
public class CrimeCursorWrapper extends CursorWrapper {

    //Definos el constructor
    public CrimeCursorWrapper(Cursor cursor) {super(cursor);}

    //Añadimos el metodo getCrime que extraerá los datos de las columnas
    public Crime getCrime(){

        //Obtenemos el contenido de las columnas
        String uuidString = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SUSPECT));

        //Ahora vamos a crear el objeto Crime a devolver
        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        crime.setSuspect(suspect);
        return crime;

    }

}
